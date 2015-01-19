package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.services.SysLog;

public class GuitarPhoto extends Photo {
	
	/* Collaboration: 
	 * GuitarPhoto-Guitar
	 */
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
	//------------
	
	
	protected void assertInvariants() throws IllegalStateException {
		boolean isValid = (this.guitar != null);
		
		if (!isValid) {
			throw new IllegalStateException("class invariant violated");
		}
	}
	
	/* Collaboration: 
	 * Factory
	 */
	public GuitarPhoto() {
		super();
		initialize();
	}
	public GuitarPhoto(PhotoId myId) {
		super(myId);
		initialize();
	}
	//---------------
	
	/* Collaboration: 
	 * Factory
	 * Serializer
	 */
	public GuitarPhoto(ResultSet rset) throws SQLException {
		super(rset);
	}
	//--------------
	
	protected void initialize(){
		try {
			this.guitar = GuitarManager.getInstance().getGuitarFromId(-1);
		} catch (GuitarNotFoundException e) {
			throw new IllegalStateException("Default Guitar (Id:-1) not found!");
		}
	}
	
	/* Collaboration: 
	 * Serializer
	 */
	public void readFrom(ResultSet rset) throws SQLException {
		super.readFrom(rset);
		
		try {
			this.guitar = GuitarManager.getInstance().getGuitarFromId(rset.getInt("guitar_id"));
		} catch (GuitarNotFoundException e) {
			SysLog.logThrowable(e);
			SysLog.logSysError("Guitar with id "+rset.getInt("guitar_id")+" was not found. Changed to default Guitar." );
			try {
				this.guitar = GuitarManager.getInstance().getGuitarFromId(-1);
			} catch (GuitarNotFoundException ex) {
				throw new IllegalStateException("Default Guitar (Id:-1) not found!");
			}
		}
	}
	public void writeOn(ResultSet rset) throws SQLException {
		super.writeOn(rset);
		
		rset.updateInt("guitar_id", this.guitar.getId());
	}


}
