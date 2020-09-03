package gui;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VisualTile extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	StartMenu sm;
	GameWindow gw = StartMenu.gw;
	HumanOpp ho = StartMenu.ho;
	public String front; //front, displaying the images
	private int idCard;	
	public static ImageIcon back = new ImageIcon("src/back.jpg"); // back of the images
	public final static int SIZE_TILE = 100;
	public  boolean isMatched = false; 	
	public boolean isShowing = false;
	private VisualTile currentCard = null;
		

	public VisualTile(int id, String front, ImageIcon back) {
		idCard = id;
		this.front = front;		
		setSize(new Dimension(VisualTile.SIZE_TILE,VisualTile.SIZE_TILE));
		
		addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				setIcon(new ImageIcon(getFront()));
				currentCard = ((VisualTile) e.getSource());
				currentCard.isShowing = true;
				//System.out.println(currentCard.getID()); uncomment to see the ID of the cards selected
				if (StartMenu.opponent == 0) {
					StartMenu.gw.trigger(currentCard);
				} else {
					StartMenu.ho.trigger(currentCard);
				}
			}});				
	}	

	public int getID(){
		return this.idCard;
	}

	public void setID(int id){
		this.idCard=id;
	}

	public String getFront() {
		return front;
	}

	public void setFront(String front) {
		this.front = front;
	}	

	public ImageIcon getBack() {
		return back;
	}
	
	public boolean setHidden() {
		return this.isShowing = false;
	}

	public static void setBack(ImageIcon back) {
		VisualTile.back = back;
	}

	public void setBackImg() {
		this.setIcon(back);
		this.setHidden();
		repaint();
	}

	public VisualTile getCurrentCard() {
		return currentCard;
	}

	public void setCurrentCard(VisualTile currentCard) {
		this.currentCard = currentCard;
	}
	

	public void setMatched() {
		this.isMatched = true;
		this.setEnabled(false);
	}

}
