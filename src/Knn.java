import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author Ofir Ben Shoham. Implements KNN algorithm.
 *
 */
public class Knn extends AbstractAlgorithm {

	private int k;

	// Constructor.
	public Knn(List<FeaturesAndTag> trainingList, int k) {
		super(trainingList);
		this.k = k;
	}
	
	// FeaturesWithPrices is a class that extends FeaturesAndTag and contains the price of this example.
	private class FeaturesWithPrices extends FeaturesAndTag implements Comparable<FeaturesWithPrices>{
		private int cost; 
		
		public FeaturesWithPrices(FeaturesAndTag f, int cost) {
			super(f.getFeatures(), f.getTag());
			this.cost = cost;
		}
		
		@Override
		public int compareTo(FeaturesWithPrices o) {
			return cost - o.cost;
		}
	}

	@Override
	protected void predict(FeaturesAndTag inputFeatures) {

		List<FeaturesWithPrices> withPricesList = getPriceMap(inputFeatures);
		Collections.sort(withPricesList);
		

		// get the K elements with the lowest price.
		List<String> tagOfLowestPrices = new LinkedList<>(); // Addition in O(1)
																
		//System.out.println("sorted_map is: " + sorted_map);
		for (int i = 0; i < k; i++) {
			FeaturesAndTag value = withPricesList.get(i);
			tagOfLowestPrices.add(value.getTag());
		}
		//System.out.println("size of tagOfLowestPrices is: " + tagOfLowestPrices.size());
		String predTag = this.getMostFrequentFromList(tagOfLowestPrices);
		inputFeatures.setTag(predTag);
	}


	// return  List<FeaturesWithPrices>, where each one is FeaturesAndTag + his cost.
	private List<FeaturesWithPrices> getPriceMap(FeaturesAndTag inputFeatures) {
		 List<FeaturesWithPrices>  withPricesList = new LinkedList<>();
		int currentRowPrice;
		for (FeaturesAndTag f : trainingList) {
			currentRowPrice = getPriceOfRow(f, inputFeatures);
			FeaturesWithPrices fWithPrice = new FeaturesWithPrices(f, currentRowPrice);
			withPricesList.add(fWithPrice);
		}
		return withPricesList;
	}

	private int getPriceOfRow(FeaturesAndTag fromTrainingSet, FeaturesAndTag inputFeatures) {
		int totalPrice = 0;
		Map<String, String> featuresMap = fromTrainingSet.getFeatures();
		Map<String, String> compareToMap = inputFeatures.getFeatures();

		Set<String> featuresList = featuresMap.keySet();
		for (String s : featuresList) {
			if (compareToMap.containsKey(s)) {
				totalPrice += getPrice(featuresMap.get(s), compareToMap.get(s));
			}
		}
		return totalPrice;
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

	// return 1 if different, 0 if equals.
	private <T> int getPrice(T first, T second) {
		if (first.equals(second))
			return 0; // equals, the distance is zero
		return 1; // different, the distance is 1.
	}
}
