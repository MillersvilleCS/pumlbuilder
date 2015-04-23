/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLSymbol;

/**
 * @author Mervin
 *
 */
public class SymbolIdentifierChange extends UMLDocumentChange<String> {
	final UMLSymbol symbol;
	
	/**
	 * @param oldValue
	 * @param newValue
	 */
	public SymbolIdentifierChange(Change<String> c, UMLSymbol symbol) {
		super(c.getOldValue(), c.getNewValue());
		this.symbol = symbol;
	}

	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#redo()
	 */
	@Override
	public void redo() {
		this.symbol.setIdentifier(this.newValue);
	}

	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.change.UMLDocumentChange#undo()
	 */
	@Override
	public void undo() {
		this.symbol.setIdentifier(this.oldValue);
	}

}
