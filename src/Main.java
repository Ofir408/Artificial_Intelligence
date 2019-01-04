import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputTextReader inputTextReaderTrain = new InputTextReader("train.txt");
		InputTextReader inputTextReaderTest = new InputTextReader("test.txt");

		List<FeaturesAndTag> trainingList = inputTextReaderTrain.getDataFromFile();
		List<FeaturesAndTag> testList = inputTextReaderTest.getDataFromFile();
		
		List<FeaturesAndTag> KNNrealTagList = Main.predictOnTest(trainingList, cloneList(testList), new Knn(trainingList, 5));
		List<FeaturesAndTag> DTrealTagList = Main.predictOnTest(trainingList, cloneList(testList), new DecisionTree(trainingList));
		List<FeaturesAndTag> NBrealTagList = Main.predictOnTest(trainingList, cloneList(testList), new NaiveBayes(trainingList));

		System.out.println(" NB acc is: " + AccuracyCalculator.calcAccuracy(testList, NBrealTagList));
		System.out.println(" KNN acc is: " + AccuracyCalculator.calcAccuracy(testList, KNNrealTagList));
		System.out.println(" DT acc is: " + AccuracyCalculator.calcAccuracy(testList, DTrealTagList));
		
		ResultsWriter.writeResults(DTrealTagList, KNNrealTagList, NBrealTagList, testList);
	}
	
	private static List<FeaturesAndTag> predictOnTest(List<FeaturesAndTag>  trainingList, List<FeaturesAndTag> testList, AbstractAlgorithm a) {
		List<FeaturesAndTag> realTagList = new ArrayList<>();
		for (FeaturesAndTag f : testList)
			try {
				realTagList.add((FeaturesAndTag) f.clone());
			} catch (CloneNotSupportedException e) {
				realTagList = trainingList;
			}
		
		for (FeaturesAndTag f : testList)
			f.setTag("");

		
		a.predictOnTest(testList);
		for (FeaturesAndTag f : testList)
			System.out.println("TAG is: " + f.getTag());

		return testList; 
	}
	
	// clone List<FeaturesAndTag> listToClone
	private static List<FeaturesAndTag> cloneList(List<FeaturesAndTag> listToClone) {
		List<FeaturesAndTag> toReturn = new ArrayList<>(); 
		for (FeaturesAndTag f : listToClone) {
			try {
				toReturn.add((FeaturesAndTag) f.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

}
