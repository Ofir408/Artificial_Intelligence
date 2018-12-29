import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ofir Ben Shoham Implementation of Decision Tree algorithm based on
 *         ID3.
 */
public class DecisionTree extends AbstractAlgorithm {
	private static Attribute attribute;

	public DecisionTree(List<FeaturesAndTag> trainingList) {
		super(trainingList);
		attribute = createTreeFromTrainingSet();
	}

	@Override
	protected void predict(FeaturesAndTag inputFeatures) {
		// convert to the Attribute key format
		List<String> keys = new ArrayList<>();
		Map<String, String> inputFeaturesMap = inputFeatures.getFeatures();
		for (Map.Entry<String, String> entry : inputFeaturesMap.entrySet()) {
			keys.add(getAttrKeyFormat(entry.getKey(), entry.getValue()));
		}

		try {
			Attribute currentAttribute = (Attribute) attribute.clone();
			for (String k : keys) {
				if (currentAttribute.isLeaf()) {
					String tag = currentAttribute.getLeafValue();
					inputFeatures.setTag(tag);
					return;
				}
				currentAttribute = (Attribute) currentAttribute.getNextAttribute(k).clone();
			}
		} catch (CloneNotSupportedException c) {
		}

	}

	private String getAttrKeyFormat(String key, String value) {
		return key + "=" + value;
	}

	private double entrofy(String key) {
		double value = 0.0;

	}

	private List<String> getPossibleTagOptions() {
		List<String> tags = new ArrayList<>();
		for (FeaturesAndTag f : trainingList) {
			String currentTag = f.getTag();
			if (!tags.contains(currentTag))
				tags.add(currentTag);
		}
		return tags;
	}

	private int valueOccuranceCounter(String key, String valueToSearch) {
		int counter = 0;
		for (FeaturesAndTag f : trainingList) {
			Map<String, String> currentMap = f.getFeatures();
			if (currentMap.containsKey(key) && currentMap.get(key).equals(valueToSearch)) {
				counter++;
			}
		}
		return counter;
	}

	private List<FeaturesAndTag> getPartialListAccordingValue(String key, String valueToSearch) {
		List<FeaturesAndTag> partialList = new ArrayList<>();
		for (FeaturesAndTag f : trainingList) {
			Map<String, String> currentMap = f.getFeatures();
			if (currentMap.containsKey(key) && currentMap.get(key).equals(valueToSearch)) {
				partialList.add(f);
			}
		}
		return partialList;
	}

	private Attribute createTreeFromTrainingSet() {

	}

}
