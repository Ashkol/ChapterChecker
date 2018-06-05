package gui;
import javax.swing.*;

import businesslogic.ChapterInfo;
import businesslogic.Comics;
import controller.MainWindowListener;

import java.awt.*;
import java.util.ArrayList;

public class GUI {

	public ArrayList<Comics> monitoredComics = new ArrayList<Comics>();	
	private JFrame frame;
	private JScrollPane scroll;
	private Container frameContent;
	private JPanel content;
	private ChapterInfo info;
	private static Color BACKGROUND_COLOR = new Color(223, 189, 159);
	private int gridColumns, gridRows;
	private int windowHeight = 600, windowWidth = 800;

	public GUI(ChapterInfo info) 
	{
		monitoredComics = info.getMonitoredComics();
		this.info = info;
		setMainWindow();
	}
	
	// Getters
	public JFrame getFrame()
	{
		return frame;
	}
	
	private void setMainWindow()
	{
		frame = new JFrame("Chapter Checker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
		frame.addWindowListener(new MainWindowListener(info));
		
		frameContent = frame.getContentPane();
		content = new JPanel();
		content.setBackground(BACKGROUND_COLOR);
		frame.setJMenuBar(new MenuBar(info, frameContent, this));
		
		scroll = new JScrollPane(content, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		frameContent.add(scroll);
		
		for (Comics c : monitoredComics)
		{
			content.add(new ComicsPanel(c));
		}
		
		recalculateGrid();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void updateGUI()
	{	
		// Updates WHOLE GUI
		recalculateGrid();
		content.removeAll();
		
		for (Comics comics : monitoredComics)
		{
			content.add(new ComicsPanel(comics));
		}
		
		frame.setJMenuBar(new MenuBar(info, frameContent, this));
		frame.validate();
		frame.repaint();
	}
	
	private void recalculateGrid()
	{
//		Setting rows and columns for grid
		if (monitoredComics.size() > 2)
		{
			gridColumns = 2;
		} else 
		{
			gridColumns = 1;
		}
		if (gridColumns == 2)
		{
			if (monitoredComics.size() % 2 == 0)
				gridRows = monitoredComics.size()/2;
			else
				gridRows = monitoredComics.size()/2 + 1;
				
		} else 
		{
			gridRows = monitoredComics.size();
		}
		content.setLayout(new GridLayout(gridRows, gridColumns));
		content.validate();
	}

}
