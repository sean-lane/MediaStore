/**
 * Name: Sean Lane and James Decker
 * Section: 2
 * Program: MediaStore
 * Date: 2/14/13
 */
package MediaStorePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class StoreButton extends JButton {
	
	public StoreButton(String text, ActionListener a){
		super(text);
		addActionListener(a);
	}
	
	
}
