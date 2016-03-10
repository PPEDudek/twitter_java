package com.favre.ppe.twitter.gui.java;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import com.favre.ppe.twitter.tweet.java.Tweet;

public class TweetModel extends AbstractTableModel {
	private ArrayList<Tweet> tweets = new ArrayList<>();
	
	public TweetModel(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.tweets.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		Tweet tweet = this.tweets.get(row);
		switch(col) {
		case 0 :
			String encode = new String(tweet.getText().getBytes(),Charset.forName("UTF-8"));
			JLabel endLabel = new JLabel("<html><b>" + "<img src=" + tweet.getAvatar()+">" + tweet.getName() + "</b> @" + tweet.getScreen_name() + "<br>" +  encode);
			return endLabel.getText();
		default : 
			return "unknow";
		}
	}
}
