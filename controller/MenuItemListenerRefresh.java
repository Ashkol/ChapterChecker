package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import businesslogic.ChapterInfo;
import gui.GUI;

public class MenuItemListenerRefresh implements ActionListener{

	private ChapterInfo info;
	private GUI gui;

	public MenuItemListenerRefresh(ChapterInfo info, GUI gui)
	{
		this.info = info;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
			info.updateMonitoredComics();
	}
}
