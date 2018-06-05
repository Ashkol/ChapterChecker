package businesslogic;
import java.io.File;

public class PageFile extends File{

	public PageFile(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(File other)
	{
		int thisPage = 0;
		int otherPage = 0;
		String thisPageString = this.getName();
		String otherPageString = other.getName();
		
		if (thisPageString.contains("-"))
		{
			thisPageString = thisPageString.substring(0, thisPageString.indexOf("-"));
		}
		if (otherPageString.contains("-"))
		{
			otherPageString = otherPageString.substring(0, otherPageString.indexOf("-"));
		}
		
		thisPage = new Integer(thisPageString);
		otherPage = new Integer(otherPageString);
		
		if (thisPage > otherPage)
			return 1;
		else if (thisPage < otherPage)
			return -1;
		else return 0;
	}
}
