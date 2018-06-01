package tests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import de.uni_koeln.idh.ticker2chirp.data.Geolocation;

public class TestDateTimes {

	//@Test
	public void testDateTime() {
		String date = "16.06.18";
		String kickoff = "21:00:00";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
		LocalDateTime now = LocalDateTime.parse(date+" "+kickoff, formatter);
		LocalDateTime then = now.plusMinutes(3);
		System.out.println(then);
	}
	
	@Test
	public void testGeolocation(){
		Geolocation gl = new Geolocation("data/geolocations.txt");
		System.out.println(gl.getLatitude("Moskau"));
		System.out.println(gl.getLongitude("St. Petersburg"));
		System.out.println(gl.getLongitude("Klagenfurt"));
		System.out.println(gl.getLatitude("Klagenfurt"));
	}
}
