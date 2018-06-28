package gui;
import javax.imageio.ImageIO;
import javax.swing.*;

import businesslogic.Reader;
import controller.ReaderKeyListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ReaderWindow extends JFrame{
	
	private final int LABEL_HEIGHT = 16;
	private final Color BACKGROUND_COLOR = new Color(10, 10, 10);
	private final Color ACTIVE_FONT_COLOR = Color.WHITE;
	private final Color IDLE_FONT_COLOR = new Color(100, 100, 100);
	private Container frameContent;
	private JPanel content, buttonPanel, checkboxesPanel;
	private ArrayList<File> listOfImages;
//	private JLabel image;
	private Reader readerController;
	private ArrayList<JLabel> infoList; 
	private JPanel  leftPanel;
	private JPanel infoPanel;
	private File[] chapterList;
	private boolean infoPanelOn;

	// Right page
	private BufferedImage pictureRight, pictureLeft;
	private Graphics2D g2Right, g2Left;
	public JLabel imageRight,imageLeft;	
	
	public ReaderWindow(File[] chapterList, ArrayList<File> listOfImages, Reader readerController)
	{
		this.readerController = readerController;
		this.listOfImages = listOfImages;
		this.chapterList = chapterList;
		this.infoPanelOn = false;
		
		leftPanel = new JPanel();
		
		JFrame frame = new JFrame();
		setImage();
		setProperties();
		
		addKeyListener(new ReaderKeyListener(readerController,this));
	}
	
	private void setProperties()
	{	
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		getContentPane().setBackground(BACKGROUND_COLOR);
	}
	
	public void setInfo()
	{
		infoPanel = new JPanel();
		infoList = new ArrayList<JLabel>();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(BACKGROUND_COLOR);
		infoPanel.setPreferredSize(new Dimension(200, this.getHeight()));
		
		JLabel pageCounter = new JLabel("Page " + (readerController.getImageCounter() + 1) + "/" + 
				listOfImages.size());
		pageCounter.setForeground(IDLE_FONT_COLOR);
		
		infoPanel.add(pageCounter);
		
		int startingChapter = 0;
		int labelsToFit = this.getHeight()/LABEL_HEIGHT;
		labelsToFit -= 1;
		
		if (readerController.getChapterIndex() > labelsToFit / 2)
		{
			startingChapter = readerController.getChapterIndex() - (labelsToFit / 2);
		}
		
		for (int i = 0; i <chapterList.length; i++)
		{
			infoList.add(new JLabel("Chapter: " + chapterList[i].getName()));
			infoList.get(i).setForeground(IDLE_FONT_COLOR);
		}
		
		for (int i = startingChapter; i <chapterList.length; i++)
		{
			if (i == readerController.getChapterIndex())
			{
				infoList.get(i).setForeground(ACTIVE_FONT_COLOR);
			}
			infoPanel.add((infoList.get(i)), BorderLayout.CENTER);
		}
		
		add(infoPanel, BorderLayout.CENTER);
	}
	
	public JPanel getLeftPanel()
	{
		return leftPanel;
	}
	
	public void setImage()
	{
		try 
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage myPicture = ImageIO.read(listOfImages.get(readerController.getImageCounter()));			

			int imageHeight = 0, imageWidth = 0; 
			double scale = 1;
			if (myPicture.getHeight() > screenSize.getHeight())
			{
				
				scale = screenSize.getHeight()/myPicture.getHeight();
				imageHeight = (int)((double)myPicture.getHeight() * scale);
				imageWidth = (int)((double)myPicture.getWidth() * scale);
			}
			else
			{
				imageHeight = (int)myPicture.getHeight();
				imageWidth = (int)myPicture.getWidth();
			}
			
			double ratioHW = (double) imageHeight/ (double) imageWidth;		    
			getContentPane().setLayout(new FlowLayout());
			
			System.out.println("Image counter = " + readerController.getImageCounter());
			imageLeft = setPage(pictureLeft, g2Left, imageLeft, imageWidth, imageHeight);
		    imageLeft.validate();
			imageLeft.repaint();
			
			System.out.println("PREV " + prevPicRatioHW());
			
			if (readerController.getImageCounter() != 0 && ratioHW > 1)
			{	
				if (prevPicRatioHW() < 1)
				{
					readerController.setDecrVal(2);
				}
				else if (prevPicRatioHW() > 1 && readerController.getImageCounter()-1 == 0)
				{
					readerController.setDecrVal(2);
				}
				else
				{
					readerController.setDecrVal(3);
				}
				if (readerController.getImageCounter() +1 < listOfImages.size())
				{
					readerController.incrementImageCounter();
					imageRight = setPage(pictureRight, g2Right, imageRight, imageWidth, imageHeight);
				    imageRight.validate();
				    imageRight.repaint();
					getContentPane().add(imageRight, BorderLayout.CENTER);
					System.out.println("PREV " + prevPicRatioHW());
				}
				else
				{
					readerController.setDecrVal(2);
				}
			}
			else if (prevPicRatioHW() > 1)
			{
				readerController.setDecrVal(2);
			}
			else
			{
				readerController.setDecrVal(1);
			}
			getContentPane().add(imageLeft, BorderLayout.CENTER);
			
			validate();
			repaint();
		} 
		catch (IOException ex) 
		{
			System.out.println("image not loaded");
				imageLeft = new JLabel("Image not loaded");
				imageRight = new JLabel("Image not loaded");
		}
	}
	
	private boolean isTwoPage(BufferedImage page)
	{
		if (page.getWidth() > page.getHeight())
		{
			return true;
		}
		else return false;
	}
	
	private JLabel setPage(BufferedImage picture, Graphics2D g2, JLabel label, int imageWidth, int imageHeight) throws IOException
	{
		System.out.println("setPage " + readerController.getImageCounter());
		picture= ImageIO.read(listOfImages.get(readerController.getImageCounter()));
		BufferedImage resizedImgOther = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
	    g2= resizedImgOther.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(picture, 0, 0, imageWidth, imageHeight, null);
	    g2.dispose();
	    return new JLabel(new ImageIcon(resizedImgOther));
	}
	
	public double prevPicRatioHW() 
	{	
		BufferedImage prevPicture;
		if (readerController.getImageCounter()-1 >= 0)
		{
			try {				
				prevPicture = ImageIO.read(listOfImages.get(readerController.getImageCounter()-1));
				return (double) prevPicture.getHeight() / (double) prevPicture.getWidth();
			} catch (IOException e) {
				return 1;
			}	
		}
		else
		{
			return 1;
		}
	}
	
	// Maybe unnecessary, should not be public
	public double currentPicRatioHW() 
	{	
		BufferedImage prevPicture;
		if (readerController.getImageCounter()-1 >= 0)
		{
			try {				
				prevPicture = ImageIO.read(listOfImages.get(readerController.getImageCounter()));
				return (double) prevPicture.getHeight() / (double) prevPicture.getWidth();
			} catch (IOException e) {
				return 1;
			}	
		}
		else
		{
			return 1;
		}
	}
	
	public void setInfoPanelOn(boolean b)
	{
		infoPanelOn = b;
	}
	
	public boolean getInfoPanelOn()
	{
		return infoPanelOn;
	}
}
