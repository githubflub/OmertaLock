import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.util.Timer;
import java.util.TimerTask;
import java.net.URL; 

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
	
	static final int VOL_MIN = 0; 
	static final int VOL_MAX = 1000; 
	static final int VOL_INIT = 100; 
	
	final JLabel volume_slider_label = new JLabel("Volume", JLabel.LEFT); 
	final JSlider volume_slider = new JSlider(JSlider.HORIZONTAL, VOL_MIN, VOL_MAX, VOL_INIT); 
	public static int volume = VOL_INIT; 
	
	private EventListenerList listenerList = new EventListenerList(); 
	private LED green; 
	private LED red; 
	private int numberOfBlinks;
	private URL blinkColor;	 
	
	private TimerTask my_red_blink; 
	private TimerTask my_red_duration; 
	private TimerTask my_green_blink; 
	private TimerTask my_green_duration; 
	
	/**
	 * Creates the panel with buttons and lights
	 * for the GUI. Also adds action listeners to 
	 * each button. 
	 */
	public LockPanel()
	{
		volume_slider.addChangeListener(new ChangeListener() 
			{
				public void stateChanged(ChangeEvent e) 
				{
					JSlider source = (JSlider)e.getSource(); 
//					if (!source.getValueIsAdjusting()) {
						int new_volume = (int)source.getValue(); 
						volume = new_volume; 
//						System.out.print( "\nYour new volume " + volume + "\n");
//					}
				}
			}
		);
		
		Dimension size = getPreferredSize();
		size.width = 270; 
		setPreferredSize(size); 
		setLayout(new GridBagLayout()); 
		
		// components (buttons and labels)		
		green = new LED( LED.GREEN_OFF );		
		red = new LED( LED.RED_OFF ); 	
		
		JLabel speakerholes = new JLabel(); 
		URL speakerholes_url = LockPanel.class.getResource("speakerHoles.png"); 
		speakerholes.setIcon(new ImageIcon(speakerholes_url)); 

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
		
		gc.gridx = 0; 
		gc.gridy = 7;
		gc.gridwidth = 3;
		add(volume_slider_label, gc); 
		add(volume_slider, gc);
		gc.gridwidth = 1;
		
		gc.gridx = 0; 
		gc.gridy = 8;
		gc.gridwidth = 3;
		add(volume_slider, gc);
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
		
		/*
		 * Reminder for JavaScript brains:
		 * Java is a totally class based language, so instead of 
		 * providing an event handler function, you provide a class
		 * that implements an event handler function. 
		 * 
		 * To save some time, you can both define a class and 
		 * create an instance of it "on the fly" 
		 * using this syntax
		 * 
		 * new ActionListener() { 
		 *   // implement event handler function. 
		 *   public void actionPerformed() {
		 *   	// Do stuff. 
		 *   }
		 * }
		 * 
		 * This returns an instance of a class that implements ActionListener. 
		 * The stuff inside the {} is the class definition. 
		 * 
		 */
		
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
	
	public void stopBlinking() 
	{
		if (my_red_blink != null) {
			my_red_blink.cancel();
		}
		if (my_red_duration != null) {
			my_red_duration.cancel(); 
		}
		
		if (my_green_blink != null) {
			my_green_blink.cancel(); 
		}
		if (my_green_duration != null) {
			my_green_duration.cancel(); 
		}
		
		green.setIcon(new ImageIcon(LED.GREEN_OFF)); 
		red.setIcon(new ImageIcon(LED.RED_OFF)); 
		repaint(); 
			
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
		my_red_blink = new TimerTask()
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
		}; 
		
		blink.scheduleAtFixedRate(my_red_blink, no_delay, period);
		
		Timer blinkDurationTimer = new Timer(); 
		
		my_red_duration = new TimerTask()
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
		}; 
		
		blinkDurationTimer.scheduleAtFixedRate(my_red_duration, LED.BLINK_DURATION, LED.BLINK_WAIT_TIME);
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
		my_green_blink = new TimerTask()
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

		};
		
		blink.scheduleAtFixedRate(my_green_blink, no_delay, period);
		
		Timer blinkDurationTimer = new Timer(); 
		my_green_duration = new TimerTask()
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
		};
		blinkDurationTimer.scheduleAtFixedRate(my_green_duration, LED.BLINK_DURATION, LED.BLINK_WAIT_TIME);
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
	
	/**
	 * If you need to get the volume. 
	 */
	public static int getVolume() {
		return volume; 
	}
}
