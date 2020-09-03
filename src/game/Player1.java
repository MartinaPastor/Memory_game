package game;

import gui.StartMenu;

public class Player1 {


	public String username1;
	public int scoreP1 = 0;	

	public Player1() {
	}


	public String getUsername1() {
		return username1;
	}


	public void setUsername1(String username1) {
		this.username1 = username1;
	}

	public int addPenalties() {
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP1 -=1;
			break;
		case 1:
			this.scoreP1 -= 2;
			break;
		case 2:
			this.scoreP1 -= 3;
		}
		System.out.println("Score P1: " + this.getCurrentScoreP1());
		return this.getCurrentScoreP1();		
	}

	public int addMatchToScore() {
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP1 += 3;
			break;
		case 1:
			this.scoreP1 += 5;
			break;
		case 2:
			this.scoreP1 += 6;
		}
		System.out.println("Score P1: " + this.getCurrentScoreP1());
		return this.getCurrentScoreP1();	
	}

	public int encountetsOldHag() {
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP1 += 0; //no oldhag for easy mode
			break;
		case 1:
			this.scoreP1 -= 7; //
			break;
		case 2:
			this.scoreP1 -= 14;
		}
		System.out.println("Score P1: " + this.getCurrentScoreP1());
		return this.getCurrentScoreP1();		
	}

	public int endsGame() { // the player who ends the game gets a bonus
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			this.scoreP1 += 10; 
			break;
		case 1:
			this.scoreP1 += 15; 
			break;
		case 2:
			this.scoreP1 += 20;
		}
		System.out.println("P1 ends with: " + this.getCurrentScoreP1());
		return this.getCurrentScoreP1();
	}


	public int getCurrentScoreP1() {
		return scoreP1;
	}


	public void setScoreP1(int scoreP1) {
		this.scoreP1 = scoreP1;
	}

}
