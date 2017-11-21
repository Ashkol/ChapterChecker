import java.io.*;
import java.util.ArrayList;

public class ChapterInfo implements Subject {

	private ArrayList<Comics> monitoredComics = new ArrayList<Comics>();
	
	ChapterInfo()
	{
		OnePiece onePiece = new OnePiece();
		Berserk berserk = new Berserk();
		MyHeroAcademia myHeroAcademia = new MyHeroAcademia();
		DrStone drStone = new DrStone();
		registerObserver(onePiece);
		registerObserver(berserk);
		registerObserver(myHeroAcademia);
		registerObserver(drStone);
		System.out.println("Last read chapter of One Piece: " + onePiece.getLastReadChapter());
		System.out.println("Last read chapter of Berserk: " + berserk.getLastReadChapter());
		System.out.println("Last read chapter of My Hero Academia: " + myHeroAcademia.getLastReadChapter());
		System.out.println("Last chapter of One Piece: " + onePiece.getLastChapter());
	}
	
	public void addComics(String title, String author, int lastChapter, int lastReadChapter)
	{
		monitoredComics.add(new Comics(title, title, lastChapter, lastReadChapter));
	}
	
	public ArrayList<Comics> getMonitoredComics()
	{
		return monitoredComics;
	}
	
	@Override
	public void registerObserver(Comics comics)
	{

		monitoredComics.add(comics);
	}
	
	@Override
	public void removeObserver(Comics comics)
	{
		int i = monitoredComics.indexOf(comics);
		if (i >= 0)
		{
			monitoredComics.remove(i);
		}
		
	}
	
	@Override
	public void notifyObservers()
	{
		for(Comics c : monitoredComics)
		{
			c.updateStatus();
		}
	}
	
	public void updateChapterInfo()
	{
		
	}
	
	public void  saveChapterInfo()
	{
		FileOutputStream fileStream = null;
		ObjectOutputStream objOutStream = null;
		try
		{
			fileStream = new FileOutputStream("D:\\Java\\eclipse-workspace\\Chapter Updater\\log.txt");
			objOutStream = new ObjectOutputStream(fileStream);
			for(Comics c : monitoredComics)
			{
				objOutStream.writeObject(c);
			}	
		} catch (FileNotFoundException ex)
		{
			
		} catch (IOException ex)
		{
			
		} finally 
		{
			try
			{
			objOutStream.close();
			} catch (IOException ex) {}
		}
	}


}
