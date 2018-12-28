import java.util.List;

/**
 * 
 * @author Ofir Ben Shoham.
 * Responsible to calculate the accuracy of the algorithm.
 *
 */
public class AccuracyCalculator {
	
	public static double calcAccuracy(List<FeaturesAndTag> realValues, List<FeaturesAndTag> predValues) {
		double currentPredCounter = 0; 
		int commonLength = realValues.size();
		if (commonLength != predValues.size())
			System.out.println("ERROR IN AccuracyCalculator:calcAccuracy");
		for (int i=0; i<commonLength; i++) {
			FeaturesAndTag currentReal = realValues.get(i);
			FeaturesAndTag currentPred = predValues.get(i);
			if (currentPred.getTag().equals(currentReal.getTag()))
				currentPredCounter ++; 
		}
		return (double) currentPredCounter / commonLength;
	}

}
