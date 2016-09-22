import java.util.EventListener;

/**
 * Custom listener interface for custom event (button pressing)
 * @author Administrator
 *
 */
public interface InputListener extends EventListener
{
	public void inputEventOccurred(InputEvent event);
}
