/**
 * Name: James Decker and Sean Lane 
 * Section: 2 
 * Program: MediaStore 
 * Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.Store;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is an extension of the JPanel class for displaying user store
 * actions via a GUI. It allows the user to list our three types of store items
 * by individual list, and also allows for searching by title. It also doubles
 * as an entry point to the GUI regarding additional user options, depending on
 * whether or not the user is a manager or customer.
 *
 * @author James Decker and Sean Lane
 * @version 1.0 3/21/13
 *
 */
public class UserPanel extends JPanel implements ActionListener {

    private JButton accountInfo;    //button that brings the customer to their account info
    private JButton adminMenu;      //Button that brings the manager to the admin options window
    private JButton listMusic;      // Buttons for listing media
    private JButton listBooks;
    private JButton listMovies;
    private JLabel search;          // JLabel for search box
    private JLabel userID;          // JLabel to display current user
    private JTextField searchText;  // JTextField for search text
    private GridBagLayout layout;   // layout components for GridBagLayout
    private GridBagConstraints constraints;

    /**
     * init constructor takes in a bool for whether or not the user is a manager;
     * if yes, a different set of options is displayed and the lists become able
     * to delete items from the store rather than purchase them.
     */
    public UserPanel(boolean isManager) {
        layout = new GridBagLayout();             // set up layout
        setLayout(layout);
        constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH; // set up GridBagLayout
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        accountInfo = new JButton("Account Info");  // init JButtons
        adminMenu = new JButton("Admin Options");
        listMusic = new JButton("List Music");
        listBooks = new JButton("List AudioBooks");
        listMovies = new JButton("List Movies");

        search = new JLabel("Search");  // init JLabel and corresponding text field
        searchText = new JTextField("Enter Search Terms Here", 20);

        constraints.gridx = 0;                   // define constraints for button
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        layout.setConstraints(listMusic, constraints);
        add(listMusic);             // add element to GUI with GridBagLayout

        // repeat process for all elements
        constraints.gridx = 1;                   // define constraints for button
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        layout.setConstraints(listBooks, constraints);
        add(listBooks);

        constraints.gridx = 2;                   // define constraints for button
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        layout.setConstraints(listMovies, constraints);
        add(listMovies);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;                   // define constraints for element
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        layout.setConstraints(search, constraints);
        add(search);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 0, 10, 20);
        constraints.gridx = 1;                   // define constraints for element
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        layout.setConstraints(searchText, constraints);
        add(searchText);



        if (isManager) {    // if user is manager, set up admin options panel
            userID = new JLabel("Manager ID: " + Store.getInstance().getCurrentUser().getID());
            constraints.gridx = 2;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            layout.setConstraints(userID, constraints);
            add(userID);
            
            constraints.gridx = 0;                   // define constraints for button
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            layout.setConstraints(adminMenu, constraints);

            add(adminMenu);                 // add menu and add to actionListener
            adminMenu.addActionListener(this);
        } 
        
        else {  // if user is a customer, set up customer info panel
            userID = new JLabel("Customer ID: " + Store.getInstance().getCurrentUser().getID());
            constraints.gridx = 2;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            layout.setConstraints(userID, constraints);
            add(userID);
            
            constraints.gridx = 0;                   // define constraints for button
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            layout.setConstraints(accountInfo, constraints);
            add(accountInfo);
        }

        accountInfo.addActionListener(this);        // add the rest of the buttons to ActionListener
        adminMenu.addActionListener(this);
        listMusic.addActionListener(this);
        listBooks.addActionListener(this);
        listMovies.addActionListener(this);
        searchText.addActionListener(this);

        setSize(500, 200);      // set initial size of panel
    }

    /**
     * Event handling function allows buttons to function
     */
    @Override
    public void actionPerformed(ActionEvent e) {    // for each button, call a storeFrame command to bring up a new panel
        if (e.getActionCommand().equals(adminMenu.getActionCommand())) {
            Store.getInstance().getFrame().toAdminOptions();    // bring up admin options
        } else if (e.getActionCommand().equals(accountInfo.getActionCommand())) {
            Store.getInstance().getFrame().toUserAccount();     // bring up user options
        } else if (e.getActionCommand().equals(listMusic.getActionCommand())) {
            Store.getInstance().getFrame().toListPanel(Store.LIST_MUSIC);   // bring up various media lists
        } else if (e.getActionCommand().equals(listBooks.getActionCommand())) {
            Store.getInstance().getFrame().toListPanel(Store.LIST_BOOKS);
        } else if (e.getActionCommand().equals(listMovies.getActionCommand())) {
            Store.getInstance().getFrame().toListPanel(Store.LIST_MOVIES);
        } else if (e.getSource() == searchText && searchText.getText() != null) {
            Store.getInstance().getFrame().toSearch(searchText.getText());  // bring up list with searched content
        }
    }
}
