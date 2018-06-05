package controller;
import java.awt.event.*;

import businesslogic.Comics;
import gui.ComicsPanel;

public class ButtonListener implements ActionListener{

	Comics comics;
	ComicsPanel panel;
	
	public ButtonListener (Comics comics, ComicsPanel panel)
	{
		this.comics = comics;
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource().equals(panel.getIncrButton()) && comics.getLastReadChapter() < comics.getLastChapter())
		{
			comics.setlastReadchapter(comics.getLastReadChapter() + 1);
			comics.getTableData()[0][2] = comics.getLastReadChapter();
			comics.notifyObservers();
		} 
		else if (event.getSource().equals(panel.getDecrButton()) && comics.getLastReadChapter() > 0)
		{
			comics.setlastReadchapter(comics.getLastReadChapter() - 1);
			comics.getTableData()[0][2] = comics.getLastReadChapter();
			comics.notifyObservers();
		}
	}
	
}
