/**
 * @author Merv Fansler	
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * 
 */
public class ToolBarPane extends VBox {

	private ToggleGroup tbToggleGroup;
	/**
	 * 
	 */
	public ToolBarPane() {
		super();
		this.setStyle("-fx-background-color: #444; -fx-padding: 20;");
		this.setMaxWidth(100);
		
		tbToggleGroup = new ToggleGroup();
		
		for (EditorAction a : EditorAction.values()) {
			ToolBarButton button = new ToolBarButton();
			
			// store action info for event processing
			button.setUserData(a);
			
			button.setToggleGroup(tbToggleGroup);
			
			// add to toolbar
			this.getChildren().add(button);
		}
		
		// TEST CODE!!!
		// this demonstrates how the action can be propagated
		tbToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				if (newValue != null)
					System.out.println(newValue.getUserData());
			}
		});
	}
}
