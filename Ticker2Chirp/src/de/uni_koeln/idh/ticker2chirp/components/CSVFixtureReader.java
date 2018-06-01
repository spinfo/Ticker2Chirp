package de.uni_koeln.idh.ticker2chirp.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;

/**
 * Class to read match fixtures from a tsv file and transform them into football matches.
 * @author jhermes
 *
 */
public class CSVFixtureReader {
	
	/**
	 * Reads match fixtures from the specified tsv file. 
	 * @param inputFileName 
	 * @param codes Fifa codes
	 * @return A list of matches from the specified file
	 */
	public static List<FootballMatch> collectFixtures(String inputFileName, FifaCodes codes) {
		
		List<FootballMatch> games = new ArrayList<FootballMatch>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(inputFileName)));
			String line = in.readLine();
			line = in.readLine();
			while(line!=null) {				
				String[] parts = line.split("\\t");
				String date = parts[0].trim();;
				String kickoff = parts[2].trim();;
				String location = parts[5].trim();;
				String[] teams = parts[1].split(" - ");
				String homeTeam = teams[0].trim();
				String awayTeam = teams[1].trim();
				FootballMatch fg = new FootballMatch(null, null, homeTeam, awayTeam, date, kickoff, codes);
				fg.setLocation(location);
				
//				PrintWriter out = new PrintWriter(new FileWriter(new File(outputFolderName + "/" + fg.getHashtag())));
//				out.println(fg.export(fg.getHashtag()));
//				out.flush();
//				out.close();
				games.add(fg);
				line = in.readLine();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return games;
	}

}
