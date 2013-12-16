/**
 * Name: James Decker and Sean Lane Section: 2 Program: MediaStore Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.Customer;
import MediaStorePackage.Store;
import MediaStorePackage.StoreItem;
import MediaStorePackage.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class is an extension of the JPanel for usage in creating a store GUI.
 * In particular, it is used to pull item arrays from the store and place them
 * within a JList as part of the GUI. It employs an array of StoreItems to move
 * store content, as well as various JPanels to organize the list and associated
 * title on-screen. It also uses an instance of the store to fetch the actual
 * content data. It uses ListSelectionListener to allow end-users to purchase
 * content from the store using the list itself.
 *
 * @author Sean Lane and James Decker
 * @version 1.0 3/21/13
 *
 */
public class ListContentPanel extends JPanel implements ListSelectionListener {

    protected JList<StoreItem> contentList;          // content list
    private JScrollPane scrollPane;//JScrollPane to allow scrolling through JList
    private JLabel content;             // content label
    private BorderLayout layout1;        // layout for elements
    private JPanel titlePanel;          // JPanels to organize list/title
    private JPanel contentPanel;
    private static Store store = Store.getInstance();   // current instance of store
    protected StoreItem[] array;        // array of content items
    protected boolean isEmpty = false;  // boolean for when list is empty
    private User user = store.getCurrentUser();    // fetch usertype from store

    /**
     * Init constructor for list content panel; takes in content type, which
     * allows us to use the same panel for many different types of content
     */
    public ListContentPanel(String contentType, StoreItem[] contentArray) {
        if (contentArray.length == 0) {   // check to see if contentArray is empty
            isEmpty = true;
        }

        array = contentArray;           // init item array

        layout1 = new BorderLayout();   // define layouts for GUI   
        setLayout(layout1);

        setSize(800, 500);              // set size of panel

        content = new JLabel(contentType);  // init title label

        contentList = new JList<StoreItem>();   // init JList, add to event handling
        contentList.setListData(contentArray);
        contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contentList.addListSelectionListener(this);

        titlePanel = new JPanel();          // set up GUI panels for organization
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        scrollPane = new JScrollPane(contentList);
        titlePanel.add(content);            // add GUI components together
        add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel);

    }

    /**
     * Override for ListSelectionListener events, tells driver what to do when
     * an element of the list is clicked
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {        // event handling
        if (user instanceof Customer) {           // if user is a customer
            StoreItem toBuy = contentList.getSelectedValue();   // pull hash value for current item
            Customer customer = (Customer) user;                // establish user as customer

            // prompt user if they are sure about purchase
            int returnVal = JOptionPane.showConfirmDialog(store.getFrame(),
                    "Are you sure you would like to buy\n", "Purchasing" + toBuy.toString(), JOptionPane.YES_NO_OPTION);
            if (returnVal == JOptionPane.YES_OPTION) {    // purchase if yes
                if (customer == null) {
                    JOptionPane.showMessageDialog(store.getFrame(), "Purchase Successful");
                }
            }
        }
    }

    /**
     * Resets the content panel to a blank state for usage in another list
     */
    protected void removeList() {
        remove(contentPanel);
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentPanel.remove(contentList);
        contentPanel.add(new JList<String>());
    }

    /**
     * Adds the designated content list back to the panel
     */
    protected void addList() {
        contentPanel.add(contentList);
        add(contentPanel);
    }

    /**
     * Adds a specific component to the content panel
     */
    public void addToPanel(Component comp) {
        contentPanel.add(comp);
    }
}
