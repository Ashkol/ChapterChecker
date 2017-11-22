
public interface SubjectForGUI {

	public void registerObserver(GUIElement guiElement);
	
	public void removeObserver(GUIElement guiElement);
	
	public void notifyObservers();
	
}
