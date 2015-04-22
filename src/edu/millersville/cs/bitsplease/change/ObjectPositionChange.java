/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

import javafx.geometry.Point2D;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;

/**
 * @author Mervin
 *
 */
public class ObjectPositionChange extends UMLDocumentChange<Point2D> {
	final private UMLObjectSymbol umlObject;
	
	public ObjectPositionChange(Point2D oldValue, Point2D newValue, UMLObjectSymbol umlObject) {
		super(oldValue, newValue);
		this.umlObject = umlObject;
	}
	
	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		umlObject.setOrigin(newValue);
	}

	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		umlObject.setOrigin(oldValue);
	}
	
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		if (other instanceof ObjectPositionChange) {
			if (umlObject == ((ObjectPositionChange) other).getUMLObject()) {
				return Optional.of(new ObjectPositionChange(this.oldValue, (Point2D) other.newValue, umlObject));
			} else {
				return super.mergeWith(other);
			}
		} else if (other instanceof ObjectXChange) {
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
	 * @return the umlObject
	 */
	public UMLObjectSymbol getUMLObject() {
		return umlObject;
	}
}
