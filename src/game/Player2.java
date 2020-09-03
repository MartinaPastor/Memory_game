package game;

import gui.StartMenu;

public class Player2 {


	public String username2;
	public int scoreP2 = 0;

	public Player2() {
	}

	
	public String getUsername2() {
		return username2;
	}
	
	
	public void setUsername2(String username2) {
		this.username2 = username2;
	}

	public int addPenalties() {
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP2 -=1;
			break;
		case 1:
			this.scoreP2 -= 2;
			break;
		case 2:
			this.scoreP2 -= 3;
		}
		System.out.println("Score P2: " + this.getCurrentScoreP2());
		return this.getCurrentScoreP2();		
	}
	
	public int addMatchToScore() {
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP2 += 3;
			break;
		case 1:
			this.scoreP2 += 5;
			break;
		case 2:
			this.scoreP2 += 6;
		}		
		System.out.println("Score P2: " + this.getCurrentScoreP2());
		return this.getCurrentScoreP2();	
	}
	
	public int encountetsOldHag() {
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP2 += 0; //no oldhag for easy mode
			break;
		case 1:
			this.scoreP2 -= 8; //
			break;
		case 2:
			this.scoreP2 -= 16;
		}
		System.out.println("Score P2: " + this.getCurrentScoreP2());
		return this.getCurrentScoreP2();		
	}
	
	public int endsGame() { 
		// player2 gets a lower bonus, to account for player 1 playing first
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP2 += 5; 
			break;
		case 1:
			this.scoreP2 += 10; 
			break;
		case 2:
			this.scoreP2 += 15;
		}
		
		System.out.println("P2 ens with: " + this.getCurrentScoreP2());
		return this.getCurrentScoreP2();
	}
	
	
	public int getCurrentScoreP2() {
		return scoreP2;
	}


	public void setScoreP2(int scoreP2) {
		this.scoreP2 = scoreP2;
	}


}
