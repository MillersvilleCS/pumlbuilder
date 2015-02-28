/**
 * @author Merv Fansler	
 * @author Josh Wakefield
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import edu.millersville.cs.bitsplease.PUMLBuilder;

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
		
		Font.loadFont(PUMLBuilder.class.getResource("fontawesome-webfont.ttf").toExternalForm(), 24);
		
		// TODO: move style code to CSS files
		this.setStyle("-fx-background-color: #444; -fx-font-family: FontAwesome");
		
		tbToggleGroup = new ToggleGroup();
		
		//create the button with icon
		ToolBarButton moveSelectButton = new ToolBarButton("\uf047");
		moveSelectButton.setTooltip(new Tooltip("Move/Select"));
		
		ToolBarButton newClassButton = new ToolBarButton("\uf022");
		newClassButton.setTooltip(new Tooltip("New Class"));
		
		ToolBarButton newRelationButton = new ToolBarButton("\uf040");
		newRelationButton.setTooltip(new Tooltip("New Relation"));
		
		ToolBarButton newDependencyButton = new ToolBarButton("\uf062");
		newDependencyButton.setTooltip(new Tooltip("New Dependency"));
		
		ToolBarButton deleteObjectButton = new ToolBarButton("\uf014");
		deleteObjectButton.setTooltip(new Tooltip("Delete Object"));
		
		
		// store action info for event processing
		moveSelectButton.setUserData(EditorAction.SELECT);
		newClassButton.setUserData(EditorAction.CREATE_CLASS);
		newRelationButton.setUserData(EditorAction.CREATE_ASSOCIATION);
		newDependencyButton.setUserData(EditorAction.CREATE_DEPENDENCY);
		deleteObjectButton.setUserData(EditorAction.DELETE);
		
		moveSelectButton.setToggleGroup(tbToggleGroup);
		newClassButton.setToggleGroup(tbToggleGroup);
		newRelationButton.setToggleGroup(tbToggleGroup);
		newDependencyButton.setToggleGroup(tbToggleGroup);
		deleteObjectButton.setToggleGroup(tbToggleGroup);
		
		
		// add to toolbar
		this.getChildren().add(moveSelectButton);
		this.getChildren().add(newClassButton);
		this.getChildren().add(newRelationButton);
		this.getChildren().add(newDependencyButton);
		this.getChildren().add(deleteObjectButton);
				
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
