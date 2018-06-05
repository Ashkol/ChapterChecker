package gui;
import javax.swing.JComponent;
import javax.swing.JLabel;

import businesslogic.GUIElementObserver;

public class ComicsPanelLabelPercentage extends JLabel implements GUIElementObserver {
	
	private Double readPercentage =new Double(0);
	
	public ComicsPanelLabelPercentage()
	{
		this.setText("You read " + readPercentage + "% of the comics.");
	}
	
	@Override
	public void update(Object[][] data)
	{
		// Alternatively use BigDecimal to hold percentage
		double devidend = Double.parseDouble(data[0][2].toString());
		double divider = Double.parseDouble(data[0][3].toString());
		if (divider == 0)
		{
			readPercentage = 0.0;
		}
		else
		{
			readPercentage = devidend/divider*100;
		}
		if (readPercentage >= 10.0)
		{
			readPercentage = Double.parseDouble(readPercentage.toString().substring(0, 4));
		} 
		else
		{
			readPercentage = Double.parseDouble(readPercentage.toString().substring(0, 3));
		}
		setText("You read " + readPercentage + "% of the comics.");
			
		validate();
		repaint();
	}
	
	@Override
	public JComponent returnComponent()
	{
		return this;
	}
	
}
