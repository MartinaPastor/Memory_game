package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import game.*;


@SuppressWarnings("serial")
public class GameWindow extends JFrame{
	/* Game window rendering the single player mode game 
	 */

	// some of the classes needed
	public Player1 p1 = StartMenu.player1;
	public Images img = StartMenu.img;
	public VisualTile tile;
	public OldHag oldHag;
	public Bomb bomb = new Bomb();
	public static ScoreBoard scoreboard; // should not be static
	public StartMenu sm;
	public PopUp popup;

	// 
	public int xTiles;	//number of tiles on x axis
	public int nImgCards; //number of normal non special cards
	public int nOldHag;
	public int nBomb;
	private VisualTile card1 = null;
	private VisualTile card2 = null;
	private int clickCount = 0;
	private int matchesMade = 0;
	private int computerMatches = 0;
	private int bombTime = bomb.getCd();
	private boolean playersTurn = true; //p1 starts
	private boolean gameOver = false;

	// JFrame components
	private JFrame f = new JFrame("Christmas Game");
	private JPanel p = new JPanel(); //general panel
	private JPanel panelMap = new JPanel();  //grid of tiles
	private JPanel sideMenu = new JPanel(); // sidemenu where all the info is stored
	private JPanel sideMenu2 = new JPanel();
	public JLabel displayUserName = new JLabel();  //player's username
	public JLabel currentTurn = new JLabel();
	public JLabel currentLevL = new JLabel("Difficulty: "); //current level label
	public JLabel levelValue = new JLabel();
	private JButton viewScoreBoard = new JButton("View High Scores");
	private JLabel displayCurrentScore;
	private JLabel timerLabel = new JLabel("Time remaining:");
	private JLabel timeLabel = new JLabel(bombTime + " secs.");
	private JLabel displayGameType = new JLabel("Single Player Mode");
	private JButton exit = new JButton("Exit");

	private Timer cardTimer;
	private Timer bombTimer;
	private Timer oldHagTimer;
	private Timer computersTimer;
	private HashMap<Integer, String> idToImg = new HashMap<Integer, String>();
	private ArrayList<Integer> idCards= new ArrayList<Integer>();
	private ArrayList<VisualTile> allCards;


	public GameWindow(int xTiles, int nImgCards, int nOldHag, int nBomb) {
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
		sideMenu.setMaximumSize(new Dimension(400,550));
		sideMenu.setMinimumSize(new Dimension(300,300));
		sideMenu.setBorder(new EmptyBorder(10, 20, 10, 15));

		// additional Jlabel to group things together
		sideMenu2.setLayout(new BoxLayout(sideMenu2, BoxLayout.Y_AXIS));
		sideMenu2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		sideMenu2.setBackground(new Color(80, 201, 215));


		displayUserName.setText(StartMenu.player1.username1); 
		displayUserName.setFont(new Font("Serif", Font.BOLD, 22));
		displayUserName.setForeground(Color.BLACK);
		displayUserName.setBorder(new EmptyBorder(5, 10, 5, 5));

		currentTurn.setText(StartMenu.player1.username1 + "'s turn"); 
		currentTurn.setFont(new Font("Serif", Font.BOLD, 18));
		currentTurn.setForeground(Color.BLACK);
		currentTurn.setBorder(new EmptyBorder(5, 5, 5, 25));

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

		displayCurrentScore = new JLabel("Score = " + p1.getCurrentScoreP1());
		displayCurrentScore.setFont(new Font("Serif", Font.BOLD, 16));
		displayCurrentScore.setForeground(Color.BLACK);
		displayCurrentScore.setBorder(new EmptyBorder(0, 20, 05, 15));

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
				triggerBomb();							  
			}});
		bombTimer.setRepeats(true);	//I made use of the repeats to update the timer

		//adding all the components		
		sideMenu2.add(displayUserName);
		sideMenu2.add(currentTurn);
		sideMenu2.add(displayGameType);
		sideMenu2.add(viewScoreBoard);
		sideMenu2.add(currentLevL);
		sideMenu2.add(levelValue);
		sideMenu2.add(displayCurrentScore);
		//sideMenu2.add(displayErrors);
		sideMenu2.add(timerLabel);
		sideMenu2.add(timeLabel);
		sideMenu2.add(exit);		

		sideMenu.add(sideMenu2, BorderLayout.LINE_END);

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
		// triggers the reaction of the tiles to the click
		if (clickCount != 2) { //if the count is not 2 it starts by adding 1
			clickCount++;
			if (c.getID() != 12 && c.getID() != 13) { //the case of normal cards
				if(clickCount == 1) {
					card1 = c;
					if (!playersTurn) {
						computersMove();
					}
					return;
				} else if (clickCount == 2) {
					card2 = c;
					cardTimer.start(); //triggers the timer, and tryMatch()
					return;		
				}
			//if statements for the special cards
			} else if (c.getID() == OldHag.OLD_HAG_ID) {  
				if (clickCount == 2) { //in case there is a normal card already picked
					card1.setBackImg();
					card1 = null;
				}					
				clickCount = 0;
				if (playersTurn) {
					p1.encountetsOldHag();
					displayCurrentScore.setText("Score = " + p1.getCurrentScoreP1());
					popup = new PopUp("<html> Oh no! You met the Old Hag!<BR>"
							+ "Contrary to the AI, who doesn't eat,<BR>"
							+ "you eat her coal candy and get a stomachache. <BR>" 
							+ "You also lose points. Be careful! </html>", false);
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
				if (clickCount == 2) {        		
					card1.setBackImg();
					card1 = null;        		
				}
				clickCount = 0; //resets the clickcount to zero
				bombTimer.start();
				switch (StartMenu.difficulty.getValue()) { //timer duration differs depending on levels
				case 0:
					popup = new PopUp("<html> Oh no! It's the bomb!<BR>"
							+ " You have 10 seconds <BR>"
							+ "to find the rest of the presents!<BR>" 
							+ "Fast!! </html>", false); 
					break;
				case 1:
					popup = new PopUp("<html> Oh no! It's the bomb!<BR>"
							+ " You have 25 seconds <BR>"
							+ "to find the rest of the presents!<BR>" 
							+ "Fast!! </html>", false);
					break;
				case 2:
					popup = new PopUp("<html> Oh no! It's the bomb!<BR>"
							+ " You have 25 seconds <BR>"
							+ "to find the rest of the presents!<BR>" 
							+ "Fast!! </html>", false);
				}
				switchTurn();
			}
		}
	}	

	//method to check whether the cards match, called by the timer
	private void tryMatch() { 
		clickCount = 0;
		if (playersTurn) { //player's case
			if (card1.getID() == card2.getID() && card1 != card2) {
				card1.setMatched();
				card2.setMatched();
				matchesMade++;
				p1.addMatchToScore();
				displayCurrentScore.setText("Score = " + p1.getCurrentScoreP1());				
				if (matchesMade + computerMatches == nImgCards){
					p1.endsGame();
					displayCurrentScore.setText("Score = " + p1.getCurrentScoreP1());
					gameOver = true;
					scoreboard = new ScoreBoard("Single Player", p1.getCurrentScoreP1(), 0);
					popup = new PopUp("<html> Congratulations! <BR>"
							+ "You won with " + p1.getCurrentScoreP1() + " points! <BR>"
							+ "You have found all the presents!</html>", false);
				}
			} else { //if they don't match
				card1.setBackImg();
				card2.setBackImg();
				p1.addPenalties();
				displayCurrentScore.setText("Score = " + p1.getCurrentScoreP1());				
			} 
		} else	{ //AI's case
			if (card1.getID() == card2.getID() && card1 != card2) {
				card1.setMatched();
				card2.setMatched();
				computerMatches++;
				if (matchesMade + computerMatches == nImgCards){
					gameOver = true;
					scoreboard = new ScoreBoard("Single Player", p1.getCurrentScoreP1(), 0);
					popup = new PopUp("<html> Haha! <BR>"
							+ "The computer ended the game! <BR>"
							+ "You did your best with " + p1.getCurrentScoreP1() + " points! <BR>"
							+ "And all the presents are found!</html>", false);
					disableCards();
				}
			} else {
				card1.setBackImg();
				card2.setBackImg();
			}
		}
		switchTurn();
	}

	public void triggerBomb() {
		// starts the countdown	after clicking on the bomb
		timerLabel.setVisible(true);
		timeLabel.setVisible(true);
		bombTime--;
		if (bombTime >= 0) {
			timeLabel.setText(bombTime + " secs.");
		} else {
			bombTimer.stop();
		} 
		if (bombTime == 0 && matchesMade + computerMatches != nImgCards) {
			gameOver = true;			
			scoreboard = new ScoreBoard("Single Player", p1.getCurrentScoreP1(), 0);
			popup = new PopUp("<html> TIME'S UP!! <BR>"
					+ "The bomb exploded.<BR>"
					+ "And you with it.<BR>"
					+ "The presents are lost forever )= </html>", false);
			disableCards();
		}
	}

	//switcher turns between player and AI, updating the label
	public void switchTurn() {
		if (playersTurn) {
			setPlayersTurn(false);
			computersMove();
			currentTurn.setText(getActivePlayer() + "'s turn");
		} else {
			setPlayersTurn(true);
			currentTurn.setText(getActivePlayer() + "'s turn");
		}		
	}
	// formulates the computer's actions
	private void computersMove() {		
		// first it takes away from the VisualTile arraylist of cards the ones who are visible or matched
		ArrayList<VisualTile> cardsLeft = new ArrayList<>(allCards);
		cardsLeft.removeIf(tile -> tile.isMatched);
		cardsLeft.removeIf(tile -> tile.isShowing);
		cardsLeft.removeIf(tile -> tile.getID()==13); //computer can't choose bomb
		computersTimer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				// then clicks on a random tile. No complex AI algorithm here
				Random rand = new Random();
				VisualTile randomTile = cardsLeft.get(rand.nextInt(cardsLeft.size()));
				randomTile.doClick();
			}});	        
		computersTimer.setRepeats(false);
		if (!gameOver) { //if the game is not over yet
			computersTimer.start();	
		}
	}

	// returns the current player
	private String getActivePlayer() {
		if (isPlayersTurn()) {
			return StartMenu.player1.username1;
		} else {
			return "AI";
		}
	}

	private void disableCards() {
		//disables all cards, to be used when the game ends
		for ( VisualTile card : allCards) { 
			card.setEnabled(false);
		}
	}


	private boolean isPlayersTurn() {
		return playersTurn;
	}


	private void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}

	/* quick testing method
	 * public static void main(String[] args) {
		new GameWindow(4,7,1,1);
	}*/

}

