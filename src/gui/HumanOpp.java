  package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import game.Bomb;
import game.Images;
import game.OldHag;
import game.Player1;
import game.Player2;

public class HumanOpp extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*class rendering the game vs actual opponent
	 */
	// some of the classes needed
	public Player1 p1 = StartMenu.player1;
	public Player2 p2 = StartMenu.player2;
	public VisualTile tile;
	PopUp popup;
	public Images img = StartMenu.img;;
	public OldHag oldHag;
	public Bomb bomb = new Bomb();
	public ScoreBoard scoreboard;

	public int xTiles;	//number of tiles on x axis
	public int nImgCards; //number of normal non special cards
	public int nOldHag;
	public int nBomb;
	private VisualTile card1 = null;
	private VisualTile card2 = null;
	private int matchesMadeP1 = 0;
	private int matchesMadeP2 = 0;
	private int clickCount = 0;
	private int activePlayer = 1; // values 1 and 2, Player 1 starts
	private Timer oldHagTimer;
	private int bombTime = bomb.getCd();
	private ArrayList<VisualTile> allCards;

	// JFrame components
	private JFrame f = new JFrame("Christmas Game");
	private JPanel p = new JPanel(); //general panel
	private JPanel panelMap = new JPanel();  //grid of tiles
	private JPanel sideMenu = new JPanel(); // sidemenu where all the info is stored
	private JPanel sideMenu2 = new JPanel();
	public JLabel currentTurn = new JLabel();
	public JLabel currentLevL = new JLabel("Difficulty: "); //current level label
	public JLabel levelValue = new JLabel();
	private JButton viewScoreBoard = new JButton("View High Scores");
	private JLabel displayScoreP1;
	private JLabel displayScoreP2;
	private JLabel timerLabel = new JLabel("Time remaining:");
	private JLabel timeLabel = new JLabel(bombTime + " secs.");
	private JLabel displayGameType = new JLabel("Double Player Mode");
	private JButton exit = new JButton("Exit");

	private Timer cardTimer;
	private Timer bombTimer;
	private HashMap<Integer, String> idToImg = new HashMap<Integer, String>();
	private ArrayList<Integer> idCards= new ArrayList<Integer>();


	public HumanOpp(int xTiles, int nImgCards, int nOldHag, int nBomb) {
		/* xTiles = number of tiles on x axis
		 * nImgCards = number of unique normal cards
		 * nOldHag = number of instances of old hag card
		 * nBomb = number of instances of bomb card		 * 
		 */
		this.xTiles = xTiles;
		this.nImgCards = nImgCards;
		this.nOldHag = nOldHag;
		this.nBomb = nBomb;

		// defining general pane
		p.setLayout(new BorderLayout()); //layout for ordering the main sections in the panel
		p.setMaximumSize(new Dimension(800,500));
		p.setMinimumSize(new Dimension(700,300));
		p.setVisible(true);
		//sideMenu
		sideMenu.setLayout(new BorderLayout());	//sidemenu
		sideMenu.setMaximumSize(new Dimension(300,550));
		sideMenu.setMinimumSize(new Dimension(300,300));
		sideMenu.setBorder(new EmptyBorder(10, 20, 10, 15));
		// additional Jlabel to group things together
		sideMenu2.setLayout(new BoxLayout(sideMenu2, BoxLayout.Y_AXIS));
		sideMenu2.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		sideMenu2.setBackground(new Color(80, 201, 215));

		currentTurn.setText(getActivePlayer() + "'s turn"); 
		currentTurn.setFont(new Font("Serif", Font.BOLD, 18));
		currentTurn.setForeground(Color.BLACK);
		currentTurn.setBorder(new EmptyBorder(5, 25, 5, 25));

		// jbutton redirecting to high scores		
		viewScoreBoard.setBackground(Color.WHITE);
		viewScoreBoard.setForeground(Color.BLUE);
		viewScoreBoard.setSize(new Dimension(100,100));
		viewScoreBoard.setAlignmentX(LEFT_ALIGNMENT);
		viewScoreBoard.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				scoreboard = new ScoreBoard("", 0, 0);
			}});

		//jlabel introducing current level
		currentLevL.setForeground(Color.BLACK);
		currentLevL.setForeground(Color.BLUE);	
		currentLevL.setBorder(new EmptyBorder(20, 40, 00, 15));
		//Jtext displaying current level
		levelValue.setText(StartMenu.difficultyLevels[StartMenu.currentDifficulty]);
		levelValue.setFont(new Font("Serif", Font.BOLD, 20));
		levelValue.setForeground(Color.BLACK);
		levelValue.setBorder(new EmptyBorder(0, 40, 10, 15));

		displayScoreP1 = new JLabel("Score P1: " + p1.getCurrentScoreP1());
		displayScoreP1.setFont(new Font("Serif", Font.BOLD, 16));
		displayScoreP1.setForeground(Color.BLACK);
		displayScoreP1.setBorder(new EmptyBorder(0, 20, 05, 15));

		displayScoreP2 = new JLabel("Score P2: " + p2.getCurrentScoreP2());
		displayScoreP2.setFont(new Font("Serif", Font.BOLD, 16));
		displayScoreP2.setForeground(Color.BLACK);
		displayScoreP2.setBorder(new EmptyBorder(0, 20, 10, 15));

		displayGameType.setBackground(Color.WHITE);
		displayGameType.setForeground(Color.BLUE);
		displayGameType.setSize(new Dimension(100,100));
		displayGameType.setAlignmentX(LEFT_ALIGNMENT);
		displayGameType.setBorder(new EmptyBorder(0, 10, 10, 15));

		timerLabel.setFont(new Font("Serif", Font.BOLD, 16));
		timerLabel.setForeground(Color.RED);
		timerLabel.setBorder(new EmptyBorder(5, 15, 0, 2));
		timerLabel.setVisible(false); //to be made visible when bomb is picked

		timeLabel.setFont(new Font("Serif", Font.BOLD, 14));
		timeLabel.setForeground(Color.RED);
		timeLabel.setBorder(new EmptyBorder(0, 36, 10, 0));
		timeLabel.setVisible(false); //to be made visible when bomb is picked

		exit.setBackground(Color.white);
		exit.setForeground(Color.black);
		exit.setAlignmentX(LEFT_ALIGNMENT); // does not align
		exit.setBorder(new EmptyBorder(0, 55, 00, 55));
		exit.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}});

		//panel map where the actual game takes place
		panelMap.setLayout(new GridLayout(xTiles,xTiles));
		panelMap.setMaximumSize(new Dimension(500,500));
		panelMap.setMinimumSize(new Dimension(300,300));

		//creating the cards
		createCards();		
		//adding tiles to the grid with a for loop
		fillGrid(panelMap);	

		// card timer for matching	
		cardTimer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					tryMatch();
				} catch (Exception matchcards) {
					System.out.println("Error starting the matching process");
				} return;
			}});	        
		cardTimer.setRepeats(false);

		// bomb time set on difficulty
		bombTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					triggerBomb();
				} catch (Exception bombTimer) {
					System.out.println("Error with bomb trigger");
				} return;	           
			}});
		bombTimer.setRepeats(true);	//I made use of the repeats to update the timer

		//adding all the components		
		sideMenu2.add(currentTurn);
		sideMenu2.add(displayGameType);
		sideMenu2.add(viewScoreBoard);
		sideMenu2.add(currentLevL);
		sideMenu2.add(levelValue);
		sideMenu2.add(displayScoreP1);
		sideMenu2.add(displayScoreP2);
		sideMenu2.add(timerLabel);
		sideMenu2.add(timeLabel);
		sideMenu2.add(exit);		

		sideMenu.add(sideMenu2, BorderLayout.CENTER);

		p.add(panelMap, BorderLayout.WEST);
		p.add(sideMenu, BorderLayout.EAST);

		f.setContentPane(p);
		f.setMaximumSize(new Dimension (800, 800));	
		f.setMinimumSize(new Dimension(600,300));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes all windows
		f.setResizable(false);
		f.setVisible(true);
		f.pack();
		f.setLocationRelativeTo(null);
	}	


	private void createCards() {
		//hashmap for IDs and Strings
		for (int a = 0; a < nImgCards; a++ ) { 
			idToImg.put(a, img.cardPics[a]);				
		}		
		for (int b = 0; b < nImgCards; b++) {//normal cards
			idCards.add(b);
			idCards.add(b);
		}
		for(int a = 0; a < nOldHag; a++) { //special card1
			idToImg.put(OldHag.OLD_HAG_ID, OldHag.OLD_HAG_FRONT);
			idCards.add(OldHag.OLD_HAG_ID);
		}
		//special card2
		idToImg.put(Bomb.BOMB_ID, Bomb.BOMB_FRONT);
		idCards.add(Bomb.BOMB_ID);
	}

	//method to fill the grid of tiles
	private void fillGrid(JPanel p) {				
		Collections.shuffle(idCards); //randomises location of id on the map;
		allCards = new ArrayList<VisualTile>();
		//filling the grid  of tiles with IDs to be connected to images
		for (int IDcard : idCards) {
			VisualTile tile = new VisualTile(IDcard, null, null);	
			allCards.add(tile);
			tile.setID(IDcard);
			tile.setFront(idToImg.get(IDcard));	
			tile.setBackImg(); //sets the images on their back
		}
		for (VisualTile tile : allCards) {
			p.add(tile);
		}
		p.repaint();
	}	

	protected void trigger(VisualTile c) {			
		if (clickCount != 2) { //if the count is not 2 it starts by adding 1
			clickCount++;
			if (c.getID() != 12 && c.getID() != 13) { //the case of normal cards
				if(clickCount == 1) {
					card1 = c;
					return;
				} else if (clickCount == 2) {
					card2 = c;
					cardTimer.start(); //triggers the timer, and tryMatch()
					return;
				//if statements for the special cards		
				}} else if (c.getID() == OldHag.OLD_HAG_ID) {      	   
					if (clickCount == 2) { //in case there is a normal card already picked
						card1.setBackImg();
						card1 = null;
					}
					popup = new PopUp("<html> Oh no! You met the Old Hag!<BR>"
							+ "You eat her coal candy and <BR>"
							+ "get an awful stomachache )= <BR>"
							+ "Be careful! </html>", false);
					clickCount = 0;
					if (activePlayer == 1) {
						p1.encountetsOldHag();
						displayScoreP1.setText("Score P1: " + p1.getCurrentScoreP1());
					} else {
						p2.encountetsOldHag();
						displayScoreP2.setText("Score P2: " + p2.getCurrentScoreP2());
					}
					oldHagTimer = new Timer(1000, new ActionListener(){
						public void actionPerformed(ActionEvent ae){
							c.setBackImg();
						}});
					oldHagTimer.setRepeats(false);
					oldHagTimer.start();
					switchTurn();
					return;
				} else if (c.getID() == Bomb.BOMB_ID) {
					bombTimer.start();
					switch (StartMenu.difficulty.getValue()) { //timer duration differs depending on levels
					case 0:
						popup = new PopUp("<html> Oh no! You found the bomb!<BR>"
								+ " You both have 10 seconds <BR>"
								+ "to find the rest of the presents!<BR>" 
								+ "Fast!! </html>", false); 
						break;
					case 1:
						popup = new PopUp("<html> Oh no! You found the bomb!<BR>"
								+ " You both have 25 seconds <BR>"
								+ "to find the rest of the presents!<BR>" 
								+ "Fast!! </html>", false);
						break;
					case 2:
						popup = new PopUp("<html> Oh no! You found the bomb!<BR>"
								+ " You both have 25 seconds <BR>"
								+ "to find the rest of the presents!<BR>" 
								+ "Fast!! </html>", false);
					}

					if (clickCount == 2) {        		
						card1.setBackImg();
						card1 = null;        		
					}
					switchTurn();
					clickCount = 0;
				}}       		
	}	

	//method to check whether the cards match, called by the timer
	private void tryMatch() { 
		clickCount = 0;	
		if (activePlayer == 1) {
			if (card1.getID() == card2.getID()  && card1 != card2) {
				card1.setMatched();
				card2.setMatched();
				matchesMadeP1++;
				p1.addMatchToScore();
				displayScoreP1.setText("Score P1: " + p1.getCurrentScoreP1());
				if (matchesMadeP2+matchesMadeP1 == nImgCards){
					p1.endsGame();
					scoreboard = new ScoreBoard("Double Player", p1.getCurrentScoreP1(), p2.getCurrentScoreP2());
					getWinningPlayer();
					disableCards();
				}
			} else {
				card1.setBackImg();
				card2.setBackImg();
				p1.addPenalties();
				displayScoreP1.setText("Score P1: " + p1.getCurrentScoreP1());
			}
		} else if (activePlayer == 2) {
			if (card1.getID() == card2.getID() && card1 != card2) {
				card1.setMatched();
				card2.setMatched();
				matchesMadeP2++;
				p2.addMatchToScore();
				displayScoreP2.setText("Score P2: " + p2.getCurrentScoreP2());
				if (matchesMadeP2+matchesMadeP1 == nImgCards){
					p2.endsGame();
					scoreboard = new ScoreBoard("Double Player", p1.getCurrentScoreP1(), p2.getCurrentScoreP2());
					getWinningPlayer();
					disableCards();
				}
			} else {
				card1.setBackImg();
				card2.setBackImg();
				p2.addPenalties();
				displayScoreP2.setText("Score P2: " + p2.getCurrentScoreP2());
			}
		}
		switchTurn();
	}

	private String getActivePlayer() {
		// returns the active player's username
		if (activePlayer == 1) {
			return StartMenu.player1.username1;
		} else {
			return StartMenu.player2.username2;
		}
	}

	private void switchTurn() {
		//switches turn between the two players
		if (activePlayer == 1) {
			activePlayer++;
			currentTurn.setText(getActivePlayer() + "'s turn");
		} else {
			activePlayer--;
			currentTurn.setText(getActivePlayer() + "'s turn");
		}
	}

	public void triggerBomb() {
		// starts the countdown
		timerLabel.setVisible(true);
		timeLabel.setVisible(true);
		bombTime--;
		if (bombTime >= 0) {
			timeLabel.setText(bombTime + " secs.");
		} else {
			bombTimer.stop();
		} 
		if (bombTime == 0 && matchesMadeP2+matchesMadeP1 != nImgCards) {
			scoreboard = new ScoreBoard("Double Player", p1.getCurrentScoreP1(), p2.getCurrentScoreP2());
			popup = new PopUp("<html> TIME'S UP!! <BR>"
					+ "The bomb exploded.<BR>"
					+ "And you two with it.<BR>"
					+ "The presents are lost forever )= </html>", false);
			disableCards();
		}
	}
	
	private void getWinningPlayer() {
		if (p1.getCurrentScoreP1() > p2.getCurrentScoreP2()) {
			popup = new PopUp("<html> Congratulations " + StartMenu.player1.username1 +"! <BR>"
					+ "You won with " + p1.getCurrentScoreP1() + " points! <BR>"
					+ " And the presents are connected! </html>", false);
		} else if (p1.getCurrentScoreP1() < p2.getCurrentScoreP2()) {
			popup = new PopUp("<html> Congratulations " + StartMenu.player2.username2 +"! <BR>"
					+ "You won with " + p2.getCurrentScoreP2() + " points! <BR>"
					+ " And the presents are connected! </html>", false);
		} else if (p1.getCurrentScoreP1() == p2.getCurrentScoreP2()){
			popup = new PopUp("<html> Congratulations " + StartMenu.player1.username1 +" and " + StartMenu.player2.username2 + "!<BR>"
					+ "You two tied with " + p2.getCurrentScoreP2() + " points! <BR>"
					+ "And the presents are connected! </html>", false);
		}
	}

	private void disableCards() {
		//disables all cards, to be used when the game ends
		for ( VisualTile card : allCards) { 
			card.setEnabled(false);
		}
	}

}
