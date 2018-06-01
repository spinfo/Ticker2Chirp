package de.uni_koeln.idh.ticker2chirp.applications;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import de.uni_koeln.idh.ticker2chirp.components.CSVFixtureReader;
import de.uni_koeln.idh.ticker2chirp.components.TweetGenerator;
import de.uni_koeln.idh.ticker2chirp.components.XMLTickerReader;
import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;
import de.uni_koeln.idh.ticker2chirp.data.Geolocation;
import de.uni_koeln.idh.ticker2chirp.data.TweetData;

/**
 * This application generates Tweets for all matches of the specified fixtures that are found in the specified corpus.
 * @author jhermes
 *
 */
public class TweetGenForFixturesApplication {

	public static void main(String[] args) {
		
		//Resources
		String corpusFilePath = "data/Sample_Data.xml";
		String fixturesTableFilePath = "data/SpielplanWM18.csv";
		String fifaCodesFilePath = "data/FifaCodes"; 
		String geolocationsFilePath = "data/FifaCodes"; 
			
		//corpusFilePath = "data/Liveticker_combined.xml";
		
		FifaCodes codes = new FifaCodes(fifaCodesFilePath);
		XMLTickerReader reader = new XMLTickerReader(codes, "ticker");
		Map<String, List<FootballMatch>> processFile = reader.processFile(corpusFilePath, false);	
		File outputFolder = new File("tweets");
		if(!outputFolder.exists()) {
			outputFolder.mkdirs();
		}
		
		TweetGenerator tg = new TweetGenerator(processFile);
		tg.setGeolocations(new Geolocation(geolocationsFilePath));
		
		List<FootballMatch> collectFixtures = CSVFixtureReader.collectFixtures(fixturesTableFilePath, codes);
		for (FootballMatch footballMatch : collectFixtures) {
			List<TweetData> generateTweets = tg.generateTweets(footballMatch);
			
			if(generateTweets!=null) {
				try {
					PrintWriter out = new PrintWriter(
							new FileWriter(new File(outputFolder + "/" + footballMatch.getHashtag() + ".tsv")));
					System.out.println(footballMatch.getHashtag() + " " + footballMatch.getKickoff());
					for (TweetData tweetData : generateTweets) {
						out.println(tweetData);
					} 
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();				}
			}
			
		}
		
	}
}
