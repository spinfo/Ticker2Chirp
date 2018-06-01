package de.uni_koeln.idh.ticker2chirp.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to parse a geolocation file (Constructor) and to manage all contained geolacations 
 * @author jhermes
 */
public class Geolocation {
	
public Map<String, String> geolocation;
	
	/** Gernerates a new Geolocation object based on content of the specified file;
	 * @param filename
	 */
	public Geolocation(String filename) {
		geolocation = new HashMap<String, String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			String line = in.readLine();
			while(line != null) {
				String[] parts = line.split("\\t");
				geolocation.put(parts[0].trim()+".lat", parts[1].trim());
				geolocation.put(parts[0].trim()+".lon", parts[2].trim());
				line = in.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Returns the latitude of the specified location
	 * @param location
	 * @return
	 */
	public String getLatitude(String location) {
		return geolocation.get(location+".lat");
	}
	
	/** 
	 * Returns the longitude of the specified location
	 * @param location
	 * @return
	 */
	public String getLongitude(String location) {
		return geolocation.get(location+".lon");
	}

}
