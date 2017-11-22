/*Adam Szkolny*/
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Comics implements ComicsInterface, SubjectForGUI//, Serializable
{
	protected String title;
	protected String author;
	protected int lastChapter;
	protected int lastReadChapter;
	protected String pathToCover;
	
	ArrayList<GUIElement> observerList = new ArrayList<GUIElement>();
	
	JPanel interactiveElements = new JPanel();
	public JButton button;
	public JButton incrReadChapter;
	public JButton decrReadChapter;
	String[] columnNames = {"Title", "Author", "Last Read Chapter","Newest Chapter"};
	Object[][] tableData = new Object[1][4];
	public JTable table;
	public JScrollPane tableScroll;
	
	//Constructors
	Comics() 
	{	
		this.title = "N/A";
		this.author = "N/A";
		this.lastChapter = 0;
		this.lastReadChapter = 0;
		registerObserver(new HeaderTable());
		
	}
	
	Comics(String title)
	{
		this.title = title;
		this.author = "N/A";
		this.lastChapter = 0;
		this.lastReadChapter = 0;
	}
	
	Comics(String title, String author)
	{
		this.title = title;
		this.author = author;
		this.lastChapter = 0;
		this.lastReadChapter = 0;
	}
	
	Comics(String title, String author, int lastChapter)
	{
		this.title = title;
		this.author = author;
		this.lastChapter = lastChapter;
		this.lastReadChapter = 0;
	}
	
	Comics(String title, String author, int lastChapter, int lastReadChapter)
	{
		this.title = title;
		this.author = author;
		this.lastChapter = lastChapter;
		this.lastReadChapter = lastReadChapter;
	}

	//Setters
	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Override
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	@Override
	public void setLastChapter(int lastChapter)
	{
		this.lastChapter = lastChapter;
	}
	
	@Override
	public void setlastReadchapter(int lastReadChapter)
	{
		this.lastReadChapter = lastReadChapter;
	}
	
	//Getters
	@Override
	public String getTitle()
	{
		return title;
	}
	
	@Override
	public String getAuthor()
	{
		return author;
	}
	
	@Override
	public int getLastChapter()
	{
		return lastChapter;
	}
	
	@Override
	public int getLastReadChapter()
	{
		return lastReadChapter;
	}
	
	@Override
	public void updateStatus()
	{
		
	}
	
	public void updateDatabase()
	{
		
	}
	
	// Overriding SubjectForGUI interface methods
	@Override
	public void registerObserver(GUIElement observer)
	{
		observerList.add(observer);
	}
	
	@Override
	public void removeObserver(GUIElement observer)
	{
		observerList.remove(observer);
	}
	
	@Override
	public void notifyObservers()
	{
		for (GUIElement o : observerList)
		{
			o.update(tableData);
		};
	}
	
	
}
