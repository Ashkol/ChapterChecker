package businesslogic;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class ChapterInfo{

	private ArrayList<Comics> monitoredComics = new ArrayList<Comics>();
	
	String separator = "%%";
	
	ChapterInfo()
	{
		loadComicsFromDatabase();
	}
	
	private void loadComicsFromDatabase()
	{
		String[] array;
		Charset charset = Charset.forName("UTF-8");
		Path path = Paths.get("splitdatabase.txt");
		String line;
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset))
		{
			while ((line = reader.readLine()) != null)
			{
				array = line.split(separator);
				addMonitoredComics(new Comics(array[0]));
			}
		} catch (IOException exception)
		{
			System.out.println("Exception during loading database. Check if splitdatabase.txt is in the same directory as jar executable.");
			System.out.println(exception);
		}
	}
	
	public void updateDatabase()
	{		
		try
		{
			Path path = Paths.get("splitdatabase.txt");
			String line = "";
			String newContent = "";
			
			for (Comics comics : monitoredComics)
			{
				line += comics.getTitle() + separator;
				line += comics.getAuthor() + separator;
				line += comics.getLastReadChapter() + separator;
				line += comics.getLastChapter() + separator;
				line += comics.getPathToCover() + separator;
				line += comics.getWebpageAdress() + separator;
				line += comics.getLineBeginning() + "\n";
				newContent += line;
				line = "";
			}
			
			Files.write(path, newContent.getBytes());
		}
		catch (IOException exception)
		{
			System.out.println("Exception during updating database. Check if splitdatabase.txt is in the same directory as jar executable.");
			System.out.println(exception);
		}
	}
	
	public void removeComicsByTitle(String title)
	{
		int index = 0;
		for (int i = monitoredComics.size()-1; i >= 0; i--)
		{
			if (monitoredComics.get(i).getTitle().equals(title))
			{
				index = monitoredComics.indexOf(monitoredComics.get(i));
				monitoredComics.remove(index);
				break;
			}
		}
	}
	
	public Comics getComicsByTitle(String title)
	{
		for (int i = monitoredComics.size()-1; i >= 0; i--)
		{
			if (monitoredComics.get(i).getTitle().equals(title))
			{
				return 	monitoredComics.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Comics> getMonitoredComics()
	{
		return monitoredComics;
	}
	

	public void addMonitoredComics(Comics comics)
	{
		monitoredComics.add(comics);
		Collections.sort(monitoredComics);
	}
	
	private void removeMonitoredComics(Comics comics)
	{
		int i = monitoredComics.indexOf(comics);
		if (i >= 0)
		{
			monitoredComics.remove(i);
		}
	}
	
	public void updateMonitoredComics()
	{
		for(Comics c : monitoredComics)
		{
			c.updateStatus();
		}
	}

}
