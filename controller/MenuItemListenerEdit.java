package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.EditTitleWindow;
import gui.GUI;

public class MenuItemListenerEdit extends MenuItemListener{

	public MenuItemListenerEdit(ChapterInfo info, GUI gui, Comics comics)
	{
		super(info, gui, comics);
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
