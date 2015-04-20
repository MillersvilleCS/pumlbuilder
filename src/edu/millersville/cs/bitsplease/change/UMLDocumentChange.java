/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

/**
 * @author Mervin
 *
 */
public abstract class UMLDocumentChange<T> {
	protected final T oldValue;
	protected final T newValue;
	
	protected UMLDocumentChange(T oldValue, T newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public abstract void redo();
	public abstract void undo();
	
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		return Optional.empty();
	}
}
