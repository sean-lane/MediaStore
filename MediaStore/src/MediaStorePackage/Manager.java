/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

/**
* This class represents a Manager within an online store. This class is a subclass
* of the User class. It has attributes userID and password. It has many other
* abilities that are accessible in the driver by way of the instanceof operator. 
*
* @author James Decker and Sean Lane
* @version 1.0 2/14/13
*
*/
public class Manager extends User {

	/**
	 * Constructs a Manager with the User ID and Password specified
	 * @param id represents this Manager's User ID
	 * @param pass represents this Manager's Password
	 */
	public Manager(String id, String pass){
		super(id,pass);
	}
	
	/**
	 * Checks to see if this Manager is equivilant to the Manager manager.
	 * If they are, true is returned. False is returned otherwise.
	 * @param manager represents another instance of the Manager Class.
	 * @return
	 */
	public boolean equals(Manager manager){
		return super.equals(manager);
	}
}