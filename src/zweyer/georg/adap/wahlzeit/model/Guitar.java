package zweyer.georg.adap.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.DataObject;

public class Guitar extends DataObject {
	
	/* Collaboration: 
	 * Manager
	 * GuitarPhoto-Guitar
	 */
	protected Integer id;
	//---------
	
	protected String color;
	protected int yearBuilt;
	
	/* Collaboration: 
	 * TypeObject
	 */
	protected GuitarType type = GuitarTypeManager.getInstance().getGuitarTypeFromId(-1);
	public GuitarType getType() {
		return type;
	}
	public void setType(GuitarType type) {
		//precondition
		if(type == null){
			throw new IllegalArgumentException();
		}
		this.type = type;
		incWriteCount();
	}
	
	//----------
	
	public Integer getId() {
		return id;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		//precondition
		if(color == null){
			throw new IllegalArgumentException();
		}
		this.color = color;
		incWriteCount();
	}
	public int getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
		incWriteCount();
	}
	
	/* Collaboration: 
	 * Factory
	 */
	public Guitar(Integer id) {
		this.id = id;
		incWriteCount();
	}
	//---------------
	
	/* Collaboration: 
	 * Factory
	 * Serializer
	 */
	public Guitar(ResultSet rset) throws SQLException{
		this.readFrom(rset);
	}
	//-------------
	
	/* Collaboration: 
	 * Manager
	 */
	@Override
	public String getIdAsString() {
		return String.valueOf(this.id);
	}
	//-----------------
	
	/* Collaboration: 
	 * Serializer
	 */
	@Override
	public void readFrom(ResultSet rset) throws SQLException {
		this.id = rset.getInt("id");
		this.color = rset.getString("color");
		this.yearBuilt = rset.getInt("year_built");
		this.type = GuitarTypeManager.getInstance().getGuitarTypeFromId(rset.getInt("guitar_type_id"));
	}
	@Override
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateInt("id", this.id);
		rset.updateString("color", this.color);
		rset.updateInt("year_built", this.yearBuilt);
		rset.updateInt("guitar_type_id", this.type.getId());
	}
	@Override
	public void writeId(PreparedStatement stmt, int pos) throws SQLException {
		stmt.setInt(pos, this.id);
	}
	//------------
}
