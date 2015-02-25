/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease;

import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.EditorAction;
import edu.millersville.cs.bitsplease.view.UMLEditorPane;

public class GUIController {
	
	private UMLEditorPane editorPane;
	
	// State Variables
	private UMLSymbol selectedUMLSymbol;
	private EditorAction currentEditorAction = EditorAction.SELECT;
	
	/**
	 * Default constructor for class.
	 */
	public GUIController() {
		editorPane = new UMLEditorPane();
		selectedUMLSymbol = null;
	}

	/**
	 * @return the editorPane
	 */
	public UMLEditorPane getEditorPane() {
		return editorPane;
	}

	/**
	 * @return the selectedUMLSymbol
	 */
	public UMLSymbol getSelectedUMLSymbol() {
		return selectedUMLSymbol;
	}

	/**
	 * @param selectedUMLSymbol the selectedUMLSymbol to set
	 */
	public void setSelectedUMLSymbol(UMLSymbol selectedUMLSymbol) {
		this.selectedUMLSymbol = selectedUMLSymbol;
	}

	/**
	 * @return the currentEditorAction
	 */
	public EditorAction getCurrentEditorAction() {
		return currentEditorAction;
	}

	/**
	 * @param currentEditorAction the currentEditorAction to set
	 */
	public void setCurrentEditorAction(EditorAction currentEditorAction) {
		this.currentEditorAction = currentEditorAction;
	}
	
}
