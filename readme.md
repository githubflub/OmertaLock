Omerta Lock 
===

![Screenshot of Omerta Lock](http://i.imgur.com/8CPJiQ4.png)


How to test/use this lock: 
== 

1. Download OmertaLock.jar from [the releases page](https://github.com/githubflub/omertalock/releases), save it anywhere, and double click to run it. 
2. You can start unlocking doors immediately by using the default unlock PIN. On the keypad, using your mouse, enter 4321 followed by the # key. If you do this correctly, the green light on the lock will blink twice, and you will hear two beeps at the same time. This means the lock has unlocked. By default, in 5 seconds, the lock will relock, but this will happen in the background, so you won't see this unless you're looking at the Java console. 

3. This lock "runs" on a battery, which is draining constantly. When the battery gets below 25%, the lock will start playing a buzzing sound. When the battery hits 0%, the lock will die and the program will close. You can "replace" or "recharge" the battery by pressing the Omerta button. When you press the Omerta button, the green light will blink and beep 3 times to indicate that the lock is ready to receive special commands such as the one to "recharge" the battery. Enter 1 followed by the # key. After pressing the # key, the green light will blink and beep 3 times again. This will recharge the battery and return the lock to its default state, where it is ready to receive normal commands. For example, you can try entering the default PIN again. 

3. Once you have verified that the default PIN is working, you can start "programming" the lock for "real world" use. Enter the default programming PIN, 1234 followed by the # key. The green light will blink and beep 5 times to indicate that the lock has entered programming mode. In this step, we are going to add a new programming PIN (because we wouldn't want to use the default one). So, enter 1 followed by the # key. The green light will blink and beep 5 times again. Now you can enter your new programming PIN. Stick to a PIN that is 4 numbers long, for example, 9999, and submit it by entering the # key. The green light will blink and beep twice. You are required to confirm your new PIN, so enter 9999 followed by the # key again. This time, the green light will blink and beep 5 times, and the lock will return to its default state. Additionally, the old programming PIN will have been deleted, so you can try entering it again to put the lock in programming mode, but it should fail, and you should see/hear 1 red blink and 1 buzz. 

4. Now that you've changed the programming PIN, you can add a "normal use" PIN. Put the lock in programming mode by entering your new programming PIN. In this case, 9999 followed by #. Enter 2 followed by # to tell the lock that you would like to add a new "normal use" PIN. The green light will blink and beep 5 times to confirm. Enter your new PIN, for example, 5555 followed by #. 


How to set up: 
==

If you don't have the java development kit installed on your computer, download it from here. 
https://download.eclipse.org/oomph/jre/

I made this in Eclipse. If you don't have 

I made this in Eclipse, so just clone this into any Eclipse workspace of your choice. Then just import the project using the following steps. 

1. Open Eclipse
2. Right click on empty space in Package Explorer
3. Choose Import...
4. Expand General
5. Select Existing Projects into Workspace
6. Click Next > 
7. The Select root directory radio button should be selected. 
8. Click Browse...
9. Navigate to and select the workspace folder you are using.
10. Click OK
11. OmertaLock should appear in Projects: box. Select it. 
12. Click Finish. 
13. That's it!


How to run: 
==

1. Select OmertaLock in Package Explorer
2. Press CTRL+F11 or Run > Run or click the round green button with the white play symbol in it. 
3. Run as Java Application if it asks. 
4. That's it!
