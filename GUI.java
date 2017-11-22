import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI {

	public ArrayList<Comics> monitoredComics = new ArrayList<Comics>();
	private ArrayList<JPanel> panels = new ArrayList<JPanel>();
	private ArrayList<JPanel> interactiveElements = new ArrayList<JPanel>();
	private ArrayList<JButton> incrButtons = new ArrayList<JButton>();
	private ArrayList<JButton> decrButtons = new ArrayList<JButton>();
	private ArrayList<JLabel> covers = new ArrayList<JLabel>();
	
	JTextField currentOPField = new JTextField(1);
	

	GUI(ArrayList<Comics> comics) 
	{
		JFrame frame = new JFrame("Chapter Checker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(2, 2));
		Container content = frame.getContentPane();
			
		loadCovers();
		monitoredComics = comics;
		
		for (int i = 0; i < comics.size(); i++)
		{
			panels.add(new JPanel());
		}
		
		loadCovers();

		int i = 0;
		for (JPanel panel : panels)
		{
			panel.setLayout(new BorderLayout());
			panel.add(covers.get(i), BorderLayout.WEST);
			interactiveElements.add(new JPanel());
			interactiveElements.get(i).setLayout(new BoxLayout(interactiveElements.get(i), BoxLayout.Y_AXIS));
			interactiveElements.get(i).setPreferredSize(new Dimension(300,400));
//			interactiveElements.get(i).add(monitoredComics.get(i).incrReadChapter);
//			interactiveElements.get(i).add(monitoredComics.get(i).decrReadChapter);
			panel.add(interactiveElements.get(i), BorderLayout.EAST);
			
			//Buttons
			incrButtons.add(new JButton("+1"));
			decrButtons.add(new JButton("-1"));
			incrButtons.get(i).addActionListener(new ButtonListener(monitoredComics.get(i)));
			decrButtons.get(i).addActionListener(new ButtonListener(monitoredComics.get(i)));
			monitoredComics.get(i).incrReadChapter = incrButtons.get(i);
			monitoredComics.get(i).decrReadChapter = decrButtons.get(i);
			interactiveElements.get(i).add(incrButtons.get(i));
			interactiveElements.get(i).add(decrButtons.get(i));
			
			
			panel.add(monitoredComics.get(i).observerList.get(0).component, BorderLayout.NORTH);
//			panel.add(monitoredComics.get(i).tableScroll, BorderLayout.NORTH);

			comics.get(i).interactiveElements.setLayout(new BoxLayout(comics.get(i).interactiveElements, BoxLayout.Y_AXIS));
			panel.add(interactiveElements.get(i), BorderLayout.EAST);
			panel.add(covers.get(i), BorderLayout.WEST);
			
			i++;
		}
			
		ButtonListener listener = new ButtonListener(monitoredComics.get(0));
		monitoredComics.get(0).button.addActionListener(listener);


		System.out.println("DR = " + monitoredComics.get(3).getTitle());
		
		i = 0;
		for (i = 0; i < panels.size(); i++)
		{
			content.add(panels.get(i));
		}
		frame.setVisible(true);
	}

	
	private void loadCovers() {
		
		for (Comics c : monitoredComics)
		{	
			try {
				BufferedImage myPicture = ImageIO.read(new File(c.pathToCover));
				BufferedImage resizedImg = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
				 Graphics2D g2 = resizedImg.createGraphics();
				 
				 g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				    g2.drawImage(myPicture, 0, 0, 200, 300, null);
				    g2.dispose();
				 
				   System.out.println("NULL!!!");
				covers.add(new JLabel(new ImageIcon(resizedImg)));	
				System.out.println("Cover loaded");
				//cover = new JLabel(new ImageIcon(myPicture));
				
			} catch (IOException ex) {
				
			}
		}
	}
	
	
	
}
