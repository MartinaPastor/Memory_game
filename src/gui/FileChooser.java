package gui;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.image.RenderedImage;

import javax.swing.filechooser.*; 

public class FileChooser extends JFrame{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Jlabel to show the files user selects 
	private JFrame f = new JFrame("Choose your jpg images");
	private JPanel p = new JPanel();
	private JPanel topPane = new JPanel();
	private JPanel bottomPane = new JPanel();
	private JButton selectButton = new JButton("Select");
	private JButton back = new JButton("Back");
	private String savingFolder =  "src/uploadedImgs/";
	private String FORMAT_NAME = "jpg";
	public static String[] imgNames = new String[11];
	private JLabel label = new JLabel();

	FileChooser(){ 
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	
		p.setPreferredSize(new Dimension(500, 250));
		p.setBackground(new Color(80, 100, 215));

		topPane.setBackground(Color.cyan);
		topPane.setPreferredSize(new Dimension(500, 200));
		JLabel instructions = new JLabel("<html> You can choose the presents to find!<BR>"
				+ "The images need be 11, <BR>"
				+ "JPG format, dimension 100x100 at max. <BR>"
				+ "Click on the select button!<BR>"
				+ "When finished, you may go back.</html>", JLabel.CENTER);		
		instructions.setForeground(Color.BLACK);
		instructions.setHorizontalAlignment(JLabel.CENTER);
		instructions.setFont(new Font("Serif", Font.BOLD, 16));

		label.setBorder(new EmptyBorder(50, 0, 0, 0));

		bottomPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		bottomPane.setPreferredSize(new Dimension(500, 50));
		bottomPane.setBackground(new Color(80, 100, 215));


		selectButton.setFont(new Font("Serif", Font.BOLD, 16));
		selectButton.setAlignmentX(CENTER_ALIGNMENT);
		selectButton.setForeground(Color.BLACK);
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// first it automatically empties the folder uploadedImgs
				File dir= new File(savingFolder);
				for(File i: dir.listFiles()) 
					if (!i.isDirectory()) 
						i.delete(); 
				//then displays the filechooser window to select files
				JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				j.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg"));
				j.setMultiSelectionEnabled(true); 
				int option = j.showOpenDialog(f);
				if(option == JFileChooser.APPROVE_OPTION){
					//proceeds to save the images, and stored the names to be used later
					File[] files = j.getSelectedFiles();
					int i = 0;
					String fileNames = ""; //names for label
					for(File file: files){						
						imgNames[i] = savingFolder +file.getName();
						i++;						
						fileNames += file.getName() + ", ";
						RenderedImage bi = null;
						try {
							bi = ImageIO.read(file);
							ImageIO.write(bi, FORMAT_NAME, new File(savingFolder + file.getName()));
						} catch (IOException e) {
							System.out.println("The image " + file.getName() + " could not be saved.");
						}
					}
					label.setText("Images Selected: " + fileNames);
				}else{
					label.setText("Select command canceled");
		}}});
		// button to go back to the main menu
		back.setBackground(Color.white);
		back.setForeground(Color.black);
		back.setVerticalTextPosition(AbstractButton.BOTTOM);
		back.setHorizontalTextPosition(AbstractButton.TRAILING);
		back.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				f.setVisible(false);
			}});

		topPane.add(instructions, BorderLayout.NORTH);
		topPane.add(label,BorderLayout.PAGE_END);
		bottomPane.add(selectButton);
		bottomPane.add(back);
		p.add(topPane, BorderLayout.NORTH);
		p.add(bottomPane, BorderLayout.SOUTH);
		f.setVisible(true);
		f.setContentPane(p);		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
	} 
	
	/*testing
	public static void main(String args[]) { 
		new FileChooser();
	}*/
} 
