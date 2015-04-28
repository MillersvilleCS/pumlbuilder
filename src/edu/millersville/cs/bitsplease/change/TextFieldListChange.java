/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * Data structure for changes in a list of TextFields
 */
public class TextFieldListChange extends UMLDocumentChange<TextField[]> {
	final private List<Node> textFieldList;
	final private boolean isAddition;
	
	/**
	 * Constructor for the change in a list
	 * @param c change that list underwent
	 * @param textFieldList reference to the list that underwent change
	 */
	public TextFieldListChange(Change<TextField> c, List<Node> textFieldList) {
		super(c.getRemoved().toArray(new TextField[0]), c.getAddedSubList().toArray(new TextField[0]));
		isAddition = c.wasAdded();
		this.textFieldList = textFieldList;
	}

	/**
	 * method to redo list change
	 */
	@Override
	public void redo() {
		if (isAddition) {
			for (TextField tf : newValue) {
				textFieldList.add(tf);
			}
		} else {
			for (TextField tf : oldValue) {
				textFieldList.remove(tf);
			}
		}
	}

	/**
	 * method to undo list change
	 */
	@Override
	public void undo() {
		if (isAddition) {
			for (TextField tf : newValue) {
				textFieldList.remove(tf);
			}
		} else {
			for (TextField tf : oldValue) {
				textFieldList.add(tf);
			}
		}
	}
}
