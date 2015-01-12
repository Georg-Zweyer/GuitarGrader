package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.services.SysLog;

public class GuitarFactory{
	
	/**
	 * Hidden singleton instance;
	 */
	private static GuitarFactory instance = null;
	/**
	 * Public singleton access method.
	 */
	public static synchronized GuitarFactory getInstance() {
		if (instance == null) {
			setInstance(new GuitarFactory());
		}
		
		return instance;
	}
	protected static synchronized void setInstance(GuitarFactory guitarFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initalize GuitarFactory twice");
		}
		instance = guitarFactory;
	}
	
	/**
	 * 
	 */
	protected GuitarFactory() {
		super();
	}
	
	/* Collaboration: 
	 * Factory
	 */
	public Guitar createGuitar(Integer id) {
		return new Guitar(id);
	}	
	public Guitar createGuitar(ResultSet rset) throws SQLException {
		return new Guitar(rset);
	}
	//-------------
}
