/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

import javafx.collections.ListChangeListener.Change;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;

/**
 * Data structure for changes in a list of UMLSymbols
 */
public class SymbolListChange extends UMLDocumentChange<UMLSymbol[]> {

	final private boolean isAddition;
	final private DocumentViewPane document;
	
	/**
	 * Constructor
	 * @param c change that list underwent
	 * @param document reference to the DocumentViewPane that manages the list
	 */
	public SymbolListChange(Change<UMLSymbol> c, DocumentViewPane document) {
		super(c.getRemoved().toArray(new UMLSymbol[0]), c.getAddedSubList().toArray(new UMLSymbol[0]));
		isAddition = c.wasAdded();
		this.document = document;
	}

	/**
	 * method to redo the change to the list
	 */
	@Override
	public void redo() {
		if (isAddition) {
			for (UMLSymbol s : newValue) {
				document.addUMLSymbol(s);
				document.setSelectedUMLSymbol(s);
			}
		} else {
			for (UMLSymbol s : oldValue) {
				document.removeUMLSymbol(s);
			}
		}
	}

	/**
	 * method to undo the change to the list
	 */
	@Override
	public void undo() {
		if (isAddition) {
			for (UMLSymbol s : newValue) {
				document.removeUMLSymbol(s);
			}
		} else {
			for (UMLSymbol s : oldValue) {
				document.addUMLSymbol(s);
				document.setSelectedUMLSymbol(s);
			}
		}
	}
	
	/**
	 * Merges contiguous change events in a common EventStream
	 */
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		if (other instanceof SelectedSymbolChange) {
			return Optional.of(this);
		} else {
			return super.mergeWith(other);
		}
	}

}
