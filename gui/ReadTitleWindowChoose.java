package gui;
import javax.swing.*;
import businesslogic.ChapterInfo;
import businesslogic.Comics;
import businesslogic.FileNumberComparator;
import controller.ReadComicsListListener;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReadTitleWindowChoose extends JFrame{
	
	private int windowWidth = 400;
	private int windowHeight = 400;
	private ChapterInfo info;
	private GUI gui;
	private JScrollPane scroll;
	private Comics comics;
	private File directory;
	private File[] listOfChapters;
	private ArrayList<File> listOfChaptersList;
	
	private Container frameContent;
	private JPanel content, buttonPanel;
	private JList<File> list;
	private JButton readButton;
	
	public ReadTitleWindowChoose(ChapterInfo info, GUI gui, Comics comics)
	{
		frameContent = this.getContentPane();
		content = new JPanel();
		frameContent.add(content);
		
		this.info = info;
		this.gui = gui;
		this.comics = comics;
		this.directory = new File(this.comics.getTitle());
		this.listOfChapters = directory.listFiles();
		
		this.listOfChaptersList = new ArrayList<>(Arrays.asList(listOfChapters));
		Collections.sort(this.listOfChaptersList, new FileNumberComparator());
		this.listOfChapters = listOfChaptersList.toArray(new File[0]);
		
		list = new JList(listOfChapters);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(-1);

		setProperties();
		setList();
	}
	
	private void setProperties()
	{
		setSize(new Dimension(windowWidth, windowHeight));	
		setResizable(false);
		setVisible(true);
	}

	private void setList()
	{
		list = new JList(listOfChapters);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setName("Chapters' list");
		list.addListSelectionListener(new ReadComicsListListener(listOfChapters, list, this));
		JScrollPane listScroller = new JScrollPane(list);
		scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(250, 300));
		content.add(scroll);
	}
}
 