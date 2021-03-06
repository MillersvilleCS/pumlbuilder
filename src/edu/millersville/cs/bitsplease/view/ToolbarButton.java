/**
 * @author Merv Fansler
 * @author Mike Sims
 * @since February 25, 2015
 * @version 0.3.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * ToolbarButton is GUI component used in the Toolbar.
 * Each Editor Mode is associated with a toolbar button.
 */
public class ToolbarButton extends ToggleButton {

	/**
	 * Default Button constructor
	 */
	public ToolbarButton(UMLEditorMode editorMode){
		ImageView icon = new ImageView(
				new Image(getClass().getResourceAsStream(editorMode.getImagePath())));
		setGraphic(icon);
		setTooltip(new Tooltip(editorMode.getTooltipText()));
		
		setUserData(editorMode);
		
		setPrefWidth(40);
		setPrefHeight(40);
		
		// prevent keyboard navigation of buttons
		setFocusTraversable(false);
		
		// prevent unselecting by direct clicking
		addPersistentToggle();
	}

	/**
	 *  prevent MOUSE_PRESSED from propagating when already selected
	 */
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
