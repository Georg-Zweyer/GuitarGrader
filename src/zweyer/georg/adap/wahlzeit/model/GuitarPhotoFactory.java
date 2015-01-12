package zweyer.georg.adap.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoId;

public class GuitarPhotoFactory extends PhotoFactory {

	/* Collaboration: 
	 * Factory
	 */
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
	//-------------
}
