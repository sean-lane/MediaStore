/**
 * Name: Sean Lane and James Decker Section: 2 Program: MediaStore Date: 2/14/13
 */
package MediaStorePackage;

/**
 * This class is an extension of the StoreItem class for creating Music Albums
 * within an online store. it is defined with the StoreItem super constructor
 * and an artist attribute. It has overridden preview and toString functions. It
 * has a constructor that creates an Album for use in the store with the given
 * attributes.
 *
 * @author James Decker and Sean Lane
 * @version 1.0 2/14/13
 *
 */
public class MusicItem extends StoreItem {

    protected final String artist;  // Artist of audio media
    protected static MP3Player player;

    /**
     * Primary constructor that creates a new MusicItem with the set parameters;
     * uses super constructor to set all fields but artist
     */
    public MusicItem(double i_price, String i_title, String i_duration, String i_genre, String i_artist) {
        super(i_price, i_title, i_duration, i_genre);
        artist = i_artist;
    }

    /**
     * the preview method for the AudiobookItem class is implemented to play a
     * short excerpt from the book.
     */
    @Override
    public void preview() {     // where code for a media preview would go
        if (player == null) {
            player = new MP3Player(previewURL);
        }
        player.play();
    }

    @Override
    public String toString() {   // toString override to send all the item's attributes to a string
        return ("\nTitle: " + title + "\n Artist: " + artist + "\n Genre: " + genre + "\n Duration: " + duration + "\n Rating: " + avgRating + " Stars \n Ranking: " + ranking + " \n $" + price + "\n\n");
    }
    
    public String getArtist() {
        // returns artist value
        return artist;
    }
}