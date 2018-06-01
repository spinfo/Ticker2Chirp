package de.uni_koeln.idh.ticker2chirp.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that links country names (in German) to the international Fifa Code.
 * @author jhermes
 *
 */
public class FifaCodes {
	
	public Map<String, String> countries2codes;
	
	/**
	 * Generates a new FifaCodes object based on the content of the specified file.
	 * @param filename
	 */
	public FifaCodes(String filename) {
		countries2codes = new HashMap<String, String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			String line = in.readLine();
			while(line != null) {
				String[] parts = line.split("\\t");
				countries2codes.put(parts[0].trim(), parts[parts.length-1].trim());
				line = in.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(countries2codes);
	}
	
	/**
	 * Returns the fifa code of the specified country.
	 * @param country
	 * @return
	 */
	public String getCode(String country) {
		String code =countries2codes.get(country);
		if(code==null) {
			code=country;
		}
		return code;
	}

}
