package gui;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.GUIElementObserver;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComponent;

public class HeaderTable extends JScrollPane implements GUIElementObserver{

	String[] columnNames = {"Title", "Author", "Last Read Chapter","Newest Chapter"};
	Object[][] tableData;
	
	JTable subTable = null;
	
	// Constructors
	public HeaderTable(Object[][] tableData)
	{		
		subTable = new JTable(tableData,columnNames);
		subTable.setBackground(new Color(243, 229, 216));
		subTable.getTableHeader().setReorderingAllowed(false);
		subTable.setEnabled(false);
		setViewportView(subTable);
		this.setPreferredSize(new Dimension(500, 39));
	}
	
	@Override
	public void update(Object[][] data)
	{
		tableData = data;
		subTable = new JTable(tableData, columnNames);

		repaint();
	}
	
	@Override
	public JComponent returnComponent()
	{
		return this;
	}
	
}
