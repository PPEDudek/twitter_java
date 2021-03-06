package com.favre.ppe.twitter.json.java;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.favre.ppe.twitter.tweet.java.Tweet;

public class Json {

	static JSONArray jobj = null;
	static String json = "";
	private JLabel userLabel = null;
	
	private ArrayList<Tweet> tweets = new ArrayList<>();	

	public JSONArray ReadJson(StringBuilder response) throws IOException, JSONException {
		json = response.toString();
		return jobj;
	}
	
	public JLabel ParseJsonUser(JSONObject jsonObj) throws JSONException {
			String name = jsonObj.getString("name");
			String screenName = jsonObj.getString("screen_name");
			String profileImage = jsonObj.getString("profile_image_url");
			return this.userLabel = new JLabel("<html><b>" + "<img src=" + profileImage +"><br>" + name + "</b><br>@" + screenName + "<br>");
	}
	
	public ArrayList<Tweet> ParseJson () throws JSONException {
		String tempDate = "";
		String tempText = "";
		String tempName = "";
		String tempScreen_name = "";
		String tempAvatar = "";
		
		/*
		 * 	Parsing of Json
		 */
		JSONArray jsonarray = new JSONArray(json);
		ArrayList<Tweet> listTweet = new ArrayList<Tweet>();
		
		for(int i = 0; i < jsonarray.length(); i++) {

			//Récupération premier tweet
			JSONObject tweetObj = jsonarray.getJSONObject(i);
			tempDate = tweetObj.getString("created_at");
			tempText = tweetObj.getString("text");

			
			// Récupération premier tweet -> user 
			JSONObject userObj = tweetObj.getJSONObject("user");
			tempName = userObj.getString("name");
			tempScreen_name = userObj.getString("screen_name");
			tempAvatar = userObj.getString("profile_image_url");
			
			listTweet.add(new Tweet(tempDate, tempText, tempName, tempScreen_name, tempAvatar));			
		}
		return listTweet;
	}
	
	
	/*
	 * GETTERS & SETTERS
	 * 
	 */	
	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}
}
