package api_twitter_java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface extends JFrame 
{
	 private JPanel container = new JPanel();
	 private JTextField jtf = new JTextField("Search");
	 private JLabel top = new JLabel("top");
	 
	 public Interface() 
	 {
	     	this.setTitle("Ma première fenêtre Java");
		    this.setSize(300, 300);
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    this.setLocationRelativeTo(null);
		    container.setBackground(Color.white);
		    container.setLayout(new BorderLayout());
		    JPanel top = new JPanel();
		    Font police = new Font("Arial", Font.BOLD, 14);
		    jtf.setFont(police);
		    jtf.setPreferredSize(new Dimension(150, 30));
		    jtf.setForeground(Color.BLUE);
		    top.add(this.top);
		    top.add(jtf);
		    container.add(top, BorderLayout.NORTH);
		    JPanel pan = new JPanel();
			container.setBackground(Color.ORANGE);
			this.setContentPane(container); 
		 	this.setVisible(true);
	}
}


