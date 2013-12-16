/**
 * Name: James Decker and Sean Lane Section: 2 Program: MediaStore Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.AudiobookItem;
import MediaStorePackage.Customer;
import MediaStorePackage.Manager;
import MediaStorePackage.MovieItem;
import MediaStorePackage.MusicItem;
import MediaStorePackage.Store;
import MediaStorePackage.StoreItem;
import MediaStorePackage.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class TableContentPanel extends JPanel {

    protected JTable contentList;          // content list
    private JScrollPane scrollPane;//JScrollPane to allow scrolling through JList
    private JLabel content;             // content label
    private BorderLayout layout1;        // layout for elements
    private JPanel titlePanel;          // JPanels to organize list/title
    private JPanel contentPanel;
    private static Store store = Store.getInstance();   // current instance of store
    protected boolean isEmpty = false;  // boolean for when list is empty
    private static User user = store.getCurrentUser();    // fetch usertype from store
    private Object[][] data;
    private static String[] columnNames;
    private int columnCount;
    private static final int MAX_ROWS = 50;
    private String tableType;
    private AudiobookItem audiobook;
    private MovieItem movie;
    private MusicItem music;
    private StoreItem item;
    private ListSelectionModel listSelectionModel;

    /**
     * Init constructor for list content panel; takes in content type, which
     * allows us to use the same panel for many different types of content
     */
    public TableContentPanel(String contentType, ResultSet results) {
        try {
            if (results.next() == false) {   // check to see if contentArray is empty
                isEmpty = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {
            columnCount = results.getMetaData().getColumnCount();
        } catch (SQLException ex) {
        }

        layout1 = new BorderLayout();   // define layouts for GUI   
        setLayout(layout1);
        setSize(800, 500);              // set size of panel

        content = new JLabel(contentType);  // init title label

        columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            try {
                columnNames[i] = results.getMetaData().getColumnName(i + 1);
            } catch (SQLException ex) {
            }
        }

        data = new Object[MAX_ROWS][columnCount];
        contentList = new JTable(data, columnNames);   // init JTable, add to event handling
        contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = contentList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        contentList.setSelectionModel(listSelectionModel);
        contentList.setCellSelectionEnabled(false);
        contentList.setColumnSelectionAllowed(false);
        tableType = contentType;
        //contentList.addMouseListener(null); ************************

        int row = 0;

        switch (contentType) {
            case "Audiobooks":
                try {
                    do {
                        contentList.setValueAt(results.getString("TITLE"), row, 0);
                        contentList.setValueAt(results.getString("AUTHOR"), row, 1);
                        contentList.setValueAt(results.getString("GENRE"), row, 2);
                        contentList.setValueAt(results.getString("DURATION"), row, 3);
                        contentList.setValueAt(results.getDouble("PRICE"), row, 4);
                        contentList.setValueAt(results.getDouble("AVG_RATING"), row, 5);
                        contentList.setValueAt(results.getInt("RANKING"), row, 6);

                        row++;
                    } while (results.next());
                } catch (SQLException ex) {
                    Logger.getLogger(TableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Music":
                try {
                    do {
                        contentList.setValueAt(results.getString("TITLE"), row, 0);
                        contentList.setValueAt(results.getString("ARTIST"), row, 1);
                        contentList.setValueAt(results.getString("GENRE"), row, 2);
                        contentList.setValueAt(results.getString("DURATION"), row, 3);
                        contentList.setValueAt(results.getDouble("PRICE"), row, 4);
                        contentList.setValueAt(results.getDouble("AVG_RATING"), row, 5);
                        contentList.setValueAt(results.getInt("RANKING"), row, 6);

                        row++;
                    } while (results.next());
                } catch (SQLException ex) {
                    Logger.getLogger(TableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Movies":
                try {
                    do {
                        contentList.setValueAt(results.getString("TITLE"), row, 0);
                        contentList.setValueAt(results.getString("DIRECTOR"), row, 1);
                        contentList.setValueAt(results.getString("GENRE"), row, 2);
                        contentList.setValueAt(results.getString("RELEASE_YEAR"), row, 3);
                        contentList.setValueAt(results.getString("DURATION"), row, 4);
                        contentList.setValueAt(results.getDouble("PRICE"), row, 5);
                        contentList.setValueAt(results.getDouble("AVG_RATING"), row, 6);
                        contentList.setValueAt(results.getInt("RANKING"), row, 7);
                        row++;
                    } while (results.next());
                } catch (SQLException ex) {
                    Logger.getLogger(TableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "PASSWORD":
                try {
                    do {
                        contentList.setValueAt(results.getString("USER_ID"), row, 0);
                        contentList.setValueAt(results.getInt("CREDITS"), row, 2);
                        contentList.setValueAt(results.getString("NAME"), row, 3);
                        contentList.setValueAt(results.getString("ADDRESS"), row, 4);
                        row++;
                    } while (results.next());
                } catch (SQLException ex) {
                    Logger.getLogger(TableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Customer Info":
            case "Search Results":
                try {
                    if (!isEmpty) {
                        do {
                            contentList.setValueAt(results.getString("TITLE"), row, 0);
                            contentList.setValueAt(results.getString("AUTHOR_ARTIST_DIRECTOR"), row, 1);
                            contentList.setValueAt(results.getString("GENRE"), row, 2);
                            contentList.setValueAt(results.getString("DURATION"), row, 3);
                            contentList.setValueAt(results.getString("RELEASE_YEAR"), row, 4);
                            contentList.setValueAt(results.getDouble("PRICE"), row, 5);
                            contentList.setValueAt(results.getDouble("AVG_RATING"), row, 6);

                            row++;
                        } while (results.next());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        titlePanel = new JPanel();          // set up GUI panels for organization
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setSize(600, 400);
        setLayout(new BorderLayout());
        contentList.setSize(600, 400);

        scrollPane = new JScrollPane(contentList);
        scrollPane.setSize(600, 400);
        titlePanel.add(content);            // add GUI components together
        add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel);

    }

    /**
     * Override for ListSelectionListener events, tells driver what to do when
     * an element of the list is clicked
     */
    class SharedListSelectionHandler implements ListSelectionListener { // event handling for table; based on Oracle's tutorial

        @Override
        public void valueChanged(ListSelectionEvent e) {
            audiobook = null;
            movie = null;
            music = null;
            user = store.getCurrentUser();
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            int firstIndex = e.getFirstIndex();
            int lastIndex = e.getLastIndex();
            boolean isAdjusting = e.getValueIsAdjusting();
            if (lsm.isSelectionEmpty()) {
            } else {                // Find out which indexes are selected.                
                int selectedIndex = lsm.getMinSelectionIndex();

                switch (tableType) {
                    case "Audiobooks":
                        audiobook = new AudiobookItem((Double) contentList.getValueAt(selectedIndex, 4), (String) contentList.getValueAt(selectedIndex, 0),
                                (String) contentList.getValueAt(selectedIndex, 3), (String) contentList.getValueAt(selectedIndex, 2),
                                (String) contentList.getValueAt(selectedIndex, 1));
                        audiobook.setRanking((int) contentList.getValueAt(selectedIndex, 6));
                        break;
                    case "Movies":
                        movie = new MovieItem((Double) contentList.getValueAt(selectedIndex, 5), (String) contentList.getValueAt(selectedIndex, 0),
                                (String) contentList.getValueAt(selectedIndex, 4), (String) contentList.getValueAt(selectedIndex, 2),
                                (String) contentList.getValueAt(selectedIndex, 1), (String) contentList.getValueAt(selectedIndex, 3));
                        movie.setRanking((int) contentList.getValueAt(selectedIndex, 7));
                        break;
                    case "Music":
                        music = new MusicItem((Double) contentList.getValueAt(selectedIndex, 4), (String) contentList.getValueAt(selectedIndex, 0),
                                (String) contentList.getValueAt(selectedIndex, 3), (String) contentList.getValueAt(selectedIndex, 2),
                                (String) contentList.getValueAt(selectedIndex, 1));
                        music.setRanking((int) contentList.getValueAt(selectedIndex, 6));
                        break;
                    case "Customer Info":
                    case "Search Results":
                        if(((String)contentList.getValueAt(selectedIndex, 4)).equals("(music)")){
                            music = new MusicItem((Double) contentList.getValueAt(selectedIndex, 5),(String) contentList.getValueAt(selectedIndex, 0),
                                (String) contentList.getValueAt(selectedIndex, 3), (String) contentList.getValueAt(selectedIndex, 2),
                                (String) contentList.getValueAt(selectedIndex, 1));
                        }
                        else if(((String)contentList.getValueAt(selectedIndex, 4)).equals("(audiobook)")){
                            audiobook = new AudiobookItem((Double) contentList.getValueAt(selectedIndex, 5),(String) contentList.getValueAt(selectedIndex, 0),
                                (String) contentList.getValueAt(selectedIndex, 3), (String) contentList.getValueAt(selectedIndex, 2),
                                (String) contentList.getValueAt(selectedIndex, 1));
                        }else{
                            movie = new MovieItem((Double) contentList.getValueAt(selectedIndex, 5),(String) contentList.getValueAt(selectedIndex, 0),
                                (String) contentList.getValueAt(selectedIndex, 3), (String) contentList.getValueAt(selectedIndex, 2),
                                (String) contentList.getValueAt(selectedIndex, 1), (String) contentList.getValueAt(selectedIndex, 4));
                        }
                        break;
                }

                if (user instanceof Customer) {           // if user is a customer
                    // pull hash value for current item
                    Customer customer = (Customer) user;                // establish user as customer

                    // prompt user if they are sure about purchase
                    if (audiobook != null) {
                        int returnVal = JOptionPane.showConfirmDialog(store.getFrame(),
                                "Are you sure you would like to buy:\n" + audiobook.toString(), "Purchasing" + audiobook.toString(), JOptionPane.YES_NO_OPTION);
                        if (returnVal == JOptionPane.YES_OPTION) {    // purchase if yes
                            if (store.buy(audiobook, customer)) {
                                JOptionPane.showMessageDialog(store.getFrame(), "Purchase Successful");
                            }
                        }
                    } else if (movie != null) {
                        int returnVal = JOptionPane.showConfirmDialog(store.getFrame(),
                                "Are you sure you would like to buy\n" + movie.toString(), "Purchasing" + movie.toString(), JOptionPane.YES_NO_OPTION);
                        if (returnVal == JOptionPane.YES_OPTION) {    // purchase if yes
                            if (store.buy(movie, customer)) {
                                JOptionPane.showMessageDialog(store.getFrame(), "Purchase Successful");
                            }
                        }
                    } else if (music != null) {
                        int returnVal = JOptionPane.showConfirmDialog(store.getFrame(),
                                "Are you sure you would like to buy\n" + music.toString(), "Purchasing" + music.toString(), JOptionPane.YES_NO_OPTION);
                        if (returnVal == JOptionPane.YES_OPTION) {    // purchase if yes
                            if (store.buy(music, customer)) {
                                JOptionPane.showMessageDialog(store.getFrame(), "Purchase Successful");
                            }
                        }
                    }
                } else {           // if user is a Manager

                    item = null;
                    if (audiobook != null) {
                        item = audiobook;
                    } else if (movie != null) {
                        item = movie;
                    } else if (music != null) {
                        item = music;
                    }
                    if (item != null) {
                        // prompt manager if they are sure about removing the item
                        int returnVal = JOptionPane.showConfirmDialog(store.getFrame(),
                                "Are you sure you would like to remove:\n" + item.toString(),
                                "Removing " + item.toString(), JOptionPane.YES_NO_OPTION);
                        if (returnVal == JOptionPane.YES_OPTION) {    // purchase if yes
                            store.remove(item);
                            JOptionPane.showMessageDialog(store.getFrame(), item.getTitle() + " was deleted");
                        }
                    }

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
