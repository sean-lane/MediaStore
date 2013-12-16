/**
 * Name: James Decker and Sean Lane Section: 2 Program: MediaStore Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * StoreFrame holds everything together. As each new window is navigated to, a
 * new JPanel is instantiated and swapped for whatever was in its place. This
 * mostly done from within the StoreFrame Class.
 *
 * @author jtd5217
 *
 */
public class StoreFrame extends JFrame implements ActionListener {

    public static final int LIST_MUSIC = 0,
            LIST_BOOKS = 1,
            LIST_MOVIES = 2;
    private static Store store;
    private static User currentUser;
    private LoginPanel loginPanel;
    private UserPanel userPanel = null;
    private TableContentPanel userInfo;
    private ManagerOptionsPanel adminOptions;
    private TableContentPanel listMedia;
    private TableContentPanel searchResults;
    private Manager manager;
    private Customer customer;
    private Customer poorCustomer;
    private JButton back;
    private JButton logout;
    private JLabel noItemsFound;
    private String userID;

    /**
     * Instantiates the StoreFrame and creates a few Example Media Items, then
     * adds them to the library.
     *
     * @param title
     */
    public StoreFrame(String title) {
        super(title);
        noItemsFound = new JLabel("No Items were found!");
        back = new JButton("Back");
        logout = new JButton("Logout");
        store = Store.getInstance();
        store.connectDatabase();
        currentUser = null;
        loginPanel = new LoginPanel(Store.getInstance());
        adminOptions = new ManagerOptionsPanel();
        setLayout(new BorderLayout(10, 10));
        back.setVisible(false);
        logout.setVisible(false);
        logout.addActionListener(this);
        back.addActionListener(this);

        add(loginPanel);
        add(logout, BorderLayout.SOUTH);
        add(back, BorderLayout.NORTH);
        setSize(loginPanel.getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Clears all elements from the StoreFrame to make room for the next JPanel
     * or ScrollPane.
     */
    private void clear() {
        if (userPanel != null) {
            remove(userPanel);
        }
        remove(loginPanel);
        remove(adminOptions);
        if (userInfo != null) {
            remove(userInfo);
        }
        if (listMedia != null) {
            remove(listMedia);
        }
        if (noItemsFound != null) {
            remove(noItemsFound);
        }
        if (searchResults != null) {
            remove(searchResults);
        }
    }

    /**
     * Instantiates and adds a user panel to the frame. Also sets currentUser to
     * current
     *
     * @param current
     */
    public void toUserPanel(User current) {
        clear();
        userID = current.getID();
        back.setVisible(false);
        logout.setVisible(true);
        currentUser = current;
        userPanel = new UserPanel(currentUser instanceof Manager);
        add(userPanel, BorderLayout.CENTER);
        setSize(userPanel.getWidth(), userPanel.getHeight() + 100);
        if (!(currentUser instanceof Manager)) {
            store.getFrame().setTitle("User ID:: " + userID);
        }
        validate();
        setVisible(true);
    }

    /**
     * Logs out of the currentUser and returns to the loginPanel
     */
    public void logout() {
        clear();
        userID = "";
        loginPanel = new LoginPanel(Store.getInstance());
        add(loginPanel, BorderLayout.CENTER);
        setSize(loginPanel.getSize());
        validate();
        back.setVisible(false);
        logout.setVisible(false);
    }

    /**
     * Returns to the Userpanel. Called from the back button.
     */
    public void toUserPanel() {
        clear();
        back.setVisible(false);
        logout.setVisible(true);
        userPanel = new UserPanel(currentUser instanceof Manager);
        add(userPanel);
        setSize(userPanel.getSize().width, userPanel.getSize().height + 100);
        validate();
        setVisible(true);
    }

    /**
     * Sets up and shows the admin options panel
     */
    public void toAdminOptions() {
        clear();
        back.setVisible(true);
        add(adminOptions);
        setSize(adminOptions.getWidth(), adminOptions.getHeight() + 100);
        validate();
        setVisible(true);
    }

    /**
     * Opens the User Account window
     */
    public void toUserAccount() {
        clear();
        userInfo = new TableContentPanel("Customer Info", store.getPurchaseHistory((Customer) (currentUser)));
        back.setVisible(true);
        userInfo.addToPanel(new JLabel("Address: " + ((Customer) currentUser).getAddress()));
        userInfo.addToPanel(new JLabel("Credits: $" + ((Customer) currentUser).getCredits()));
        userInfo.addToPanel(new JLabel("Name: " + ((Customer) currentUser).getName()));
        add(userInfo, BorderLayout.CENTER);
        setSize(userInfo.getWidth(), userInfo.getHeight() + 100);
        validate();
        setVisible(true);
    }

    /**
     * Opens customer information window for Customer c.
     *
     * @param c
     */
    public void toCustomerInfo(Customer c) {
        clear();
        userID = c.getID();
        store.getFrame().setTitle("User ID:: " + userID);
        userInfo = new TableContentPanel("Customer Info", store.getPurchaseHistory(c));
        back.setVisible(true);
        userInfo.addToPanel(new JLabel("Address: " + c.getAddress()));
        userInfo.addToPanel(new JLabel("Credits: " + c.getCredits()));
        userInfo.addToPanel(new JLabel("Name: " + c.getName()));
        add(userInfo);
        setSize(userInfo.getWidth(), userInfo.getHeight() + 100);
        validate();
        userID = currentUser.getID();
        setVisible(true);
    }

    /**
     * Opens the ListPanel with a specified type of Media to list.
     *
     * @param listType
     */
    public void toListPanel(int listType) {
        clear();
        if (currentUser instanceof Manager) {
            switch (listType) {
                case Store.LIST_BOOKS:
                    listMedia = new TableContentPanel("Audiobooks", store.listItems(LIST_BOOKS));
                    break;
                case Store.LIST_MOVIES:
                    listMedia = new TableContentPanel("Movies", store.listItems(LIST_MOVIES));
                    break;
                case Store.LIST_MUSIC:
                    listMedia = new TableContentPanel("Music", store.listItems(LIST_MUSIC));
                    break;
            }
            add((TableContentPanel) (listMedia));
        } else {
            switch (listType) {
                case Store.LIST_BOOKS:
                    listMedia = new TableContentPanel("Audiobooks", store.listItems(LIST_BOOKS));
                    break;
                case Store.LIST_MOVIES:
                    listMedia = new TableContentPanel("Movies", store.listItems(LIST_MOVIES));
                    break;
                case Store.LIST_MUSIC:
                    listMedia = new TableContentPanel("Music", store.listItems(LIST_MUSIC));
                    break;
            }
            add(listMedia, BorderLayout.CENTER);
        }

        back.setVisible(true);
        setSize(listMedia.getWidth(), listMedia.getHeight() + 100);
        validate();
        setVisible(true);
    }

    /**
     * Allows adding of content to the Media Store. Only for the Manager.
     */
    public void addContent() {
        Object[] possibilities = {"Music", "AudioBook", "Movie"};
        String input = (String) JOptionPane.showInputDialog(
                this,
                "What type of media would you like to add?",
                "Select a type",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "Music");
        if (input != null) {
            switch (input) {
                case "Music":
                    double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter a price  for the music item (double):"));
                    String title = JOptionPane.showInputDialog(this, "Please enter the title of this music item:");
                    String duration = JOptionPane.showInputDialog(this, "Please enter the Duration (HH:MM:SS)");
                    String genre = JOptionPane.showInputDialog(this, "Please enter this Music item's Genre:");
                    String artist = JOptionPane.showInputDialog(this, "Please enter the Artist of this music item:");
                    store.add(new MusicItem(price, title, duration, genre, artist));
                    break;
                case "AudioBook":
                    price = Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter a price  for the Audiobook item (double):"));
                    title = JOptionPane.showInputDialog(this, "Please enter the title of this AudioBook item:");
                    duration = JOptionPane.showInputDialog(this, "Please enter the Duration (HH:MM:SS)");
                    genre = JOptionPane.showInputDialog(this, "Please enter this AudioBook item's Genre:");
                    String author = JOptionPane.showInputDialog(this, "Please enter the Author:");
                    store.add(new AudiobookItem(price, title, duration, genre, author));
                    break;
                case "Movie":
                    price = Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter a price  for the movie item (double):"));
                    title = JOptionPane.showInputDialog(this, "Please enter the title of this movie item:");
                    duration = JOptionPane.showInputDialog(this, "Please enter the Duration (HH:MM:SS)");
                    genre = JOptionPane.showInputDialog(this, "Please enter this Movie item's Genre:");
                    String director = JOptionPane.showInputDialog(this, "Please enter this Movie's Director:");
                    ;
                    String releaseYear = JOptionPane.showInputDialog(this, "Please enter this Movie's release year:");
                    ;
                    store.add(new MovieItem(price, title, duration, genre, director, releaseYear));
                    break;
            }
        }

    }

    /**
     * Searches for a Media item with the title "text"
     *
     * @param text
     */
    public void toSearch(String text) {
        clear();
        back.setVisible(true);
            searchResults = new TableContentPanel("Search Results", store.getItem(text));
            add(searchResults, BorderLayout.CENTER);
            setSize(searchResults.getWidth(), searchResults.getHeight() + 100);
        validate();
        setVisible(true);
    }

    /**
     * ActionPerformed method, triggered when user clicks the back button or the
     * logout button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(back.getActionCommand())) {
            toUserPanel();
        } else if (e.getSource() == logout) {
            logout();
        }


    }
}
