package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PopUp extends JFrame{
	/**  frame to display messages
	 * 	 the message is to be added in the constructor
	 * 	 it has a hasJfield value to allow reception of text
	 */
	
	JFrame f = new JFrame("POPUP");
	JPanel p = new JPanel();
	JLabel popUpMessage = new JLabel();
	JTextField insertInput = new JTextField();
	String popUpInput;
	JButton okButton = new JButton("OK");
	
	
	public String message;
	boolean hasJField;
	
	
	public PopUp(String message, boolean jField) {
		this.message = message;
		this.hasJField = jField;
				
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	
		p.setPreferredSize(new Dimension(350, 150));
		p.setBackground(new Color(80, 100, 215));
		
		popUpMessage.setHorizontalAlignment(JLabel.CENTER);
		popUpMessage.setForeground(Color.BLACK);
		popUpMessage.setPreferredSize(new Dimension(200,150));
		popUpMessage.setText(message);
		popUpMessage.setFont(new Font("Serif", Font.BOLD, 16));
		popUpMessage.setAlignmentX(CENTER_ALIGNMENT);
		
		if (jField) {
			insertInput.setForeground(Color.BLACK);
			insertInput.setPreferredSize(new Dimension(150, 25));
			insertInput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					popUpInput = insertInput.getText();
				}
			});	
		} else {
			insertInput.setVisible(false);
		};
		
		
		okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				 f.setVisible(false);			
			}});
			
		p.add(popUpMessage);
		p.add(insertInput);
		p.add(okButton);
		f.setVisible(true);
		f.setContentPane(p);		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		
	}

}

