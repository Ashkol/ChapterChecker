package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.GUI;
import gui.RemoveTitleWindow;

public class MenuItemListenerRemove extends MenuItemListener implements ActionListener{
	
	public MenuItemListenerRemove(ChapterInfo info, GUI gui)
	{
		super(info, gui);
		comics = null;
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
