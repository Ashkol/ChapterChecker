package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import businesslogic.ChapterInfo;
import gui.GUI;

public class RemoveComicsButtonListener implements ActionListener {

	private ChapterInfo info;
	private GUI gui;
	private ArrayList<JCheckBox> checkBoxes;
	private ArrayList<JCheckBox> optionsCheckBoxes;
	private JFrame window;
	
	
	public RemoveComicsButtonListener(ChapterInfo info, GUI gui, ArrayList<JCheckBox> checkBoxes, ArrayList<JCheckBox> optionsCheckBoxes, JFrame window)
	{
		this.info = info;
		this.gui = gui;
		this.checkBoxes = checkBoxes;
		this.window = window;
		this.optionsCheckBoxes = optionsCheckBoxes;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		for (JCheckBox box : checkBoxes)
		{
			if (box.isSelected())
			{
				info.removeComicsByTitle(box.getText());
			
				// Deleting folder with chapters
				if (optionsCheckBoxes != null && optionsCheckBoxes.get(0).isSelected())
				{
					File dirToBeDeleted = new File(box.getText());	// box.getText() returns comics title, it's also its directory name
					deleteDirectory(dirToBeDeleted);
				}
				// Be VERY CAREFUL, if there's another directory with the same name as comics' title, it will be deleted with all of its content
			}
		}
		
		gui.updateGUI();
		info.updateDatabase();
		window.dispose();
	}

	// Deletes directory with its contents
	private boolean deleteDirectory(File dirToBeDeleted)
	{
		System.out.println("Deleting");
		File[] allContents = dirToBeDeleted.listFiles();
		if (allContents != null) 
		{
			for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
		
		return dirToBeDeleted.delete();
	}
	
}
