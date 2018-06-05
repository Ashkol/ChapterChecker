package gui;
import javax.swing.*;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import controller.RemoveComicsButtonListener;

import java.awt.*;
import java.util.ArrayList;

public class RemoveTitleWindow extends JFrame{
	
	private int windowWidth = 400;
	private int windowHeight = 400;
	private ChapterInfo info;
	private GUI gui;
	private JScrollPane scroll;
	private ArrayList<JCheckBox> checkBoxes;
	
	private Container frameContent;
	private JPanel content, buttonPanel, checkboxesPanel;
	private JButton removeButton;
	
	public RemoveTitleWindow(ChapterInfo info, GUI gui)
	{
		frameContent = this.getContentPane();
		content = new JPanel();
		frameContent.add(content);
		
		checkboxesPanel = new JPanel(new GridLayout((info.getMonitoredComics().size()/2) + info.getMonitoredComics().size()%2, 2));
		buttonPanel = new JPanel();
		scroll = new JScrollPane(checkboxesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setMaximumSize(new Dimension(windowWidth-20, windowHeight - 100));
		frameContent.add(content);
		content.add(scroll);
		content.add(buttonPanel,BorderLayout.SOUTH);
		checkBoxes = new ArrayList<JCheckBox>(info.getMonitoredComics().size());
		this.info = info;
		this.gui = gui;
		
		setProperties();
		setCheckBoxes();
		setRemoveButton();
	}
	
	private void setProperties()
	{
		setSize(new Dimension(windowWidth, windowHeight));	
		setResizable(false);
		setVisible(true);
	}
	
	private void setCheckBoxes()
	{
		int i = 0;
		for (Comics comics : info.getMonitoredComics())
		{
			checkBoxes.add(new JCheckBox(comics.getTitle()));
			JCheckBox checkBox = checkBoxes.get(i);
			checkboxesPanel.add(checkBox);
			i++;
		}
	}
	
	private void setRemoveButton()
	{
		removeButton = new JButton("Remove comics");
		removeButton.setPreferredSize(removeButton.getPreferredSize());
		buttonPanel.add(removeButton);
		removeButton.addActionListener(new RemoveComicsButtonListener(info, gui, checkBoxes, this));
	}
	
}
