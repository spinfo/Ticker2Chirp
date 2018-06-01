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
		
		//String corpus = "data/Liveticker_combined.xml";
		String corpus = "data/Sample_Data.xml";
		FifaCodes codes = new FifaCodes("data/FifaCodes");
		XMLTickerReader reader = new XMLTickerReader(codes, "ticker");
		reader.processFile("data/Liveticker_Autochirp.xml", false);
		Map<String, List<FootballMatch>> processFile = reader.processFile(corpus, false);	
		
		
		TweetGenerator tg = new TweetGenerator(processFile);
		tg.setGeolocations(new Geolocation("data/geolocations"));
		
		List<FootballMatch> collectFixtures = CSVFixtureReader.collectFixtures("data/SpielplanWM18.csv", new FifaCodes("data/FifaCodes"));
		for (FootballMatch footballGame : collectFixtures) {
			List<TweetData> generateTweets = tg.generateTweets(footballGame);
			
			if(generateTweets!=null) {
				try {
					PrintWriter out = new PrintWriter(
							new FileWriter(new File("tweets/" + footballGame.getHashtag() + ".tsv")));
					System.out.println(footballGame.getHashtag() + " " + footballGame.getKickoff());
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
