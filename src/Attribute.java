import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ofir Ben Shoham
 * Represents an attribute in the Decision tree.
 */
public class Attribute {
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Attribute(new HashMap<String, Attribute>(featuresToAttributeMap));
	}

	private String leafValue; // if not a leaf, not a null.
	// Each string in that format: <attribute_name>=<attribute_value>
	private Map<String, Attribute> featuresToAttributeMap = new HashMap<>();

	// constructor
	public Attribute(Map<String, Attribute> featuresToAttributeMap) {
		this.leafValue = null;
		this.featuresToAttributeMap = featuresToAttributeMap;
	}
	
	// constructor
	public Attribute(Map<String, Attribute> featuresToAttributeMap, String leaf) {
		this.leafValue = leaf;
		this.featuresToAttributeMap = featuresToAttributeMap;
	}
	
	// getLeafValue (String).
	public String getLeafValue() {
		if (!isLeaf())
			System.out.println("Not a leaf");
		return leafValue;
	}
	
	// set Leaf to the Attribute. 
	public void setLeaf(String leafValue) {
		this.leafValue = leafValue; 
	}
	
	// return true if this attribute is a leaf, otherwise false.
	public boolean isLeaf() {
		return leafValue != null; 
	}
	
	// add an attribute to the map.
	public void addToMap(String attrKey, String attrValue, Attribute attribute) {
		String updatedKey = attrKey + "=" + attrValue; 
		featuresToAttributeMap.put(updatedKey, attribute);
	}
	
	// return true if key in featuresToAttributeMap.
	public boolean isExistsInMap(String key) {
		return featuresToAttributeMap.containsKey(key);
	}
	
	// return the next attribute according the given key.
	public Attribute getNextAttribute(String key) {
		if (featuresToAttributeMap.containsKey(key))
			return featuresToAttributeMap.get(key);
		System.out.println("key: " + key + "  not found");
		return null;
	}
	
	// get the map. 
	public Map<String, Attribute> getMap() {
		return featuresToAttributeMap; 
	}

}
