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
 * Data structure for changes in UMLObjectSymbol x position
 */
public class ObjectXChange extends UMLDocumentChange<Number> {
	final private UMLObjectSymbol umlObject;
	
	/**
	 * Constructor using a change instance and a reference
	 * @param c change in the x value
	 * @param umlObject symbol which underwent change
	 */
	public ObjectXChange(Change<Number> c, UMLObjectSymbol umlObject) {
		super(c.getOldValue(), c.getNewValue());
		this.umlObject = umlObject;
	}
	
	/**
	 * Constructor using simple old and new values
	 * @param oldValue previous x position
	 * @param newValue new x position
	 * @param umlObject object that moved
	 */
	public ObjectXChange(double oldValue, double newValue, UMLObjectSymbol umlObject) {
		super(oldValue, newValue);
		this.umlObject = umlObject;
	}
	
	/** 
	 * method to redo change
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		umlObject.setLayoutX((double) newValue);
	}

	/**
	 * method to undo change
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		umlObject.setLayoutX((double) oldValue);
	}
	
	/**
	 * method to merge this change with other changes
	 */
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		if (other instanceof ObjectPositionChange) {
			//merge with position changes
			if (umlObject == ((ObjectPositionChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectPositionChange(
								new Point2D((double) this.oldValue, ((Point2D) other.oldValue).getY()),
								(Point2D) other.newValue,
								umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectYChange) {
			//merge with y changes
			if (umlObject == ((ObjectYChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectPositionChange(
								new Point2D((double) this.oldValue, (double) other.oldValue), 
								new Point2D((double) this.newValue, (double) other.newValue), 
								umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectXChange) {
			//merge with x changes
			if (umlObject == ((ObjectXChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectXChange((double) this.oldValue, (double) other.newValue, umlObject)
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
