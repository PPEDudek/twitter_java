package com.favre.ppe.twitter.programm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import com.favre.ppe.twitter.authentification.java.TwitterApi;
import com.favre.ppe.twitter.gui.java.MainWindow;
import com.favre.ppe.twitter.json.java.Json;

public class TimelineTwitter {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, IOException, JSONException {
		// TODO Auto-generated method stub
		TwitterApi tah = new TwitterApi();
		Json json = new Json();
		json.ReadJson(tah.getTwitterAuth()); 
		MainWindow mw = new MainWindow(json);
	}

}
