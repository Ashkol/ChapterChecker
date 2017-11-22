import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;

import javax.swing.JButton;

public class HeaderTable extends GUIElement /*implements Observer*/ {

	
	String[] columnNames = {"Title", "Author", "Last Read Chapter","Newest Chapter"};
	Object[][] tableData = {{"A", "B", "C", "D"}};//new Object[1][4];
	
	public JScrollPane headerTable = null;
	JTable subTable = null;
	
	// Constructors
	HeaderTable()
	{
		
		
		headerTable= new JScrollPane(subTable);
		subTable = new JTable(tableData,columnNames);
		headerTable.add(subTable);
		//update();
		headerTable= new JScrollPane(subTable);
		headerTable.setPreferredSize(new Dimension(500, 39));
		component = headerTable;	
		
		//component = new JTable({1, 2, 3}, {"A"});
	}
	
	@Override
	public void update()
	{
		System.out.println("update101");
	}
	
	@Override
	public void update(Object[][] data)
	{
		
		tableData = data;
		subTable = new JTable(tableData, columnNames);
		

//		headerTable.add(subTable);
System.out.println("\n" + tableData[0][2] + "\n");
		headerTable = new JScrollPane(subTable);
//		headerTable.repaint();
		
		component = headerTable;
		component.setPreferredSize(new Dimension(500, 39));
		component.repaint();
	}
	
}
