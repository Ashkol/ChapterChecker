package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import businesslogic.ChapterInfo;
import gui.GUI;

public class DeleteChaptersButtonListener implements ActionListener {

	private ChapterInfo info;
	private GUI gui;
	private ArrayList<JCheckBox> checkBoxes;
	private JFrame window;
	ArrayList<File> directories;
	
	public DeleteChaptersButtonListener(ChapterInfo info, GUI gui, JFrame window, ArrayList<JCheckBox> checkBoxes, ArrayList<File> directories )
	{
		this.info = info;
		this.gui = gui;
		this.checkBoxes = checkBoxes;
		this.window = window;
		this.directories = directories;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		for (JCheckBox box : checkBoxes)
		{
			if (box.isSelected())
			{
				for (File file : directories)
				{
					// Be careful, if directories name ends with <something>box.getText it also will be included
					if (file.getName().endsWith(box.getText()))
					{
						deleteDirectory(file);	
					}
				}
				
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
		System.out.println(dirToBeDeleted.getName());
		File[] allContents = dirToBeDeleted.listFiles();
		System.out.println(dirToBeDeleted.getPath());
		if (allContents != null) 
		{
			System.out.println("Loop");
			for (File file : allContents) 
			{
	            deleteDirectory(file);
	        }
	    }
		
		return dirToBeDeleted.delete();
	}
}