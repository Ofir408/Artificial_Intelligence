import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ofir Ben Shoham
 * Represents an attribute in the Decision tree.
 */
public class Attribute {
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Attribute(new HashMap<String, Attribute>(featuresToAttributeMap));
	}

	private String leafValue; // if not a leaf, not a null.
	// Each string in that format: <attribute_name>=<attribute_value>
	private Map<String, Attribute> featuresToAttributeMap = new HashMap<>();

	
	public Attribute(Map<String, Attribute> featuresToAttributeMap) {
		this.leafValue = null;
		this.featuresToAttributeMap = featuresToAttributeMap;
	}
	
	public String getLeafValue() {
		if (!isLeaf())
			System.out.println("Not a leaf");
		return leafValue;
	}
	public boolean isLeaf() {
		return leafValue != null; 
	}
	
	public Attribute getNextAttribute(String key) {
		if (featuresToAttributeMap.containsKey(key))
			return featuresToAttributeMap.get(key);
		System.out.println("key: " + key + "  not found");
		return null;
	}
	
	

}
