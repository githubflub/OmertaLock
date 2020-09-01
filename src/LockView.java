import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class LockView extends JFrame
{
	private LockPanel lockPanel; 
	private Beeper lockBeeper; 
	
	/**
	 * Constructor
	 * Makes the GUI look cool.
	 * Calls a method to initialize components such as
	 * the JPanel used for the keypad. 
	 */
	public LockView()
	{
		// Try to make the GUI look cool. 
		try 
		{
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {}
		
		initComponents(); 		
	}
	
	/**
	 * Prepares the GUI frame. 
	 * Adds some components to it, 
	 * like the LockPanel and the LockBeeper. 
	 */
	private void initComponents()
	{
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	    setTitle("Omerta Electronic Lock");
	    setSize(270, 600);
	    setLayout(new BorderLayout());
	    
	    lockPanel = new LockPanel();
	    lockBeeper = new Beeper(); 
	    
	    Container c = getContentPane(); 
	    c.add(lockPanel, BorderLayout.CENTER); 
	}
	
	/**
	 * Used to add a listener to the Panel. 
	 * @param listenForBtns
	 */
	void addLockPanelListener(InputListener listenForBtns)
	{
		lockPanel.addInputListener(listenForBtns);
	}
	
	/**
	 * Just a middle man method between the controller
	 * and the LockPanel. 
	 * @param blinkDetails
	 */
	public void blink(LED blinkDetails)
	{
		lockPanel.stopBlinking(); // Just in case it's currently blinking. 
		lockPanel.blink(blinkDetails); 		 
	}
	
	/**
	 * Just a middle man method between the controller
	 * and the LockPanel. 
	 * @param beepkDetails
	 */
	public void beep(Beeper beepDetails)
	{
		// Kill existing lockBeeper if it's still going? 
		lockBeeper.kill();
		lockBeeper = beepDetails; 
		lockBeeper.beep(); 
	}
	
	/**
	 * Makes the lock give a low battery life 
	 * warning by causing special beeps and blinks. 
	 */
	public void lowBatteryLifeWarning() 
	{
		beep(Lock.BAD_PIN_ENTRY);
		lockPanel.blink(Lock.BAD_PIN_ENTRY_LED); 		
	}
}


