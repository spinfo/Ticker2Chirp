package de.uni_koeln.idh.ticker2chirp.data;


/**
 * Class to collect all relevant tweet data
 * @author jhermes
 *
 */
public class TweetData {
	
	private String date;
	private String time;
	private String text;
	public String imageUrl;
	public String longitude;
	public String latitude;
	
	public TweetData() {
		longitude = "";
		latitude = "";
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(date);
		buff.append("\t");
		buff.append(time);
		buff.append("\t ");
		buff.append(text);
		buff.append("\t");
		if(imageUrl!=null) 
			buff.append(imageUrl);
		buff.append("\t");
		buff.append(latitude);
		buff.append("\t");
		buff.append(longitude);
		
		return buff.toString();
	}

}
