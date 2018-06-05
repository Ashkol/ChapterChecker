package gui;
import javax.swing.JComponent;
import javax.swing.JLabel;

import businesslogic.GUIElementObserver;

public class ComicsPanelLabelNew extends JLabel implements GUIElementObserver {
	
	public ComicsPanelLabelNew()
	{
		this.setText("There is no new chapters");
	}
	
	@Override
	public void update(Object[][] data)
	{
		if (!(data[0][2].equals(data[0][3])))
		{
			this.setText("A new chapter is available");
		} else 
		{
			this.setText("There is no new chapters");
		}
		
		validate();
		repaint();
	}
	
	@Override
	public JComponent returnComponent()
	{
		return this;
	}
	
}
