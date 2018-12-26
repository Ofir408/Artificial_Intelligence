import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Ofir Ben Shoham This class takes care to read the data from the input
 *         text file.
 */
public class InputTextReader {

	private String fileName; // the name of input file.

	// constructor.
	public InputTextReader(String name) {
		this.fileName = name;
	}

	// returns list of string, each one is a feature from the first line in the
	// training file
	// and the last one is the classification.
	private List<String> getFeaturesAndTag(String firstLine) {
		String seperator = "\t";
		return Arrays.asList(firstLine.split(seperator));
	}

	// returns FeaturesAndTag object, that derives from currentLine.
	private FeaturesAndTag getDataFromLine(List<String> featuresAndTagNames, String currentLine) {
		// TODO - Get List<String> featuresAndTagValues from currentLine, 
		// call to getDataFromLine(.. )  func and returns his result.
		
	}

	private FeaturesAndTag getDataFromLine(List<String> featuresAndTagNames, List<String> featuresAndTagValues) {
		FeaturesAndTag featuresAndTag = new FeaturesAndTag();
		int length = featuresAndTagNames.size();
		if (length < 1) {
			System.out.println("Problem into InputTextReader, getDataFromLine()");
			return null;
		}
		featuresAndTag.setTag(featuresAndTagValues.get(length - 1));
		for (int i = 0; i < length - 1; i++) {
			String currentFeatureName = featuresAndTagNames.get(i);
			String currentFeatureValue = featuresAndTagValues.get(i);
			// add to the map.
			featuresAndTag.addFeature(currentFeatureName, currentFeatureValue);
		}
		return featuresAndTag;
	}

	// read from the input file and returns GameInitialDetails class that has
	// the initial details.
	public GameInitialDetails getInitialGameDetails() {
		int algorithemNumber = 0;
		int boardSize = 0;
		String initialBoardState = null;

		try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {

			algorithemNumber = Integer.parseInt(br.readLine()); // Algorithm
																// number.
			boardSize = Integer.parseInt(br.readLine());
			initialBoardState = br.readLine();
		} catch (NumberFormatException n) {
			System.out.println("Error while reading into InputTextReader: getInitialGameDetails()");
		} catch (FileNotFoundException f) {
			if (!fileName.contains("src")) {
				this.fileName = "src/" + this.fileName;
				return getInitialGameDetails(); // check if the user put the
												// file under src library.
			}
			System.out.println("File not found exception into InputTextReader: getInitialGameDetails()");
		} catch (IOException i) {
			System.out.println("IOException  into InputTextReader: getInitialGameDetails()");
		}
		return new GameInitialDetails(algorithemNumber, boardSize, initialBoardState);
	}
}
