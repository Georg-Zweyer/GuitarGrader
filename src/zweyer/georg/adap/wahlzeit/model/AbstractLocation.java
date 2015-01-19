package zweyer.georg.adap.wahlzeit.model;

public abstract class AbstractLocation implements Location {
	
	public void setLocation(String location) throws IllegalLocationException {
		try {
			assertIsValidLocation(location);
			basicSetLocation(location);
		} catch (IllegalArgumentException e) {
			throw new IllegalLocationException("Illeagal location for "+this.getClass().getName());
		}
	}
	protected abstract void assertIsValidLocation(String location);
	protected abstract void basicSetLocation(String location);
}
