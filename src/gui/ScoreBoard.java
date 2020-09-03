package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ScoreBoard extends JFrame {

	private String gameType;
	private int scorePlayer1;
	private int scorePlayer2;
	private String userName1 = StartMenu.player1.username1;
	private String userName2 = StartMenu.player2.username2;
	private String scoresFile = "src/scores.txt";
	private String comma = ",";
	private String[] headerScores =  {"SCORE", "USERNAME", "LEVEL",  "TYPE"};
	private ArrayList<String[]> scoreList = new ArrayList<String[]>();

	// GUI elements
	private JFrame f = new JFrame("The Best Elves");	
	private JPanel p = new JPanel();
	private JLabel header = new JLabel("ILLUSTRIOUS ELVES", SwingConstants.CENTER);
	private JPanel displayScore= new JPanel();
	private JPanel bottomPane = new JPanel();
	private JButton back = new JButton("Back");
	private JButton exit = new JButton("Exit");


	public ScoreBoard(String gameType, int scoreP1, int scoreP2) {
		this.gameType = gameType;
		scorePlayer1 = scoreP1;
		scorePlayer2 = scoreP2;

		if (gameType.equals("") == false) {
			saveScore();
		}

		p.setLayout(new BorderLayout());
		p.setBackground(Color.cyan);
		p.setPreferredSize(new Dimension(600,400));

		header.setFont(new Font("Serif", Font.BOLD, 30));
		header.setForeground(Color.RED);

		displayScore.setLayout(new GridLayout(6, 4));
		displayScore.setAlignmentX(CENTER_ALIGNMENT);
		displayScore.setSize(new Dimension(300,300));
		displayScore.setBackground(Color.white);
		displayScores();

		back.setBackground(Color.white);
		back.setForeground(Color.black);
		back.setVerticalTextPosition(AbstractButton.BOTTOM);
		back.setHorizontalTextPosition(AbstractButton.LEADING);
		back.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				f.setVisible(false);
			}});

		exit.setBackground(Color.white);
		exit.setForeground(Color.black);
		exit.setVerticalTextPosition(AbstractButton.BOTTOM);
		exit.setHorizontalTextPosition(AbstractButton.TRAILING);
		exit.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}});


		bottomPane.add(back);
		bottomPane.add(exit);

		p.add(header, BorderLayout.PAGE_START);
		p.add(displayScore, BorderLayout.CENTER);
		p.add(bottomPane, BorderLayout.PAGE_END);

		f.setContentPane(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		f.pack();
		f.setLocationRelativeTo(null);

	}

	private void saveScore() {
		try { //saving scores for single player
			if (gameType.equals("Single Player") == true) {
				FileWriter myWriter = new FileWriter(scoresFile, true);
				String line = scorePlayer1 + comma + userName1 + comma + StartMenu.difficultyLevels[StartMenu.difficulty.getValue()] + comma +gameType +  System.lineSeparator(); 
				myWriter.write(line);
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} else { //saving scores for two players
				FileWriter myWriter = new FileWriter(scoresFile, true);
				String line1 = scorePlayer1 + comma + userName1 + comma + StartMenu.difficultyLevels[StartMenu.difficulty.getValue()] + comma +gameType +  System.lineSeparator();
				String line2 = scorePlayer2 + comma + userName2 + comma + StartMenu.difficultyLevels[StartMenu.difficulty.getValue()] + comma +gameType +  System.lineSeparator();
				myWriter.write(line1);
				myWriter.write(line2);
				myWriter.close();
				System.out.println("Successfully wrote to the file.");

			}
		} catch (IOException e) {
			System.out.println("Error saving the scores");
			e.printStackTrace();
		}
	}

	private void sortScores() { 
		// first I sorted the scores in desc order into a temporary arraylist
		ArrayList<String> tempList = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(scoresFile));
			String line = reader.readLine();            
			while (line != null){
				tempList.add(line);                 
				line = reader.readLine();
			}
			reader.close();
			tempList.sort(Comparator.comparingInt(a -> Integer.valueOf(a.substring(0, a.indexOf(comma)))));
			Collections.reverse(tempList); //DESC order
			//then split the line into an array of string values to store into score list
			//as it will make them ready for display
			for (String l: tempList) {
				String[] splitLine = l.split(comma);
				scoreList.add(splitLine);
			}             
		} catch (IOException ue) {
			System.out.println("Error sorting scores");
			ue.printStackTrace();
		}
	}

	private void displayScores() {
		//calling the top 5 entries based on the score value for display
		sortScores();
		ArrayList<String[]> topFive = new ArrayList<String[]>();
		ArrayList<String> topFiveNew = new ArrayList<String>();
		try { //getting the first 5 entries			
			for (int a = 0; a < 5; a++) {
				topFive.add(scoreList.get(a));
			}
			// getting the singular values to fill in each field of the grid
			for (int a = 0; a < topFive.size(); a++) {
				for (int b = 0; b < topFive.get(a).length; b++) {
					topFiveNew.add(scoreList.get(a)[b]);
			}}
			//adding everything as a Jlabel to the panel displayScore
			for (String field : headerScores) {	 //fields			
				JLabel column = new JLabel(field, SwingConstants.CENTER);
				column.setForeground(Color.cyan);
				column.setFont(new Font("Serif", Font.BOLD, 20));
				displayScore.add(column);
			}
			for (String field : topFiveNew) { 
				JLabel value = new JLabel(field,SwingConstants.CENTER);
				value.setForeground(Color.BLACK);
				value.setFont(new Font("Serif", Font.BOLD, 15));
				displayScore.add(value);
			}
		} catch (Exception displayFiles ) {
			System.out.println("Error displaying scores");
		}
	}



	/*	public static void main(String[] args) {
		new ScoreBoard("", 0, 0);
	}*/



}
