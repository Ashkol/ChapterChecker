package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import gui.GUI;

public class MenuItemListener  implements ActionListener{

	protected ChapterInfo info;
	protected GUI gui;
	protected Comics comics;

	public MenuItemListener(ChapterInfo info, GUI gui, Comics comics)
	{
		this.info = info;
		this.gui = gui;
		this.comics = comics;
	}
	
	public MenuItemListener(ChapterInfo info, GUI gui)
	{
		this.info = info;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
