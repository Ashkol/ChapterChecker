package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class TextFieldListener implements ActionListener{

	JTextField textField;
	String text;
	
	TextFieldListener(JTextField textField, String text)
	{
		this.textField = textField;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JTextField)
		{
			text = textField.getText();
		}
	}
}