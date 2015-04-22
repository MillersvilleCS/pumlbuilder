/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

import javafx.geometry.Point2D;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;

/**
 * @author Mervin
 *
 */
public class ObjectYChange extends UMLDocumentChange<Number> {
	final private UMLObjectSymbol umlObject;
	
	public ObjectYChange(Change<Number> c, UMLObjectSymbol umlObject) {
		super(c.getOldValue(), c.getNewValue());
		this.umlObject = umlObject;
	}
	
	public ObjectYChange(double oldValue, double newValue, UMLObjectSymbol umlObject) {
		super(oldValue, newValue);
		this.umlObject = umlObject;
	}
	
	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		umlObject.setLayoutY((double) newValue);
	}

	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		umlObject.setLayoutY((double) oldValue);
	}
	
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		if (other instanceof ObjectPositionChange) {
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
	 * @return the umlObject
	 */
	public UMLObjectSymbol getUMLObject() {
		return umlObject;
	}

}
