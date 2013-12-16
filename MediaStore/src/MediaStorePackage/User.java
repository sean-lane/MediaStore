/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

import java.util.ArrayList;

/**
* This class represents a user within an online store. It has attributes
* userID and password. It has the ability to attempt login and will return true if
* 		the correct password is given.
*
* @author James Decker and Sean Lane
* @version 1.0 2/14/13
*
*/
public class User {
	private String userID;//User ID for this user account
	private String password;//Password for this user account

	/**
	 * Default Constructor: creates a User account with userID and
	 * Password equal to an empty string.
	 */
	public User(){
		userID = password = "";
	}
	
	/**
	 * Constructs a User account with specified user ID and password.
	 * @param i_userID represents the user ID for this user account
	 * @param i_password represents the password for this user account
	 */
	public User(String i_userID, String i_password){
		userID = i_userID;
		password = i_password;
	}
	
	/**
	 * Accesses this User's UserID
	 * @return
	 */
	public String getID(){
		return userID;
	}
	
        /**
         * accesses user's password (FOR SQL)
         * @return 
         */
        public String getPassword() {
            return password;
        }
        
	/**
	 * Creates a hash code for the user
	 */
	public int hashCode(){
		return userID.hashCode();
	}
	
	/**
	 * Changes the password to newPass, as long as the oldPass is equivilant to the
	 * current password. Returns true if successful, false otherwise.
	 * @param oldPass current password.
	 * @param newPass new password to be set
	 * @return
	 */
	public boolean changePassword(String oldPass,String newPass){
		if(login(oldPass)){
			password = newPass;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks to see if the password is equal to the password provided. If it is,
	 * true is returned. False is returned otherwise.
	 * @param pass
	 * @return
	 */
	public boolean login(String pass){
		if (password.equals(pass)){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Checks equality of this User to User other. If they are equivilant, true is returned.
	 * False is returned otherwise.
	 * @param other
	 * @return
	 */
	public boolean equals(User other){
		return this.getID() == other.getID();
	}
	
	
}
