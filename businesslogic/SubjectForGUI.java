package businesslogic;
public interface SubjectForGUI 
{
	public void registerObserver(GUIElementObserver guiElement);
	
	public void removeObserver(GUIElementObserver guiElement);
	
	public void notifyObservers();	
}
