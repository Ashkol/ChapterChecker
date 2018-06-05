package businesslogic;
import java.io.File;
import java.util.Comparator;

public class FileNumberComparator implements Comparator<File> {

	@Override
	public int compare(File file1, File file2) {
		
		int thisPage = 0;
		int otherPage = 0;
		String thisPageString = file1.getName();
		String otherPageString = file2.getName();
		
		if (thisPageString.contains(".png"))
			thisPageString = thisPageString.replace(".png", "");
		if (otherPageString.contains(".png"))
			otherPageString = otherPageString.replace(".png", "");
		
		if (thisPageString.contains("-"))
		{
			thisPageString = thisPageString.substring(0, thisPageString.indexOf("-"));
		}
		if (otherPageString.contains("-"))
		{
			otherPageString = otherPageString.substring(0, otherPageString.indexOf("-"));
		}
		
		thisPageString.replaceAll(",", ".");
		otherPageString.replaceAll(",", ".");
		
		if (thisPageString.contains(",") || otherPageString.contains("."))
		{
			if (new Double(thisPageString) > new Double(otherPageString))
				return 1;
			else
				return -1;
		} 
		else
		{
			thisPage = new Integer(thisPageString);
			otherPage = new Integer(otherPageString);
		}
		
		if (thisPage > otherPage)
			return 1;
		else if (thisPage < otherPage)
			return -1;
		else return 0;
	
	}

}
