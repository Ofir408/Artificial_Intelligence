import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Ofir Ben Shoham. Represents a line in the train.txt file, that
 *         contains features & their values, and the TAG of this example.
 *
 */
public class FeaturesAndTag {
	@Override
	protected Object clone() throws CloneNotSupportedException {
		FeaturesAndTag f = new FeaturesAndTag(features, tag);
		f.setTagKey(tagKey);
		return f;
	}

	// members
	private Map<String, String> features = new LinkedHashMap<String, String>();
	private String tag, tagKey; 

	// constructors.
	public FeaturesAndTag() {
	}

	public FeaturesAndTag(Map<String, String> features, String tag) {
		this.features = features;
		this.tag = tag;
	}

	// add new feature & his value to the map.
	public void addFeature(String newFeature, String featureValue) {
		if (!this.features.containsKey(newFeature)) {
			this.features.put(newFeature, featureValue);
		}
	}

	// getters & setters
	public Map<String, String> getFeatures() {
		return features;
	}

	// getters & setters.
	public void setFeatures(Map<String, String> features) {
		this.features = features;
	}

	public String getTag() {
		return tag;
	}
	
	public String getTagKey() {
		return tagKey;
	}
	
	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
