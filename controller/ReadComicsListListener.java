package controller;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businesslogic.Reader;

public class ReadComicsListListener implements ListSelectionListener {

	private JList list;
	private File[] listOfChapters;
	private String comicsTitle;
	JFrame parentWindow;
	
	public ReadComicsListListener(File[] listOfChapters, JList<File> list, JFrame parentWindow)
	{
		this.list = list;
		this.listOfChapters = listOfChapters;
		this.parentWindow = parentWindow;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if (e.getValueIsAdjusting() == false)
		{
			new Reader(listOfChapters, list.getSelectedValue().toString(), list.getSelectedIndex());
			parentWindow.dispose();
		}	
	}
}
