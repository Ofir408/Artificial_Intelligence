import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ofir Ben Shoham. Class that implements Naive Base algorithm.
 *
 */
public class NaiveBayes extends AbstractAlgorithm {

	public NaiveBayes(List<FeaturesAndTag> trainingList) {
		super(trainingList);
	}

	// predict the tag if inputFeatures.
	@Override
	protected void predict(FeaturesAndTag inputFeatures) {
		double bestProb = -100000;
		String bestTag = "";

		List<String> possibleTags = getPossibleTagOptions();
		for (String tagValue : possibleTags) {
			double tempResult = calcAllFeaturesProb(inputFeatures, inputFeatures.getTagKey(), tagValue)
					* tagOccuranceCounter(tagValue);
			System.out.println("tagOccuranceCounter(tagValue) is: " + tagOccuranceCounter(tagValue));
			System.out.println("temp result is: " + tempResult);
			if (tempResult > bestProb) {
				System.out.println("here");
				bestProb = tempResult;
				bestTag = tagValue;
			}
		}
		// set the tag that was predicted
		inputFeatures.setTag(bestTag);
	}

	// return the valueOccuranceCounter of valueToSearch with key.
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

	// return the tagOccuranceCounter of tagName on the training set.
	private int tagOccuranceCounter(String tagName) {
		System.out.println("tag name is: " + tagName);
		int counter=0; 
		for (FeaturesAndTag f : trainingList)
			if (tagName.equals(f.getTag()))
					counter ++; 
		System.out.println("Counter is: " + counter);
		return counter; 
	}

	// get Possible Tag Options from the training list.
	private List<String> getPossibleTagOptions() {
		List<String> tags = new ArrayList<>();
		for (FeaturesAndTag f : trainingList) {
			String currentTag = f.getTag();
			if (!tags.contains(currentTag))
				tags.add(currentTag);
		}
		return tags;
	}

	// return the result of P(a | Tag)
	private double calcProbability(String aKey, String a, String tagKey, String tagValue, int numOfFeatureValue) {
		double theSame = 0.0;
		double notTheSame = 0.0;
		for (FeaturesAndTag f : trainingList) {
			Map<String, String> currentMap = f.getFeatures();
			if (currentMap.containsKey(aKey) && f.getTagKey().equals(tagKey))
				if (currentMap.get(aKey).equals(a))
					if (f.getTag().equals(tagValue)) {
						theSame++;
					} else {
						notTheSame++;
					}
		}
		System.out.println("The same:" + String.valueOf(theSame) + ", Not the same: " + String.valueOf(notTheSame) );

		double total = theSame + notTheSame;
		if (total == 0) {
			System.out.println("total in NaiveBase : calcProbability() is zero");
			return 1;
		}
		double temp = theSame / total;

		return (temp + 1) / (tagOccuranceCounter(tagValue) + numOfFeatureValue);
		//return temp;
	}

	// return the result of P(f1, f2 ... , Fn | Tag)
	// F1... Fn are the features, Tag is possible tag.
	/// In our case P(f1, f2 ... , Fn | Tag) = P(f1 | Tag) * ... * P(Fn | Tag).
	private double calcAllFeaturesProb(FeaturesAndTag inputFeaturesAndTag, String tagKey, String tagValue) {
		double probSum = 1.0;

		Map<String, String> featureAndValue = inputFeaturesAndTag.getFeatures();
		for (Map.Entry<String, String> entry : featureAndValue.entrySet()) {
			String featureKey = entry.getKey();
			String featureValue = entry.getValue();
			int numOfFeatureValue = this.valueOccuranceCounter(featureKey, featureValue);
			probSum *= calcProbability(featureKey, featureValue, tagKey, tagValue, numOfFeatureValue);
		}
		System.out.println("probSum is: " + probSum);
		return probSum;
	}
}
