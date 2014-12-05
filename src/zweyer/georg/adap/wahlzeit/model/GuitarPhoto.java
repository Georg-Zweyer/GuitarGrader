package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;

public class GuitarPhoto extends Photo {
	
	protected Guitar guitar;
	
	
	public Guitar getGuitar() {
		return guitar;
	}

	public void setGuitar(Guitar guitar) {
		//precondition
		if(guitar == null){
			throw new IllegalArgumentException();
		}
		
		this.doSetGuitar(guitar);
		
		//postcondition
		assert(this.guitar.equals(guitar));
		
	}	
	protected void doSetGuitar(Guitar guitar) {
		this.guitar = guitar;
		incWriteCount();
	}
	
	
	
	protected void assertInvariants() throws IllegalStateException {
		boolean isValid = (this.guitar != null);
		
		if (!isValid) {
			throw new IllegalStateException("class invariant violated");
		}
	}
	
	public GuitarPhoto() {
		super();
		initialize();
	}
	public GuitarPhoto(PhotoId myId) {
		super(myId);
		initialize();
	}
	public GuitarPhoto(ResultSet rset) throws SQLException {
		super(rset);
	}
	protected void initialize(){
		this.guitar = GuitarManager.getInstance().getGuitarFromId(-1);;
	}
	public void readFrom(ResultSet rset) throws SQLException {
		super.readFrom(rset);
		
		this.guitar = GuitarManager.getInstance().getGuitarFromId(rset.getInt("guitar_id"));
	}
	public void writeOn(ResultSet rset) throws SQLException {
		super.writeOn(rset);
		
		rset.updateInt("guitar_id", this.guitar.getId());
	}


}
