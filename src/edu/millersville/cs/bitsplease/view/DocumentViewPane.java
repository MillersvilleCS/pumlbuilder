/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

import javafx.scene.layout.Pane;

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
}
