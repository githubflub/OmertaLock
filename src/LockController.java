import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer; 
import java.util.TimerTask; 

public class LockController
{
	private static final long NO_DELAY = 0; 
	private static final long TEN_SECONDS = 10000; 
	private Lock model; 
	private LockView view;	
	
	/**
	 * Constructor 
	 * Connects controller to the model. 
	 * Connects controller to the view. 
	 * Starts monitoring the battery. 
	 * Sets the GUI visible. 
	 * @param model
	 * @param view
	 */
	public LockController(Lock model, LockView view)
	{
		this.model = model; 
		this.view = view; 
		
		this.view.addLockPanelListener(new LockPanelListener());
		
		batteryMonitor(); 
		
		this.view.setVisible(true); 
	}
	
	/**
	 * This method uses a TimerTask to check battery life 
	 * every ten seconds. 
	 * If the battery is low, tells the GUI to beep
	 * and blink. 
	 */
	private void batteryMonitor()
	{
		long startCheckingDelay = NO_DELAY; 
		long checkInterval = TEN_SECONDS; 
		final int lowBatteryLifeThreshold = Battery.LOW_BATTERY_LIFE_THRESHOLD; 
		
		Timer batteryTimer = new Timer(); 
		batteryTimer.scheduleAtFixedRate(new TimerTask() 
		{
			public void run()
			{
				if ( model.getCurrentBatteryPowerPercent() <= lowBatteryLifeThreshold )
				{
					view.lowBatteryLifeWarning(); 
				}
			}
		}, startCheckingDelay, checkInterval);
		
	}
	
	/**
	 * This Listener listens for input from the GUI and 
	 * then sends it to the model for handling. 
	 * Then it tells the GUI what to do using information
	 * obtained from the model. 
	 * @author Administrator
	 *
	 */
	class LockPanelListener implements InputListener
	{
		public void inputEventOccurred(InputEvent e)
		{
			// Send input to model for processing. 
			System.out.println( "\n" + e.getBtnID() );
			model.getNextInput( e.getBtnID() ); 
			
			// Make the GUI beep. 
			try
			{				
				if (model.beeperActive() == true)
					view.beep( model.getBeepDetails() );
				else
					view.beep( new Beeper(false) );
			}
			catch (Exception err)
			{
				System.out.println("Do more sound programming.");  
			}
			
			// Make the GUI blink. 
			try
			{				 
				view.blink( model.getBlinkDetails() );
			}
			catch (Exception ex)
			{
				System.out.println("Do more LED programming"); 
			}
		}
	}
}

