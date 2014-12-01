package zweyer.georg.adap.wahlzeit.model;

import java.util.HashMap;

public class GuitarManufacturer {


	private final String manufacturer;
	private static final HashMap<String,GuitarManufacturer> map = new HashMap<String,GuitarManufacturer>();
	
	
	/**
	 * @methodtype get method
	 * @return manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * @methodtype assertion method
	 * @throws IllegalStateException
	 */
	protected void assertInvariants() throws IllegalStateException {
		boolean isValid = (this.manufacturer != null);
		
		if (!isValid) {
			throw new IllegalStateException("class invariant violated");
		}
	}
	
	/**
	 * @methodtype constructor
	 * @pre manufacturer != null
	 * @post this.manufacturer == manufacturer
	 * @param manufacturer
	 */
	private GuitarManufacturer(String manufacturer){

		// precondition
		if(manufacturer == null){
			throw new IllegalArgumentException();
		}
		
		
		this.manufacturer = manufacturer;
		
		//postcondition
		assert(this.manufacturer == manufacturer);
		
		//invariant
		assertInvariants();
	}
	
	/**
	 * @methodtype get methode
	 * @param manufacturer
	 * @return GuitarManufacturer instance
	 */
	public static GuitarManufacturer getInstance(String manufacturer) {
		if (map.containsKey(manufacturer)) {
			return map.get(manufacturer);
		} else {
			GuitarManufacturer result = new GuitarManufacturer(manufacturer);
			map.put(manufacturer, result);
			return result;
		}
	}
	
	//needed no longer
//	public boolean equals(Object object) {
//		if (object instanceof GuitarManufacturer) {
//			GuitarManufacturer other = (GuitarManufacturer) object;
//			if (other.getManufacturer().equals(this.manufacturer)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * @methodtype conversion method
	 * @return
	 */
	public String asString() {
		return manufacturer;
	}
	/**
	 * For print()
	 * @methodtype conversion method
	 * @return
	 */
	public String toString() {
		return "GuitarManufacturer: "+this.manufacturer;
	}
}
