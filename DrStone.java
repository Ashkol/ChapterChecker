import java.awt.Dimension;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.net.*;

public class DrStone extends Comics implements ComicsInterface {

	DrStone()
	{
		title = "Dr. Stone";
		author = "Inagaki Riichiro & Boichi";
		pathToCover = "images/drstone.jpg";
		button = new JButton("Check for new chapter");
		//loadLastChapter();
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
		
		//updateDatabase();
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
				if (line.equals("Dr. Stone")) {
					lastReadChapter = Integer.parseInt(reader.readLine());
					//System.out.println(lastReadChapter);
					break;
				}
				line = reader.readLine();
			}
		}
		catch (IOException x)
		{
			System.out.println("File with mangas not found; Dr. Stone");
		}
	}
	
	/*
	private void loadLastChapter()
	{
		Path filePath = FileSystems.getDefault().getPath("data", "manga.txt");
		Charset charset = Charset.forName("ASCII");
		try (BufferedReader reader = Files.newBufferedReader(filePath, charset))
		{	
			
			while (reader.readLine() != "One Piece") 
			{
			}
			reader.read();
			this.lastReadChapter = reader.read();
		}
		catch (IOException x)
		{
			System.out.println("File with mangas not found; One Piece");
		}
	}
	*/
	
	@Override
	public void updateStatus()
	{
		System.out.println("updateStatus()");
		URL url;
		InputStream is = null;
		BufferedReader reader;
		String line = null;
		
		try
		{
			url = new URL("https://mangastream.com/manga/dr_stone");
			is = url.openStream();
			reader = new BufferedReader(new InputStreamReader(is));
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
		
		String line = "<td><a href=\"/r/dr_stone/";
		
		String  number = null;
		if (stringFromWebpage.startsWith(line))
		{
			stringFromWebpage = stringFromWebpage.replace("<td><a href=\"/r/dr_stone/", "");
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
