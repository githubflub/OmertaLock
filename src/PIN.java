
public class PIN
{
	public static final int PIN_MAX_LENGTH = 6; 
	public static final String PROGRAMMING_PIN = "programming pin";
	public static final String NORMALUSE_PIN = "normal use pin"; 
	
	private static final Beeper NON_PRO_MODE_GOOD_ENTRY_BEEPER = new Beeper(Beeper.GOOD_BEEP, Beeper.TWO_BEEPS);
	private static final Beeper PRO_MODE_GOOD_ENTRY_BEEPER = new Beeper(Beeper.GOOD_BEEP, Beeper.FIVE_BEEPS);
	private static final Beeper SPECIAL_BEEPER = new Beeper(Beeper.GOOD_BEEP, Beeper.THREE_BEEPS); 
	public static final Beeper SELF_DESTRUCT_BEEPER = new Beeper (Beeper.GOOD_BEEP, Beeper.FIVE_BEEPS); 
	
	private static final LED NON_PRO_MODE_GOOD_ENTRY_LED = new LED(LED.GREEN_ON, LED.TWO_BLINKS);
	private static final LED PRO_MODE_GOOD_ENTRY_LED = new LED(LED.GREEN_ON, LED.FIVE_BLINKS);
	private static final LED SPECIAL_LED = new LED(LED.GREEN_ON, LED.THREE_BLINKS); 
	public static final LED SELF_DESTRUCT_LED = new LED(LED.RED_ON, LED.FIVE_BLINKS); 
	
	
	private static int pinLength;
	
	
	// instance members. 
	private int pinNum;	
	private PINType pinType;
	private PINType pinTypeToAdd;
	
	private LED blinkDetails; 
	private Beeper beepDetails;
	private boolean enableProgrammingMode; 
	
	// Constructor 1
	public PIN(int newPIN)
	{
		pinNum = newPIN;  
	}
	
	/**
	 * Main constructor. 
	 * Creates a PIN and gives it semi-unique beep
	 * and blink details. 
	 * @param pinNum
	 * @param pinType
	 */
	public PIN(int pinNum, PINType pinType)
	{
		this.pinNum = pinNum;
		this.pinType = pinType;
		
		switch (this.pinType)
		{
			case NORMAL_USE:
				enableProgrammingMode = false; 
				beepDetails = NON_PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = NON_PRO_MODE_GOOD_ENTRY_LED;
				break; 
				
			case PROGRAMMING:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED;
				enableProgrammingMode = true;
				break;
				
			case TOGGLE_LOCK:
				beepDetails = NON_PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = NON_PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case FREEZE:
				beepDetails = NON_PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = NON_PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case VIP:
				beepDetails = NON_PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = NON_PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case CHANGE_PROGRAMMING_CODE:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case ADD_NORMAL_USE:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED;
				pinTypeToAdd = PINType.NORMAL_USE;
				enableProgrammingMode = false;
				break;
				
			case ADD_TOGGLE:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED;
				enableProgrammingMode = false;
				break;
				
			case ADD_FREEZE:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case ADD_VIP:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case DELETE:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case TOGGLE_BEEPER:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case CHANGE_RELOCK_TIME:
				beepDetails = PRO_MODE_GOOD_ENTRY_BEEPER;
				blinkDetails = PRO_MODE_GOOD_ENTRY_LED; 
				enableProgrammingMode = false;
				break;
				
			case REPLACE_BATTERY:
				beepDetails = SPECIAL_BEEPER; 
				blinkDetails = SPECIAL_LED; 
				break; 
				
			case RESTORE_FACTORY_DEFAULTS:
				beepDetails = SPECIAL_BEEPER; 
				blinkDetails = SPECIAL_LED; 
				break; 
				
			case SELF_DESTRUCT:
				beepDetails = SELF_DESTRUCT_BEEPER; 
				blinkDetails = SELF_DESTRUCT_LED; 
				break; 
		}
		
	}
	
	public Beeper getBeepDetails()
	{
		return beepDetails;
	}
	
	public LED getBlinkDetails()
	{
		return blinkDetails;
	}
	
	/**
	 * Get the PIN type.
	 */
	public PINType getPinType()
	{
		return pinType;
	}
	
	public int getPinNum()
	{
		return pinNum; 
	}
	
	/**
	 * Sometimes the caller will want to know 
	 * if a PIN enables programming mode or not. 
	 * @return yes or no. 
	 */
	public boolean enableProgrammingMode()
	{
		return enableProgrammingMode; 
	}
	
	/**
	 * Check if the PIN is equal.
	 */
	public boolean equals(PIN pin)
	{
		if (this.pinNum - pin.getPinNum() == 0)
			return true; 
		else 
			return false; 
	}
	
}
