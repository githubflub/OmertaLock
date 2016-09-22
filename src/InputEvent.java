import java.util.EventObject;

/**
 * Custom event for a pressed button; designed to carry the "ID" of
 * the button that was pressed. (1, 2, 3, *, etc...) 
 * @author Administrator
 *
 */
public class InputEvent extends EventObject 
{
	private String btnID; 
	
	public InputEvent(Object source, String btnID)
	{
		super(source);
		
		this.btnID = btnID; 
	}
	
	public String getBtnID()
	{
		return btnID; 
	}	
}
