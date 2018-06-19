package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import businesslogic.ChapterInfo;
import gui.GUI;

public class MenuItemListenerRefresh extends MenuItemListener{

	public MenuItemListenerRefresh(ChapterInfo info, GUI gui)
	{
		super(info, gui);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
			info.updateMonitoredComics();
	}
}
