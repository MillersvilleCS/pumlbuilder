/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import org.reactfx.Change;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

/**
 * @author Mervin
 *
 */
public class StringPropertyChange extends UMLDocumentChange<String> {
	final StringProperty stringProperty;
	
	/**
	 * @param oldValue
	 * @param newValue
	 */
	public StringPropertyChange(Change<String> c, StringProperty stringProperty) {
		super(c.getOldValue(), c.getNewValue());
		this.stringProperty = stringProperty;
	}
	
	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		stringProperty.setValue(this.newValue);
	}

	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		stringProperty.setValue(this.oldValue);
	}
	
	static public EventStream<StringPropertyChange> toEventStream(StringProperty stringProperty) {
		return EventStreams.changesOf(stringProperty).map(
				c -> new StringPropertyChange(c, stringProperty));
	}
	
	static public EventStream<StringPropertyChange> toEventStream(TextField textField) {
		return toEventStream(textField.textProperty());
	}
}
