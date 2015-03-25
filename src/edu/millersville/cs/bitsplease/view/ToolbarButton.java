/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

public class ToolbarButton extends ToggleButton {

	/**
	 * @param label
	 */
	public ToolbarButton(String label) {
		super(label);
		addPersistentToggle();
		setPrefWidth(40);
		setPrefHeight(40);
		setStyle("-fx-font-size: 16");
	}

	// prevent MOUSE_PRESSED from propagating when already selected
	private void addPersistentToggle() {
		final ToolbarButton that = this;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (that.isSelected())
					event.consume();
			}
		});
	}
}
