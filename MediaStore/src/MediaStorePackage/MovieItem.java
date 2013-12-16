/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

import java.io.IOException;

/**
 * This class is an extension of the StoreItem class for creating Movies within an online
 * store. it is defined with the StoreItem super constructor and director and release year attributes.
 * It has overridden preview and toString functions. It has a constructor that creates an Movie for
 * use in the store with the given attributes.
 *
 * @author James Decker and Sean Lane
 * @version 1.0 2/14/13
 *
 */
public class MovieItem extends StoreItem {
	protected final String director;  // Artist of audio medi
	protected final String releaseYear; // Year of release of the movie

	/**
	 * Primary constructor that creates a new MusicItem with the set parameters; uses
	 * super constructor to set all fields but director and releaseYear
	 */
	public MovieItem(double i_price, String i_title, String i_duration, String i_genre, String i_director, String i_releaseYear) {
		super(i_price,i_title,i_duration,i_genre);
		director = i_director;
		releaseYear = i_releaseYear;
	}


	@Override
	public void preview() {     // placeholder for where code to preview media would go
		try
		{ 
			Process p=Runtime.getRuntime().exec("cmd /c start "+previewURL); 
		} 
		catch(IOException e1) {System.out.println(e1);} 
	}


	@Override
	public String toString() {  // toString override to send all the item's attributes to a string
		return ("\nTitle: "+title+"\n Director: "+director+"\n Genre: "+genre+"\n Release Year: "+releaseYear+"\n Duration: "+duration+"\n Rating: "+avgRating+" Stars \n Ranking: "+ranking+" \n $"+price+"\n\n"); 
	}

        public String getDirector() {
            // returns director value
            return director;
        }
        
        public String getReleaseYear() {
            // returns releaseYear value
            return releaseYear;
        }
}