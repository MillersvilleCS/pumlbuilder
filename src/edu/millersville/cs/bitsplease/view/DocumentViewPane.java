/**
 * @author Merv Fansler
 */
package edu.millersville.cs.bitsplease.view;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;

/**
 * @author Mervin
 *
 */
public class DocumentViewPane extends Pane {

	/**
	 * 
	 */
	public DocumentViewPane() {
		super();
		
		//Test Case
		/*UMLClassSymbol c = new UMLClassSymbol("First Class");
		UMLObjectView v = new UMLObjectView(c);
		getChildren().add(v);
		
		v.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//System.out.println(e);
				v.UpdatePosition(new Point2D(e.getX(), e.getY()));
			}
		});*/
	}

}
