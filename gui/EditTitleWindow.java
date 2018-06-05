package gui;
import javax.swing.JFrame;
import javax.swing.JTextField;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import controller.EditComicsButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditTitleWindow extends JFrame{
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	private int numberOfFields = 0;
	
	private ChapterInfo info;
	private GUI gui;
	private Comics comics;
	private ArrayList<JTextField> textFields;
	private Container content;
	private SpringLayout layout;
	private ArrayList<JLabel> labels;
	private JButton editButton;
	private JButton deleteButton;
	
	public EditTitleWindow(ChapterInfo info, GUI gui, Comics comics)
	{
		content = this.getContentPane();
		layout = new SpringLayout();
		content.setLayout(layout);
		textFields = new ArrayList<JTextField>();
		labels = new ArrayList<JLabel>();
		this.info = info;
		this.gui = gui;
		this.comics = comics;
		numberOfFields = comics.getTableData().length + 2;
		
		setProperties();
		setLabels();
		setTextFields();
		setEditButton();
	}
	
	private void setProperties()
	{
		this.setSize(new Dimension(WIDTH, HEIGHT));	
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void setLabels()
	{
		labels.add(new JLabel("Title:"));
		labels.add(new JLabel("Author:"));
		labels.add(new JLabel("Last Read:"));
		labels.add(new JLabel("Last released:"));
		labels.add(new JLabel("Path to cover:"));
		labels.add(new JLabel("Webpage address:"));
		labels.add(new JLabel("Line Beginning:"));
		
		for (int i = 0; i < labels.size(); i++)
		{
			JLabel label = labels.get(i);
			content.add(label);
			
			layout.putConstraint(SpringLayout.WEST, label,
		    5,
		    SpringLayout.WEST, content);
			if (i == 0)
			{
				layout.putConstraint(SpringLayout.NORTH, label,
			    5,
			    SpringLayout.NORTH, content);
			} else 
			{
				layout.putConstraint(SpringLayout.NORTH, label,
				20,
				SpringLayout.NORTH, labels.get(i - 1));
			}
		}
	}
	
	private void setTextFields()
	{
		for (int i = 0; i < numberOfFields; i++)
		{
			
		}
		
		textFields.add(new JTextField(comics.getTitle()));
		textFields.add(new JTextField(comics.getAuthor()));
		textFields.add(new JTextField("" + comics.getLastReadChapter()));
		textFields.add(new JTextField("" + comics.getLastChapter()));
		textFields.add(new JTextField(comics.getPathToCover()));
		textFields.add(new JTextField(comics.getWebpageAdress()));
		textFields.add(new JTextField(comics.getLineBeginning()));

		textFields.get(3).setEditable(false);
		
		for (int i = 0; i < textFields.size(); i++)
		{
			JTextField textField = textFields.get(i);
			textField.setPreferredSize(new Dimension(260, 20));
			content.add(textField);
			
			layout.putConstraint(SpringLayout.WEST, textField, 120, SpringLayout.WEST, content);
			if (i == 0)
			{
				layout.putConstraint(SpringLayout.NORTH, textField,
			    5,
			    SpringLayout.NORTH, content);
			} else 
			{
				layout.putConstraint(SpringLayout.NORTH, textField,
				20,
				SpringLayout.NORTH, textFields.get(i - 1));
			}
		}
	}
	
	private void setEditButton()
	{
		editButton = new JButton("Edit comics");
		content.add(editButton);
		layout.putConstraint(SpringLayout.WEST, editButton,
			    220,
			    SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, editButton,
				HEIGHT-100,
			    SpringLayout.WEST, content);
		editButton.addActionListener(new EditComicsButtonListener(info, gui, textFields, this, comics));
		
	}
}
