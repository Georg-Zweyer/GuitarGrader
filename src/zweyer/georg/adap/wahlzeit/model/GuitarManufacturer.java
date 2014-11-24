package zweyer.georg.adap.wahlzeit.model;

public class GuitarManufacturer {

	public static final GuitarManufacturer EMPTY = new GuitarManufacturer("");
	protected String manufacturer;
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		
		// precondition
		assert(manufacturer != null);
		
		
		doSetManufacturer(manufacturer);
		
		//postcondition
		assert(this.manufacturer == manufacturer);
		
		//invariant
		assertInvariants();
	}
	protected void doSetManufacturer(String manufacturer){
		this.manufacturer = manufacturer;
	}
	
	protected void assertInvariants() throws IllegalStateException {
		boolean isValid = (this.manufacturer != null);
		
		if (!isValid) {
			throw new IllegalStateException("class invariant violated");
		}
	}
	
	public GuitarManufacturer(String manufacturer){
		this.setManufacturer(manufacturer);
	}
	public boolean equals(Object object) {
		if (object instanceof GuitarManufacturer) {
			GuitarManufacturer other = (GuitarManufacturer) object;
			if (other.getManufacturer().equalsIgnoreCase(this.manufacturer)) {
				return true;
			}
		}
		return false;
	}
	public String asString() {
		return manufacturer;
	}
}
