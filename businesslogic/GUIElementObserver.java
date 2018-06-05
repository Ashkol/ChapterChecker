package businesslogic;
import javax.swing.JComponent;

public interface GUIElementObserver{
	
	public void update(Object[][] data);
	
	public JComponent returnComponent();
}
