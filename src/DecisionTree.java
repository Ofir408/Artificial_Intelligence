import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 
 * @author Ofir Ben Shoham Implementation of Decision Tree algorithm based on
 *         ID3.
 */
public class DecisionTree extends AbstractAlgorithm {
	private static Attribute attribute;

	// constructor.
	public DecisionTree(List<FeaturesAndTag> trainingList) {
		super(trainingList);
		attribute = createTreeFromTrainingSet();
		// write the Decision tree to a file
		TreeWriter.writeToFile(attribute);
	}

	// predict the tag of inputFeatures.
	@Override
	protected void predict(FeaturesAndTag inputFeatures) {
		// convert to Attribute key format
		List<String> keys = new ArrayList<>();
		Map<String, String> inputFeaturesMap = inputFeatures.getFeatures();
		for (Map.Entry<String, String> entry : inputFeaturesMap.entrySet()) {
			keys.add(getAttrKeyFormat(entry.getKey(), entry.getValue()));
		}

		try {
			Attribute currentAttribute = (Attribute) attribute.clone();
			List<String> keyDuplicate = new ArrayList<>(keys);

			while (!keyDuplicate.isEmpty()) {
				if (currentAttribute.isLeaf()) {
					String tag = currentAttribute.getLeafValue();
					System.out.println("set_the_tag");
					inputFeatures.setTag(tag);
					return;
				}
				keys.clear();
				keys = new ArrayList<>(keyDuplicate);

				for (String k : keys) {
					if (keyDuplicate.contains(k) && currentAttribute.isExistsInMap(k)) {
						keyDuplicate.remove(k);

						currentAttribute = (Attribute) currentAttribute.getNextAttribute(k);
						if (currentAttribute.isLeaf()) {
							String tag = currentAttribute.getLeafValue();
							System.out.println("set_the_tag");
							inputFeatures.setTag(tag);
							return;
						}
						break;
					}
				}
			}
		} catch (CloneNotSupportedException c) {
		}

	}

	// return the format of attribute key & value.
	private String getAttrKeyFormat(String key, String value) {
		return key + "=" + value;
	}

	// Choose the next Attribute with the best Gain value.
	private String chooseAttribute(List<FeaturesAndTag> updatedExamples, List<String> remaningFeaturesName,
			String lastChosenFeature) {
		double maxGain = -10000.0;
		String maxGainFeature = null;
		for (String currRemaningFeature : remaningFeaturesName) {
			double currentGainValue = gain(updatedExamples, lastChosenFeature, currRemaningFeature);
			System.out.println(
					"currRemaningFeature is: " + currRemaningFeature + ", currentGainValue: " + currentGainValue);
			if (currentGainValue > maxGain) {
				maxGain = currentGainValue;
				maxGainFeature = currRemaningFeature;
			}
		}
		return maxGainFeature;
	}

	// get the Gain
	// Gain (Decision | otherAttrKey)
	private double gain(List<FeaturesAndTag> updatedExamples, String decisionKey, String otherAttrKey) {
		double gainResult = 0.0;
		System.out.println("decisionKey is: " + decisionKey);
		gainResult += entrofy(updatedExamples, decisionKey);
		System.out.println("gainResult is: " + gainResult);

		List<String> otherAttrPossibleValues = getPossibleValueOptions(updatedExamples, otherAttrKey);
		int updatedExamplesLength = updatedExamples.size();
		for (String otherCurrentVal : otherAttrPossibleValues) {
			double currProb = (double) valueOccuranceCounter(updatedExamples, otherAttrKey, otherCurrentVal)
					/ updatedExamplesLength;
			List<FeaturesAndTag> updated = getPartialListAccordingValue(updatedExamples, otherAttrKey, otherCurrentVal);
			double currEntropy = entrofy(updated, decisionKey);
			gainResult -= currProb * currEntropy;
		}
		return gainResult;
	}

	// calculate Entrofy(Decision | OtherAttr = something), when the
	// updatedExamples list is the features that
	// according OtherAttr = something.
	private double entrofy(List<FeaturesAndTag> updatedExamples, String decisionKey) {
		double result = 0.0;
		List<String> keyPossibleValues = this.getPossibleValueOptions(updatedExamples, decisionKey);
		for (String currentValue : keyPossibleValues) {
			result += stepInEntropy(updatedExamples, decisionKey, currentValue);
		}
		return result;
	}

	// return log2(num)
	private double log2(double num) {
		return Math.log(num) / Math.log(2.0);
	}

	// make a step in Entropy calculation and return the result.
	private double stepInEntropy(List<FeaturesAndTag> updatedExamples, String key, String value) {
		// calculate the probability of each option.
		double prob = (double) valueOccuranceCounter(updatedExamples, key, value) / updatedExamples.size();
		double result = -prob * log2(prob);
		return result;
	}

	// getPossibleValueOptions of key on updateExamples.
	private List<String> getPossibleValueOptions(List<FeaturesAndTag> updateExamples, String key) {
		List<String> valuesList = new ArrayList<>();
		for (FeaturesAndTag f : updateExamples) {
			Map<String, String> features = f.getFeatures();
			if (features.containsKey(key)) {
				String value = features.get(key);
				if (!valuesList.contains(value))
					valuesList.add(value);
			} else {
				// check if this is the TAG
				if (f.getTagKey().equals(key)) {
					String value = f.getTag();
					if (!valuesList.contains(value))
						valuesList.add(value);
				}
			}
		}
		return valuesList;
	}

	// return the Occurance number of valueToSearch in updateExamples.
	private int valueOccuranceCounter(List<FeaturesAndTag> updateExamples, String key, String valueToSearch) {
		int counter = 0;
		for (FeaturesAndTag f : updateExamples) {
			Map<String, String> currentMap = f.getFeatures();
			if (f.getTagKey().equals(key)) {
				if (f.getTag().equals(valueToSearch))
					counter++;
			} else if (currentMap.containsKey(key) && currentMap.get(key).equals(valueToSearch)) {
				counter++;
			}
		}
		return counter;
	}

	// getPartialListAccordingValue of values the features that has key & valueToSearch
	private List<FeaturesAndTag> getPartialListAccordingValue(List<FeaturesAndTag> updatedList, String key,
			String valueToSearch) {
		List<FeaturesAndTag> partialList = new ArrayList<>();
		for (FeaturesAndTag f : updatedList) {
			Map<String, String> currentMap = f.getFeatures();
			if (currentMap.containsKey(key) && currentMap.get(key).equals(valueToSearch)) {
				partialList.add(f);
			}
		}
		return partialList;
	}

	private Attribute createTreeFromTrainingSet() {
		// get the features name.
		List<String> featuresNames = new ArrayList<>();
		List<FeaturesAndTag> trainingExamples = new ArrayList<>();
		if (!trainingList.isEmpty()) {
			Set<String> names = trainingList.get(0).getFeatures().keySet();
			for (String name : names)
				featuresNames.add(name);
		}

		// clone the training list, since we don't want to change it.
		for (FeaturesAndTag f : trainingList) {
			try {
				FeaturesAndTag featuresAndTag = (FeaturesAndTag) f.clone();
				featuresAndTag.setTagKey(f.getTagKey());
				trainingExamples.add(featuresAndTag);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return createTreeRecursive(trainingExamples, featuresNames, trainingExamples.get(0).getTagKey(), "");

	}
	
	// getMostFrequency tag in examples.
	private String getMostFreq(List<FeaturesAndTag> examples) {
		System.out.println("getMostFreq wad called");
		List<String> tags = new ArrayList<>();
		for (FeaturesAndTag f : examples) {
			tags.add(f.getTag());
		}
		String result = getMostFrequentFromList(tags);
		return result;
	}
	
	// given a list in length K, returns the most frequent value.
	private <T> T getMostFrequentFromList(List<T> list) {
		Map<T, Integer> map = new HashMap<>();

		for (T t : list) {
			Integer val = map.get(t);
			map.put(t, val == null ? 1 : val + 1);
		}
		Entry<T, Integer> max = null;
		for (Entry<T, Integer> e : map.entrySet()) {
			if (max == null || e.getValue() > max.getValue())
				max = e;
		}
		return max.getKey();
	}

	// the recursive function that creates the Decision tree.
	private Attribute createTreeRecursive(List<FeaturesAndTag> examples, List<String> featureNames,
			String lastChosenFeature, String fatherTag) {
		// stop conditions
		if (examples.size() == 0)
			return new Attribute(new HashMap<>(), fatherTag);
		if (haveSameTag(examples) && !examples.isEmpty())
			return new Attribute(new HashMap<>(), examples.get(0).getTag());

		if (featureNames.isEmpty()) {
			// make prediction according the most frequent tag between the
			// examples.
			System.out.println("featureNames.isEmpty");
			String tag = getMostFreq(examples);
			return new Attribute(new HashMap<>(), tag);
		}

		// otherwise:
		String bestGainFeature = chooseAttribute(examples, featureNames, lastChosenFeature);
		// lastChosenFeature = bestGainFeature;
		Attribute tree = new Attribute(new HashMap<>());
		System.out.println("bestGainFeature is: " + bestGainFeature);
		
		List<String> possibleValueOfFeature = getPossibleValueOptions(examples, bestGainFeature);
		List<String> fn = new ArrayList<String>(featureNames);
				
		for (String value : possibleValueOfFeature) {
			featureNames = new ArrayList<String>(fn);
			if (value.equals("crew")) {
				System.out.println("...");
			}

			List<FeaturesAndTag> updatedExamples = getPartialListAccordingValue(examples, bestGainFeature, value);
			featureNames.remove(bestGainFeature);
			Attribute subTree = createTreeRecursive(updatedExamples, featureNames, lastChosenFeature, getMostFreq(examples));
			System.out.println("value is: " + value);
			tree.addToMap(bestGainFeature, value, subTree);
			System.out.println("Added to map");
		}
		return tree;
	}

	// return true if the whole input examples have the same classification.
	private boolean haveSameTag(List<FeaturesAndTag> examples) {
		boolean sameTag = true;
		String tagForSearch = "";
		if (!examples.isEmpty())
			tagForSearch = examples.get(0).getTag();

		for (FeaturesAndTag f : examples) {
			if (!tagForSearch.equals(f.getTag())) {
				sameTag = false;
				break;
			}
		}

		return sameTag;
	}

}
