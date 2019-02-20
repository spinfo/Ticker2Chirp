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
import de.uni_koeln.idh.ticker2chirp.data.AutoChirpTable;
import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;
import de.uni_koeln.idh.ticker2chirp.data.Geolocation;
import de.uni_koeln.idh.ticker2chirp.data.TweetData;

/**
 * This application generates Tweets for all matches of the specified fixtures that are found in the specified corpus.
 * @author jhermes
 *
 */
public class TweetGen4BuliApp {

	public static void main(String[] args) {
		
		//Resources
		String corpusFilePath = "data/buli0607.xml";
		String fixturesTableFilePath = "data/SpielplanBL25.csv";
		String fifaCodesFilePath = "data/BuliCodes"; 
		String geolocationsFilePath = "data/Geolocations"; 
			
		String[] split = corpusFilePath.split("/");
		String[] split2 = split[split.length-1].split("\\.");
		String fileNameSuffix = split2[0];
		
		//corpusFilePath = "data/Liveticker_combined.xml";
		
		FifaCodes codes = new FifaCodes(fifaCodesFilePath);
		XMLTickerReader reader = new XMLTickerReader(codes, "ticker");
		Map<String, List<FootballMatch>> processFile = reader.processFile(corpusFilePath, false);	
				
		TweetGenerator tg = new TweetGenerator(processFile);
		tg.setGeolocations(new Geolocation(geolocationsFilePath));
		
		List<FootballMatch> collectFixtures = CSVFixtureReader.collectFixtures(fixturesTableFilePath, codes);
		for (FootballMatch footballMatch : collectFixtures) {
			List<AutoChirpTable> generatesTables = tg.generateTweets(footballMatch);

			if (generatesTables != null) {
				for (AutoChirpTable autoChirpTable : generatesTables) {
				try {
					PrintWriter out = new PrintWriter(
							new FileWriter(new File("tweetsBuli/" + autoChirpTable.getHashTag()+ "_" + fileNameSuffix + ".tsv")));
					System.out.println(footballMatch.getHashtag() + " " + footballMatch.getKickoff());
					for (TweetData tweetData : autoChirpTable.getTweets()) {
						out.println(tweetData);
					}
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
			}
			
		}
		
	}
}
