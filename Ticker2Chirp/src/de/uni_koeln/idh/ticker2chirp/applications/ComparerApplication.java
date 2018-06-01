package de.uni_koeln.idh.ticker2chirp.applications;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.uni_koeln.idh.ticker2chirp.components.CSVFixtureReader;
import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;

/**
 * Counts how many matches from the actual fixtures are in the (exported) historic corpus.
 * @author jhermes
 *
 */
public class ComparerApplication {

	public static void main(String[] args) {
		File tickerFolder = new File("ticker");
		// File fixturesFolder = new File("fixtures");
		// File[] listFiles = fixturesFolder.listFiles();
		List<FootballMatch> fixtures = CSVFixtureReader.collectFixtures("data/SpielplanWM18.csv", new FifaCodes("data/FifaCodes"));
		
		
		Set<String> fixturesNames = new TreeSet<String>();
		System.out.println("Number of games 2018: " + fixtures.size());
		for (FootballMatch footballGame : fixtures) {
			fixturesNames.add(footballGame.getHashtag());
		}

		File[] listFiles = tickerFolder.listFiles();
		System.out.println("Number of games 2002-2016: " + listFiles.length);
		for (File file : listFiles) {
			if(fixturesNames.contains(file.getName())) {
				System.out.println(file.getName());
			}			
		}
		
	}

}
