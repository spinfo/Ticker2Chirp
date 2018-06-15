package de.uni_koeln.idh.ticker2chirp.applications;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import de.uni_koeln.idh.ticker2chirp.components.TweetGenerator;
import de.uni_koeln.idh.ticker2chirp.components.XMLTickerReader;
import de.uni_koeln.idh.ticker2chirp.data.AutoChirpTable;
import de.uni_koeln.idh.ticker2chirp.data.FifaCodes;
import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;
import de.uni_koeln.idh.ticker2chirp.data.Geolocation;
import de.uni_koeln.idh.ticker2chirp.data.TweetData;

/**
 * This application generates Tweets for a single matches when it is found in
 * the specified corpus.
 * 
 * @author jhermes
 *
 */
public class TweetGenForSingleGame {

	public static void main(String[] args) {
		// Resources
		String corpusFilePath = "data/Sample_Data.xml";
		String fifaCodesFilePath = "data/FifaCodes";
		String geolocationsFilePath = "data/Geolocations";

		// corpusFilePath = "data/Liveticker_combined.xml";

		FifaCodes codes = new FifaCodes(fifaCodesFilePath);
		XMLTickerReader reader = new XMLTickerReader(codes, "ticker");
		Map<String, List<FootballMatch>> processFile = reader.processFile(corpusFilePath, false);
		File outputFolder = new File("tweets");
		if (!outputFolder.exists()) {
			outputFolder.mkdirs();
		}

		TweetGenerator tg = new TweetGenerator(processFile);
		tg.setGeolocations(new Geolocation(geolocationsFilePath));

		FootballMatch footballMatch = new FootballMatch(null, null, "Österreich", "Deutschland", "02.06.18", "18:00:00",
				codes);
		footballMatch.setLocation("Klagenfurt");
		List<AutoChirpTable> generatesTables = tg.generateTweets(footballMatch);

		if (generatesTables != null) {
			for (AutoChirpTable autoChirpTable : generatesTables) {
			try {
				PrintWriter out = new PrintWriter(
						new FileWriter(new File("tweets/" + autoChirpTable.getHashTag()+ autoChirpTable.getDescription() + ".tsv")));
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
