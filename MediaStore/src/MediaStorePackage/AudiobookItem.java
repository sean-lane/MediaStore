/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

/**
* This class is an extension of the StoreItem class for creating AudioBooks within an online
* store. it is defined with the StoreItem super constructor and an author attribute. It has 
* overridden preview and toString functions. It has a constructor that creates an Audiobook for
* use in the store with the given attributes.
*
* @author James Decker and Sean Lane
* @version 1.0 2/14/13
*
*/
public class AudiobookItem extends StoreItem {
    protected final String author;  // Artist of audio media
    protected static MP3Player player;//MP3Player used to play the preview
    
    /**
     * Constructs an AudiobookItem
     * @param i_price cost of the audiobook in credits (1 credit == $1 USD)
     * @param i_title the title of this book
     * @param i_duration the duration of the audiobook (Preferably in the format "##h##m##s")
     * @param i_genre The genre in which this audiobook is classified
     * @param i_author the author of the book on which this audiobook is based
     */
    public AudiobookItem(double i_price, String i_title, String i_duration, String i_genre, String i_author) {
        // Creates an AudiobookItem with the given attributes
        super(i_price,i_title,i_duration,i_genre);
        author = i_author;
    }
    
    /**
     * the preview method for the AudiobookItem class is implemented to play a short excerpt from the book.
     */
    @Override
    public void preview() {     // where code for a media preview would go
    	if(player==null){
    		player = new MP3Player(previewURL);
    	}
    	player.play();
    }
    
    /**
     * Creates a string providing all of the public data members of the AudiobookItem in a
     * convenient console-friendly format
     */
    @Override
    public String toString() {      // toString override to send all the item's attributes to a string
        return ("\nTitle: "+title+"\n Author: "+author+"\n Genre: "+genre+"\n Duration: "+duration+"\n Rating: "+avgRating+" Stars \n Ranking: "+ranking+" \n $"+price+"\n\n"); 
    }
    
    public String getAuthor() {
        // returns author value
        return author;
    }
}