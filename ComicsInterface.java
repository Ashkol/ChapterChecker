
public interface ComicsInterface {

	//Setters
	public void setTitle(String title);
	
	public void setAuthor(String author);
	
	public void setLastChapter(int lastChapter);
	
	public void setlastReadchapter(int lastReadChapter);
	//Getters
	public String getTitle();

	public String getAuthor();

	public int getLastChapter();

	public int getLastReadChapter();
	
	//Methods
	public void updateStatus();
	
}
