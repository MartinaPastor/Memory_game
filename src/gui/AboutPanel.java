package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/* simple JFrame class displaying the the rules of the game
 * 
 */
public class AboutPanel extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	
	JFrame f = new JFrame("What a good Elf does on Christmas"); //title of the window
	JPanel p = new JPanel(); // panel containing every element
	JPanel bottomPanel = new JPanel();
	JButton okButton = new JButton(" OK "); //returns to the Start Menu
	JButton runAwayButton = new JButton(" Nope, bye. "); //exits the game
	
	
	public AboutPanel() {
		
	
	p.setLayout(new BorderLayout()); 
	p.setBackground(new Color(80, 201, 215));
	p.setPreferredSize(new Dimension(1000,800));
	p.setAlignmentY(CENTER_ALIGNMENT);
	p.setVisible(true);
	
	//Text header
	JLabel aboutTextHeader = new JLabel("<html> <BR> Hey, you! You over there! </html>");		
	aboutTextHeader.setForeground(Color.BLACK);
	aboutTextHeader.setHorizontalAlignment(JLabel.CENTER);
	aboutTextHeader.setFont(new Font("Serif", Font.ITALIC, 30));
	
	
	// text body
	JLabel aboutTextBody = new JLabel("<html> Thank god you didn't go home yet. We must be quick, Santa needs your help!<BR>"
									+ "You see, he has lost the presents all his little helpers made.<BR>"
									+ "You'd think he'd get more careful with age, but alas we know better. <BR>"
									+ "The presents are in the Vortex. They are split in essence and material form.<BR>"
									+ "There are two versions of everything: one is matter, the other is spirit. <BR>"
									+ "This is bad. This is very bad. Christmas gifts split in two?<BR>"
									+ "The very spirit of Christmas is at stake! <BR>"
									+ "Could you help me connect them? <BR>"
									+ "Only once we have connected them all the gifts will return to their previous complete bliss...<BR>"
									+ "But be careful! The Vortex was created by an evil Old Hag. She is somewhere in there.<BR>"
									+ "You must avoid her at all costs, or bad things will happen.<BR>"
									+ "Also, just so you know... Santa might have lost a bomb there.<BR>"
									+ "A teeny-tiny ticking bomb. <BR>"
									+ "By mistake, he says. <BR>"
									+ "Sure... <BR>"
									+ " <BR>"
									+ "Ready? For the spirit of Christmas!!!!! </html>");		
	aboutTextBody.setForeground(Color.BLACK);
	aboutTextBody.setHorizontalAlignment(JLabel.CENTER);
	aboutTextBody.setFont(new Font("Serif", Font.PLAIN, 16));
	// lowest panel hosting buttons
	bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 	
	bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	bottomPanel.setBackground(new Color(80, 201, 215));
	bottomPanel.setMinimumSize(new Dimension(400, 400)); 
	
	
	okButton.setBackground(Color.white);
	okButton.setForeground(Color.black);
	okButton.addActionListener (new ActionListener() {
		 public void actionPerformed (ActionEvent e) {
			 f.setVisible(false);
		 }});
	
	runAwayButton.setBackground(Color.white);
	runAwayButton.setForeground(Color.black);
	runAwayButton.addActionListener (new ActionListener() {
		 public void actionPerformed (ActionEvent e) {
			 System.exit(0);;
		 }});
	
	bottomPanel.add(okButton);
	bottomPanel.add(runAwayButton);
	
	// JLabels added in the general pane
	p.add(aboutTextHeader, BorderLayout.NORTH);	
	p.add(aboutTextBody, BorderLayout.CENTER);
	p.add(bottomPanel, BorderLayout.SOUTH);
		
	f.setContentPane(p); // panel containing the labels used to display the text
	f.setPreferredSize(new Dimension(600, 600));
	f.pack();
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //not to close both windows
	f.setLocationRelativeTo(null);
	}
	
}

