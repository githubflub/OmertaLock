import java.util.Timer;
import java.util.TimerTask;

/**
 * Lock class - Main class for the model. 
 * @author Administrator
 *
 */
public class Lock
{	
	// Class static data
	private static final PIN DEFAULT_PROGRAMMING_PIN = new PIN(1234, PINType.PROGRAMMING);
	private static final PIN DEFAULT_NORMALUSE_PIN = new PIN(4321, PINType.NORMAL_USE); 	
	
	private static final PIN CHANGE_PROGRAMMING_COMMAND = new PIN(1, PINType.CHANGE_PROGRAMMING_CODE);
	private static final PIN ADD_NORMALUSE_COMMAND = new PIN(2, PINType.ADD_NORMAL_USE);
	private static final PIN ADD_TOGGLE_COMMAND = new PIN(3, PINType.ADD_TOGGLE);
	private static final PIN ADD_FREEZE_COMMAND = new PIN(4, PINType.ADD_FREEZE);
	private static final PIN ADD_VIP_COMMAND = new PIN(5, PINType.ADD_VIP);
	// This should be called DELETE_PIN_COMMAND
	private static final PIN DELETE_COMMAND = new PIN(6, PINType.DELETE);
	private static final PIN TOGGLE_BEEPER_COMMAND = new PIN(7, PINType.TOGGLE_BEEPER);
	private static final PIN CHANGE_RELOCK_TIME_COMMAND = new PIN(8, PINType.CHANGE_RELOCK_TIME);	
	
	private static final PIN SPECIAL_REPLACE_BATT = new PIN(1, PINType.REPLACE_BATTERY);
	private static final PIN SPECIAL_RESTORE_DEFAULTS = new PIN(2, PINType.RESTORE_FACTORY_DEFAULTS);
	private static final PIN SPECIAL_SELF_DESTRUCT = new PIN(3, PINType.SELF_DESTRUCT); 
	private static final PIN SPECIAL_EXIT_PROGRAMMING_MODE = new PIN(0, PINType.EXIT_PROGRAMMING_MODE);
	
	private static final Beeper NUMBER_OR_STAR_BUTTON_PRESSED = new Beeper(Beeper.GOOD_BEEP, Beeper.ONE_BEEP);
	private static final Beeper POUND_BUTTON_PRESSED = new Beeper(Beeper.NO_BEEP); 
	private static final Beeper SUCCESSFUL_PIN_ENTRY = new Beeper(Beeper.GOOD_BEEP, Beeper.TWO_BEEPS);
	public static final Beeper BAD_PIN_ENTRY = new Beeper(Beeper.ERROR_BEEP, Beeper.ONE_BEEP);
	private static final Beeper BAD_PIN_ENTRY_PRO = new Beeper(Beeper.ERROR_BEEP, Beeper.TWO_BEEPS);
	private static final Beeper SPECIAL_ACTIVATED_BEEPER = new Beeper (Beeper.GOOD_BEEP, Beeper.THREE_BEEPS);
			
	private static final LED NUMBER_OR_STAR_PRESSED_LED = new LED(LED.GREEN_ON, LED.ONE_BLINK);
	private static final LED POUND_BUTTON_PRESSED_LED = new LED( LED.NO_BLINK ); 
	private static final LED SUCCESSFUL_PIN_ENTRY_LED = new LED( LED.GREEN_ON, LED.TWO_BLINKS); 
	public static final LED BAD_PIN_ENTRY_LED = new LED( LED.RED_ON, LED.ONE_BLINK ); 
	private static final LED BAD_PIN_ENTRY_LED_PRO = new LED( LED.RED_ON, LED.TWO_BLINKS ); 
	private static final LED SPECIAL_ACTIVATED_LED = new LED( LED.GREEN_ON, LED.THREE_BLINKS);
	
	private static final int DEFAULT_RELOCK_TIME = 5000; // in milliseconds
	
	
	// instance members
	private Battery battery; 
	private CommandPreparer commandPreparer; 
	private Beeper beepDetails; 
	private LED blinkDetails; 
	private boolean programmingModeEnabled;	
	private PIN_List pinList;
	private PIN_List commandList;	
	private PINType commandPINType;
	private PIN currentProgrammingPIN;
	private int relockTime; 
	private ProgrammingModeCommandPreparer proModeCP;
	private boolean isLocked;
	private boolean isFrozen;
	private boolean beeperActive;
	
	/**
	 * Constructor
	 * Instantiates a battery and
	 * sets the lock to its factory defaults. 
	 */
	public Lock()
	{
		battery = new Battery(); 
		restoreFactoryDefaults();	
	}
	
	/**
	 * restores factory defaults...
	 */
	private void restoreFactoryDefaults()
	{
		pinList = new PIN_List();
		commandList = new PIN_List();
		//maintenanceCommList = new PIN_List();
		loadFactoryDefaults();
		commandPreparer = new CommandPreparer();
		proModeCP = new ProgrammingModeCommandPreparer();
		programmingModeEnabled = false;	
	}
	
	/**
	 * loads factory defaults (PINs and such) to the 
	 * hash tables that hold this information. 
	 */
	private void loadFactoryDefaults()
	{
		pinList.insert(DEFAULT_PROGRAMMING_PIN);
		currentProgrammingPIN = DEFAULT_PROGRAMMING_PIN;
		pinList.insert(DEFAULT_NORMALUSE_PIN);		
		
		pinList.insert(SPECIAL_REPLACE_BATT);
		pinList.insert(SPECIAL_RESTORE_DEFAULTS);
		pinList.insert(SPECIAL_SELF_DESTRUCT); 
		
		commandList.insert(CHANGE_PROGRAMMING_COMMAND);
		commandList.insert(ADD_NORMALUSE_COMMAND);
		commandList.insert(ADD_TOGGLE_COMMAND);
		commandList.insert(ADD_FREEZE_COMMAND);
		commandList.insert(ADD_VIP_COMMAND);
		commandList.insert(DELETE_COMMAND);
		commandList.insert(TOGGLE_BEEPER_COMMAND);
		commandList.insert(CHANGE_RELOCK_TIME_COMMAND);
		
		commandList.insert(SPECIAL_REPLACE_BATT);
		commandList.insert(SPECIAL_RESTORE_DEFAULTS);
		commandList.insert(SPECIAL_SELF_DESTRUCT);
		
		relockTime = DEFAULT_RELOCK_TIME;
		isLocked = true; 
		isFrozen = false; 
		beeperActive = true; 
	}
	
	/**
	 * Sends the nextInput to the correct handler
	 * depending on whether the lock is in 
	 * programming mode or not. 
	 * @param nextInput
	 */
	public void getNextInput(String nextInput)
	{
		if ( programmingModeEnabled )
			handleNextInputPRO(nextInput);
		else
			handleNextInputNONPRO(nextInput);
	}
	
	/**
	 * Handler for input in non-programming mode. 
	 * Passes the input to the non-programming mode
	 * command preparer and then checks the command
	 * preparer's flags and takes action from there. 
	 * Prepares beep/blink details or executes a 
	 * command.
	 * @param nextInput
	 */
	private void handleNextInputNONPRO(String nextInput)
	{
		commandPreparer.getNextInput(nextInput);
		
		if ( commandPreparer.lastInputJustANumberEntry() )
		{
			commandPreparer.resetJustANumberFlag();
			setBeepDetails(NUMBER_OR_STAR_BUTTON_PRESSED);
			setBlinkDetails(NUMBER_OR_STAR_PRESSED_LED); 
		}
		
		if ( commandPreparer.special() )
		{
			commandPreparer.resetSpecialFlag();
			setBeepDetails(SPECIAL_ACTIVATED_BEEPER);
			setBlinkDetails(SPECIAL_ACTIVATED_LED);
		}
		
		if ( commandPreparer.commandIsReady() )
		{
			commandPreparer.resetCommandPreparer();			
			executeCommandNOPRO( commandPreparer.getCommand() ); 
		}
	}
	
	/**
	 * Handler for input in programming mode. 
	 * Passes the input to the programming mode
	 * command preparer and then checks the command
	 * preparer's flags and takes action from there.
	 * Prepares beep/blink details or executes a 
	 * command.  
	 * @param nextInput
	 */
	private void handleNextInputPRO(String nextInput)
	{
		proModeCP.getNextInput(nextInput);
		
		if ( proModeCP.lastInputJustANumberEntry() )
		{
			proModeCP.resetJustANumberFlag();
			setBeepDetails(NUMBER_OR_STAR_BUTTON_PRESSED);
			setBlinkDetails(NUMBER_OR_STAR_PRESSED_LED); 
		}
		
		if ( proModeCP.special() && !proModeCP.commandIsReady() )
		{
			//proModeCP.resetSpecialFlag();
			proModeCP.setPhaseTwoActive();
			
			setBeepDetails(SPECIAL_ACTIVATED_BEEPER);
			setBlinkDetails(SPECIAL_ACTIVATED_LED); 
		}
		
		if ( proModeCP.commandIsReady() && proModeCP.phaseOneIsActive() == true)
		{
			proModeCP.resetAll();			
			executeCommandPRO( proModeCP.getCommand() ); 
		}
		
		if ( proModeCP.commandIsReady() && proModeCP.phaseOneIsActive() == false)
		{
			PIN insertMe;
			
			proModeCP.resetAll();	
			PIN tmp = proModeCP.getCommand();
			setBeepDetails(SUCCESSFUL_PIN_ENTRY);
			setBlinkDetails(SUCCESSFUL_PIN_ENTRY_LED);
			proModeCP.setPhaseOneActive();
			programmingModeEnabled = false;
						

			// in case of special 
			if (proModeCP.special())
			{
				if (tmp.getPinNum() == SPECIAL_SELF_DESTRUCT.getPinNum() )
					commandPINType = PINType.SELF_DESTRUCT; 
				if (tmp.getPinNum() == SPECIAL_REPLACE_BATT.getPinNum() )
					commandPINType = PINType.REPLACE_BATTERY; 
				if (tmp.getPinNum() == SPECIAL_RESTORE_DEFAULTS.getPinNum() )
					commandPINType = PINType.RESTORE_FACTORY_DEFAULTS;  
				if (tmp.getPinNum() == SPECIAL_EXIT_PROGRAMMING_MODE.getPinNum() )
					commandPINType = PINType.EXIT_PROGRAMMING_MODE; 
				proModeCP.resetSpecialFlag(); 
			}
			
			System.out.println(commandPINType); 
			
			switch (commandPINType) 
			{
				case CHANGE_PROGRAMMING_CODE:
					pinList.remove(currentProgrammingPIN);
					insertMe = new PIN(tmp.getPinNum(), PINType.PROGRAMMING);
					pinList.insert( insertMe );
					currentProgrammingPIN = insertMe;
					break;
				
				case ADD_NORMAL_USE:
					insertMe = new PIN(tmp.getPinNum(), PINType.NORMAL_USE);
					pinList.insert( insertMe );
					break;
					
				case ADD_TOGGLE:
					insertMe = new PIN(tmp.getPinNum(), PINType.TOGGLE_LOCK);
					pinList.insert( insertMe );
					break;
					
				case ADD_FREEZE:
					insertMe = new PIN(tmp.getPinNum(), PINType.FREEZE);
					pinList.insert( insertMe );
					break;
					
				case ADD_VIP:
					insertMe = new PIN(tmp.getPinNum(), PINType.VIP);
					pinList.insert( insertMe );
					break;
					
				case DELETE:
					if ( !tmp.equals(currentProgrammingPIN) )
					{
						pinList.remove(tmp);
						//System.out.println("Baleeted");
					}
					break;
					
				case TOGGLE_BEEPER:
					toggleBeeperCase();
					System.out.println("beeper toggled"); 
					break;
					
				case CHANGE_RELOCK_TIME:
					relockTime = (tmp.getPinNum());    
					break;
					
				case EXIT_PROGRAMMING_MODE:
					break;
					
				case REPLACE_BATTERY:
					replaceBattery(); 
					break; 
				case RESTORE_FACTORY_DEFAULTS:
					restoreFactoryDefaults(); 
					break; 
				case SELF_DESTRUCT:
					System.out.println("here"); 
					selfDestruct(); 
					break;  					
			}			
		} 
	}
	
	/**
	 * How the lock toggles the beeper. 
	 * Uses a flag. 
	 */
	private void toggleBeeperCase()
	{
		if (beeperActive == true)
			beeperActive = false;
		else if (beeperActive == false)
			beeperActive = true;
	}
	
	/**
	 * Command executer for programming mode. 
	 * Checks for a valid command. 
	 * Prepares beep and blink information.
	 * Stores the type of command in memory because it will be recalled soon. 
	 * Sets the programming mode command preparer to 
	 * phase 2. 
	 * @param command a PIN. 
	 */
	private void executeCommandPRO(PIN command)
	{
		if ( !commandList.contains(command) )
		{
			setBeepDetails(BAD_PIN_ENTRY_PRO);
			setBlinkDetails(BAD_PIN_ENTRY_LED_PRO); 
			System.out.println("Invalid programming command!");
		}
		else
		{
			PIN comm = commandList.getPIN( command );
			setBeepDetails( comm.getBeepDetails() ); 
			setBlinkDetails( comm.getBlinkDetails() );			
			commandPINType = comm.getPinType();
			proModeCP.setPhaseTwoActive();
			System.out.println("on to phase 2"); 
		}
	}
	
	/**
	 * Command executer for non-programming mode. 
	 * Checks for a valid command. 
	 * Prepares beep and blink information.
	 * Performs the action indicated by the command. 
	 * @param command a PIN. 
	 */
	private void executeCommandNOPRO(PIN command)
	{
		// Check if command is valid
		if ( !pinList.contains(command) )
		{			 
			setBeepDetails(BAD_PIN_ENTRY);
			setBlinkDetails(BAD_PIN_ENTRY_LED); 
			System.out.println("Invalid PIN!");
		}
		else // pinList.contains(command) 
		{
			PIN pin = pinList.getPIN( command );
			setBeepDetails( pin.getBeepDetails() ); 
			setBlinkDetails( pin.getBlinkDetails() );			
			System.out.println("Valid PIN!"); 
			
			switch ( pin.getPinType() )
			{
				case NORMAL_USE:
					normalUseCase();
					break;
				case PROGRAMMING:
					programmingCase(); 
					break;
				case TOGGLE_LOCK:
					toggleLockCase();
					break;
				case FREEZE:
					freezeCase();
					break;
				case VIP:
					vipCase();
					break;
				case REPLACE_BATTERY:
					replaceBattery(); 
					break; 
				case RESTORE_FACTORY_DEFAULTS:
					restoreFactoryDefaults(); 
					break; 
				case SELF_DESTRUCT:
					selfDestruct(); 
					break; 
				case EXIT_PROGRAMMING_MODE:
					break;
			}
		}
	}
	
	/**
	 * How Self Destruct works 
	 * Prepares the necessary blinks and beeps. 
	 * Uses a timer to delay the self destruction until 
	 * after all the beeps and blinks have completed. 
	 */
	private void selfDestruct() 
	{
		long delay = (long) Beeper.BEEP_WAIT_TIME_MS;
		long period = (long) Beeper.BEEP_WAIT_TIME_MS; 
		
		final int numberOfBeeps = PIN.SELF_DESTRUCT_BEEPER.getNumberOfBeeps(); 
		setBeepDetails(PIN.SELF_DESTRUCT_BEEPER);
		setBlinkDetails(PIN.SELF_DESTRUCT_LED);
		
		Timer selfDestructCountdown = new Timer(); 
		selfDestructCountdown.scheduleAtFixedRate(new TimerTask()
		{
			int count = 1; 
			
			public void run()
			{
				if (count++ == numberOfBeeps)
				{
					this.cancel(); 
					System.exit(0);
				}
			}
		}, delay, period); 
		
		 
	}
	
	private void replaceBattery()
	{
		battery.replace(); 
	}
	
	/**
	 * What to do when a VIP PIN has been entered. 
	 * Just unlock the door momentarily. 
	 * Relock using a TimerTask. 
	 */
	private void vipCase()
	{
		long delay = relockTime;
		long period = 2000; // this number does not matter
		
		isLocked = false;
		System.out.println("unlocked!..." + isLocked);
		
		Timer relockTimer = new Timer();
		relockTimer.scheduleAtFixedRate(new TimerTask()
		{
			int count = 1; 
			int onlyFireOnce = 1; 
			
			public void run()
			{
				isLocked = true;
				System.out.println("locked! ");
								
				if (count++ == onlyFireOnce)
					this.cancel();
			}
		}, delay, period);
	}
	
	/**
	 * What to do when a FREEZE PIN has been entered. 
	 * Changes a flag to put the lock in and 
	 * out of freeze mode. 
	 */
	private void freezeCase()
	{
		if (isFrozen == true )
		{
			isFrozen = false;
			System.out.println("lock is now unfrozen."); 
		}
		else if (isFrozen == false )
		{
			isFrozen = true;
			System.out.println("lock is now frozen.");
		}
	}
	
	/**
	 * What to do when a TOGGLE PIN has been entered. 
	 * Changes a flag to unlock or lock 
	 * the lock. 
	 */
	private void toggleLockCase()
	{
		if (isFrozen == true)
		{
			System.out.println("had no effect. lock is frozen!"); 
			return;
		}
		
		if ( isLocked == true )
		{
			isLocked = false;
			System.out.println("unlocked!"); 
		}
		else if ( isLocked == false )
		{
			isLocked = true;
			System.out.println("locked!"); 
		}
		
		//System.out.println("isLocked: " + isLocked); 
	}
	
	/**
	 * What to do when the PROGRAMMING PIN has been entered. 
	 * Just set a flag!
	 */
	private void programmingCase()
	{
		programmingModeEnabled = true;
	}
	
	/**
	 * What to do when a NORMAL USE PIN has been entered. 
	 * Make sure the lock is not frozen. 
	 * Unlock the lock momentarily. 
	 */
	private void normalUseCase()
	{
		if (isFrozen == true)
		{
			System.out.println("had no effect. lock is frozen!"); 
			return; 
		}
			 
		
		long delay = relockTime;
		long period = 2000; // this number does not matter
		
		isLocked = false;
		System.out.println("unlocked!...");		
		
		Timer relockTimer = new Timer();
		relockTimer.scheduleAtFixedRate(new TimerTask()
		{
			int count = 1; 
			int onlyFireOnce = 1; 
			
			public void run()
			{
				isLocked = true;
				System.out.println("\nlocked! ");
								
				if (count++ == onlyFireOnce)
					this.cancel();
			}
		}, delay, period);
	}	
	
	private void setBeepDetails(Beeper beepDetails)
	{
		this.beepDetails = beepDetails; 
	}
	
	private void setBlinkDetails(LED blinkDetails)
	{
		this.blinkDetails = blinkDetails; 
	}
	
	public Beeper getBeepDetails()
	{
		return beepDetails; 
	}
	
	public LED getBlinkDetails()
	{
		return blinkDetails; 
	}
	
	public int getCurrentBatteryPowerPercent()
	{
		return battery.getCurrentPowerPercent(); 
	}
	
	public boolean beeperActive()
	{
		return beeperActive;
	}
}
