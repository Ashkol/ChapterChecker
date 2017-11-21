import java.awt.event.*;

public class ButtonListener implements ActionListener{

	Comics comics = null;
	
	ButtonListener (Comics comics)
	{
		this.comics = comics;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource().equals(comics.incrReadChapter) && comics.getLastReadChapter() < comics.getLastChapter())
		{
				comics.setlastReadchapter(comics.lastReadChapter + 1);
				comics.updateDatabase();
				comics.tableData[0][2] = comics.lastReadChapter;
				comics.table.repaint();
		} else if (event.getSource().equals(comics.decrReadChapter) && comics.getLastReadChapter() > 0)
		{
		comics.setlastReadchapter(comics.lastReadChapter - 1);
		comics.updateDatabase();
		comics.tableData[0][2] = comics.lastReadChapter;
		comics.table.repaint();
		}
	}
	
}
