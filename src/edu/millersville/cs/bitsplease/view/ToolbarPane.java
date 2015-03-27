/**
 * @author Merv Fansler	
 * @author Josh Wakefield
 * @author Mike Sims
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
		
		tbToggleGroup = new ToggleGroup();
		this.setStyle("-fx-background-color: #444");
		
		ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Select.png")));
		
		//create the button with icon
		ToolbarButton moveSelectButton = new ToolbarButton();
		moveSelectButton.setImage(icon);
		moveSelectButton.setTooltip(new Tooltip("Move/Select"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Class.png")));
		ToolbarButton newClassButton = new ToolbarButton();
		newClassButton.setImage(icon);
		newClassButton.setTooltip(new Tooltip("New Class"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Association.png")));
		ToolbarButton newAssociationButton = new ToolbarButton();
		newAssociationButton.setImage(icon);
		newAssociationButton.setTooltip(new Tooltip("New Association"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Dependency.png")));
		ToolbarButton newDependencyButton = new ToolbarButton();
		newDependencyButton.setImage(icon);
		newDependencyButton.setTooltip(new Tooltip("New Dependency"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Aggregation.png")));
		ToolbarButton newAggregationButton = new ToolbarButton();
		newAggregationButton.setImage(icon);
		newAggregationButton.setTooltip(new Tooltip("New Aggregation"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Composition.png")));
		ToolbarButton newCompostionButton = new ToolbarButton();
		newCompostionButton.setImage(icon);
		newCompostionButton.setTooltip(new Tooltip("New Composition"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Generalization.png")));
		ToolbarButton newGeneralizationButton = new ToolbarButton();
		newGeneralizationButton.setImage(icon);
		newGeneralizationButton.setTooltip(new Tooltip("New Generaliztion"));
		
		icon = new ImageView(new Image(getClass().getResourceAsStream("/img/Delete.png")));
		ToolbarButton deleteObjectButton = new ToolbarButton();
		deleteObjectButton.setImage(icon);
		deleteObjectButton.setTooltip(new Tooltip("Delete Object"));
		
		
		// store action info for event processing
		moveSelectButton.setUserData(UMLEditorMode.SELECT);
		newClassButton.setUserData(UMLEditorMode.CREATE_CLASS);
		newAssociationButton.setUserData(UMLEditorMode.CREATE_ASSOCIATION);
		newDependencyButton.setUserData(UMLEditorMode.CREATE_DEPENDENCY);
		newAggregationButton.setUserData(UMLEditorMode.CREATE_AGGREGATION);
		newCompostionButton.setUserData(UMLEditorMode.CREATE_COMPOSITION);
		newGeneralizationButton.setUserData(UMLEditorMode.CREATE_GENERALIZATION);
		deleteObjectButton.setUserData(UMLEditorMode.DELETE);
		
		moveSelectButton.setToggleGroup(tbToggleGroup);
		newClassButton.setToggleGroup(tbToggleGroup);
		newAssociationButton.setToggleGroup(tbToggleGroup);
		newDependencyButton.setToggleGroup(tbToggleGroup);
		newAggregationButton.setToggleGroup(tbToggleGroup);
		newCompostionButton.setToggleGroup(tbToggleGroup);
		newGeneralizationButton.setToggleGroup(tbToggleGroup);
		deleteObjectButton.setToggleGroup(tbToggleGroup);
		
		
		// add to toolbar
		this.getChildren().add(moveSelectButton);
		this.getChildren().add(newClassButton);
		this.getChildren().add(newAssociationButton);
		this.getChildren().add(newDependencyButton);
		this.getChildren().add(newAggregationButton);
		this.getChildren().add(newCompostionButton);
		this.getChildren().add(newGeneralizationButton);
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
