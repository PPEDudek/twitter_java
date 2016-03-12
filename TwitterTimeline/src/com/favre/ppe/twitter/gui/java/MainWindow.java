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

import org.json.JSONException;

import com.favre.ppe.twitter.authentification.java.TwitterAuthHeader;
import com.favre.ppe.twitter.json.java.Json;
import com.favre.ppe.twitter.tweet.java.Tweet;

public class MainWindow extends JFrame {
	private JPanel mainContainer;
	private JPanel header;
	
	public MainWindow(Json json) {
		this.setTitle("Twitter Timeline");
		this.setSize(1200, 1200);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(this.getJPanel(json));
		pack();
		this.setVisible(true);
	}
	
	public JPanel getJPanel(Json json) {
		mainContainer = new JPanel(new BorderLayout());
		
		JMenuBar menuBar= new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");
		file.add(new JMenuItem("Open"));
		file.add(new JMenuItem("Exit"));
		menuBar.add(file);
		
		header = new JPanel(new BorderLayout());
		
		JButton refresh = new JButton();
		refresh.setPreferredSize(new Dimension(150,80));
		refresh.setText("Refresh");
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TwitterAuthHeader tah = new TwitterAuthHeader();
				Json json = new Json();
				try {
					tah.getTwitterAuth();
					json.ReadJson(tah.getTwitterAuth());
					MainWindow mw = new MainWindow(json);
				} catch (InvalidKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		header.add(refresh);

		
		ArrayList<Tweet> tweets = new ArrayList<>();
		tweets = json.getTweets();

		JTable tweetsTable = new JTable (new TweetModel(tweets));
		JScrollPane scroll = new JScrollPane(tweetsTable);
				
		tweetsTable.setRowHeight(150);
		mainContainer.add(scroll);
		this.mainContainer.add(header, BorderLayout.NORTH);
		header.setVisible(true);
		return mainContainer;
	}
	
}
