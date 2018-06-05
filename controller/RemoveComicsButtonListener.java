package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import businesslogic.ChapterInfo;
import gui.GUI;

public class RemoveComicsButtonListener implements ActionListener {

	private ChapterInfo info;
	private GUI gui;
	private ArrayList<JCheckBox> checkBoxes;
	private JFrame window;
	
	
	public RemoveComicsButtonListener(ChapterInfo info, GUI gui, ArrayList<JCheckBox> checkBoxes, JFrame window)
	{
		this.info = info;
		this.gui = gui;
		this.checkBoxes = checkBoxes;
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		for (JCheckBox box : checkBoxes)
		{
			if (box.isSelected())
				info.removeComicsByTitle(box.getText());
		}
		gui.updateGUI();
		info.updateDatabase();
		window.dispose();
	}
	
}
