package com.favre.ppe.twitter.gui.java;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.favre.ppe.twitter.json.java.Json;
import com.favre.ppe.twitter.tweet.java.Tweet;

public class MainWindow extends JFrame {
	private JPanel mainContainer;
	private JPanel header;
	
	public MainWindow() {
		this.setTitle("Twitter Timeline");
		this.setSize(1200, 1200);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(this.getJPanel());
		pack();
		this.setVisible(true);
	}
	
	public JPanel getJPanel() {
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
		header.add(refresh);

		
		Json json = new Json();	
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
