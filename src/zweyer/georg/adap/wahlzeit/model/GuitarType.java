package zweyer.georg.adap.wahlzeit.model;

public class GuitarType {

	protected String name = "";
	protected GuitarManufacturer manufacturer = GuitarManufacturer.getInstance("");

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//precondition
		if(name == null){
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	public GuitarManufacturer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(GuitarManufacturer manufacturer) {
		//precondition
		if(manufacturer == null){
			throw new IllegalArgumentException();
		}
		this.manufacturer = manufacturer;
	}	
	
}
