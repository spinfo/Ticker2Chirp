package de.uni_koeln.idh.ticker2chirp.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import de.uni_koeln.idh.ticker2chirp.data.FootballMatch;
import de.uni_koeln.idh.ticker2chirp.data.Geolocation;
import de.uni_koeln.idh.ticker2chirp.data.TweetData;

/**
 * A class to generate autochirp-conform Tweets from football matches of the corpus.
 * @author jhermes
 *
 */
public class TweetGenerator {

	private Map<String, List<FootballMatch>> retromatches;
	private Geolocation geolocations;
	

	/**
	 * Generates a new TweetGenerator based on the specified corpus of historic matches.
	 * @param retrogames The corpus
	 */
	public TweetGenerator(Map<String, List<FootballMatch>> retromatches){
		this.retromatches = retromatches;
	}

	/**
	 * Tweet generation for a list of matches
	 * @param actualgames
	 */
	public void generateTweets(List<FootballMatch> actualgames) {
		for (FootballMatch footballGame : actualgames) {
			if (retromatches.containsKey(footballGame.getHashtag())) {
				generateTweets(footballGame);
			}
		}
	}
	
	/** Tweet generation for a single match
	 * @param footballGame
	 * @return
	 */
	public List<TweetData> generateTweets(FootballMatch footballGame) {
		List<TweetData> tweets = new ArrayList<TweetData>();
		String date = footballGame.getDate();
		String kickoff = footballGame.getKickoff();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
		LocalDateTime kickoffTime = LocalDateTime.parse(date + " " + kickoff, formatter);

		List<FootballMatch> games = retromatches.get(footballGame.getHashtag());
		if(games==null)
			return null;
		for (FootballMatch game : games) {
			SortedMap<Integer, List<String>> messages = game.getMessages();
			Set<Integer> keySet = messages.keySet();
			LocalDateTime postTime;
			for (Integer minute : keySet) {
				postTime = kickoffTime.plusMinutes(minute-1);
				if(minute>45) {
					postTime = postTime.plusMinutes(15);
				}
				List<String> posts = messages.get(minute);
				for (int i = 0; i < posts.size(); i++) {
					TweetData td = new TweetData();
					td.setDate(kickoffTime.getDayOfMonth() + "." + kickoffTime.getMonthValue() + "." + kickoffTime.getYear());
					String text = posts.get(i);
					if(text.length()>270) {
						td.setText(text.substring(0, text.indexOf('\'')+2) + footballGame.getHashtag() + text.substring(text.indexOf('\'')+1));
					}
					else {
						td.setText(text + " " + footballGame.getHashtag());
					}
					if (minute == 0) {
						int backwards = posts.size()- i;
						postTime = kickoffTime.minusMinutes(backwards);

					} else {
						if (minute == 120) {
							postTime = postTime.plusSeconds(i*20);	
						} else {
							postTime = postTime.plusSeconds(i*10);
						}
					}
					String hour = postTime.getHour()+"";
					if(hour.length()<2) hour = "0" + postTime.getHour();
					String minute1 = postTime.getMinute()+"";
					if(minute1.length()<2) minute1 = "0" + postTime.getMinute();
					String second = postTime.getSecond()+"";
					if(second.length()<2) second = "0" + postTime.getSecond();
					td.setTime(hour+":"+minute1+":"+second);
					
					if(geolocations!=null) {
						String location = footballGame.getLocation();
						
						if(location !=null) {
							td.setLatitude(geolocations.getLatitude(location));
							td.setLongitude(geolocations.getLongitude(location));
						}
					}
					
					
					tweets.add(td);
				}
			}
			TweetData end = new TweetData();
			end.setText("********************* END OF MATCH *********************");
			tweets.add(end);
		}
		return tweets;
	}

	/**
	 * Sets geolocations, if available
	 * @param geolocations
	 */
	public void setGeolocations(Geolocation geolocations) {
		this.geolocations = geolocations;
	}

}
