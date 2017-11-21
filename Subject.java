
public interface Subject {

	void registerObserver(Comics comics);
	
	void removeObserver(Comics comics);
	
	void notifyObservers();
	
}
