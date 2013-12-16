/**
 * Name: James Decker and Sean Lane Section: 2 Program: MediaStore Date: 3/21/13
 */
package MediaStorePackage;

import MediaStoreGui.StoreFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This class represents A MediaStore. This class holds both StoreItems and
 * Customers. It has a single static member manager that holds the Manager
 * object for this Store. A single static member instance holds the only
 * instance of store. No others can be created as is limited by the
 * getInstance() method.
 *
 * @author James Decker and Sean Lane
 * @version 1.0 2/14/13
 *
 */
public class Store {

    public static final int LIST_MUSIC = 0,
            LIST_BOOKS = 1,
            LIST_MOVIES = 2,
            LIST_ALL = 3;
    private Hashtable<Integer, StoreItem> library = new Hashtable<Integer, StoreItem>();//Hashes each item in the library to an integer representing the hashcode 
    private Hashtable<Integer, Customer> userbase = new Hashtable<Integer, Customer>();//Hashes the each user in the userbase to the String representing their ID
    private static Manager manager;//Represents the manager of this store
    private static Store instance;//This single instance is used to send the store to any other classes that may need to access these methods
    private static User currentUser;
    private StoreFrame frame;
    private Connection databaseConnection;
    private Statement stmt;
    private ResultSet results;
    private String query;

    /**
     * This method is the only way to invoke the Constructor (who's
     * accessibility was set to private). This design was to ensure that no more
     * than one instance of this class is created at one time. This version
     * places a list of items and customers to the library/userbase of the
     * store. If The store has already been constructed, then a static copy of
     * the instance (labeled instance) will be returned to the caller of this
     * static method.
     *
     * @param items items that are to be sold in the store
     * @param users Customers to be added to the userbase
     * @return
     */
    public static Store getInstance(StoreItem[] items, Customer[] users) {
        if (instance == null) {
            instance = new Store(items, users);
        }
        return instance;
    }

    /**
     * This method is the only way to invoke the Constructor (who's
     * accessibility was set to private). This design was to ensure that no more
     * than one instance of this class is created at one time. If The store has
     * already been constructed, then a static copy of the instance (labeled
     * instance) will be returned to the caller of this static method.
     *
     * @return
     */
    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    /**
     * This private constructor creates an object with a list of storeItems to
     * be added to the library and a list of customers to be added to the
     * userbase.
     *
     * @param items array of store items to be added to store
     * @param users array of customers to be added to userbase
     */
    private Store(StoreItem[] items, Customer[] users) {
        try {
            // *****************************need SQL login before this is deleted *************************
            for (Customer user : users) {
                add(user);
            }
            frame = null;
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * private constructor to create an instance of the media store.
     */
    private Store() {
        frame = null;
        try {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds item StoreItem to the store's library
     *
     * @param item StoreItem to be added to the library
     */
    public void add(MovieItem item) {
        try {
            query = "insert into MASTER_MOVIE (TITLE, DIRECTOR, RELEASE_YEAR, GENRE, DURATION, PRICE, AVG_RATING, NUM_RATINGS, RANKING, NUMBER_SOLD) values ('"
                    + item.getTitle() + "','" + item.getDirector() + "','" + item.getReleaseYear() + "','" + item.getGenre() + "','"
                    + item.getDuration() + "'," + item.getPrice() + "," + item.getAvgRating() + "," + item.getNumRatings() + ","
                    + item.getRanking() + "," + item.getNumberSold() + ")";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add(MusicItem item) {
        try {
            query = "insert into MASTER_MUSIC (TITLE, ARTIST, GENRE, DURATION, PRICE, AVG_RATING, NUM_RATINGS, RANKING, NUMBER_SOLD) values ('"
                    + item.getTitle() + "','" + item.getArtist() + "','" + item.getGenre() + "','"
                    + item.getDuration() + "'," + item.getPrice() + "," + item.getAvgRating() + "," + item.getNumRatings() + ","
                    + item.getRanking() + "," + item.getNumberSold() + ")";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add(AudiobookItem item) {
        try {
            query = "insert into MASTER_AUDIOBOOK (TITLE, AUTHOR, GENRE, DURATION, PRICE, AVG_RATING, NUM_RATINGS, RANKING, NUMBER_SOLD) values ('"
                    + item.getTitle() + "','" + item.getAuthor() + "','" + item.getGenre() + "','"
                    + item.getDuration() + "'," + item.getPrice() + "," + item.getAvgRating() + "," + item.getNumRatings() + ","
                    + item.getRanking() + "," + item.getNumberSold() + ")";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * ReRanks the list of items by sorting the list in ascending order based on
     * each item's numberSold attribute. The item with the highest value for
     * numbersold recieves the lowest value for ranking but the highest rank, a
     * rank of 1. The lowest sold ite recieves a value of library.size()-1.
     */
    public void reRank() {
        int[] numberSold = new int[library.size()];
        StoreItem[] list = null;
        for (int i = 0; i < numberSold.length; i++) {
            numberSold[i] = list[i].getNumberSold();
        }
        Arrays.sort(numberSold);
        for (int i = 0; i < library.size(); i++) {
            for (int j = 0; j < numberSold.length; j++) {
                if (list[i].getNumberSold() == numberSold[i]) {
                    list[i].setRanking(numberSold.length + 1 - i);
                    j = i = library.size();
                }
            }
        }
    }

    /**
     * Adds Customer user to the the userbase.
     *
     * @param user the customer to be added
     */
    public void add(Customer user) {
        try {
            query = "insert into CUSTOMERS (USER_ID, PASSWORD, CREDITS, NAME, ADDRESS) values ('" + user.getID() + "','"
                    + user.getPassword() + "'," + user.getCredits() + ",'" + user.getName() + "','" + user.getAddress() + "')";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes item from the library of StoreItems.
     *
     * @param item
     */
    public void remove(StoreItem item) {
        try {
            if (item instanceof MovieItem) {
                query = "delete from MASTER_MOVIE where TITLE = '" + item.getTitle()+"'";
                stmt.executeUpdate(query);
            } else if (item instanceof MusicItem) {
                query = "delete from MASTER_MUSIC where TITLE = '" + item.getTitle()+"'";
                stmt.executeUpdate(query);
            } else if (item instanceof AudiobookItem) {
                query = "delete from MASTER_AUDIOBOOK where TITLE = '" + item.getTitle()+"'";
                stmt.executeUpdate(query);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        reRank();
    }

    /**
     * Removes Customer user from the user base.
     *
     * @param user Customer to be removed
     */
    public void remove(Customer user) {
        try {
            query = "delete from CUSTOMERS where USER_ID = " + user.getID();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates an array of StoreItems and fills it with the library's list of
     * items.
     *
     * @return
     */
    public ResultSet listItems(int listType) {
        try {
            StoreItem[] list = new StoreItem[library.size()];
            list = library.values().toArray(list);
            StoreItem[] newList = null;
            int count = 0;
            switch (listType) {
                case Store.LIST_BOOKS:
                    query = "select TITLE, AUTHOR, GENRE, DURATION, PRICE, AVG_RATING, RANKING from MASTER_AUDIOBOOK";
                    results = stmt.executeQuery(query);
                    break;
                case Store.LIST_MOVIES:
                    query = "select TITLE, DIRECTOR, RELEASE_YEAR, GENRE, DURATION, PRICE, AVG_RATING, RANKING from MASTER_MOVIE";
                    results = stmt.executeQuery(query);
                    break;
                case Store.LIST_MUSIC:
                    query = "select TITLE, ARTIST, GENRE, DURATION, PRICE, AVG_RATING, RANKING from MASTER_MUSIC";
                    results = stmt.executeQuery(query);
                    break;
                case Store.LIST_ALL:
                    query = "";
                    //newList = list;
                    results = stmt.executeQuery(query);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Store.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /**
     * Creates and returns an array consisting of all of the Customers in the
     * Userbase.
     *
     * @return
     */
    public ResultSet listCustomers() {
        query = "select USER_ID, PASSWORD, CREDITS, NAME, ADDRESS from CUSTOMERS";
        try {
            results = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /**
     * Calculates the total number of credits transferred through the Media
     * store and returns the value to the caller of this method.
     *
     * @return
     */
    public double getTotalSales() {
        double sales = 0;
        try {
            query = "select PRICE*NUMBER_SOLD from MASTER_AUDIOBOOK";
            results = stmt.executeQuery(query);
            while (results.next()) {
                sales = sales + results.getDouble(1);
            }

            query = "select PRICE*NUMBER_SOLD from MASTER_MUSIC";
            results = stmt.executeQuery(query);
            while (results.next()) {
                sales = sales + results.getDouble(1);
            }

            query = "select PRICE*NUMBER_SOLD from MASTER_MOVIE";
            results = stmt.executeQuery(query);
            while (results.next()) {
                sales = sales + results.getDouble(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Store.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return sales;
    }

    /**
     * Accesses the Manager of this store.
     *
     * @return
     */
    public static Manager getManager() {
        return manager;
    }

    /**
     * sets the static reference variable manager holding the one and only
     * Manager of the store.
     *
     * @param m
     */
    public void setManager(Manager m) {
        manager = m;
    }

    /**
     * Attempts to access the item in the library associated with the specified
     * hashcode. If the hashcode is not found to be in the hashtable, then the
     * hashcode is invalid and null is returned.
     *
     * @param key an integer representing the hashcode of an item.
     * @return
     */
    public ResultSet getItem(String title) {
                try {
            query ="(select TITLE, DIRECTOR as  AUTHOR_ARTIST_DIRECTOR, GENRE, DURATION, RELEASE_YEAR, PRICE, AVG_RATING from MASTER_MOVIE where '"+title+"' like TITLE ) UNION" +
"(select TITLE, AUTHOR as AUTHOR_ARTIST_DIRECTOR, GENRE, DURATION, '(audiobook)' as RELEASE_YEAR, PRICE, AVG_RATING from MASTER_AUDIOBOOK where '"+title+"' like TITLE) UNION" +
"(select TITLE, ARTIST as AUTHOR_ARTIST_DIRECTOR, GENRE, DURATION, '(music)' as  RELEASE_YEAR, PRICE, AVG_RATING from MASTER_MUSIC where '"+title+"' like TITLE)";

            results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Accesses the Customer in the Userbase Hashtable associated with the
     * hashcode provided in Integer key. If there is no user associated to this
     * hashcode, null is returned.
     *
     * @param key an Integer representing a hashcode.
     * @return
     */
    public Customer getCustomer(String userID) {
        try {
            query = "select * from CUSTOMERS where USER_ID = '"+userID+"'";
            results = stmt.executeQuery(query);
            Customer c = null;
            while(results.next()){
                c = new Customer(results.getString("USER_ID"), results.getString("PASSWORD"), results.getString("FULL_NAME"), results.getString("ADDRESS"));
                c.setCredits(results.getDouble("CREDITS"));
            }
            
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Creates a string holding all StoreItem objects purchased by the Customer
     * c in a neat, easily understandable format that can be printed.
     *
     * @param c Customer who's purchase history is to be accessed.
     * @return
     */
    public ResultSet getPurchaseHistory(Customer c) {
        try {
            query = "(select TITLE, DIRECTOR as  AUTHOR_ARTIST_DIRECTOR, GENRE, DURATION, RELEASE_YEAR, PRICE, AVG_RATING from MASTER_MOVIE where TITLE in (select TITLE from PURCHASE_HISTORY where USER_ID ='"+ c.getID() +"')) UNION" +
"(select TITLE, AUTHOR as AUTHOR_ARTIST_DIRECTOR, GENRE, DURATION, 'N/A' as RELEASE_YEAR, PRICE, AVG_RATING from MASTER_AUDIOBOOK where TITLE in (select TITLE from PURCHASE_HISTORY where USER_ID ='"+ c.getID() +"')) UNION" +
"(select TITLE, ARTIST as AUTHOR_ARTIST_DIRECTOR, GENRE, DURATION, 'N/A' as  RELEASE_YEAR, PRICE, AVG_RATING from MASTER_MUSIC  where TITLE in (select TITLE from PURCHASE_HISTORY where USER_ID ='"+ c.getID() +"'))";
            results = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /**
     * Attempts to authenticate the username password combination. Returns the
     * User account if it is either the Manager or is a customer located in the
     * Userbase. otherwise, null is returned.
     *
     * @param userID User ID associated with a User account (either a customer
     * of the Manager).
     * @param password Password to authenticate the User ID specified
     * @return
     */
    public User authenticate(String userID, String password) {
        try {
            //User user = getCustomer(userID.hashCode());

            query = "select USER_ID, PASSWORD, CREDITS, FULL_NAME, ADDRESS from CUSTOMERS where USER_ID = '" + userID + "' and PASSWORD ='" + password + "'";
            results = stmt.executeQuery(query);


            if (results.next() == false) {
                query = "select USER_ID, PASSWORD from MANAGERS where USER_ID = '" + userID + "' and PASSWORD ='" + password + "'";
                results = stmt.executeQuery(query);
                while (results.next()) {
                    Manager m = new Manager(results.getString("USER_ID"), results.getString("PASSWORD"));
                    setManager(m);
                    return m;
                }
            } else {
                Customer c = new Customer(results.getString("USER_ID"), results.getString("PASSWORD"), results.getString("FULL_NAME"), results.getString("ADDRESS"));
                c.setCredits(results.getDouble("CREDITS"));
                return c;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sendUser(User user) {
        currentUser = user;
        frame.toUserPanel(user);
    }

    public User getCurrentUser() {
        // TODO Auto-generated method stub
        return currentUser;
    }

    public void setFrame(StoreFrame frame) {
        this.frame = frame;
    }

    public StoreFrame getFrame() {
        return frame;

    }

    public void connectDatabase() {
        try {
            databaseConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/MediaStore;create=true", "root", "root");
            stmt = databaseConnection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, e);
        }
        query = null;
        results = null;
    }

    public void closeDatabase() {
        try {
            results.close();
            stmt.close();
            databaseConnection.close();

        } catch (SQLException ex) {
            Logger.getLogger(Store.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean buy(StoreItem item, Customer customer) {
        try {
            // check if customer already owns item
            query = "select USER_ID, TITLE from PURCHASE_HISTORY where USER_ID ='" + customer.getID() + "' and TITLE ='" + item.getTitle() + "'";
            results = stmt.executeQuery(query);

            if (results.next() == false) {  // if false, they don't own it
                if (item.getPrice() <= customer.getCredits()) { // check to see if they have enough credits
                    // if so, buy item
                    query = "insert into PURCHASE_HISTORY values ('" + customer.getID() + "','" + item.getTitle() + "')";
                    stmt.executeUpdate(query);
                    
                    // remove price from account
                    query = "update CUSTOMERS set CREDITS =" + (customer.getCredits() - item.getPrice()) + " where USER_ID = '" + customer.getID() + "'";
                    stmt.executeUpdate(query);
                    
                    // depending on type of item, increment item sold number
                    if (item instanceof AudiobookItem) {
                        query = "update MASTER_AUDIOBOOK set NUMBER_SOLD = NUMBER_SOLD + 1 where TITLE = '" + item.getTitle() + "'";
                        stmt.executeUpdate(query);
                    } else if (item instanceof MovieItem) {
                        query = "update MASTER_MOVIE set NUMBER_SOLD = NUMBER_SOLD + 1 where TITLE = '" + item.getTitle() + "'";
                        stmt.executeUpdate(query);
                    } else if (item instanceof MusicItem) {
                        query = "update MASTER_MUSIC set NUMBER_SOLD = NUMBER_SOLD + 1 where TITLE = '" + item.getTitle() + "'";
                        stmt.executeUpdate(query);
                    }
                    return true;
                } else {
                    JOptionPane.showMessageDialog(getFrame(), "You do not have enough credits.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(getFrame(), "Item is already owned.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
}
