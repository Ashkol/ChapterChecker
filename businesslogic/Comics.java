package businesslogic;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import gui.ComicsPanelLabelNew;
import gui.ComicsPanelLabelPercentage;
import gui.HeaderTable;

public class Comics implements SubjectForGUI, Comparable<Comics>
{
	private String title;
	private String author;
	private int lastChapter;
	private int lastReadChapter;
	private String pathToCover;
	private JLabel cover;
	private String webpageAddress;
	private String lineBeginning;
	
	ArrayList<GUIElementObserver> observerList = new ArrayList<GUIElementObserver>();
	ArrayList<String> chapterList = new ArrayList<String>();
	JPanel informationElements = new JPanel();
	public JButton button;
	Object[][] tableData = new Object[1][4];
	public ComicsPanelLabelNew isThereNew;
	public JScrollPane tableScroll;
	
	//Constructors
	Comics() 
	{	
		this.title = "N/A";
		this.author = "N/A";
		this.lastChapter = 0;
		this.lastReadChapter = 0;
	}
	
	Comics(String title)
	{
		this.title = title;
		this.author = "N/A";
		this.lastChapter = 0;
		this.lastReadChapter = 0;
		loadData();
		updateStatus();
		loadCover();
		tableData[0][0] = this.title;
		tableData[0][1] = author;
		tableData[0][2] = lastReadChapter;
		tableData[0][3] = lastChapter;
		
		registerObserver(new HeaderTable(tableData));
		registerObserver(new ComicsPanelLabelNew());
		registerObserver(new ComicsPanelLabelPercentage());
		notifyObservers();
	}
	
	public Comics(String title, String author, int lastReadChapter, String pathToCover, String webpageAddress, String lineBeginning)
	{
		this.title = title;
		this.author = author;
		this.pathToCover = pathToCover;
		this.lastReadChapter = lastReadChapter;
		this.webpageAddress = webpageAddress;
		this.lineBeginning = lineBeginning;
		updateStatus();
		loadCover();
		tableData[0][0] = title;
		tableData[0][1] = author;
		tableData[0][2] = lastReadChapter;
		tableData[0][3] = lastChapter;

		registerObserver(new HeaderTable(tableData));
		registerObserver(new ComicsPanelLabelNew());
		registerObserver(new ComicsPanelLabelPercentage());
		notifyObservers();
	}
	
	//Setters
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public void setLastChapter(int lastChapter)
	{
		this.lastChapter = lastChapter;
	}

	public void setlastReadchapter(int lastReadChapter)
	{
		this.lastReadChapter = lastReadChapter;
	}
	
	public void setPathToCover(String path)
	{
		this.pathToCover = path;
	}
	
	public void setCover(JLabel cover)
	{
		this.cover = cover;
	}
	
	public void setWebpageAddress(String address)
	{
		this.webpageAddress = address;
	}
	
	public void setLineBeginning(String lineBeginning)
	{
		this.lineBeginning = lineBeginning;
	}
	
	//Getters
	public String getTitle()
	{
		return title;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public int getLastChapter()
	{
		return lastChapter;
	}
	
	public int getLastReadChapter()
	{
		return lastReadChapter;
	}
	
	public String getPathToCover()
	{
		return pathToCover;
	}
	
	public JLabel getCover()
	{
		return cover;
	}
	
	public String getWebpageAdress()
	{
		return webpageAddress;
	}
	
	public String getLineBeginning()
	{
		return lineBeginning;
	}
	
	public Object[][] getTableData()
	{
		return tableData;
	}
	
	public ArrayList<String> getChapterList()
	{
		return chapterList;
	}
	
	public ArrayList<GUIElementObserver> getObserverList()
	{
		return observerList;
	}
	
	
	// Overriding SubjectForGUI interface methods
	@Override
	public void registerObserver(GUIElementObserver observer)
	{
		observerList.add(observer);
	}
	
	@Override
	public void removeObserver(GUIElementObserver observer)
	{
		observerList.remove(observer);
	}
	
	@Override
	public void notifyObservers()
	{
		for (GUIElementObserver o : observerList)
		{
			updateTableData();
			o.update(tableData);
		};
	}
	
	@Override
	public int compareTo(Comics other)
	{
		if (title.compareToIgnoreCase(other.getTitle()) <= 0)
		{
			return -1;
		}
		else 
		{
			return 1;	
		}
	}
	
	private void loadCover() 
	{	
		try 
		{
			BufferedImage myPicture = ImageIO.read(new File(pathToCover));
			BufferedImage resizedImg = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = resizedImg.createGraphics();
				 
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g2.drawImage(myPicture, 0, 0, 200, 300, null);
		    g2.dispose();

			cover = new JLabel(new ImageIcon(resizedImg));	
		} 
		catch (IOException ex) 
		{
			cover = new JLabel("Image not loaded");
		}
	}
	
	public void reloadCover()
	{
		loadCover();
	}
	
	public void updateStatus()
	{
		BufferedReader reader = null;
		String line = null;
		URLConnection connection = null;
		
		try
		{
			System.out.println("Connection attempt");
			connection = new URL(webpageAddress).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null)
			{	
//				System.out.println(line);
//				if (line.startsWith(lineBeginning))
				if (line.contains(lineBeginning))
				{
					System.out.println("Line found");
					if (getNewChapter(line) >= lastChapter)
					{
						System.out.println("new chapter");
						lastChapter = getNewChapter(line);
						notifyObservers();
					}
					
					addToChapterList(line);
				}
			}
		} catch (MalformedURLException mue)
		{
			mue.printStackTrace();
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to connect to: " + title + "\nAddress: " + webpageAddress);
		}
		catch (SocketTimeoutException ex)
		{
			System.out.println("Read timed out");
		}
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
	}
	
	private void updateTableData()
	{
		tableData[0][0] = title;
		tableData[0][1] = author;
		tableData[0][2] = lastReadChapter;
		tableData[0][3] = lastChapter;
	}

	// Loads data about comics from splitdatabase.txt file
	public void loadData()
	{
		String[] array;
		Charset charset = Charset.forName("UTF-8");
		Path path = Paths.get("splitdatabase.txt");
		String line;
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset))
		{
			while ((line = reader.readLine()) != null)
			{
				array = line.split("%%");
				if (array[0].equals(title))
				{
					title = array[0];
					author = array[1];
					lastReadChapter = Integer.parseInt(array[2]);
					lastChapter = Integer.parseInt(array[3]);
					pathToCover = array[4];
					webpageAddress = array[5];
					lineBeginning = array[6];
				} 
			}
		} catch (IOException exception)
		{
			
		}
	}
	
	private void addToChapterList(String line)
	{
		String chapterTitle;
		String workLine = line;
		
		workLine = workLine.replace(lineBeginning, "");
		chapterTitle = workLine.substring(0, workLine.indexOf("/"));

		
		if (!chapterList.contains(chapterTitle))
			 chapterList.add(chapterTitle);
	}
	
	private int getNewChapter(String stringFromWebpage)
	{
		System.out.println("getNewChapter");
		String line = lineBeginning;
		String  numberString = null;
		int number;
		int secondIndex = 0;
		
		if (stringFromWebpage.contains(line))
		{
			
			System.out.println("stringFromWebpage: " + stringFromWebpage);
			stringFromWebpage = stringFromWebpage.replace(line, "");
			stringFromWebpage = stringFromWebpage.replaceAll(" ", "");
			System.out.println("stringFromWebpage: " + stringFromWebpage);
			System.out.println("stringFromWebpage.length(): " + stringFromWebpage.length());
			
			
			
			for (int index = 0; index <stringFromWebpage.length(); index++)
			{
				System.out.println(index);
				if (!Character.isDigit(stringFromWebpage.charAt(index)) && !Character.isWhitespace(stringFromWebpage.charAt(index)))
				{
					secondIndex = index;
					System.out.println("charAt:" + stringFromWebpage.charAt(index));
					System.out.println("seconIndex = " + secondIndex);
					break;
				}
			}
			
			numberString = stringFromWebpage.substring(0, secondIndex);
			System.out.println("number = " + numberString);
			
			try
			{
				number = Integer.parseInt(numberString);
			} catch (NumberFormatException ex)
			{
				number = lastReadChapter;
			}
	
			if (number > lastReadChapter)
			{
				return number;
			}
		}
		return lastReadChapter;			
	}
}

