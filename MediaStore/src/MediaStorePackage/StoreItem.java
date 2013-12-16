/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

/**
* This class is an modeling of an object within an online store. It has attributes
* price, title of media, duration of media, and genre of media. The store also tracks
* the average rating out of 5 stars for the item, as well as the overall ranking
* of the item within the store based on the number sold.
*
* @author James Decker and Sean Lane
* @version 1.0 2/14/13
*
*/
public abstract class StoreItem {
	protected double price;//represents the cost of this specific item
	protected String title;//the title of this item
	protected String duration; //Duration of item in form of "hours:minutes:second"
	protected String genre;//The specific category of artistic subdivisions
	protected int avgRating; //number of stars out of 5
	protected int numRatings; //the number of ratings that this item has received
        protected int ranking;  // The current calculated ranking of an item
        protected int numberSold;   // The total number of this item sold in the store
	protected String previewURL;
        
	// Constructs a default media item with no input parameters
        public StoreItem() {
            price = 0;
            title = null;
            duration = null;
            genre = null;
            avgRating = 3;
            numRatings = 0;
            ranking = 0;
        }

	
	// Constructs an item that has just been added to the store with the given parameters and a default rating of 3
	public StoreItem(double i_price, String i_title, String i_duration, String i_genre){
		price = i_price;
		title = i_title;
		duration = i_duration;
		genre = i_genre;
		avgRating = 3;
		numRatings = 0;
                ranking = 0;
                numberSold = 0;
	}
	
	
	/**
	 * Constructs an item that has been in the store for sometime, and therefore possesses a
	 * rating previously calculated (possibly for database usage)
         * 
         **/
	public StoreItem(double i_price, String i_title, String i_duration, String i_genre, int i_accessCode, int i_numRatings, int i_rating, int i_numberSold){
		price = i_price;
		title = i_title;
		duration = i_duration;
		genre = i_genre;
                numRatings = i_numRatings;
		avgRating = i_rating;
                numberSold = i_numberSold;
                ranking = 0;
	}

	/**
	 * Plays a clip (either audio or video) from the database
	 */
	abstract void preview();
	
	public void setPreview(String preview){
		previewURL = preview;
	}
	
        /**
         * Rate() processes a new user rating into the avgRating and numRating variables
         * to be able to average the total star rating value into a value from
         * one to five.
         */
	public String rate(int newRating){
		if(newRating>5 || newRating<0){
			return "Invalid Rating Input!";
		}
		if(numRatings==0){
			avgRating = newRating;
			numRatings++;
		}
		else{
			avgRating = (avgRating*(numRatings) + newRating)/(numRatings+1);
			numRatings++;
		}
		return "Rating successful";
	}
        
        @Override
        public int hashCode() {
            // POST: Method returns a unique access number for each store item by turning the contained strings
            //       into a hashcode
            return (title+duration+genre).hashCode();
        }
        
        public int getNumberSold() {
            // POST: returns numberSold
            return numberSold;
        }
        
        public void buy() {
            // POST: increments numberSold
            numberSold++;
        }
       
        public void setNumberSold(int newNumberSold) {
            // PRE: newNumberSold is an initialized positive integer
            // POST: sets numberSold to given value
            numberSold = newNumberSold;
        }
        
        public int getRanking() {
            // POST: returns ranking
            return ranking;
        }
        
        public void setRanking(int newRanking) {
            // PRE: newRanking is an initialized positive integer
            // POST: ranking is set to newRanking
            ranking = newRanking;
        }
        
    	public double getPrice() {
            // returns price of the item
            return price;
	}

	public void setPrice(double price) {
            // sets the price attribute of the item
            this.price = price;
	}
		
	public String getTitle(){
            // returns the title attribute of the item
            return title;
	}
        
        public String getDuration() {
            // returns duration of item
            return duration;
        }
        
        public String getGenre() {
            // returns genre of item
            return genre;
        }
        
        public int getAvgRating() {
            // returns average rating
            return avgRating;
        }
        
        public int getNumRatings() {
            // returns number of ratings
            return numRatings;
        }
        
        @Override
        public String toString() {    // toString override to send all the item's attributes to a string
            return ("\nTitle: "+title+"\n\nGenre: "+genre+"\nDuration: "+duration+"\nRating: "+avgRating+" Stars \n$"+price+"\nRanking: "+getRanking()); 
        }
}
