/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */

package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

import javafx.geometry.Point2D;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;

/**
 * Change event class to handle changes in UMLObjectSymbol positions.
 * @see org.fxmisc.undo
 * @see edu.millersville.cs.bitsplease.model.UMLObjectSymbol
 */
public class ObjectPositionChange extends UMLDocumentChange<Point2D> {
	final private UMLObjectSymbol umlObject;
	
	/**
	 * Constructor for position changes
	 * @param oldValue the previous position of the object
	 * @param newValue the new position of the object
	 * @param umlObject reference to the object
	 */
	public ObjectPositionChange(Point2D oldValue, Point2D newValue, UMLObjectSymbol umlObject) {
		super(oldValue, newValue);
		this.umlObject = umlObject;
	}
	
	/**
	 * redo implementation
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		umlObject.setOrigin(newValue);
	}

	/**
	 * undo implementation
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		umlObject.setOrigin(oldValue);
	}

	/**
	 * Specification of optional super class method to merge this change with others
	 * 
	 */
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		
		if (other instanceof ObjectPositionChange) {
			// merge with other position changes
			if (umlObject == ((ObjectPositionChange) other).getUMLObject()) {
				return Optional.of(new ObjectPositionChange(this.oldValue, (Point2D) other.newValue, umlObject));
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectXChange) {
			// merge with x changes
			if (umlObject == ((ObjectXChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectPositionChange(
								this.oldValue, 
								new Point2D((double) other.newValue, this.newValue.getY()), 
								umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectYChange) {
			// merge with y changes
			if (umlObject == ((ObjectYChange) other).getUMLObject()) {
				return Optional.of(
						new ObjectPositionChange(
								this.oldValue, 
								new Point2D(this.newValue.getX(), (double) other.newValue), 
								umlObject)
						);
			} else {
				return super.mergeWith(other);
			}
		} else {
			return super.mergeWith(other);
		}
	}

	/**
	 * method to retrieve the object
	 * necessary for merging events
	 * @return the umlObject
	 */
	public UMLObjectSymbol getUMLObject() {
		return umlObject;
	}
}
