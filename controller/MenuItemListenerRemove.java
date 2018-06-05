package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import gui.GUI;
import gui.RemoveTitleWindow;

public class MenuItemListenerRemove implements ActionListener{

	private ChapterInfo info;
	private GUI gui;

	public MenuItemListenerRemove(ChapterInfo info, GUI gui)
	{
		this.info = info;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		
		if (event.getSource() instanceof JMenuItem)
		{
			new RemoveTitleWindow(info, gui);
		}
		
	}
	
}
