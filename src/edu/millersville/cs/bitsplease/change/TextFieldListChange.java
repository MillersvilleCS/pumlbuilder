/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * @author Mervin
 *
 */
public class TextFieldListChange extends UMLDocumentChange<TextField[]> {
	final private List<Node> textFieldList;
	private boolean isAddition;
	
	public TextFieldListChange(Change<TextField> c, List<Node> textFieldList) {
		super(c.getRemoved().toArray(new TextField[0]), c.getAddedSubList().toArray(new TextField[0]));
		isAddition = c.wasAdded();
		this.textFieldList = textFieldList;
	}

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
