package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.GUI;
import gui.ReadTitleWindowChoose;

public class MenuItemListenerRead extends MenuItemListener{


	public MenuItemListenerRead(ChapterInfo info, GUI gui, Comics comics)
	{
		super(info, gui, comics);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		File directory = new File(this.comics.getTitle());
		if (event.getSource() instanceof JMenuItem &&
			directory.exists())
		{
			new ReadTitleWindowChoose(info, this.gui, comics);
		}
		
	}
}
