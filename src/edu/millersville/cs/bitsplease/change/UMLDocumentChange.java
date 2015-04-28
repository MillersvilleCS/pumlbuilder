/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

/**
 * Abstract base class for managing changes to the UML document
 * This class provides the common base type for EventStream of the UndoFX UndoManager
 * @param <T> concrete type of change the document underwent
 */
public abstract class UMLDocumentChange<T> {
	protected final T oldValue;
	protected final T newValue;
	
	/**
	 * base constructor
	 * @param oldValue previous value
	 * @param newValue updated value
	 */
	protected UMLDocumentChange(T oldValue, T newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	/**
	 * methods to undo-redo changes the UML document underwent
	 */
	public abstract void redo();
	public abstract void undo();
	
	/**
	 * method to merge changes that are contiguous in an EventStream
	 * @param other the adjacent change
	 * @return a hybrid change event
	 */
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		return Optional.empty();
	}
}
