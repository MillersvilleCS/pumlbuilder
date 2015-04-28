/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import org.reactfx.Change;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

/**
 * Generic data structure for changes in string properties
 */
public class StringPropertyChange extends UMLDocumentChange<String> {
	final StringProperty stringProperty;
	
	/**
	 * Constructor
	 * @param c change object of String
	 * @param stringProperty the property that underwent a change
	 */
	public StringPropertyChange(Change<String> c, StringProperty stringProperty) {
		super(c.getOldValue(), c.getNewValue());
		this.stringProperty = stringProperty;
	}
	
	/**
	 * method to redo change in string
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		stringProperty.setValue(this.newValue);
	}

	/**
	 * method to undo change in string
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		stringProperty.setValue(this.oldValue);
	}
	
	/**
	 * Utility to convert a StringProperty to a change event stream
	 * @param stringProperty the property to generate a stream for
	 * @return an EventStream of change events
	 */
	static public EventStream<StringPropertyChange> toEventStream(StringProperty stringProperty) {
		return EventStreams.changesOf(stringProperty).map(
				c -> new StringPropertyChange(c, stringProperty));
	}
	
	/**
	 * Utility to convert a TextField to a change event stream
	 * @param textField the TextField to generate a stream for
	 * @return an EventStream of change events
	 */
	static public EventStream<StringPropertyChange> toEventStream(TextField textField) {
		return toEventStream(textField.textProperty());
	}
}
