package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import businesslogic.Comics;
import businesslogic.GUIElementObserver;
import controller.ButtonListener;

public class ComicsPanel extends JPanel{
	
	private JPanel informationElements;
	private static final Color BACKGROUND_COLOR = new Color(223, 189, 159);
	public JButton incrButton;
	public JButton decrButton;
	BoxLayout layout  = new BoxLayout(informationElements, BoxLayout.Y_AXIS);
	
	ComicsPanel(Comics comics)
	{
		informationElements = new JPanel();	
		
		incrButton = new JButton("+1");
		incrButton.addActionListener(new ButtonListener(comics, this));
		decrButton = new JButton("-1");
		decrButton.addActionListener(new ButtonListener(comics, this));
		
		this.setPreferredSize(new Dimension(500, 360));
		this.setLayout(new BorderLayout());
		this.add(comics.getCover(), BorderLayout.WEST);
		
		informationElements.setBackground(BACKGROUND_COLOR);
		this.setBackground(BACKGROUND_COLOR);
	
		for (GUIElementObserver observer : comics.getObserverList())
		{
			if (observer instanceof HeaderTable)
			{
				this.add(observer.returnComponent(), BorderLayout.NORTH);
			} 
			else
			{
				informationElements.add(observer.returnComponent(), BorderLayout.EAST);
				informationElements.add(Box.createRigidArea(new Dimension(5,0)));
			}
		}
		
		informationElements.add(incrButton);
		informationElements.add(decrButton);
		this.add(informationElements);
	}
	
	// Getters
	public JButton getIncrButton()
	{
		return incrButton;
	}
	
	public JButton getDecrButton()
	{
		return decrButton;
	}
}
