/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLDocument;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;
import edu.millersville.cs.bitsplease.view.EditorAction;
import edu.millersville.cs.bitsplease.view.UMLEditorPane;
import edu.millersville.cs.bitsplease.view.UMLObjectView;

public class GUIController implements ChangeListener<Toggle>, EventHandler<MouseEvent> {
	
	private UMLEditorPane editorPane;
	
	// Model
	private UMLDocument currentDocument;
	
	// View State Variables
	private EditorAction currentEditorAction = EditorAction.SELECT;
	private UMLSymbol selectedUMLSymbol;
	
	// Drag State Variables
	private Boolean isMoving = false;
	private UMLObjectView movingObjectView;
	private double dragOffsetX = 0.0;
	private double dragOffsetY = 0.0;
	
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
		editorPane.getDocumentViewPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
		editorPane.getDocumentViewPane().addEventHandler(MouseEvent.MOUSE_RELEASED, this);
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

	// EVENT HANDLERS
	
	/** 
	 * Provides MouseEvent handling for DocumentViewPane
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(MouseEvent e) {
		
		if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			switch (currentEditorAction) {
			case CREATE_CLASS:

				UMLClassSymbol c = new UMLClassSymbol(new Point2D(e.getX(),e.getY()), 100, 100);
				currentDocument.addClass(c);
				UMLObjectView objView = new UMLObjectView(c);
				editorPane.getDocumentViewPane().addUMLSymbol(objView);
				setSelectedUMLSymbol(objView.getUmlClassSymbol());
				
				break;
			case SELECT: // selecting items
				setSelectedUMLSymbol(resolveUMLSymbolObject((Node) e.getTarget()));
				
				break;
			case DELETE:
				setSelectedUMLSymbol(null);
				UMLObjectView toDelete = resolveUMLObjectView((Node)e.getTarget());
				if (toDelete != null) {
					currentDocument.getObjects().remove(toDelete.getUmlClassSymbol());
					editorPane.getDocumentViewPane().getChildren().remove(toDelete);
					
					// destroy event, since target object is now removed
					e.consume();
				}
				break;
			default:
				break;
			}
		} else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			switch (currentEditorAction) {
			case SELECT:
				if (!isMoving) {
					movingObjectView = resolveUMLObjectView((Node)e.getTarget());
					
					if (movingObjectView != null) { // begin dragging object
						setSelectedUMLSymbol(movingObjectView.getUmlClassSymbol());
						
						// compute offsets, so drag does not snap to origin
						dragOffsetX = ((UMLObjectSymbol)selectedUMLSymbol).getX() - e.getX();
						dragOffsetY = ((UMLObjectSymbol)selectedUMLSymbol).getY() - e.getY();
						
						// set state
						isMoving = true;
					}
				} else {
					((UMLClassSymbol) selectedUMLSymbol).setOrigin(new Point2D(e.getX() + dragOffsetX, e.getY() + dragOffsetY));
					movingObjectView.refreshSymbolPosition();
					editorPane.getPropertiesPane().updatePane(selectedUMLSymbol);
				}
				break;
			default:
				isMoving = false;
				movingObjectView = null;
			}
		} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			isMoving = false;
			movingObjectView = null;
		}
	}
	
	/**
	 * Utility to traverse scene graph and identify UMLSymbolObject of
	 *   ancestor UMLObjectView
	 * @author Merv Fansler
	 * @param target initial node to test for ancestor UMLObjectView
	 * @return UMLSymbolObject of UMLObjectView ancestor, otherwise null
	 */
	private UMLSymbol resolveUMLSymbolObject(Node target) {
		UMLObjectView result = resolveUMLObjectView(target);

		if (result != null) {
			return result.getUmlClassSymbol();
		} else {
			return null;
		}
	}
	
	/**
	 * Utility to traverse scene graph and identify ancestor UMLObjectView
	 * @author Merv Fansler
	 * @param target initial node to test for ancestor UMLObjectView
	 * @return UMLObjectView ancestor, otherwise null
	 */
	private UMLObjectView resolveUMLObjectView(Node target) {
		UMLObjectView result;
		
		if (target != null) {
			if (target instanceof UMLObjectView) {
				result = (UMLObjectView)target;
			} else if (target instanceof DocumentViewPane) {
				result = null;
			} else {
				result = resolveUMLObjectView(target.getParent());
			}
		} else {
			result = null;
		}
		
		return result;
	}
}
