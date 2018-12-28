import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NaiveBaseTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputTextReader inputTextReaderTrain = new InputTextReader("train.txt");
		InputTextReader inputTextReaderTest = new InputTextReader("test.txt");

		List<FeaturesAndTag> trainingList = inputTextReaderTrain.getDataFromFile();
		List<FeaturesAndTag> testList = inputTextReaderTest.getDataFromFile();
		List<FeaturesAndTag> realTagList = inputTextReaderTest.getDataFromFile();
		for (FeaturesAndTag f : testList)
			f.setTag("");

		AbstractAlgorithm a = new NaiveBase(trainingList);
		a.predictOnTest(testList);
		for (FeaturesAndTag f : testList)
			System.out.println("TAG is: " + f.getTag());
		
		double acc = AccuracyCalculator.calcAccuracy(realTagList, testList);
		System.out.println(" acc is: " + acc);
	}

}
