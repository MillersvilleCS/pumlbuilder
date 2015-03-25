/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;

public class DocumentViewPane extends Pane {

	private ObjectProperty<UMLSymbol> selectedUMLSymbol = new SimpleObjectProperty<UMLSymbol>();
	
	/**
	 * Constructor
	 */
	public DocumentViewPane() {
		super();
	}

	/**
	 * Add UML elements to view.
	 * @param objView UML element view to add to display
	 */
	public void addUMLSymbol(UMLClassView objView) {
		this.getChildren().add(objView);
	}

	/**
	 * Add UML elements to view.
	 * @param objView UML element view to add to display
	 */
	public void addUMLSymbol(UMLRelationView relView) {
		this.getChildren().add(relView);
	}
	
	public void refreshRelations(UMLObjectSymbol obj) {
		for (Node relView : getChildren().filtered(referencesUMLObject(obj))) {
			((UMLRelationView)relView).refresh();
		}
	}

	public void removeUMLSymbol(UMLSymbolView toDelete) {
		
		// remove all relation symbols that references an object being removed
		if (toDelete instanceof UMLClassView) {
			getChildren().removeIf(
					referencesUMLObject(((UMLClassView) toDelete).getUmlClassSymbol()));
		}
		
		getChildren().remove(toDelete);
	}
	
	/**
	 * Provides a means for finding relations of a given object
	 * @param obj an object
	 * @return predicate that selects for relating UMLRelationViews
	 */
	private Predicate<Node> referencesUMLObject (UMLObjectSymbol obj) {
		return n -> n instanceof UMLRelationView && 
				(((UMLRelationView)n).getSourceObject() == obj ||
				((UMLRelationView)n).getTargetObject() == obj);
	}

	/**
	 * @return the selectedUMLSymbol
	 */
	public ObjectProperty<UMLSymbol> getSelectedUMLSymbol() {
		return selectedUMLSymbol;
	}

	/**
	 * @param selectedUMLSymbol the selectedUMLSymbol to set
	 */
	public void setSelectedUMLSymbol(UMLSymbol umlSymbol) {
		this.selectedUMLSymbol.setValue(umlSymbol);
	}
}
