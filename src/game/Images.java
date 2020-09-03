package game;

import gui.FileChooser;

public class Images {
	/* Sets the cards to be used
	 * the choice is betweent the already available pics and
	 * the ones to be uploaded by the user
	 */	
	public String[] availablePics = {"res/bells.jpg", "res/berry.jpg",
			"res/santa.jpg", "res/helper.jpg",
			"res/holly.jpg", "res/ribbon.png",
			"res/giftBlue.jpg", "res/giftGreen.jpg",
			"res/giftRed.jpg", "res/giftYellow.jpg",
	"res/giftWhite.jpg"};
	public String[] cardPics;
	public int changeTheme =1; //set as 1 in case people don't click the theme change option
	public boolean themeChangeSelected = false;

	public Images() {


	}
	// sets the cards to be used, called by StartMenu
	public String[] setCards() {
		if (themeChangeSelected = true && changeTheme == 0) {
			return cardPics = FileChooser.imgNames;
		} else  {
			return cardPics = availablePics;
		}
	} 
}
