package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import gui.AddTitleWindow;
import gui.GUI;

public class MenuItemListenerAdd extends MenuItemListener{

	public MenuItemListenerAdd(ChapterInfo info, GUI gui)
	{
		super(info, gui);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JMenuItem)
		{
			new AddTitleWindow(info, this.gui);
		}
		
	}
}
