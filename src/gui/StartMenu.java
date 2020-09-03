package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.*;


public class StartMenu extends JFrame {
	/* Class rendering the starting menu window of the game 
	 * Start the game here
	 * */


	private static final long serialVersionUID = 1L;

	JFrame f = new JFrame("Christmas Game");
	JPanel p = new JPanel(); // general panel containing everything

	// button linking to the game rules
	JButton aboutGame = new JButton("Elves' Manual"); 
	//central panel
	JPanel centerPanel = new JPanel(); //middle section
	// where the player enters nickname information
	JPanel userNameInput = new JPanel(); 
	JLabel enterUserName = new JLabel("Enter your username: ");
	JTextField userNameField = new JTextField();			
	// difficulty option Label, displayed with JSlider		
	static public JSlider difficulty = new JSlider(0, 2, 1); 
	JLabel chooseDifficulty = new JLabel("Difficulty Level: ");
	JLabel labelDifficulty = new JLabel(difficultyLevels[difficulty.getValue()]);
	// new subpanel containing the option for the opponent selection
	JPanel opponentPanel = new JPanel(); 
	JLabel choiceOpponent = new JLabel("Select your Opponent: "); 
	ButtonGroup opponents = new ButtonGroup(); 
	JRadioButton oppPlayer = new JRadioButton("Another Player");
	JRadioButton oppComputer = new JRadioButton("Computer");
	// panel at the bottom, boxed together horizontally with BoxLayout
	JPanel bottomPanel = new JPanel(); 
	JButton themeChangeB = new JButton("Change Theme");	
	JButton scoresB = new JButton("Top Scores");
	JButton startGameB = new JButton("Start"); 
	JButton exitB = new JButton("Exit");

	public static String[] difficultyLevels = {"Easy", "Medium", "Hard"};	
	public static int currentDifficulty = difficulty.getValue();
	public static int opponent;

	public static GameWindow gw;  
	public static HumanOpp ho;
	public ScoreBoard scoreboard;
	public AboutPanel aboutPanel;
	public PopUp popup;
	public FileChooser fc;
	public static Player1 player1 = new Player1();
	public static Player2 player2 = new Player2();
	public static Images img = new Images();


	public StartMenu() {

		// defining the general panel's properties
		p.setLayout(new BorderLayout()); //layout for ordering the main sections in the panel
		p.setBackground(Color.LIGHT_GRAY);
		p.setPreferredSize(new Dimension(400,400));
		p.setVisible(true);

		//center panel properties
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // things are to be ordered along the y axis
		centerPanel.setSize(200,200 );

		// button calling the frame AboutPanel
		aboutGame.setBackground(Color.white);
		aboutGame.setForeground(Color.blue);
		aboutGame.setSize(new Dimension(100,100));
		aboutGame.setAlignmentX(CENTER_ALIGNMENT);
		// method to call the AboutPanel window
		aboutGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutPanel = new AboutPanel();
			}});

		userNameInput.setLayout(new FlowLayout());
		userNameInput.setPreferredSize(new Dimension(200, 200));		
		userNameInput.setBackground(new Color(80, 201, 215)) ;
		userNameInput.setBorder(new EmptyBorder(90, 30, 00, 15));

		enterUserName.setForeground(Color.white);
		enterUserName.setAlignmentX(CENTER_ALIGNMENT);
		enterUserName.setFont(new Font("Serif", Font.PLAIN, 20));

		userNameField.setForeground(Color.BLACK);
		userNameField.setPreferredSize(new Dimension(150, 25));
		userNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.username1 = userNameField.getText(); // assigns to the player a username
			}
		});

		//game difficulty slider
		difficulty.setMaximumSize(new Dimension(420, 25));

		//difficulty.setBackground(Color.gray);
		difficulty.addChangeListener(new ChangeListener() { 
			public void	stateChanged(ChangeEvent chEv) {
				labelDifficulty.setText(difficultyLevels[difficulty.getValue()]);
			}
		});
		chooseDifficulty.setForeground(Color.BLACK);
		chooseDifficulty.setAlignmentX(CENTER_ALIGNMENT);
		chooseDifficulty.setFont(new Font("Serif", Font.PLAIN, 20));

		opponentPanel.setBorder(new EmptyBorder(15, 00, 00, 05));
		opponentPanel.setLayout(new BoxLayout(opponentPanel, BoxLayout.Y_AXIS));
		opponentPanel.setAlignmentX(CENTER_ALIGNMENT);

		choiceOpponent.setForeground(Color.BLACK);
		choiceOpponent.setAlignmentX(CENTER_ALIGNMENT);

		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 	
		bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		bottomPanel.setBackground(Color.gray);
		bottomPanel.setMinimumSize(new Dimension(400, 400)); 

		themeChangeB.setBackground(Color.white);
		themeChangeB.setForeground(Color.black);
		themeChangeB.addActionListener (new ActionListener() {
			// button to change the cards
			public void actionPerformed (ActionEvent e) {
				img.themeChangeSelected = true;
				img.changeTheme = JOptionPane.showConfirmDialog(null, 
						"Do you want to change the image theme?", 
						"Theme Change",JOptionPane.YES_NO_OPTION);
				if (img.changeTheme == JOptionPane.YES_OPTION) { // changeTheme = 0
					fc = new FileChooser();
				}
			}});

		scoresB.setBackground(Color.white);
		scoresB.setForeground(Color.black);
		scoresB.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				scoreboard = new ScoreBoard("", 0, 0);
			}});

		startGameB.setBackground(Color.white);
		startGameB.setForeground(Color.black);
		startGameB.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (userNameField.getText().equals("") == false ) { //if username has been inserted
					if (opponent == 0) { //computer opponent
						switch (difficulty.getValue()) { //changes properties of gamewindow depending on the difficulty
						/* Easy = tiles 3*3 = 4*2+1S
						 * Medium = tiles 4*4 = 7*2+2S
						 * Difficult = tiles 5*5 = 11*2+3S
						 */					 
						case 0: //Easy
							img.setCards();
							gw = new GameWindow(3,4,0,1);
							gw.levelValue.setText("Easy");
							player1.username1 = userNameField.getText();
							gw.displayUserName.setText(userNameField.getText());
							gw.currentTurn.setText(userNameField.getText() + "'s turn");
							break;

						case 1: // Medium
							img.setCards();
							gw = new GameWindow(4,7,1,1);
							gw.levelValue.setText("Medium");
							player1.username1 = userNameField.getText();
							gw.displayUserName.setText(userNameField.getText());
							gw.currentTurn.setText(userNameField.getText() + "'s turn");							
							break;

						case 2: // Hard
							img.setCards();
							gw = new GameWindow(5,11,2,1);
							gw.levelValue.setText("Hard");
							player1.username1 = userNameField.getText();
							gw.displayUserName.setText(userNameField.getText());
							gw.currentTurn.setText(userNameField.getText() + "'s turn");							
							break;
						}
					}  else if (opponent == 1){
						// for double player mode, it prompts a showInputDialog for the second username
						player2.username2 = JOptionPane.showInputDialog(f, "What's the second player's name?");
						switch (difficulty.getValue()) { 
						case 0: //Easy
							img.setCards();
							ho = new HumanOpp(3,4,0,1);
							ho.levelValue.setText("Easy");
							player1.username1 = userNameField.getText();
							ho.currentTurn.setText(userNameField.getText() + "'s turn");	
							break;

						case 1: // Medium
							img.setCards();
							ho = new HumanOpp(4,7,1,1);
							ho.levelValue.setText("Medium");
							player1.username1 = userNameField.getText();
							ho.currentTurn.setText(userNameField.getText() + "'s turn");	
							break;

						case 2: // Hard
							img.setCards();
							ho = new HumanOpp(5,11,2,1);
							ho.levelValue.setText("Hard");
							player1.username1 = userNameField.getText();	
							ho.currentTurn.setText(userNameField.getText() + "'s turn");	
							break;
						}
					} else {
						new PopUp("Please choose either computer or human as an opponent", false);
					}
				} else { //in case the username was not entered, triggers popup
					new PopUp("Please, enter your username", false);
				}}});

		exitB.setBackground(Color.white);
		exitB.setForeground(Color.black);
		exitB.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}});

		bottomPanel.add(themeChangeB);
		bottomPanel.add(scoresB);
		bottomPanel.add(startGameB);
		bottomPanel.add(exitB);

		opponentPanel.add(choiceOpponent);
		opponentPanel.add(oppPlayer);
		opponents.add(oppPlayer);
		oppPlayer.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				opponent = 1; //human
			}});
		opponentPanel.add(oppComputer);
		opponents.add(oppComputer);
		oppComputer.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				opponent = 0; //computer
			}});


		userNameInput.add(enterUserName);
		userNameInput.add(userNameField);

		// initialising center panel
		centerPanel.add(aboutGame);
		centerPanel.add(userNameInput);
		centerPanel.add(chooseDifficulty);
		centerPanel.add(labelDifficulty);
		centerPanel.add(difficulty);
		centerPanel.add(opponentPanel);

		// adding all to the main panel
		p.add(centerPanel, BorderLayout.PAGE_START); // position in the middle
		p.add(bottomPanel, BorderLayout.PAGE_END);

		f.setContentPane(p);		
		f.setPreferredSize(new Dimension (500, 450));				
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.pack();
		f.setLocationRelativeTo(null);
	}

	//start the game here
	public static void main(String[] args) {
		new StartMenu();
	}
}

