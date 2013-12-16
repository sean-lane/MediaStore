/**
 * Name: Sean Lane and James Decker Section: 2 Program: MediaStore Date: 2/14/13
 */
package MediaStorePackage;

import MediaStoreGui.*;

/**
 * Main driver to run the Media Store Project
 *
 * @author jtd5217
 *
 */
public class StoreDriver {

    public static Store store;//the one and only instance of Store to be created
    public static User currentUser = null;//the currentUser, default (i.e. when logged out) is null

    public static void main(String args[]) {
        store = Store.getInstance();
        store.setFrame(new StoreFrame("Media Store"));
        System.out.println("Welcome to the Media Store testing program! \n\nUse username: user \nand password: pass \n  to login as customer. \n \nUse username: admin \nand password: pass\n  to log in as manager.\n\nUse username: poor \nand password: pass \n  to login as a poor customer (without credits).");

    }

    /**
     * Attempts to authenticate the user ID id with the password pass. If it can
     * be authenticated, the user/manager (depending on which account info is
     * passed) will be logged in by being passed into the currentUser reference.
     *
     * @param id userID to be authenticated
     * @param pass password associated with the userID id
     */
    public static void login(String id, String pass) {
        currentUser = store.authenticate(id, pass);
        if (currentUser != null) {
            System.out.println("\nUser: " + currentUser.getID() + " has been successfully logged in.");
        }
    }
}
