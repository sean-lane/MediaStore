/**
 * Name: James Decker and Sean Lane 
 * Section: 2 
 * Program: MediaStore 
 * Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.Customer;
import MediaStorePackage.Store;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is an extension of the JPanel class for creating a GUI to display
 * manager-only options. It displays 3 options for managers: adding new content, displaying
 * customer info, and outputting the total sales of the store. It implements ActionListener
 * to handle events derived from the buttons.
 *
 * @author James Decker and Sean Lane
 * @version 1.0 3/21/13
 *
 */
public class ManagerOptionsPanel extends JPanel implements ActionListener {
    private JButton addContentButton;           // JButtons for GUI navigation
    private JButton customerInformationButton;
    private JButton salesButton;
    private JLabel status;                      // JLabel for status/sales
    private BorderLayout layout;                // BorderLayout for panel organization
    private JPanel Panel1;                      // JPanel to store GUI elements
    private JPanel statusPanel;                 // JPanel to store label

    /**
     * Init constructor sets up the JPanel as needed with our buttons and eventListeners
     */
    public ManagerOptionsPanel() {
        layout = new BorderLayout(10, 10);      // set layout for top panel
        setLayout(layout);
        
        setSize(500, 200);          // set initial size of top panel
        
        Panel1 = new JPanel(new GridLayout(4, 1, 10, 10));      // initialize panels with respective layouts
        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        addContentButton = new JButton("Add Content");          // init JButtons
        customerInformationButton = new JButton("Customer Info");
        salesButton = new JButton("Current Sales");
        
        status = new JLabel("Choose an Option");        // init JLabel

        addContentButton.addActionListener(this);       // add components to actionListener
        customerInformationButton.addActionListener(this);
        salesButton.addActionListener(this);

        Panel1.add(addContentButton);                   // add components to form GUI
        Panel1.add(customerInformationButton);
        Panel1.add(salesButton);
        add(Panel1, BorderLayout.CENTER);
        
        statusPanel.add(status);
        add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Event-handling function allows buttons to call functions to the store
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salesButton) {         // if sales button is pressed
        	NumberFormat format = NumberFormat.getCurrencyInstance();
            status.setText("Current Sales: " + format.format(Store.getInstance().getTotalSales()));    // change JLabel to output sales
            status.revalidate();    // revalidate panel
        } 
        
        else if (e.getSource() == addContentButton) { // if add content button is pressed
            Store.getInstance().getFrame().addContent();    // use storeFrame function to add content directly to store
        } 
        
        else if (e.getSource() == customerInformationButton) {  // if customer info button is pressed
            String userID = JOptionPane.showInputDialog(this, "Please enter the UserID of the Customer you would like to observe:");    // prompt for particular customer
            Customer customer = Store.getInstance().getCustomer(userID); // search for that customer in store database
            if (customer != null) {         // if customer is found, display info
                Store.getInstance().getFrame().toCustomerInfo(customer);
            } 
            else {      // otherwise display search failure
                JOptionPane.showMessageDialog(Store.getInstance().getFrame(), "The specified UserID does not exist in the Userbase!");
            }
        }
    }
}
