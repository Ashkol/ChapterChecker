/*Adam Szkolny*/
public class ChapterUpdater {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ChapterInfo state = new ChapterInfo();
		GUI gui = new GUI(state.getMonitoredComics());
		gui.monitoredComics = state.getMonitoredComics();
		//gui.checkForNewChapter(state.getMonitoredComics().get(0));
		
		
	}

}
