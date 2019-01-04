import java.util.HashMap;

public class TreeWriterTest {

	public static void main(String[] args) {
		Attribute a2 = new Attribute(new HashMap<>());
		Attribute a4 = new Attribute(null); a4.setLeaf("yes");
		Attribute a3 = new Attribute(new HashMap<>());
		Attribute a5 = new Attribute(null); a5.setLeaf("no");
		Attribute a6 = new Attribute(null); a6.setLeaf("no");

		a3.addToMap("a4Key", "a4Value", a4);

		Attribute a1 = new Attribute(new HashMap<>());
		a1.addToMap("a3Key", "a3Value", a3);
		a1.addToMap("a2Key", "a2Value", a2);
		a2.addToMap("a5Key", "a5Value", a5);
		a2.addToMap("a6Key", "a6Value", a6);


		TreeWriter.writeToFile(a1);

	}

}
