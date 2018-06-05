package gui;
import javax.swing.*;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import controller.DownloadComicsButtonListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadTitleWindow extends JFrame implements ActionListener{
	
	private int windowWidth = 400;
	private int windowHeight = 400;
	private ChapterInfo info;
	private GUI gui;
	private Comics comics;
	private JScrollPane scroll;
	private ArrayList<JCheckBox> checkBoxes;
	private ArrayList<JTextField> textFields;
	
	private Container frameContent;
	private JPanel content, buttonPanel, checkboxesPanel, textFieldsPanel;
	private JButton downloadButton;
	
	public DownloadTitleWindow(ChapterInfo info, GUI gui, Comics comics)
	{
		frameContent = this.getContentPane();
		content = new JPanel();
		frameContent.add(content);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		checkboxesPanel = new JPanel();
		checkboxesPanel.setLayout(new BoxLayout(checkboxesPanel, BoxLayout.Y_AXIS));
		buttonPanel = new JPanel();
		
		frameContent.add(content);
		
		checkBoxes = new ArrayList<JCheckBox>(info.getMonitoredComics().size());
		this.info = info;
		this.gui = gui;
		this.comics = comics;
		
		setProperties();
		setCheckBoxes();
		setTextFields();
		setDownloadButton();
	}
	
	private void setProperties()
	{
		setSize(new Dimension(windowWidth, windowHeight));	
		setResizable(false);
		setVisible(true);
	}
	
	private void setCheckBoxes()
	{
		for (int i = 0; i < comics.getChapterList().size(); i++)
		{
			checkBoxes.add(new JCheckBox(comics.getChapterList().get(i)));
			JCheckBox checkBox = checkBoxes.get(i);
			checkboxesPanel.add(checkBox);
		}
		
		scroll = new JScrollPane(checkboxesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(windowWidth-200, windowHeight - 100));
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		content.add(scroll);
	}
	
	public void setTextFields()
	{
		textFields = new ArrayList<JTextField>(4);
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		
		textFieldsPanel = new JPanel();
		textFieldsPanel.add(new JLabel ("Height width ratio for 1 page - Min:"));
			
		textFieldsPanel.add(textFields.get(0));
		textFieldsPanel.add(new JLabel ("Max:"));
		textFieldsPanel.add(textFields.get(1));
		
		textFieldsPanel.setLayout(new FlowLayout());
		textFields.get(0).setText("1.4");
		textFields.get(1).setText("1.5");
		textFields.get(1).addActionListener(this);
		content.add(textFieldsPanel);
	
	}
	
	private void setDownloadButton()
	{
		downloadButton = new JButton("Download chapters");
//		downloadButton.setPreferredSize(downloadButton.getPreferredSize());
		buttonPanel.add(downloadButton);
		downloadButton.addActionListener(new DownloadComicsButtonListener(info , comics, textFields, checkBoxes, this));
		content.add(buttonPanel,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {


		System.out.println(	textFields.get(1).getText());
		textFields.get(1).setText(textFields.get(1).getText());
	}
	
}
