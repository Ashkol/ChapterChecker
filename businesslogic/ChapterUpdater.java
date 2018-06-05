/*
 * Created by Adam Szkolny
 */

package businesslogic;
import gui.GUI;

public class ChapterUpdater {

	public static void main(String[] args) 
	{
		ChapterInfo state = new ChapterInfo();
		GUI gui = new GUI(state);
		gui.monitoredComics = state.getMonitoredComics();		
	}

}
