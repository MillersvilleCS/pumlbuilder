/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

public class ToolBarButton extends ToggleButton {

	/**
	 * 
	 */
	public ToolBarButton() {
		super();
		addPersistentToggle();
	}

	/**
	 * @param label
	 * @param node
	 */
	public ToolBarButton(String label, Node node) {
		super(label, node);
		addPersistentToggle();
	}

	/**
	 * @param label
	 */
	public ToolBarButton(String label) {
		super(label);
		addPersistentToggle();
	}

	// prevent MOUSE_PRESSED from propagating when already selected
	private void addPersistentToggle() {
		ToolBarButton that = this;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (that.isSelected())
					event.consume();
			}
		});
	}
}
