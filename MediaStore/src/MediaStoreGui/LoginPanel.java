/**
 * Name: James Decker and Sean Lane 
 * Section: 2 
 * Program: MediaStore 
 * Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.Store;
import MediaStorePackage.User;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class is an extension of the JPanel class for creating a GUI to log into
 * the store. It uses an instance of the store to send a user-input userID and
 * password for authentication within our store class. It uses two labels and
 * two text fields to cleanly convey this to the end user. It uses GridBagLayout
 * to properly construct these fields.
 *
 * @author James Decker and Sean Lane
 * @version 1.0 3/21/13
 *
 */
public class LoginPanel extends JPanel implements ActionListener {

    private static User user;   // intermediate user variable for authentication
    private static Store store;//the one and only instance of Store to be created
    private JLabel userid;      // JLabels for text fields
    private JLabel pass;
    private JTextField inputUser;       // text field for UserId input
    private JPasswordField inputPass;   // password field for private password entry
    private GridBagLayout layout;       // GridbagLayout to display GUI
    private GridBagConstraints constraints;

    /**
     * Init constructor takes in the current instance of the store to log the user
     * into our driver.
     */
    public LoginPanel(Store store) {
        layout = new GridBagLayout();             // set up layout
        setLayout(layout);
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        this.store = store;                 // set up store

        userid = new JLabel("Username");    // init JLabels
        pass = new JLabel("Password");

        inputUser = new JTextField(20);     // init text fields
        inputUser.addActionListener(this);

        inputPass = new JPasswordField(20);
        inputPass.addActionListener(this);

        user = null;        // initial user is null; no login to start

        // Set GridBagLayout constraints for all elements of GUI
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        constraints.gridx = 0;                   // define constraints for element
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        layout.setConstraints(userid, constraints);
        add(userid);    // add label to GUI

        // repeat process for other elements
        constraints.gridx = 1;                   // define constraints for element
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        layout.setConstraints(inputUser, constraints);
        add(inputUser);

        constraints.gridx = 0;                   // define constraints for element
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        layout.setConstraints(pass, constraints);
        add(pass);

        constraints.gridx = 1;                   // define constraints for element
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        layout.setConstraints(inputPass, constraints);
        add(inputPass);

        setSize(360, 100);      // set initial panel size
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // event handling for text fields
        if ((e.getSource() == inputUser) || (e.getSource() == inputPass)) { // if user hits enter in either field
            user = store.authenticate(inputUser.getText(), new String(inputPass.getPassword()));    // attempt authentication
            
            if (user == null) {         // if login fails
                inputPass.setText("");  // reset password field
            } else {
                store.sendUser(user);   // otherwise, login
            }
        }
    }
}
