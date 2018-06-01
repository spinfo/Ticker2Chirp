package de.uni_koeln.idh.ticker2chirp.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class to collect all relevant infos for football games
 * @author jhermes
 *
 */
public class FootballMatch {
	
	private String url;
	private String title;
	private String homeTeam;
	private String awayTeam;
	private String date;
	private String kickoff;
	private String hashtag;
	private String alterHashtag;
	private String location;
	
		
	private SortedMap<Integer, List<String>> messages;	


	public FootballMatch(String url, String title, String homeTeam, String awayTeam, String date, String kickoff, FifaCodes codes) {
		super();
		this.url = url;
		this.title = title;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.date = date;
		this.kickoff = kickoff;
		
		messages = new TreeMap<Integer, List<String>>();
		
		
		generateHashtag(title, codes);
	}

	private void generateHashtag(String title, FifaCodes codes){
		hashtag = "#" + codes.getCode(homeTeam) + codes.getCode(awayTeam);		
		alterHashtag = "#" + codes.getCode(awayTeam) + codes.getCode(homeTeam);		
	}
	
	private int getTimeInSeconds(String minute){
		minute = minute.substring(0,minute.length()-1);
		int seconds = (Integer.parseInt(minute)-1) * 60;
		
		//Add halftime gap
		if(seconds >= 47*60){
			seconds += 15*60;
		}
		
		//Add overtime gap
		if(seconds >= 107*60){
			seconds += 5*60;
		}
		
		//Add penalty gap
		if(seconds >= 141*60){
			seconds += 5*60;
		}
		
		while(messages.containsKey(seconds)){
			seconds--;
		}
		
		return seconds;
	}
	
	public void addMessage(String minute, String message){
		int time = Integer.parseInt(minute.substring(0,minute.length()-1));
		List<String> mess = messages.get(time);
		if(mess==null) {
			mess = new LinkedList<String>();
		}
		mess.add(0, minute + " " + message);
		messages.put(time, mess);
	}
	
	public String export(String hashtag){
		StringBuffer buff = new StringBuffer();
		Set<Integer> keySet = messages.keySet();
		for (Integer integer : keySet) {
			List<String> mess = messages.get(integer);
			for (String string : mess) {
				buff.append("\t" + integer + "\t" +  string + " " + hashtag + "\n");
			}			
		}
		return buff.toString();
	}

	public String getHashtag() {
		return hashtag;
	}

	public String getAlterHashtag() {
		return alterHashtag;
	}		

	public SortedMap<Integer, List<String>> getMessages() {
		return messages;
	}

	public String getDate() {
		return date;
	}

	public String getKickoff() {
		return kickoff;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	
	
}
