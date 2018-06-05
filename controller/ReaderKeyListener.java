package controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;

import businesslogic.Reader;
import gui.ReaderWindow;

public class ReaderKeyListener implements KeyListener {

	private Reader readerController;
	private ReaderWindow readerWindow;
	
	public ReaderKeyListener(Reader readerController, ReaderWindow readerWindow)
	{
		this.readerController = readerController;
		this.readerWindow = readerWindow;
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		
		switch (key.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
			readerController.decrementImageCounter();
			readerWindow.getContentPane().removeAll();
			readerWindow.setImage();
			break;
		case KeyEvent.VK_LEFT:
			readerController.incrementImageCounter();
			readerWindow.getContentPane().removeAll();
			readerWindow.setImage();
			break;
		case KeyEvent.VK_UP:
			readerController.goToNextChapter();
			readerWindow.getContentPane().removeAll();
			readerWindow.setImage();
			break;
		case KeyEvent.VK_DOWN:
			readerController.goToPreviousChapter();
			readerWindow.getContentPane().removeAll();
			readerWindow.setImage();
			break;
		case KeyEvent.VK_ESCAPE:
			readerWindow.dispose();
		case KeyEvent.VK_SPACE:
			readerWindow.setInfo();
			readerWindow.validate();
			readerWindow.repaint();
		default : 
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		
		switch (key.getKeyCode())
		{
		case KeyEvent.VK_SPACE:
			readerWindow.getContentPane().removeAll();
			readerWindow.setImage();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
