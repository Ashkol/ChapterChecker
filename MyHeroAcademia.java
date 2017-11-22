import java.awt.Dimension;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.net.*;

public class MyHeroAcademia extends Comics implements ComicsInterface {

	MyHeroAcademia()
	{
		title = "My Hero Academia";
		author = "Horikoshi Kouhei";
		pathToCover = "images/myheroacademia.png";
		button = new JButton("Check for new chapter");
		loadLastReadChapter();
		updateStatus();
		tableData[0][0] = title;
		tableData[0][1] = author;
		tableData[0][2] = lastReadChapter;
		tableData[0][3] = lastChapter;
		table = new JTable(tableData, columnNames);
		table.setEnabled(false);
		tableScroll = new JScrollPane(table);
		tableScroll.setPreferredSize(new Dimension(0, 39));
		for (GUIElement guiE : observerList)
		{
			guiE.update(tableData);
		}
	}
	
	private void loadLastReadChapter()
	{
		
		Path filePath = FileSystems.getDefault().getPath("D:\\Java\\eclipse-workspace\\Chapter Updater\\", "database.txt");
		Charset charset = Charset.forName("UTF-8");
		
		
		try (BufferedReader reader = Files.newBufferedReader(filePath, charset))
		{	
			String line = reader.readLine();
			while (line != null ) 
			{
				if (line.equals("My Hero Academia")) {
					lastReadChapter = Integer.parseInt(reader.readLine());
					break;
				}
				line = reader.readLine();
			}
		}
		catch (IOException x)
		{
			System.out.println("File with mangas not found; My Hero Academia");
		}
	}
	
	@Override
	public void updateStatus()
	{
		System.out.println("updateStatus()");
		InputStream is = null;
		BufferedReader reader;
		String line = null;
		
		URLConnection connection;
		
		try
		{
			connection = new URL("http://mangastream.com/manga/my_hero_academia").openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			while ((line = reader.readLine()) != null)
			{
				if (getNewChapter(line) > lastChapter)
				{
					lastChapter = getNewChapter(line);
					updateDatabase();
					
					break;
				}
			}
			
		} catch (MalformedURLException mue)
		{
			mue.printStackTrace();
		} catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} finally 
		{
			try
			{
				if (is != null)
				{
					is.close();
				}
			} catch (IOException ex) {}
		}
	}
	
	public void updateDatabase()
	{
		
		try
		{
			Path path = Paths.get("D:\\Java\\eclipse-workspace\\Chapter Updater\\database.txt");
			
			String content = new String(Files.readAllBytes(path));
			String replaced = title;
			replaced += "\r\n";
			String replacing  = replaced;
			replaced += String.valueOf(lastReadChapter - 1);
			replacing += String.valueOf(lastReadChapter);
			content = content.replace(replaced, replacing);
			replaced = title + "\r\n" + String.valueOf(lastReadChapter + 1);	//Quick fix, later replacing method, to include decremention
			content = content.replace(replaced, replacing);
			Files.write(path, content.getBytes());
			
		} catch (IOException exception)
		{
			System.out.println("File not found");
		}
	}	
	
	private int getNewChapter(String stringFromWebpage)
	{
		String line = "<td><a href=\"/r/my_hero_academia/";
		String  number = null;
		
		if (stringFromWebpage.startsWith(line))
		{
			stringFromWebpage = stringFromWebpage.replace("<td><a href=\"/r/my_hero_academia/", "");
			number = stringFromWebpage.substring(0, stringFromWebpage.indexOf("/"));
			System.out.println(stringFromWebpage);
			System.out.println(number);
			
			if (Integer.parseInt(number) >= lastReadChapter)
			{
				return Integer.parseInt(number);
			}
		}
		return -1;			
	}
	
}
