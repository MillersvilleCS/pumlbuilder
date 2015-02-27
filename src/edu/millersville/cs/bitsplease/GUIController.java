/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLDocument;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.EditorAction;
import edu.millersville.cs.bitsplease.view.UMLEditorPane;
import edu.millersville.cs.bitsplease.view.UMLObjectView;

public class GUIController implements ChangeListener<Toggle> {
	
	private UMLEditorPane editorPane;
	
	// State Variables
	private UMLSymbol selectedUMLSymbol;
	private EditorAction currentEditorAction = EditorAction.SELECT;
	private UMLDocument currentDocument;
	
	/**
	 * Default constructor for class.
	 */
	public GUIController() {
		currentDocument = new UMLDocument();
		
		editorPane = new UMLEditorPane();
		selectedUMLSymbol = null;
		
		editorPane.getToolBarPane().selectedToggleProperty().addListener(this);
		setCurrentEditorAction(currentEditorAction);
		
		editorPane.getDocumentViewPane().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (currentEditorAction == EditorAction.CREATE_CLASS) {
					currentDocument.addClass(e.getX(),e.getY(), 100, 100);
					
					UMLObjectView objView = new UMLObjectView((UMLClassSymbol) currentDocument.getObjectList().get(currentDocument.getObjectList().size()-1));
					editorPane.getDocumentViewPane().getChildren().add(objView);
				}
			}
		});
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
	 * @param newEditorAction the currentEditorAction to set
	 */
	public void setCurrentEditorAction(EditorAction newEditorAction) {
		this.currentEditorAction = newEditorAction;
		editorPane.getToolBarPane().setSelectedEditorAction(newEditorAction);
	}

	@Override
	public void changed(ObservableValue<? extends Toggle> observable,
			Toggle oldValue, Toggle newValue) {
		if (newValue != null) {
			currentEditorAction = (EditorAction) newValue.getUserData();
		}
	}
}
