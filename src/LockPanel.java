import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import java.util.Timer;
import java.util.TimerTask;


public class LockPanel extends JPanel
{
	final JButton one = new JButton("1"); 
	final JButton four = new JButton("4"); 
	final JButton seven = new JButton("7"); 
	final JButton star = new JButton("*"); 

	final JButton two = new JButton("2"); 
	final JButton five = new JButton("5"); 
	final JButton eight = new JButton("8"); 
	final JButton zero = new JButton("0"); 
	final JButton omerta = new JButton("Omerta"); 

	final JButton three = new JButton("3"); 
	final JButton six = new JButton("6"); 
	final JButton nine = new JButton("9"); 
	final JButton pound = new JButton("#"); 
	
	private EventListenerList listenerList = new EventListenerList(); 
	private LED green; 
	private LED red; 
	private int numberOfBlinks;
	private String blinkColor;	 
	
	/**
	 * Creates the panel with buttons and lights
	 * for the GUI. Also adds action listeners to 
	 * each button. 
	 */
	public LockPanel()
	{
		Dimension size = getPreferredSize();
		size.width = 270; 
		setPreferredSize(size); 
		setLayout(new GridBagLayout()); 
		
		// components (buttons and labels)		
		green = new LED( LED.GREEN_OFF );		
		red = new LED( LED.RED_OFF ); 	
		
		JLabel speakerholes = new JLabel(); 
		speakerholes.setIcon(new ImageIcon("res/speakerHoles.png")); 

		////////Position the components on the Panel ////////////////////		
		GridBagConstraints gc = new GridBagConstraints(); 
		
		/////// First column ////////////////////////
		gc.weightx = 0.5; 
		gc.weighty = 0.5; 
		
		gc.gridx = 0; 
		gc.gridy = 0; 
		gc.anchor = GridBagConstraints.LINE_END; 
		add(green, gc); 
		gc.anchor = GridBagConstraints.CENTER;
		
		
		gc.gridx = 0; 
		gc.gridy = 1; 
		gc.gridwidth = 3; 
		add(speakerholes, gc);
		gc.gridwidth = 1; 
		
		gc.gridx = 0; 
		gc.gridy = 2; 		 
		add(one, gc); 
		
		gc.gridx = 0; 
		gc.gridy = 3; 
		add(four, gc); 
		
		gc.gridx = 0; 
		gc.gridy = 4; 
		add(seven, gc); 
		
		gc.gridx = 0; 
		gc.gridy = 5; 
		add(star, gc); 
		
		gc.gridx = 0; 
		gc.gridy = 6;
		gc.gridwidth = 3;
		add(omerta, gc);
		gc.gridwidth = 1;
		
		////// Second column //////////////////////////		
		gc.gridx = 1; 
		gc.gridy = 2;
		gc.gridwidth = 1; 
		add(two, gc);
		
		gc.gridx = 1; 
		gc.gridy = 3; 
		add(five, gc);
		
		gc.gridx = 1; 
		gc.gridy = 4; 
		add(eight, gc);
		
		gc.gridx = 1; 
		gc.gridy = 5; 
		add(zero, gc); 
		
		
		////// Third Column ///////////////////////////
		gc.gridx = 2; 
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(red, gc); 
		gc.anchor = GridBagConstraints.CENTER;
		
		gc.gridx = 2; 
		gc.gridy = 2;		
		add(three, gc); 
		
		gc.gridx = 2; 
		gc.gridy = 3;		
		add(six, gc);
		
		gc.gridx = 2; 
		gc.gridy = 4;		
		add(nine, gc);
		
		gc.gridx = 2; 
		gc.gridy = 5;		
		add(pound, gc);
		
		////// Add action listeners to each button /////////////////////
		one.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = one.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		two.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = two.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		three.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = three.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		four.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = four.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		five.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = five.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		six.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = six.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		seven.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = seven.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		eight.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = eight.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		nine.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = nine.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		zero.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = zero.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		star.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = star.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		pound.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = pound.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
		
		omerta.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String id = omerta.getText(); 
				fireInputEvent(new InputEvent(this, id)); 
				//System.out.println(id); 
			}			
		});
	}
	
	/**
	 * Alerts the LockPanelListener that an event has occurred. 
	 * @param e
	 */
	public void fireInputEvent(InputEvent e)
	{
		Object[] listeners = listenerList.getListenerList(); 
		
		for (int i = 0; i < listeners.length; i += 2)
		{
			if(listeners[i] == InputListener.class)
			{
				( (InputListener)listeners[i+1] ).inputEventOccurred(e); 
			}
		}
	}
	
	public void addInputListener(InputListener listener)
	{
		listenerList.add(InputListener.class, listener); 
	}	
	
	/**
	 * Makes the red LED blink. 
	 * Uses two timers. 
	 * One timer to light up the LED. 
	 * One timer to turn off the LED. 
	 */
	private void blinkRed()
	{
		long no_delay = 0;
		long period = (long) LED.BLINK_WAIT_TIME;

		Timer blink = new Timer(); 
		blink.scheduleAtFixedRate(new TimerTask()
		{
			int blinkCount = 1; 

			public void run() 
			{
				red.setIcon(new ImageIcon(blinkColor)); 
				repaint(); 


				if (blinkCount++ == numberOfBlinks)
				{
					this.cancel();					
					//System.out.println("blinkTimer cancelled");
				}
			}
		}, no_delay, period);
		
		Timer blinkDurationTimer = new Timer(); 
		blinkDurationTimer.scheduleAtFixedRate(new TimerTask()
		{
			int blinkCount = 1; 

			public void run()
			{
				red.setIcon(new ImageIcon(LED.RED_OFF)); 
				repaint(); 

				if (blinkCount++ == numberOfBlinks)
				{
					//System.out.println("blinkDurationTimer cancelled"); 
					this.cancel();
				}
			}
		}, LED.BLINK_DURATION, LED.BLINK_WAIT_TIME);
	}
	
	/**
	 * Makes the green LED blink. 
	 * Uses two timers. 
	 * One timer to light up the LED. 
	 * One timer to turn off the LED. 
	 */
	private void blinkGreen()
	{
		long no_delay = 0; 
		long period = (long) LED.BLINK_WAIT_TIME;
		

		Timer blink = new Timer(); 
		blink.scheduleAtFixedRate(new TimerTask()
		{
			int blinkCount = 1; 

			public void run() 
			{
				green.setIcon(new ImageIcon(blinkColor)); 
				repaint(); 

				if (blinkCount++ == numberOfBlinks)
				{
					this.cancel();
					//System.out.println("blinkTimer cancelled");
				}
			}

		}, no_delay, period);
		
		Timer blinkDurationTimer = new Timer(); 
		blinkDurationTimer.scheduleAtFixedRate(new TimerTask()
		{
			int blinkCount = 1;			 

			public void run()
			{
				green.setIcon(new ImageIcon(LED.GREEN_OFF)); 
				repaint(); 
				
				if ( blinkCount++ == numberOfBlinks )
				{
					//System.out.println("blinkDurationTimer cancelled"); 
					this.cancel();
				}
			}
		}, LED.BLINK_DURATION, LED.BLINK_WAIT_TIME);
	}
	
	/**
	 * Determines which LED (green or red) should blink
	 * then calls the corresponding method. 
	 * @param blinkDetails What color? How many blinks? 
	 */
	public void blink(LED blinkDetails)
	{
		numberOfBlinks = blinkDetails.getNumberOfBlinks(); 
		blinkColor = blinkDetails.getBlinkColor(); 		
		
		if (blinkColor == LED.GREEN_ON)
			blinkGreen(); 
		else 
			blinkRed(); 
	}	
}
