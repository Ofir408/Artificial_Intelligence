import java.util.List;

/**
 * 
 * @author Ofir Ben Shoham.
 * Will be extended by each algorithm.
 *
 */
public abstract class AbstractAlgorithm {
	protected List<FeaturesAndTag> trainingList; 
	public AbstractAlgorithm(List<FeaturesAndTag> trainingList) {
		this.trainingList = trainingList; 
	}
	
	// set into inputFeatures his tag.
	protected abstract void predict(FeaturesAndTag inputFeatures);
	
	public void predictOnTest(List<FeaturesAndTag> inputFeaturesList) {
		for (FeaturesAndTag f : inputFeaturesList) 
			predict(f);
	}
}
