/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;

/**
 * @author Mervin
 *
 */
public class ObjectXChange extends UMLDocumentChange<Number> {
	final private UMLObjectSymbol umlObject;
	
	public ObjectXChange(Change<Number> c, UMLObjectSymbol umlObject) {
		super(c.getOldValue(), c.getNewValue());
		this.umlObject = umlObject;
	}
	
	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		umlObject.setLayoutX((double) newValue);
	}

	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		umlObject.setLayoutX((double) oldValue);
	}

}
