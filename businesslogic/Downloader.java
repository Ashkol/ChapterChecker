package businesslogic;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class Downloader  implements Runnable{

	private String title;
	private String mangaStreamString = "https://readms.net";
	private int chapterNumber;
	// Number or other name under which chapter figures on the list
	private String chapterName;
	private ArrayList<String> chapterNames;
	private String tableRowLine;
	private int pageNumber, originalPageNumber;
	private String originalAddress, webpageAddress;
	private String nextPageAddress;
	private URLConnection connection;
	private String lineBeginning;
	boolean lastPage = false;
	int downloadedCounter = 0;
	private boolean isDownloaded = false;
	int chapterCounter = 0;
	
	private double minRatio, maxRatio;
	private Thread threadDownload;
	
	@Override
	public void run()
	{
		for ( chapterCounter = 0 ; chapterCounter < chapterNames.size(); chapterCounter++)
		{	
			chapterName = chapterNames.get(chapterCounter);
			chapterName = chapterName.replaceAll(" ", "");
			System.out.println(chapterNames.size() + " - " + chapterName);
			
			
			openConnectionToFirstPage();
			downloadPage();
			
			if (nextPageAddress != null)
			{
				goToNextPage();
			}
			else 
			{
				lastPage = true;
				JOptionPane.showMessageDialog(null, "No chapter on the list");
			}
			
			while (!lastPage)
			{
				openConnectionToPage();
				downloadPage();
				goToNextPage();
			}
			lastPage = false;
			downloadedCounter += 1;
		}
		
		isDownloaded = true;
		JOptionPane.showMessageDialog(null, "Downloading complete");
	}
	
	public Downloader(ChapterInfo info, Comics comics, ArrayList<String> chapterNames, double minRatio, double maxRatio)
	{
		this.originalAddress = comics.getWebpageAdress();
		this.webpageAddress = comics.getWebpageAdress();
		if (webpageAddress.contains("readms.net"))
		{
			this.title = comics.getTitle();
			this.chapterNumber = comics.getLastReadChapter();
			this.lineBeginning = comics.getLineBeginning();
			this.pageNumber = 1;
			this.originalPageNumber = this.pageNumber;
			this.chapterNames = chapterNames;
			this.minRatio = minRatio;
			this.maxRatio = maxRatio;
			threadDownload = new Thread(this, "downloading");
		}
	}
	
	public void downloadChapter()
	{
		threadDownload.start();
	}
		
	private void openConnectionToFirstPage()
	{
		System.out.println("openConnectionToFirstPage");
		BufferedReader reader = null;
		String line = null;
		String subString = null;
		
		try
		{
//			connection = new URL(webpageAddress).openConnection();
			connection = new URL(originalAddress).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			System.out.println("connects to " + webpageAddress);
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			if (reader == null)
				lastPage = true;
			
			while ((line = reader.readLine()) != null)
			{
//				line.startsWith(lineBeginnning) -> line.contains(lineBeginning)
//				System.out.println(lineBeginning);
//				line = line.replaceAll(" ", "");
//				if (line.contains(lineBeginning))
					System.out.println("line: " + line);
					System.out.println("LineBeginning: " + lineBeginning);
					System.out.println("|" + chapterName);
				
				if (line.contains(lineBeginning) && line.contains("/" + chapterName.replaceAll(" ", "") + "/"))
				{	
					tableRowLine = line;
					System.out.println("OPEN");
					int index = line.indexOf("\"") + 1;
					subString = line.substring(index, line.indexOf("\"", index + 1));
					connection = new URL(mangaStreamString + subString).openConnection();
					connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					connection.connect();
					break;
				}
			}
			
		} catch (MalformedURLException mue)
		{
			mue.printStackTrace();
			lastPage = true;
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to connect to: " + title + "\nAddress: " + webpageAddress);
			lastPage = true;
		}
		catch (SocketTimeoutException ex)
		{
			System.out.println("Read timed out");
			System.out.println("SS");
			lastPage = true;
		}
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
			System.out.println("CC");
			lastPage = true;
		} 
	}
	
	private void openConnectionToPage()
	{
		System.out.println("openConnectionToPage");
		
		try
		{	
			connection = new URL(webpageAddress).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			System.out.println("connects to " + webpageAddress);
			connection.connect();
			
		} catch (MalformedURLException mue)
		{
			mue.printStackTrace();
			System.out.println("AA");
			lastPage = true;
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to connect to: " + title + "\nAddress: " + webpageAddress);
			lastPage = true;
		}
		catch (SocketTimeoutException ex)
		{
			System.out.println("Read timed out");
			System.out.println("SS");
			lastPage = true;
		}
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
			System.out.println("CC");
			lastPage = true;
		} 
	}
	
	private void downloadPage()
	{
		System.out.println("downloadPage");
		
		BufferedReader reader = null;
		String line = null;
		String subString = null;
		String imgHeaders = "<img id=\"manga-page\"  src=\"";
		try
		{
		reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		System.out.println("Connection: " + connection + "------------------------");
//		if (reader == null)
//			System.out.println("reader = null");
		
			while ((line = reader.readLine()) != null)
			{				
				System.out.println(imgHeaders);
				if (line.contains(imgHeaders))
				{
					
					nextPageAddress = mangaStreamString + (line.substring(line.indexOf("\"/") + 1, line.indexOf("\">")));
					System.out.println("nextPageAddress" + nextPageAddress);
					
					subString = line.substring(line.lastIndexOf(imgHeaders) + imgHeaders.length(), line.indexOf("\" />"));
					URL url = new URL("https:" + subString);
				    Image image = ImageIO.read(url);

				    System.out.println("Height: " + image.getHeight(null));
				    System.out.println("Width:  " + image.getWidth(null));
				    
				    double imageHeight =  (double)image.getHeight(null);
				    double imageWidth =  (double)image.getWidth(null);
				    
				    if ((imageHeight/imageWidth <= maxRatio) && (imageHeight/imageWidth >= minRatio))
				    {
				    	saveOnePage(image, "png");
				    	System.out.println("one page");
				    } 
				    else if ((imageHeight/imageWidth <= maxRatio/2) && (imageHeight/imageWidth >= minRatio/2))
				    {
				    	saveTwoPages(image, "png");
				    	System.out.println("two page");
				    }
				    
				    pageNumber += 1;
				    break;
				}
			}
		} catch (MalformedURLException mue)
		{
			mue.printStackTrace();
			System.out.println("AA");
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to connect to: " + title + "\nAddress: " + webpageAddress);
		}
		catch (SocketTimeoutException ex)
		{
			System.out.println("Read timed out");
			System.out.println("SS");
		}
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
			System.out.println("CC");
		}
	}
	
	private void goToNextPage()
	{
		System.out.println("goToNextPage");
		String nextChapter = (chapterNumber + 1) + "";
		if (nextChapter.length() < 3)
			nextChapter = "0" + nextChapter;
		if (nextChapter.length() < 2)
			nextChapter = "0" + nextChapter;
		
		String subLine = nextPageAddress.replace(lineBeginning, "");
		// Added 17.02
		subLine = subLine.replaceAll(" ", "");
		
		System.out.println(subLine.substring(0, subLine.indexOf("/")));
		System.out.println("chapterName: " + chapterName);
		
		if (!(nextPageAddress.contains("/" + chapterName + "/")) || nextPageAddress.endsWith("end"))
		{
			lastPage = true;
			System.out.println("--------------------------------------END----------------------------------------------------\n" + webpageAddress);
		}
		else
		{
		this.webpageAddress = nextPageAddress;
		
		}
		System.out.println(webpageAddress);
	}
	
	private void saveOnePage(Image image, String extension)
	{
		System.out.println("saveOnePage");
		BufferedImage bi = (BufferedImage) image;
	    File outputfile = new File(title + "\\" + chapterName + "\\" + originalPageNumber + "." + extension);
	    outputfile.getParentFile().mkdirs();
	    try
	    {
	    	ImageIO.write(bi, extension, outputfile);
	    }
	    catch (IOException ex)
	    {
	    	
	    }
	    originalPageNumber +=1;
	}
	
	private void saveTwoPages(Image image, String extension)
	{
		System.out.println("saveTwoPages");
		BufferedImage bi = (BufferedImage) image;
		int nextPage = originalPageNumber + 1;
	    File outputfile = new File(title + "\\" + chapterName + "\\" + originalPageNumber + "-" + nextPage + "." + extension);
	    outputfile.getParentFile().mkdirs();
	    try
	    {
	    	ImageIO.write(bi, extension, outputfile);
	    }
	    catch (IOException ex)
	    {
	    	
	    }
	    originalPageNumber +=2;
	}
}
