package zweyer.georg.adap.wahlzeit.model;

public class EmptyLocation extends AbstractLocation {

	@Override
	protected void assertIsValidLocation(String location) throws AssertionError {
		throw new AssertionError();
	}

	@Override
	protected void basicSetLocation(String location) {
	}

	@Override
	public String asString() {
		return "";
	}

	@Override
	public String getLocationType() {
		return "Empty";
	}

	@Override
	public double[] asGPSCoordinates() {
		return new double[2];
	}

}
