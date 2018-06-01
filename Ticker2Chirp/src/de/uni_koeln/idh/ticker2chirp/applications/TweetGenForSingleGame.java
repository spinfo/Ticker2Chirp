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
 * This application generates Tweets for a single matches when it is found in the specified corpus.
 * @author jhermes
 *
 */
public class TweetGenForSingleGame {

	public static void main(String[] args) {
		FifaCodes codes = new FifaCodes("data/FifaCodes");
		XMLTickerReader reader = new XMLTickerReader(codes, "ticker");
		reader.processFile("data/Liveticker_Autochirp.xml", false);
		Map<String, List<FootballMatch>> processFile = reader.processFile("data/Liveticker_combined.xml", false);	
		
		
		TweetGenerator tg = new TweetGenerator(processFile);
		tg.setGeolocations(new Geolocation("data/geolocations"));
		
		
		FootballMatch footballGame = new FootballMatch(null, null, "Österreich", "Deutschland", "02.06.18", "18:00:00", codes);
		footballGame.setLocation("Klagenfurt");
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
