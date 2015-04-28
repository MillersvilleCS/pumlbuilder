/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

import javafx.geometry.Point2D;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;

/**
 * Data structure for changes in UMLObjectSymbol y position
 */
public class ObjectYChange extends UMLDocumentChange<Number> {
	final private UMLObjectSymbol umlObject;
	
	/**
	 * Constructor using a change instance and a reference
	 * @param c change in the y value
	 * @param umlObject symbol which underwent change
	 */
	public ObjectYChange(Change<Number> c, UMLObjectSymbol umlObject) {
		super(c.getOldValue(), c.getNewValue());
		this.umlObject = umlObject;
	}
	
	/**
	 * Constructor using simple old and new values
	 * @param oldValue previous y position
	 * @param newValue new y position
	 * @param umlObject object that moved
	 */
	public ObjectYChange(double oldValue, double newValue, UMLObjectSymbol umlObject) {
		super(oldValue, newValue);
		this.umlObject = umlObject;
	}
	
	/** 
	 * method to redo change
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		umlObject.setLayoutY((double) newValue);
	}

	/**
	 * method to undo change
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		umlObject.setLayoutY((double) oldValue);
	}
	
	/**
	 * method to merge this change with other changes
	 */
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		if (other instanceof ObjectPositionChange) {
			// merge with position changes
			if (umlObject == ((ObjectPositionChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectPositionChange(
								new Point2D(((Point2D) other.oldValue).getX(), (double) this.oldValue),
								(Point2D) other.newValue,
								umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectXChange) {
			// merge with x changes
			if (umlObject == ((ObjectXChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectPositionChange(
								new Point2D((double) other.oldValue, (double) this.oldValue), 
								new Point2D((double) other.newValue, (double) this.newValue), 
								umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectYChange) {
			// merge with y changes
			if (umlObject == ((ObjectYChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectYChange((double) this.oldValue, (double) other.newValue, umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else {
			return super.mergeWith(other);
		}
	}

	/**
	 * provides a means of obtaining the referenced uml object
	 * @return the umlObject
	 */
	public UMLObjectSymbol getUMLObject() {
		return umlObject;
	}

}
