/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;


import javazoom.jl.player.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//Import the Java classes
import java.io.*;

import javax.swing.JFrame;

/**
 * Class created to play mp3 files.
 * 
 * @author jtd5217
 */
public class MP3Player {
	private Player player;//an object that can be used to play mp3 files. Imported from javazoom.jl.player.*;
	private InputStream inputStream = null;//an Input stream that will be used to stream the mp3 to the player.


	/**
	 * Creates a new instance of MP3Player
	 */
	public MP3Player( String filename ) 
	{
		try
		{
			// Create an InputStream to the file
			inputStream = new FileInputStream( filename );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Starts and repeats the process of playing the mp3 file. This method loops until
	 * the process is complete.
	 */
	public void play()
	{
		try
		{
			player = new Player( inputStream );

			while(!player.isComplete())
			{
				player.play();
			}
			player.close();
			player = null;
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}