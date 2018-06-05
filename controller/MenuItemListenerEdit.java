package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.EditTitleWindow;
import gui.GUI;

public class MenuItemListenerEdit implements ActionListener{

	private ChapterInfo info;
	private GUI gui;
	private Comics comics;

	public MenuItemListenerEdit(ChapterInfo info, GUI gui, Comics comics)
	{
		this.info = info;
		this.gui = gui;
		this.comics = comics;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JMenuItem)
		{
			new EditTitleWindow(info, this.gui, comics);
		}
		
	}
}
