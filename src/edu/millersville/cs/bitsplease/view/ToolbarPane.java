/**
 * @author Merv Fansler	
 * @author Josh Wakefield
 * @author Mike Sims
 * @version 0.2.1
 */

package edu.millersville.cs.bitsplease.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Toolbar Pane GUI Component
 * This component provides the main interface for switching between editing modes.
 */
public class ToolbarPane extends VBox implements ChangeListener<Toggle> {

	private ToggleGroup tbToggleGroup;
	private ObjectProperty<UMLEditorMode> currentEditorMode = new SimpleObjectProperty<UMLEditorMode>(UMLEditorMode.SELECT);
	
	/**
	 * Default Constructor for ToolbarPane
	 */
	public ToolbarPane() {
		super();
		
		tbToggleGroup = new ToggleGroup();
		this.setStyle("-fx-background-color: #444");
		
		// Create a button for each Editor Mode
		for (UMLEditorMode m : UMLEditorMode.values()) {
			ToolbarButton b = new ToolbarButton(m);
			b.setToggleGroup(tbToggleGroup);
			this.getChildren().add(b);
		}
		
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

	/**
	 * Method required for implementing ChangeListener<Toggle>.
	 * Enables publishing of Editor mode state.
	 * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void changed(ObservableValue<? extends Toggle> observable, 
			Toggle oldValue, Toggle newValue) {
		if (newValue != null) {
			currentEditorMode.setValue((UMLEditorMode) newValue.getUserData());
		}
	}
}
