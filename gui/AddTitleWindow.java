package gui;
import javax.swing.JFrame;
import javax.swing.JTextField;

import businesslogic.ChapterInfo;
import controller.AddComicsButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddTitleWindow extends JFrame{
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	
	private ChapterInfo info;
	private GUI gui;
	private ArrayList<JTextField> textFields;
	private ArrayList<JCheckBox> checkboxes;
	private Container content;
	private SpringLayout layout;
	private ArrayList<JLabel> labels;
	private JButton addButton;
	private JPanel mangaSites;
	
	public AddTitleWindow(ChapterInfo info, GUI gui)
	{
		content = this.getContentPane();
		layout = new SpringLayout();
		content.setLayout(layout);
		textFields = new ArrayList<JTextField>();
		labels = new ArrayList<JLabel>();
		checkboxes = new ArrayList<JCheckBox>();
		mangaSites = new JPanel();
		this.info = info;
		this.gui = gui;
		
		setProperties();
		setLabels();
		setTextFields();
		setCheckboxes();
		setAddButton();
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
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());

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
	
	private void setAddButton()
	{
		addButton = new JButton("Add comics");
		content.add(addButton);
		layout.putConstraint(SpringLayout.WEST, addButton,
			    150,
			    SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, addButton,
				HEIGHT-100,
			    SpringLayout.WEST, content);
		addButton.addActionListener(new AddComicsButtonListener(info, gui, textFields, this));
	}
	
	private void setCheckboxes()
	{
		checkboxes.add(new JCheckBox("Manga Stream"));
		
		content.add(mangaSites);
		mangaSites.setLayout(new FlowLayout());
		
		layout.putConstraint(SpringLayout.WEST, mangaSites,
				5,
				SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, mangaSites,
				20,
				SpringLayout.NORTH, textFields.get(textFields.size()-1));
			
		for (int i = 0; i < checkboxes.size(); i++)
		{
			mangaSites.add(checkboxes.get(i));
			layout.putConstraint(SpringLayout.WEST, checkboxes.get(i),
					5,
					SpringLayout.WEST, content);
			layout.putConstraint(SpringLayout.NORTH, checkboxes.get(i),
					20,
					SpringLayout.NORTH, textFields.get(textFields.size()-1));
			checkboxes.get(i).setSelected(true);
			checkboxes.get(i).setEnabled(false);
		}
	}
	
}
