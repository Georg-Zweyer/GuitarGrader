package zweyer.georg.adap.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.DataObject;

public class Guitar extends DataObject {
	
	protected Integer id;
	protected String name;
	protected GuitarManufacturer manufacturer = GuitarManufacturer.getInstance("");
	
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//precondition
		if(name == null){
			throw new IllegalArgumentException();
		}
		this.name = name;
		incWriteCount();
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
		incWriteCount();
	}	

	
//	public Guitar() {
//		incWriteCount();
//	}
	public Guitar(Integer id) {
		this.id = id;
		incWriteCount();
	}
	public Guitar(ResultSet rset) throws SQLException{
		this.readFrom(rset);
	}
	
	public String asString() {
		return "ID: " + this.id + ", Manufacturer: " + this.manufacturer.asString();
	}

	@Override
	public String getIdAsString() {
		return String.valueOf(this.id);
	}
	@Override
	public void readFrom(ResultSet rset) throws SQLException {
		this.id = rset.getInt("id");
		this.name = rset.getString("name");
		this.manufacturer = GuitarManufacturer.getInstance(rset.getString("manufacturer"));
	}
	@Override
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateInt("id", this.id);
		rset.updateString("name", this.name);
		rset.updateString("manufacturer", this.manufacturer.asString());
	}
	@Override
	public void writeId(PreparedStatement stmt, int pos) throws SQLException {
		stmt.setInt(pos, this.id);
	}

}
