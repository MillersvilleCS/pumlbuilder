/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

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
		for (Node relView : getChildren().filtered(n -> n instanceof UMLRelationView)) {
			if (((UMLRelationView)relView).getSourceObject() == obj ||
				((UMLRelationView)relView).getTargetObject() == obj)
				((UMLRelationView)relView).refresh();
		}
	}
}
