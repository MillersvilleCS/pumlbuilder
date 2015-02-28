/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

import java.util.function.Predicate;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;

public class DocumentViewPane extends Pane {

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
	public void addUMLSymbol(UMLObjectView objView) {
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
		if (toDelete instanceof UMLObjectView) {
			getChildren().removeIf(
					referencesUMLObject(((UMLObjectView) toDelete).getUmlClassSymbol()));
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
}
