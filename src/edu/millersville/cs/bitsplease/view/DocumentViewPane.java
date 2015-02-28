/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class DocumentViewPane extends Pane {

	/**
	 * Constructor
	 */
	public DocumentViewPane() {
		super();
		
		//TODO: Remove this testing code
		/*UMLClassSymbol c1 = new UMLClassSymbol(new Point2D(100,100)),
				c2 = new UMLClassSymbol(new Point2D(300,100));
		UMLObjectView v1 = new UMLObjectView(c1),
				v2 = new UMLObjectView(c2);
		getChildren().addAll(v1,v2,new UMLRelationView(new UMLRelationSymbol(c1, c2,UMLRelationType.DEPENDENCY)));
	*/
	}

	/**
	 * Add UML elements to view.
	 * @param objView UML element view to add to display
	 */
	public void addUMLSymbol(UMLObjectView objView) {
		this.getChildren().add(objView);
	}
}
