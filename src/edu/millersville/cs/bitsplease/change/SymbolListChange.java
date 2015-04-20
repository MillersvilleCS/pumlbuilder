/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import java.util.Optional;

import javafx.collections.ListChangeListener.Change;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;

/**
 * @author Mervin
 *
 */
public class SymbolListChange extends UMLDocumentChange<UMLSymbol[]> {

	private boolean isAddition;
	private DocumentViewPane document;
	
	public SymbolListChange(Change<UMLSymbol> c, DocumentViewPane document) {
		super(c.getRemoved().toArray(new UMLSymbol[0]), c.getAddedSubList().toArray(new UMLSymbol[0]));
		isAddition = c.wasAdded();
		this.document = document;
	}

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
	
	@Override
	public Optional<UMLDocumentChange<?>> mergeWith(UMLDocumentChange<?> other) {
		if (other instanceof SelectedSymbolChange) {
			return Optional.of(this);
		} else {
			return super.mergeWith(other);
		}
	}

}
