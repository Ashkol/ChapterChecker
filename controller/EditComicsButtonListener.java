package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.GUI;

public class EditComicsButtonListener implements ActionListener {

	private ChapterInfo info;
	private GUI gui;
	private ArrayList<JTextField> textFields;
	private JFrame window;
	private Comics comics;
	
	EditComicsButtonListener()
	{
		this.textFields = new ArrayList<JTextField>();
	}
	
	public EditComicsButtonListener(ChapterInfo info, GUI gui, ArrayList<JTextField> textFields, JFrame window, Comics comics)
	{
		this.info = info;
		this.gui = gui;
		this.textFields = textFields;
		this.window = window;
		this.comics = comics;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		int lastReadChapter;
		try
		{
		    lastReadChapter = Integer.parseInt(textFields.get(2).getText());
		} catch (NumberFormatException e) 
		{
			lastReadChapter = -1;
		}
		
		int lastChapter;
		try
		{
		    lastChapter = Integer.parseInt(textFields.get(3).getText());
		} catch (NumberFormatException e) 
		{
			lastChapter = -1;
		}
		
		comics.setTitle(textFields.get(0).getText());
		comics.setAuthor(textFields.get(1).getText());
		if (lastReadChapter >= 0)
			comics.setlastReadchapter(lastReadChapter);
		if (lastChapter >= 0)
			comics.setLastChapter(lastChapter);
		comics.setPathToCover(textFields.get(4).getText());
		comics.setWebpageAddress(textFields.get(5).getText());
		comics.setLineBeginning(textFields.get(6).getText());
		
		comics.reloadCover();
		comics.notifyObservers();
		gui.updateGUI();
		info.updateDatabase();
		window.dispose();
	}
	
}
