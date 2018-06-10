package de.uni_koeln.idh.ticker2chirp.data;

import java.util.ArrayList;
import java.util.List;

public class AutoChirpTable {
	
	private String hashTag;
	
	private String description;
	
	private List<TweetData> tweets;

	public AutoChirpTable(String hashTag, String description) {
		super();
		this.hashTag = hashTag;
		this.description = description.substring(0, description.indexOf("M 20")+6) + ")";
		this.tweets = new ArrayList<TweetData>();
	}

	public List<TweetData> getTweets() {
		return tweets;
	}

	public void addTweet(TweetData tweet) {
		this.tweets.add(tweet);
	}

	public String getHashTag() {
		return hashTag;
	}

	public String getDescription() {
		return description;
	}
	
}