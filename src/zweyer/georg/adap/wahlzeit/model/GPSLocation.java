package zweyer.georg.adap.wahlzeit.model;

import com.mapcode.MapcodeCodec;

public class GPSLocation extends AbstractLocation {
	
	protected double latitude;
	protected double longitude;
	
	public GPSLocation(String location) throws IllegalLocationException {
		setLocation(location);
	}

	@Override
	protected void assertIsValidLocation(String location) {
		if (location != null) {
			String[] components = location.split(",");
			for (String component : components) {
				component.trim();
			}
			if (components.length != 2) {
				throw new IllegalArgumentException("To many or to less coordinates.");
			} else {
				double x = Double.parseDouble(components[0]);
				double y = Double.parseDouble(components[1]);
				if (! ( (x <= 90.0 && x >= -90.0) && (y <= 180.0 && y >= -180.0) ) ) {
					throw new IllegalArgumentException("coordinates are out of range.");
				}
			}
		}
	}

	@Override
	protected void basicSetLocation(String location) {
		String[] components = location.split(",");
		for (String component : components) {
			component.trim();
		}
		this.latitude = Double.parseDouble(components[0]);
		this.longitude = Double.parseDouble(components[1]);
	}

	@Override
	public String asString() {
		return latitude+", "+longitude;
	}
	@Override
	public String getLocationType() {
		return "GPS";
	}
	public String asMapcodeString() {
		return MapcodeCodec.encodeToShortest(latitude, longitude).asInternationalISO();
	}

	@Override
	public double[] asGPSCoordinates() {
		double [] result = {this.latitude,this.longitude};
		return result;
	}
	
	
	
	
	
	
/*	public String asDMSString() {
		int laD, laM, laS, loD, loM, loS;
		char  laSuffix, loSuffix;
		double absLatitude = Math.abs(this.latitude);
		double absLongitude = Math.abs(this.longitude);
		if (this.latitude < 0) {
			laSuffix = 'S';
		} else {
			laSuffix = 'N';
		}
		if (this.longitude < 0) {
			loSuffix = 'W';
		} else {
			loSuffix = 'E';
		}
		laD = (int) absLatitude;
		laM = (int) ((absLatitude * 60) % 60);
		laS = (int) ((absLatitude * 3600) % 60);
		loD = (int) absLongitude;
		loM = (int) ((absLongitude * 60) % 60);
		loS = (int) ((absLongitude * 3600) % 60);
		return laD+"° "+laM+"' "+laS+"\" "+laSuffix+" "+loD+"° "+loM+"' "+loS+"\" "+loSuffix;
	}*/


}
