package businesslogic;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JList;

import gui.ReaderWindow;

public class Reader {

	private ReaderWindow readerWindow;
	private File directory;
	private ArrayList<File> listOfImages;
	private File[] chapterList;
	private int imageCounter;
	private int chapterIndex = 0;
	
	public Reader(File[] listOfChapters, String directoryPath, int selectedIndex )
	{
		directory = new PageFile(directoryPath);
		listOfImages = new ArrayList<File>();
		chapterIndex = selectedIndex;
		chapterList = listOfChapters;
//		File[] images = directory.listFiles();
		
		File[] images = chapterList[chapterIndex].listFiles();

		for (File img : images)
		{
			listOfImages.add(img);
		}
		imageCounter = 0;

		Collections.sort(listOfImages, new FileNumberComparator());
		createReaderWindow();
	}
	
	private void createReaderWindow()
	{
		readerWindow = new ReaderWindow(chapterList,  listOfImages, this);
	}
	
	public int getImageCounter()
	{
		return imageCounter;
	}
	
	public int getChapterIndex()
	{
		return chapterIndex;
	}
	
	public String getComicsTitle()
	{
		return directory.getName();
	}
	
	public void incrementImageCounter()
	{
		if (imageCounter + 1 <= listOfImages.size() - 1)
		{
			imageCounter += 1;
		}
	}
	
	public void decrementImageCounter()
	{
		if (imageCounter - 1 >= 0)
		{
			imageCounter -= 1;
		}
	}
	
	public void goToNextChapter()
	{
		if (chapterIndex + 1 <= chapterList.length - 1)
		{
			chapterIndex +=1;
			imageCounter = 0;
			File[] images = chapterList[chapterIndex].listFiles();
	
			listOfImages.clear();
			for (File img : images)
			{
				listOfImages.add(img);
			}

			Collections.sort(listOfImages, new FileNumberComparator());
		}
	}
	
	public void goToPreviousChapter()
	{
		if (chapterIndex > 0)
		{
			chapterIndex -=1;
			imageCounter = 0;
			File[] images = chapterList[chapterIndex].listFiles();
	
			listOfImages.clear();
			for (File img : images)
			{
				listOfImages.add(img);
			}

			Collections.sort(listOfImages, new FileNumberComparator());
		}
	}
	
}
