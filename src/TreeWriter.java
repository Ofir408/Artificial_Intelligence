import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javafx.beans.binding.StringBinding;

/**
 * 
 * @author Ofir Ben Shoham Write the decision tree a text file.
 */
public class TreeWriter {
	private static final String fileName = "output_tree.txt";

	// write to tree to the text file.
	public static void writeToFile(Attribute attribute) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		StringBuilder s = new StringBuilder();

		generateString(s, attribute, false, true);
		System.out.println("S is: \n" + s.toString());
		writer.print(s);
		writer.close();
	}

	private static void generateString(StringBuilder str, Attribute attribute, boolean isSon, boolean isFirstIteration) {
		// System.out.println("here");
		Map<String, Attribute> map = attribute.getMap();
		if (map == null)
			return;
		System.out.println(" map.entrySet() is: " + map.entrySet().size());
		for (Map.Entry<String, Attribute> entry : map.entrySet()) {
			System.out.println("here");
			Attribute attr = entry.getValue();
			if (attr.isLeaf()) {
				if (isFirstIteration || isSon)
					str.append("\t" + "| " + entry.getKey() + ":" + attr.getLeafValue() + "\n");
				else
					str.append("| " + entry.getKey() + ":" + attr.getLeafValue() + "\n");

			} else {
				if (isFirstIteration)
					str.append("\t" + "| " + entry.getKey() + "\n");
				else
				str.append("| " + entry.getKey() + "\n");
				TreeWriter.generateString(str, attr, true, false);
			}
		}
	}
}
