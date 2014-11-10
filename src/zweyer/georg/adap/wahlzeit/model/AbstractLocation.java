package zweyer.georg.adap.wahlzeit.model;

public abstract class AbstractLocation implements Location {
	
	public void setLocation(String location) {
		assertIsValidLocation(location);
		basicSetLocation(location);
	}
	protected abstract void assertIsValidLocation(String location) throws AssertionError;
	protected abstract void basicSetLocation(String location);
}
