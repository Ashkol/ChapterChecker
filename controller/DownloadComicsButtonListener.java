package controller;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import businesslogic.Downloader;

public class DownloadComicsButtonListener implements ActionListener{

	private ChapterInfo info;
	private Comics comics;
	private ArrayList<JCheckBox> checkBoxes;
	private ArrayList<JTextField> textFields;
	private JFrame window;
	private Downloader downloader;
	double minRatio, maxRatio;
	
	public DownloadComicsButtonListener(ChapterInfo info, Comics comics, ArrayList<JTextField> textFields, ArrayList<JCheckBox> checkBoxes, JFrame window)
	{
		this.info = info;
		this.checkBoxes = checkBoxes;
		this.window = window;
		this.comics = comics;
		this.textFields = textFields;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		minRatio = new Double(textFields.get(0).getText().replace(",", "."));
		maxRatio = new Double(textFields.get(1).getText().replace(",", "."));
		ArrayList<String> listToDownload = new ArrayList<String>();
		for (JCheckBox box : checkBoxes)
		{
			if (box.isSelected())
			{
				System.out.println("Wybrano box dla: " + box.getText());
				listToDownload.add(box.getText());
				System.out.println(box.getText());
			}
		}
		downloader =  new Downloader(info, comics, listToDownload/*box.getText()*/, minRatio, maxRatio);
		downloader.downloadChapter();
		window.dispose();
		
		System.out.println(maxRatio + " " + minRatio);
	}
}
