package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuitarTypeFactory {
	/**
	 * Hidden singleton instance;
	 */
	private static GuitarTypeFactory instance = null;
	/**
	 * Public singleton access method.
	 */
	public static synchronized GuitarTypeFactory getInstance() {
		if (instance == null) {
			setInstance(new GuitarTypeFactory());
		}
		
		return instance;
	}		
	protected static synchronized void setInstance(GuitarTypeFactory guitarTypeFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initalize GuitarTypeFactory twice");
		}
		instance = guitarTypeFactory;
	}
	/**
	 * 
	 */
	protected GuitarTypeFactory() {
		super();
	}

	/* Collaboration: 
	 * Factory
	 */
	public GuitarType createGuitarType(Integer id) {
		return new GuitarType(id);
	}
	public GuitarType createGuitarType(ResultSet rset) throws SQLException {
		return new GuitarType(rset);
	}
	//------------------
	
}
