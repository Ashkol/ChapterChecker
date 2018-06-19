package gui;
import javax.swing.*;
import businesslogic.ChapterInfo;
import businesslogic.Comics;
import businesslogic.FileNumberComparator;
import controller.DeleteChaptersButtonListener;
import controller.ReadComicsListListener;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DeleteChaptersWindowChoose extends JFrame{
	
	private int windowWidth = 400;
	private int windowHeight = 400;
	private ChapterInfo info;
	private GUI gui;
	private JScrollPane scroll;
	private Comics comics;
	private File directory;
	private File[] downloadedChapters;
	private ArrayList<File> tempChaptersList;
	private ArrayList<JCheckBox> checkBoxes;
	private JButton deleteButton;
	
	private Container frameContent;
	private JPanel content, buttonPanel, checkBoxesPanel;
	private JList<File> list;
	
	public DeleteChaptersWindowChoose(ChapterInfo info, GUI gui, Comics comics)
	{
		frameContent = this.getContentPane();
		content = new JPanel();
		frameContent.add(content);
		checkBoxesPanel = new JPanel();
		checkBoxesPanel.setLayout(new BoxLayout(checkBoxesPanel, BoxLayout.Y_AXIS));
		
		this.info = info;
		this.gui = gui;
		this.comics = comics;
		this.directory = new File(this.comics.getTitle());
		this.downloadedChapters = directory.listFiles();
		
		this.tempChaptersList = new ArrayList<>(Arrays.asList(downloadedChapters));
		Collections.sort(this.tempChaptersList, new FileNumberComparator());
		this.downloadedChapters = tempChaptersList.toArray(new File[0]);
		checkBoxes = new ArrayList<JCheckBox>();
		
		list = new JList(downloadedChapters);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		setProperties();
//		setList();
		setCheckBoxes();
		setButton();
	}
	
	private void setProperties()
	{
		setSize(new Dimension(windowWidth, windowHeight));	
		setResizable(false);
		setVisible(true);
	}

	private void setCheckBoxes()
	{
		for (int i = 0; i < downloadedChapters.length; i++)
		{
			// Directory's name should be the same as comics title
			checkBoxes.add(new JCheckBox(downloadedChapters[i].getName()));
			JCheckBox checkBox = checkBoxes.get(i);
			checkBoxesPanel.add(checkBox);
		}
		
		scroll = new JScrollPane(checkBoxesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(windowWidth-200, windowHeight - 100));
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		content.add(scroll);
	}
	
	private void setList()
	{
		list = new JList(downloadedChapters);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setName("Chapters' list");
		list.addListSelectionListener(new ReadComicsListListener(downloadedChapters, list, this));
		JScrollPane listScroller = new JScrollPane(list);
		scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(250, 300));
		content.add(scroll);
	}
	
	private void setButton()
	{
		deleteButton = new JButton("Delete chapters");
		ArrayList<File> chapters = new ArrayList<File>();
		for (File file : tempChaptersList)
		{
			chapters.add(new File(comics.getTitle() + "//" + file.getName()));
		}
		
		deleteButton.addActionListener(new DeleteChaptersButtonListener(info, gui, this, checkBoxes, chapters));
		content.add(deleteButton);
	}
}
