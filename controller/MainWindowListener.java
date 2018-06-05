package controller;
import java.awt.event.*;

import businesslogic.ChapterInfo;

public class MainWindowListener extends WindowAdapter{
	
	ChapterInfo info;
	
	public MainWindowListener(ChapterInfo info)
	{
		this.info = info;
	}
	
	@Override
	public void windowClosing(WindowEvent event)
	{
		info.updateDatabase();
	}
}