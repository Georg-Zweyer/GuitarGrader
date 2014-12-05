package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.services.SysLog;

public class GuitarFactory extends PhotoFactory {
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static GuitarFactory instance = null;
	
	/**
	 * Public singleton access method.
	 */
	public static synchronized GuitarFactory getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("setting generic PhotoFactory");
			setInstance(new GuitarFactory());
		}
		
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of PhotoFactory.
	 */
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
	
	
//	public Guitar createGuitar() {
//		return new Guitar();
//	}
	
	public Guitar createGuitar(Integer id) {
		return new Guitar(id);
	}
	
	public Guitar createGuitar(ResultSet rset) throws SQLException {
		return new Guitar(rset);
	}
	
	/**
	 * @methodtype factory
	 */
	@Override
	public Photo createPhoto() {
		return new GuitarPhoto();
	}
	
	/**
	 * 
	 */
	@Override
	public Photo createPhoto(PhotoId id) {
		return new GuitarPhoto(id);
	}
	
	/**
	 * 
	 */
	@Override
	public Photo createPhoto(ResultSet rs) throws SQLException {
		return new GuitarPhoto(rs);
	}

	
}
