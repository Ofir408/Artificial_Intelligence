import java.io.BufferedReader;
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

	public List<FeaturesAndTag> getDataFromFile() throws IOException {
		List<FeaturesAndTag> listToReturn = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
			String firstLine = br.readLine();
			String currentLine = null;
			List<String> featuresAndTagNames = getFeaturesAndTag(firstLine);

			while ((currentLine = br.readLine()) != null) {
				FeaturesAndTag currentFeatureAndTag = getDataFromLine(featuresAndTagNames, currentLine);
				listToReturn.add(currentFeatureAndTag);
			}
		}
		return listToReturn;
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
		List<String> featuresAndTagValues = getFeaturesAndTag(currentLine);
		return getDataHelper(featuresAndTagNames, featuresAndTagValues);
	}

	private FeaturesAndTag getDataHelper(List<String> featuresAndTagNames, List<String> featuresAndTagValues) {
		FeaturesAndTag featuresAndTag = new FeaturesAndTag();
		int length = featuresAndTagNames.size();
		if (length < 1) {
			System.out.println("Problem into InputTextReader, getDataFromLine()");
			return null;
		}
		featuresAndTag.setTagKey(featuresAndTagNames.get(length - 1));
		featuresAndTag.setTag(featuresAndTagValues.get(length - 1));
		for (int i = 0; i < length - 1; i++) {
			String currentFeatureName = featuresAndTagNames.get(i);
			String currentFeatureValue = featuresAndTagValues.get(i);
			// add to the map.
			featuresAndTag.addFeature(currentFeatureName, currentFeatureValue);
		}
		return featuresAndTag;
	}
}
