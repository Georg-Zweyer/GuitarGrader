package zweyer.georg.adap.wahlzeit.model;

import com.mapcode.MapcodeCodec;
import com.mapcode.Point;
import com.mapcode.UnknownMapcodeException;

public class MapcodeLocation extends AbstractLocation {

	protected String mapcode;
	
	public MapcodeLocation(String location) {
		this.setLocation(location);
	}
	
	@Override
	public String asString() {
		return mapcode;
	}
	
	@Override
	public void basicSetLocation(String location) {
		this.mapcode = location;
	}

	@Override
	protected void assertIsValidLocation(String location) throws AssertionError {
		try {
			MapcodeCodec.decode(location);
		} catch (IllegalArgumentException | UnknownMapcodeException e) {
			throw new AssertionError();
		}
	}
	
	public String asGPSString() {
		try {
			Point point = MapcodeCodec.decode(this.mapcode);
			return point.getLatDeg()+", "+point.getLonDeg();
		} catch (IllegalArgumentException | UnknownMapcodeException e) {
			// should never happen
			e.printStackTrace();
			return "None";
		}
	}

	@Override
	public String getLocationType() {
		return "Mapcode";
	}

	@Override
	public double[] asGPSCoordinates() {
		double[] result = new double[2];
		try {
			Point point = MapcodeCodec.decode(this.mapcode);
			result[0] = point.getLatDeg();
			result[1] = point.getLonDeg();
		} catch (IllegalArgumentException | UnknownMapcodeException e) {
			// should never happen
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}
