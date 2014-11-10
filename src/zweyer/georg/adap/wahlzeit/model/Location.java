package zweyer.georg.adap.wahlzeit.model;

public interface Location {
	
	public final Location EMPTY_LOCATION = new EmptyLocation();
	
	public String asString();
	public double[] asGPSCoordinates();
	public void setLocation(String location);
	public String getLocationType();
}
