import javax.swing.*; 
import javax.swing.event.*; 
import java.awt.*; 
import java.awt.event.*; 

public class Battery extends JFrame
{
	public static final int LOW_BATTERY_LIFE_THRESHOLD = 30; 
	
	private static final int DEFAULT_LIFESPAN = 100;
	private static final int BATTERY_LIFE_REPORT_RATE = 1000; 
	private int currentPower; 
	private int lifeSpan; 
	
	private Timer timer; 	
	
	/**
	 * Constructor
	 */
	public Battery()
	{
		this(DEFAULT_LIFESPAN); 
	}
	
	/**
	 * Constructor
	 * @param lifeSpan Just a number to represent how long the battery lasts (in seconds).
	 */
	public Battery(int lifeSpan)
	{
		this.lifeSpan = lifeSpan;
		currentPower = lifeSpan; 
		timer = new Timer(BATTERY_LIFE_REPORT_RATE, new TimerListener());		 
		timer.start(); 
	}
	
	/**
	 * Custom listener for the timer...
	 * @author Administrator
	 *
	 */
	private class TimerListener implements ActionListener
	{
		/**
		 * Prints the current battery life. 
		 * If battery power is low, prints a warning. 
		 */
		public void actionPerformed(ActionEvent e)
		{			
			System.out.print( getCurrentPowerPercent() + "%" + " " );
				
			if (getCurrentPowerPercent() <= LOW_BATTERY_LIFE_THRESHOLD)
				System.out.print("low battery! \n");
			
			currentPower--; 			 
			
			if (currentPower == 0)
			{
				timer.stop();
				System.exit(0); 
			}
		}
	}
	
	/**
	 * 
	 * @return remaining battery life as a percent. 
	 */
	public int getCurrentPowerPercent()
	{
		int percent = currentPower * 100 / lifeSpan;		
		return percent; 
	}
	
	/**
	 * "Replaces" the battery by resetting the current power. 
	 */
	public void replace()
	{
		currentPower = lifeSpan; 
	}
}
