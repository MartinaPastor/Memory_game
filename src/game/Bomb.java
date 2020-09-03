package game;

import gui.StartMenu;

public class Bomb{
	
	public static final String BOMB_FRONT = "src/bomb.jpg"; //image of bomb
	public static final int BOMB_ID = 13; //pre-set id and icon
	public int cd; //countdown for bomb
	
	public Bomb() {
		
		switch (StartMenu.difficulty.getValue()) {
		case 0:
			cd = 11; 
			break;
		case 1:
			cd = 26;
			break;
		case 2:
			cd = 36;
		} 
	}

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}
	
	
}
