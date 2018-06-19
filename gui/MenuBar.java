package gui;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import businesslogic.ChapterInfo;
import controller.MenuItemListenerAdd;
import controller.MenuItemListenerDelete;
import controller.MenuItemListenerDownload;
import controller.MenuItemListenerEdit;
import controller.MenuItemListenerRead;
import controller.MenuItemListenerRefresh;
import controller.MenuItemListenerRemove;

public class MenuBar extends JMenuBar {

	private JMenu comicsMenu, editMenu, downloadMenu, readMenu, deleteMenu;
	private ArrayList<JMenuItem> comicsMenuItems, editdMenuItems, downloadMenuItems, readMenuItems, deleteMenuItems;
	private ChapterInfo info;
	private GUI gui;
	
	MenuBar(ChapterInfo info, Container mainWindow, GUI gui)
	{
		this.info = info;
		this.gui = gui;
		comicsMenuItems = new ArrayList<JMenuItem>(3);
		editdMenuItems = new ArrayList<JMenuItem>(info.getMonitoredComics().size());
		downloadMenuItems = new ArrayList<JMenuItem>(info.getMonitoredComics().size());
		readMenuItems = new ArrayList<JMenuItem>(info.getMonitoredComics().size());
		deleteMenuItems = new ArrayList<JMenuItem>(info.getMonitoredComics().size());
		
		setComicsMenu();
		setEditMenu();
		setDownloadMenu();
		setReadMenu();
		setDeleteMenu();
		// Adding items to first menu
	}
	
	private void setComicsMenu()
	{
		comicsMenu = new JMenu("Comics");
		comicsMenu.setMnemonic(KeyEvent.VK_ALT);
		comicsMenuItems.add(new JMenuItem("Add new title"));
		comicsMenuItems.get(0).addActionListener(new MenuItemListenerAdd(info, gui));
		comicsMenuItems.add(new JMenuItem("Remove title"));
		comicsMenuItems.get(1).addActionListener(new MenuItemListenerRemove(info, gui));
		comicsMenuItems.add(new JMenuItem("Refresh"));
		comicsMenuItems.get(2).addActionListener(new MenuItemListenerRefresh(info, gui));
		for (JMenuItem item : comicsMenuItems)
		{
			comicsMenu.add(item);
		}
		add(comicsMenu);
	}
	
	private void setEditMenu()
	{
		editMenu = new JMenu("Edit");
		for (int i = 0; i < info.getMonitoredComics().size(); i++)
		{
			editdMenuItems.add(new JMenuItem(info.getMonitoredComics().get(i).getTitle()));
			editdMenuItems.get(i).addActionListener(new MenuItemListenerEdit(info, gui, info.getMonitoredComics().get(i)));
			editMenu.add(editdMenuItems.get(i));
		}
		add(editMenu);
	}
	
	private void setDownloadMenu()
	{
		downloadMenu = new JMenu("Download");
		for (int i = 0; i < info.getMonitoredComics().size(); i++)
		{
			downloadMenuItems.add(new JMenuItem(info.getMonitoredComics().get(i).getTitle()));
			downloadMenuItems.get(i).addActionListener(new MenuItemListenerDownload(info, gui, info.getMonitoredComics().get(i)));
			downloadMenu.add(downloadMenuItems.get(i));
		}
		add(downloadMenu);
	}
	
	private void setReadMenu()
	{
		readMenu = new JMenu("Read");
		for (int i = 0; i < info.getMonitoredComics().size(); i++)
		{
			readMenuItems.add(new JMenuItem(info.getMonitoredComics().get(i).getTitle()));
			readMenuItems.get(i).addActionListener(new MenuItemListenerRead(info, gui, info.getMonitoredComics().get(i)));
			readMenu.add(readMenuItems.get(i));
		}
		add(readMenu);
	}
	
	private void setDeleteMenu()
	{
		deleteMenu = new JMenu("Delete chapters");
		for (int i = 0; i < info.getMonitoredComics().size(); i++)
		{
			deleteMenuItems.add(new JMenuItem(info.getMonitoredComics().get(i).getTitle()));
			deleteMenuItems.get(i).addActionListener(new MenuItemListenerDelete(info, gui, info.getMonitoredComics().get(i)));
			deleteMenu.add(deleteMenuItems.get(i));
		}
		add(deleteMenu);
	}
}
