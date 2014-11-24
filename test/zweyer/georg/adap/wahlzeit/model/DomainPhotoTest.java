package zweyer.georg.adap.wahlzeit.model;

import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.TagsTest;

import junit.framework.TestCase;

public class DomainPhotoTest extends TestCase {

	
	public DomainPhotoTest (String name) {
		super(name);
	}
	
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(DomainPhotoTest.class);
	}
	
	public void testNewGuitarPhoto() {
		GuitarPhoto photo = (GuitarPhoto) PhotoFactory.getInstance().createPhoto();
	}
	
	public void testNullAssignmentGuitarPhoto(){
		GuitarPhoto photo = (GuitarPhoto) PhotoFactory.getInstance().createPhoto();
		try {
			photo.setManufacturer(null);
		} catch (AssertionError as) {
			return;
		}
		fail();
	}
	
	public void testNewGuitarManufacturer() {
		GuitarManufacturer gm = new GuitarManufacturer("");
		try {
			GuitarManufacturer gm2 = new GuitarManufacturer(null);
		} catch (AssertionError as) {
			return;
		}
		fail();
	}
	
	public void testNullAssignmentGuitarManufacturer(){
		GuitarManufacturer gm = new GuitarManufacturer("");
		try {
			gm.setManufacturer(null);
		} catch (AssertionError as) {
			return;
		}
		fail();
	}
	
}
