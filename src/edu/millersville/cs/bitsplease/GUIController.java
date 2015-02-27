/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLDocument;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;
import edu.millersville.cs.bitsplease.view.EditorAction;
import edu.millersville.cs.bitsplease.view.UMLEditorPane;
import edu.millersville.cs.bitsplease.view.UMLObjectView;

public class GUIController implements ChangeListener<Toggle>, EventHandler<MouseEvent> {
	
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
		setSelectedUMLSymbol(null);
		
		editorPane.getToolBarPane().selectedToggleProperty().addListener(this);
		setCurrentEditorAction(currentEditorAction);
		
		editorPane.getDocumentViewPane().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
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
		editorPane.getPropertiesPane().updatePane(selectedUMLSymbol);
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

	@Override
	public void handle(MouseEvent e) {
		switch (currentEditorAction) {
		case CREATE_CLASS:
			currentDocument.addClass(e.getX(),e.getY(), 100, 100);
			UMLObjectView objView = new UMLObjectView((UMLClassSymbol) currentDocument.getObjectList().get(currentDocument.getObjectList().size()-1));
			editorPane.getDocumentViewPane().addUMLSymbol(objView);;
			break;
		case SELECT:
			setSelectedUMLSymbol(resolveUMLSymbolObject((Node) e.getTarget()));
			//System.out.println(selectedUMLSymbol);
		default:
			break;
		}
	}
	
	private UMLSymbol resolveUMLSymbolObject(Node target) {
		UMLSymbol result;
		
		if (target != null) {
			if (target instanceof UMLObjectView) {
				result = ((UMLObjectView)target).getUmlClassSymbol();
			} else if (target instanceof DocumentViewPane) {
				result = null;
			} else {
				result = resolveUMLSymbolObject(target.getParent());
			}
		} else {
			result = null;
		}
		
		return result;
	}
}
