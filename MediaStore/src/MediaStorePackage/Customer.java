/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
* This class is an extension of the User class for creating Customers within an online
* store. The methods here are used to access and manipulate this customer's account data.
*
* @author James Decker and Sean Lane
* @version 1.0 2/14/13
*
*/
public class Customer extends User {

    protected String name;//Name of the Customer
    protected String address;//Address of the Customer
    protected ArrayList<Integer> purchaseHistory;//holds accessCodes from each item purchased
    private double credits;//holds the amount of purchase credits that this user possesses

    /**
     * Constructs a Customer object with the specified data supplied.
     * @param id represents the userID
     * @param pass Represents the customer Password
     * @param nameStr represents the customer's name
     * @param addressStr represents the customer's address
     * @param history specifies previous purchases this customer has made (null if no purchases have been made)
     * @param cred specifies previous credits this user has (1 credit == $1 USD)
     */
    public Customer(String id, String pass, String nameStr, String addressStr, ArrayList<Integer> history, double cred) {
        super(id, pass);
        name = nameStr;
        address = addressStr;
        if(history!=null){
        	purchaseHistory = history;
        }
        else{
        	purchaseHistory = new ArrayList<Integer>();
        }
        credits = cred;

    }

    /**
     * Constructs a Customer object with the specified data supplied.
     * @param id represents the userID
     * @param pass Represents the customer Password
     * @param nameStr represents the customer's name
     * @param addressStr represents the customer's address
     */
    public Customer(String id, String pass, String nameStr, String addressStr) {
        super(id, pass);
        name = nameStr;
        address = addressStr;
        purchaseHistory = new ArrayList<Integer>();
        credits = 0;

    }

    /**
     * Accesses the name of this Customer
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of this Customer
     * @param name the new name for this customer's account
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accesses this Customer's address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets this Customer's address to a new String
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Accesses this customer's purchase history (an ArrayList holding the hashcodes of
     * all items purchased
     * @return
     */
    public ArrayList<Integer> getPurchaseHistory() {
        return purchaseHistory;
    }

    /**
     * Accesses the number of credits on this customer's account
     * @return
     */
    public double getCredits() {
        return credits;
    }

    /**
     * Sets the number of credits on this customer's account.
     * @param credits
     */
    public void setCredits(double credits) {
        this.credits = credits;
    }

    /**
     * Produces a String containing all data needed to accurately express this Customer's
     * account for the manager to observe. 
     */
    @Override
    public String toString() {
        return ("\n\nUserID: "+ getID()+"\nName: "+name+"\nAddress: "+address+"\nAccount Balance: $"+credits+"\n\n");
    }
}
