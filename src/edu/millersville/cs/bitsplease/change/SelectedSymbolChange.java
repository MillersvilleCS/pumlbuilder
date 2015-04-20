/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;

/**
 * @author Mervin
 *
 */
public class SelectedSymbolChange extends UMLDocumentChange<UMLSymbol> {
	private DocumentViewPane currentDocument;

	/**
	 * @param oldValue
	 * @param newValue
	 */
	public SelectedSymbolChange(UMLSymbol oldValue, UMLSymbol newValue) {
		super(oldValue, newValue);
	}

	public SelectedSymbolChange(Change<UMLSymbol> change, DocumentViewPane document) {
		super(change.getOldValue(), change.getNewValue());
		currentDocument = document;
	}

	@Override
	public void redo() {
		currentDocument.setSelectedUMLSymbol(newValue);
	}

	@Override
	public void undo() {
		currentDocument.setSelectedUMLSymbol(oldValue);
	}

}
