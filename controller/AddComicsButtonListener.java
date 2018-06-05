package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.GUI;

public class AddComicsButtonListener implements ActionListener {

	private ChapterInfo info;
	private GUI gui;
	private ArrayList<JTextField> textFields;
	private JFrame window;
	
	AddComicsButtonListener()
	{
		this.textFields = new ArrayList<JTextField>();
	}
	
	public AddComicsButtonListener(ChapterInfo info, GUI gui, ArrayList<JTextField> textFields, JFrame window)
	{
		this.info = info;
		this.gui = gui;
		this.textFields = textFields;
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		int lastReadChapter = 0;
		try
		{
		    lastReadChapter = Integer.parseInt(textFields.get(2).getText());
		} catch (NumberFormatException e) 
		{
		}
		finally
		{
			if (!textFields.get(0).getText().equals("") && textFields.get(4).getText().contains("readms.net") &&
					!textFields.get(4).getText().equals("") && !textFields.get(5).getText().equals(""))
			{
				info.addMonitoredComics(new Comics(textFields.get(0).getText(),	// title
												 textFields.get(1).getText(),	// author
												 lastReadChapter,				
												 textFields.get(3).getText(),	// path to image file of the cover
												 textFields.get(4).getText(),	// address
												 textFields.get(5).getText())); // beginning
				gui.updateGUI();
				info.updateDatabase();
				window.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Invalid data.");
			}
		}
	}
	
}
