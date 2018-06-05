package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import gui.AddTitleWindow;
import gui.GUI;

public class MenuItemListenerAdd implements ActionListener{

	private ChapterInfo info;
	private GUI gui;

	public MenuItemListenerAdd(ChapterInfo info, GUI gui)
	{
		this.info = info;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JMenuItem)
		{
//			AddTitleWindow addTitle =
			new AddTitleWindow(info, this.gui);
		}
		
	}
}
