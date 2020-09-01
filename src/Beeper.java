import java.io.File;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.*;


public class Beeper 
{
	// "global members" 		
	public static final String GOOD_BEEP = "pass.wav";
	public static final String ERROR_BEEP = "error.wav"; 
	public static final int NO_BEEP = 0; 
	public static final int ONE_BEEP = 1; 
	public static final int TWO_BEEPS = 2; 
	public static final int THREE_BEEPS = 3; 
	public static final int FIVE_BEEPS = 5; 	
	
	// old stock Beeps 
	public static final Beeper sound1= new Beeper(ERROR_BEEP, ONE_BEEP);
	public static final Beeper sound2= new Beeper(GOOD_BEEP, ONE_BEEP);	
	
	// Static members
	public static final int BEEP_WAIT_TIME_MS = 300;
	private boolean speakerEnabled; 
	
	// Instance Members
	private File clip_file;
	private URL clip_url; 
	private int numberOfBeeps; 	
	private TimerTask my_timer_task; 
	
	// Constructor 1 
	public Beeper() 
	{
		this(GOOD_BEEP, ONE_BEEP, true);
	}		
	
	// Constructor 2
	public Beeper(int numberOfBeeps)
	{
		this(GOOD_BEEP, numberOfBeeps, true); 
	}
	
	// Constructor 3
	public Beeper(boolean speakerEnabled)
	{
		this(GOOD_BEEP, ONE_BEEP, false);
	}
	
	// Constructor 4
	public Beeper(String filename, int numberOfBeeps)
	{
		this(filename, numberOfBeeps, true); 
	}
	
	/**
	 * Constructs a beep using the following parameters. 
	 * @param filename filename of the beep sound. 
	 * @param numberOfBeeps # of times to beep 
	 * @param speakerEnabled should it beep? 
	 */
	public Beeper(String filename, int numberOfBeeps, boolean speakerEnabled)
	{
		try
		{
			clip_file = new File(filename);
			clip_url = getClass().getResource(filename); 
			
			
//			clip = Applet.newAudioClip(Beeper.class.getResource(filename));
			
		}
		
		catch(Exception e)
		{
			System.out.println("Bad sound filename"); 
			e.printStackTrace();
		}
		
		this.numberOfBeeps = numberOfBeeps;  
		this.speakerEnabled = speakerEnabled;
	}
	
	public int getNumberOfBeeps()	
	{
		return numberOfBeeps; 
	}
	
	/* 
	 * Stops the beeping. 
	 * Useful if you need to stop the beeping
	 * before the audio file finishes playing. 
	 *
	 */
	public void kill() 
	{
		if (my_timer_task != null) {
			my_timer_task.cancel(); 
			my_timer_task = null; 
		}
	}
	
	/**
	 * Beeps using TimerTasks. 
	 */
	public void beep()
	{
		long delay = 0; 
		long period = (long) BEEP_WAIT_TIME_MS;
		
		Timer beep = new Timer(); 
		my_timer_task = new TimerTask()
		{			
			int beepCount = 1; 
			
			public void run() 
			{
				if(speakerEnabled == true)
				{
					try
					{
						new Thread()
						{
							public void run()
							{
								int volume = LockPanel.getVolume(); 
//								System.out.println( "Beeping at this volume " + volume);
								
								try {
									AudioInputStream sound = AudioSystem.getAudioInputStream(clip_url);
									Clip clip = AudioSystem.getClip(); 
									clip.open(sound); 
									// Gain control. 
									FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); 
									double small_v = volume / 1000.00; 
//									System.out.println( "Beeping at this small_v " + small_v);
									float dB = (float) (Math.log(small_v) / Math.log(10) * 20); 
//									System.out.println( "Beeping at this dB " + dB);
									gain.setValue(dB);
											
									clip.start();
								}
								catch (Exception error) {
									error.printStackTrace();
								}
								
							}
						}.start();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				
				if (beepCount++ == numberOfBeeps)
					this.cancel(); 
			}
			
		}; 
		
		beep.scheduleAtFixedRate(my_timer_task, delay, period);
	}
}
