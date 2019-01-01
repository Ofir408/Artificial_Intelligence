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

		generateString(s, attribute);
		System.out.println("S is: \n" +s.toString());
		writer.print(s); 
		writer.close();
	}

	
	private static void generateString(StringBuilder str, Attribute attribute) {
		if (attribute.isLeaf()) {
			return; 
		} else {
			Map<String, Attribute> map = attribute.getMap();
			for (Map.Entry<String, Attribute> entry : map.entrySet()) {
				Attribute attr = entry.getValue();
				if (attr.isLeaf()) {
					str.append( "| " + entry.getKey() + ":" + attr.getLeafValue() + "\n");
				} else {
					str.append("| " + entry.getKey() + "\n" + "\t" );
					TreeWriter.generateString(str, attr);
				}
			}
		}
	}
}
