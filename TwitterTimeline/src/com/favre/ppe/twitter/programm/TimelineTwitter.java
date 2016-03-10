package com.favre.ppe.twitter.programm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.favre.ppe.twitter.authentification.java.TwitterAuthHeader;
import com.favre.ppe.twitter.gui.java.MainWindow;

public class TimelineTwitter {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		MainWindow mw = new MainWindow();
		
		TwitterAuthHeader tah = new TwitterAuthHeader();
		tah.getTwitterAuthHeader();
	}

}
