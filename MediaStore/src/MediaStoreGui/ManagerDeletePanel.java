/**
 * Name: James Decker and Sean Lane Section: 2 Program: MediaStore Date: 3/21/13
 */
package MediaStoreGui;

import MediaStorePackage.Store;
import MediaStorePackage.StoreItem;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

/**
 * ManagerDeletePanel allows the Manager to delete items from the store. The
 * items to be presented are passed as a parameter and then added to a JList and
 * the JList to a JScrollPane
 *
 * @author jtd5217
 *
 */
@SuppressWarnings("serial")
public class ManagerDeletePanel extends ListContentPanel {

    Store store = Store.getInstance();

    public ManagerDeletePanel(String contentType, StoreItem[] contentArray) {
        super(contentType, contentArray);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!isEmpty) {
            StoreItem selected = contentList.getSelectedValue();

            int returnVal = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove:\n" + selected.toString(), "Remove a Media Item?" + selected, JOptionPane.YES_NO_OPTION);

            if (returnVal == JOptionPane.YES_OPTION) {
                store.remove(selected);
                StoreItem[] temp = array.clone();
                array = new StoreItem[temp.length - 1];
                for (int i = 0, j = 0; i < temp.length - 1 && j < temp.length; i++, j++) {
                    if (temp[j].equals(selected)) {
                        i--;
                    } else {
                        array[i] = temp[j];
                    }
                }
                if (array.length > 0) {
                    contentList.setListData(array);
                } else {
                    removeList();
                    contentList = new JList<StoreItem>();
                    addList();
                    validate();

                    isEmpty = true;
                }
            }
        }
    }
}
