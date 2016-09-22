/**
 * Just a class that starts the program. 
 * Makes the MVC design pattern of this program
 * really clear. 
 */
public class StartUp 
{
	public static void main(String[] args)
	{
		// Start the model
		Lock theModel = new Lock();		
		
		// Start the view
		LockView theView = new LockView(); 
		
		// Start the controller
		LockController theController = new LockController(theModel, theView); 		
	}	 
}
