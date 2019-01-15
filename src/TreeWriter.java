import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;


/**
 * 
 * @author Ofir Ben Shoham Write the decision tree a text file.
 */
public class TreeWriter {
	private static final String fileName = "output_tree.txt";
	private static final String lineSeperator = "\n";

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

		generateString(s, attribute,  0);
		int lastCharInx =s.lastIndexOf(lineSeperator);
		char lastChar = s.charAt(lastCharInx);

		if (lastChar == '\n')
			s.deleteCharAt(lastCharInx);

		System.out.println("S is: \n" + s.toString());
		writer.print(s);
		writer.close();
	}

	// generate the string to write, result was saved into str.
	private static void generateString(StringBuilder str, Attribute attribute, int fatherTabs) {

		Map<String, Attribute> map = attribute.getMap();
		if (map == null)
			return;
		System.out.println(" map.entrySet() is: " + map.entrySet().size());
		map = orderAccordingAlphabetical(map);
		for (Map.Entry<String, Attribute> entry : map.entrySet()) {
			Attribute attr = entry.getValue();
			for (int i = 0; i < fatherTabs; i++)
				str.append("\t");

			if (attr.isLeaf()) {
				if (fatherTabs == 0)
					str.append( entry.getKey() + ":" + attr.getLeafValue() + lineSeperator);
				else
					str.append("|" + entry.getKey() + ":" + attr.getLeafValue() + lineSeperator);
			} else {
				if (fatherTabs != 0)
					str.append("|" + entry.getKey() + lineSeperator);
				else
					str.append(entry.getKey() + lineSeperator);

				TreeWriter.generateString(str, attr, fatherTabs + 1);
			}
		}
	}
	
	private static Map<String, Attribute> orderAccordingAlphabetical(Map<String, Attribute> map ) {
        TreeMap<String, Attribute> sorted_map = new TreeMap<String, Attribute>(new ValueComparator()); 
        sorted_map.putAll(map);
        return sorted_map;
	}
	
	private static class ValueComparator implements Comparator<String> {
	    public int compare(String a, String b) {
	       return a.compareTo(b);
	    }
	}

}
