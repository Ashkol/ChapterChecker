package controller;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.DeleteChaptersWindowChoose;
import gui.GUI;

public class MenuItemListenerDelete extends MenuItemListener {

	public MenuItemListenerDelete(ChapterInfo info, GUI gui, Comics comics) {
		super(info, gui, comics);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JMenuItem)
		{
			new DeleteChaptersWindowChoose(info, gui, comics);
		}
		
	}

}
