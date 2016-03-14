package com.favre.ppe.twitter.gui.java;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.JSONException;

import com.favre.ppe.twitter.authentification.java.TwitterApi;
import com.favre.ppe.twitter.json.java.Json;
import com.favre.ppe.twitter.tweet.java.Tweet;

public class MainWindow extends JFrame {
	private JPanel mainContainer;
	private JPanel header;
	private static JTable tweetsTable;
	private static JTable searchTable;
	private ArrayList<Tweet> tweets;
	
	public MainWindow(Json json) throws JSONException {
		this.setTitle("Twitter Timeline");
		this.setSize(1200, 1200);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(this.getJPanel(json));
		pack();
		this.setVisible(true);
	}
	
	public MainWindow() {
		// TODO Auto-generated constructor stub
	}

	public JPanel getJPanel(Json json) throws JSONException {
		mainContainer = new JPanel(new BorderLayout());
		tweets = json.ParseJson();
		tweetsTable = new JTable (new TweetModel(tweets));
		JScrollPane scroll = new JScrollPane(tweetsTable);
		tweetsTable.setRowHeight(150);
		mainContainer.add(scroll);
		
		JMenuBar menuBar= new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");
		file.add(new JMenuItem("Open"));
		file.add(new JMenuItem("Exit"));
		menuBar.add(file);
		
		header = new JPanel(new BorderLayout());
		
		JTextField textField = new JTextField(7);
		textField.setText("Search");
		
		JButton refresh = new JButton();
		refresh.setText("Refresh");
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TwitterApi tah = new TwitterApi();
				Json refreshJson = null;

				try {
					
					refreshJson = new Json();
					refreshJson.ReadJson(tah.getTwitterAuth());

					ArrayList<Tweet> twitterUpdate;
					twitterUpdate = refreshJson.ParseJson();
	
					tweetsTable.setModel(new TweetModel(twitterUpdate));
					tweetsTable.getColumnModel().getColumn(0).setHeaderValue("Twitter Timeline Refresh");
					
				} catch (InvalidKeyException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (NoSuchAlgorithmException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (URISyntaxException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton search = new JButton();
		search.setText("Search");
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MainWindow test = new MainWindow();
				TwitterApi tah = new TwitterApi();
				Json searchJson = null;
				
				String textIn = textField.getText();
				System.out.println(textIn);
				
				try {
					searchJson = new Json();
					searchJson.ReadJson(tah.searchTweet(textIn));
					
					ArrayList<Tweet> twitterSearch;
					twitterSearch = searchJson.ParseJson();
					
					tweetsTable.setModel(new TweetModel(twitterSearch));
					tweetsTable.getColumnModel().getColumn(0).setHeaderValue("Twitter Timeline Search");
				} catch (InvalidKeyException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (NoSuchAlgorithmException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		header.add(textField, BorderLayout.CENTER);
		header.add(search, BorderLayout.WEST);
		header.add(refresh, BorderLayout.AFTER_LAST_LINE);
		this.mainContainer.add(header, BorderLayout.NORTH);
		header.setVisible(true);
		return mainContainer;
	}
	
}
