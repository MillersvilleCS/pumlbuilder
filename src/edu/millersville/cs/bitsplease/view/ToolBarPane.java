/**
 * @author Merv Fansler	
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.PUMLBuilder;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
		
		Font.loadFont(PUMLBuilder.class.getResource("fontawesome-webfont.ttf").toExternalForm(), 12);
		
		// TODO: move style code to CSS files
		this.setStyle("-fx-background-color: #444; -fx-padding: 20; -fx-font-family: FontAwesome");
		this.setMaxWidth(100);
		
		tbToggleGroup = new ToggleGroup();
		
		for (EditorAction a : EditorAction.values()) {
			ToolBarButton button = new ToolBarButton("\uf016");
			
			// store action info for event processing
			button.setUserData(a);
			
			button.setToggleGroup(tbToggleGroup);
			
			// add to toolbar
			this.getChildren().add(button);
		}
		
		tbToggleGroup.getToggles().filtered(t -> t.getUserData() == EditorAction.SELECT);
	}
	
	/**
	 * @return selectedToggleProperty object from internal toggle group
	 */
	public ReadOnlyObjectProperty<Toggle> selectedToggleProperty() {
		return tbToggleGroup.selectedToggleProperty();
	}
	
	public void setSelectedEditorAction(EditorAction action) {
		tbToggleGroup.getToggles().filtered(
				t -> t.getUserData() == action
				).get(0).setSelected(true);
	}
}
