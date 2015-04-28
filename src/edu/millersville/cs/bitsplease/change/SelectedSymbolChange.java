/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;

/**
 * Data structure for changes in selected symbol relation type
 */
public class SelectedSymbolChange extends UMLDocumentChange<UMLSymbol> {
	private DocumentViewPane currentDocument;

	/**
	 * Constructor for change in selected symbol
	 * @param change 
	 * @param document
	 */
	public SelectedSymbolChange(Change<UMLSymbol> change, DocumentViewPane document) {
		super(change.getOldValue(), change.getNewValue());
		currentDocument = document;
	}

	/**
	 * method to redo change in selected symbol
	 */
	@Override
	public void redo() {
		currentDocument.setSelectedUMLSymbol(newValue);
	}

	/**
	 * methdo to undo change in selected symbol
	 */
	@Override
	public void undo() {
		currentDocument.setSelectedUMLSymbol(oldValue);
	}

}
