import java.io.IOException;
import java.util.List;

public class InputTextReaderTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputTextReader inputTextReader = new InputTextReader("train.txt");
		List<FeaturesAndTag> list = inputTextReader.getDataFromFile();
		System.out.println("DONE");

	}

}
