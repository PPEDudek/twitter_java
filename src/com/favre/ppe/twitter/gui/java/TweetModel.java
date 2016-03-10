package com.favre.ppe.twitter.gui.java;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
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
		/*String imageString = tweet.getAvatar();
		Image image = null;
		try {
			URL url = new URL(imageString);
			image = ImageIO.read(url);
		} catch (IOException e){
			e.printStackTrace();
		}*/
		//ImageIcon imageIcon = new ImageIcon(image); 
		switch(col) {
		case 0 :
			JLabel endLabel = new JLabel("<html><b>"+ 
                    tweet.getName()+
                    "</b> @" + tweet.getScreen_name() + "<br>" +
                    tweet.getText() + "<br>" +  
                    tweet.getDate());
			//endLabel.setIcon(imageIcon);
			return endLabel;
		default : 
			return "unknow";
		}
	}
}
