import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 
 * @author Ofir Ben Shoham Write the result (predicted tags) to a text file.
 */
public class ResultsWriter {
	private static final String fileName = "output.txt";
	private static final String seperator = "\t";
	private static final String lineSeperator = "\r\n";

	public static void writeResults(List<FeaturesAndTag> dt, List<FeaturesAndTag> knn, List<FeaturesAndTag> nb,
			List<FeaturesAndTag> real) {
		int totalRowsToWrite = dt.size();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		writer.append("Num" + seperator);
		writer.append("DT" + seperator);
		writer.append("KNN" + seperator);
		writer.append("naiveBase" + lineSeperator);

		// write each line
		String dtCurrentTag, knnCurrentTag, nbCurrentTag; 
		for (int rowNum = 0; rowNum < totalRowsToWrite; rowNum++) {
			dtCurrentTag = getTag(dt.get(rowNum));
			knnCurrentTag = getTag(knn.get(rowNum));
			nbCurrentTag = getTag(nb.get(rowNum));
			writeCurrentLine(writer, rowNum, dtCurrentTag, knnCurrentTag, nbCurrentTag);
		}

		// write the accuracy line in the file.
		writer.append(seperator);
		writeAccuracy(writer, real, dt, false);
		writeAccuracy(writer, real, knn, false);
		writeAccuracy(writer, real, nb, true);
		writer.close(); // close the fd.
	}

	private static void writeAccuracy(PrintWriter writer, List<FeaturesAndTag> real, List<FeaturesAndTag> predicted,
			boolean isLast) {
		double accuracy = AccuracyCalculator.calcAccuracy(real, predicted);
		int accInt = (int) accuracy; 
		DecimalFormat df = new DecimalFormat("#.00");
		if (isLast) {
			// without writing the tag in the end.
			writer.append(String.valueOf(accInt) + df.format(accuracy));
		} else {
			writer.append(String.valueOf(accInt) + df.format(accuracy) + seperator);
		}
	}

	private static void writeCurrentLine(PrintWriter writer, int lineNum, String dtTag, String knnTag, String nbTag) {
		writer.append(String.valueOf(lineNum + 1) + seperator);
		writer.append(dtTag + seperator);
		writer.append(knnTag + seperator);
		writer.append(nbTag + lineSeperator);
	}

	private static String getTag(FeaturesAndTag f) {
		return f.getTag();
	}

}
