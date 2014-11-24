package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;

public class GuitarPhoto extends Photo {
	
	protected GuitarManufacturer manufacturer = GuitarManufacturer.EMPTY;

	public GuitarManufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(GuitarManufacturer manufacturer) {
		//precondition
		assert(manufacturer != null);
		
		this.doSetManfacturer(manufacturer);
		
		//postcondition
		assert(this.manufacturer.equals(manufacturer));
		
	}	
	protected void doSetManfacturer(GuitarManufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	protected void assertInvariants() throws IllegalStateException {
		boolean isValid = (this.manufacturer != null);
		
		if (!isValid) {
			throw new IllegalStateException("class invariant violated");
		}
	}
	
	public GuitarPhoto() {
		super();
	}
	public GuitarPhoto(PhotoId myId) {
		super(myId);
	}
	public GuitarPhoto(ResultSet rset) throws SQLException {
		this.readFrom(rset);
	}
	public void readFrom(ResultSet rset) throws SQLException {
		super.readFrom(rset);
		this.manufacturer = new GuitarManufacturer(rset.getString("manufacturer"));
	}
	public void writeOn(ResultSet rset) throws SQLException {
		super.writeOn(rset);
		rset.updateString("manufacturer", manufacturer.asString());
	}


}
