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
	private JLabel imageLeft;
	private Reader readerController;
	private ArrayList<JLabel> infoList; 
	private JPanel  leftPanel;
	private JPanel infoPanel;
	private File[] chapterList;

	// Right page
	private BufferedImage pictureRight;
	private Graphics2D g2Right;
	private JLabel imageRight;
	
	
	public ReaderWindow(File[] chapterList, ArrayList<File> listOfImages, Reader readerController)
	{
		this.readerController = readerController;
		this.listOfImages = listOfImages;
		this.chapterList = chapterList;
		
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

//		int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		JLabel pageCounter = new JLabel("Page " + (new Integer(readerController.getImageCounter() + 1)).toString() + "/" + 
				(new Integer(listOfImages.size()).toString()));
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
			infoPanel.add((infoList.get(i)), BorderLayout.WEST);
		}
		
		leftPanel.add(infoPanel);
//		leftPanel.add(pageInfoPanel);
		leftPanel.setBackground(BACKGROUND_COLOR);
		add(infoPanel, BorderLayout.WEST);
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

			
			System.out.println(readerController.getImageCounter());
			
			int imageHeight = 0, imageWidth = 0; 
			double scale;
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
			
			BufferedImage resizedImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = resizedImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(myPicture, 0, 0, imageWidth, imageHeight, null);
		    g2.dispose();
		    
//		    setRightPage(pictureRight, g2Right, imageRight, imageWidth, imageHeight);
//		    if (readerController.getImageCounter() != 0)
//			{
//		    	setRightPage(pictureRight, g2Right, imageRight, imageWidth, imageHeight);
//		    	
//				BufferedImage otherPicture = ImageIO.read(listOfImages.get(readerController.getImageCounter()+1));
//				BufferedImage resizedImgOther = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
//			    Graphics2D g2Other = resizedImgOther.createGraphics();
//			    g2Other.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//				g2Other.drawImage(otherPicture, 0, 0, imageWidth, imageHeight, null);
//			    g2Other.dispose();
//			    imageRight = new JLabel(new ImageIcon(resizedImgOther));
//			    imageRight.validate();
//			    imageRight.repaint();
//			}
	
	
//		    imageRight = new JLabel(new ImageIcon(resizedImg));
		    
		    
		    imageLeft = new JLabel(new ImageIcon(resizedImg));
		    
		    imageLeft.validate();
		    
			imageLeft.repaint();
			
			getContentPane().setLayout(new FlowLayout());
			if (readerController.getImageCounter() != 0)
			{	
				readerController.setIncrVal(2);
				imageRight = setRightPage(pictureRight, g2Right, imageRight, imageWidth, imageHeight);
			    imageRight.validate();
			    imageRight.repaint();
				System.out.println("ADDing");
				getContentPane().add(imageRight, BorderLayout.CENTER);	
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
	
	private JLabel setRightPage(BufferedImage rightPicture, Graphics2D g2Right, JLabel labelRight, int imageWidth, int imageHeight) throws IOException
	{
		System.out.println("setRightPage()");
		rightPicture = ImageIO.read(listOfImages.get(readerController.getImageCounter()+1));
		BufferedImage resizedImgOther = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
	    g2Right= resizedImgOther.createGraphics();
	    g2Right.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2Right.drawImage(rightPicture, 0, 0, imageWidth, imageHeight, null);
	    g2Right.dispose();
//	    labelRight = new JLabel(new ImageIcon(resizedImgOther));
//	    labelRight.validate();
//	    labelRight.repaint();
	    return new JLabel(new ImageIcon(resizedImgOther));
	}
}
