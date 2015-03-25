/**
 * @author Merv Fansler	
 * @author Josh Wakefield
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import edu.millersville.cs.bitsplease.PUMLBuilder;

/**
 * 
 */
public class ToolbarPane extends VBox implements ChangeListener<Toggle> {

	private ToggleGroup tbToggleGroup;
	private ObjectProperty<UMLEditorMode> currentEditorMode = new SimpleObjectProperty<UMLEditorMode>(UMLEditorMode.SELECT);
	
	/**
	 * 
	 */
	public ToolbarPane() {
		super();
		
		Font.loadFont(PUMLBuilder.class.getResource("fontawesome-webfont.ttf").toExternalForm(), 24);
		
		// TODO: move style code to CSS files
		this.setStyle("-fx-background-color: #444; -fx-font-family: FontAwesome");
		
		tbToggleGroup = new ToggleGroup();
		
		//create the button with icon
		ToolbarButton moveSelectButton = new ToolbarButton("\uf047");
		moveSelectButton.setTooltip(new Tooltip("Move/Select"));
		
		ToolbarButton newClassButton = new ToolbarButton("\uf022");
		newClassButton.setTooltip(new Tooltip("New Class"));
		
		ToolbarButton newRelationButton = new ToolbarButton("\uf040");
		newRelationButton.setTooltip(new Tooltip("New Relation"));
		
		ToolbarButton newDependencyButton = new ToolbarButton("\uf062");
		newDependencyButton.setTooltip(new Tooltip("New Dependency"));
		
		ToolbarButton deleteObjectButton = new ToolbarButton("\uf014");
		deleteObjectButton.setTooltip(new Tooltip("Delete Object"));
		
		// store action info for event processing
		moveSelectButton.setUserData(UMLEditorMode.SELECT);
		newClassButton.setUserData(UMLEditorMode.CREATE_CLASS);
		newRelationButton.setUserData(UMLEditorMode.CREATE_ASSOCIATION);
		newDependencyButton.setUserData(UMLEditorMode.CREATE_DEPENDENCY);
		deleteObjectButton.setUserData(UMLEditorMode.DELETE);
		
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
			
		tbToggleGroup.selectedToggleProperty().addListener(this);
	}
	
	/**
	 * @return the currentEditorMode
	 */
	public ObjectProperty<UMLEditorMode> getCurrentEditorMode() {
		return currentEditorMode;
	}

	/**
	 * @param currentEditorMode the currentEditorMode to set
	 */
	public void setCurrentEditorMode(UMLEditorMode editorMode) {
		
		tbToggleGroup.getToggles().filtered(
				t -> t.getUserData() == editorMode
				).get(0).setSelected(true);
	}

	@Override
	public void changed(ObservableValue<? extends Toggle> observable, 
			Toggle oldValue, Toggle newValue) {
		if (newValue != null) {
			currentEditorMode.setValue((UMLEditorMode) newValue.getUserData());
		}
	}
}
