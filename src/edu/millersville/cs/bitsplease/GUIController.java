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
import edu.millersville.cs.bitsplease.model.*;
import edu.millersville.cs.bitsplease.view.*;

public class GUIController implements ChangeListener<Toggle>, EventHandler<MouseEvent> {
	
	private UMLEditorPane editorPane;
	
	// Model
	private UMLDocument currentDocument;
	
	// View State Variables
	private EditorAction currentEditorAction = EditorAction.SELECT;
	private UMLSymbol selectedUMLSymbol;
	
	// Drag State Variables
	private Boolean isMoving = false;
	private UMLObjectView dragTarget;
	private double dragOffsetX = 0.0;
	private double dragOffsetY = 0.0;

	private boolean isRelating = false;
	
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
				UMLSymbolView toDelete = resolveUMLSymbolView((Node)e.getTarget());
				if (toDelete != null) {
					currentDocument.getObjects().remove(toDelete.getUMLSymbol());
					editorPane.getDocumentViewPane().removeUMLSymbol(toDelete);
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
					dragTarget = (UMLObjectView) resolveUMLSymbolView((Node)e.getTarget());
					
					if (dragTarget != null) { // begin dragging object
						setSelectedUMLSymbol(dragTarget.getUmlClassSymbol());
						
						// compute offsets, so drag does not snap to origin
						dragOffsetX = ((UMLObjectSymbol)selectedUMLSymbol).getX() - e.getX();
						dragOffsetY = ((UMLObjectSymbol)selectedUMLSymbol).getY() - e.getY();
						
						// set state
						isMoving = true;
					}
				} else {
					((UMLClassSymbol) selectedUMLSymbol).setOrigin(new Point2D(e.getX() + dragOffsetX, e.getY() + dragOffsetY));
					dragTarget.refreshSymbolPosition();
					editorPane.getPropertiesPane().updatePane(selectedUMLSymbol);
					editorPane.getDocumentViewPane().refreshRelations((UMLObjectSymbol) selectedUMLSymbol);
				}
				break;
			case CREATE_ASSOCIATION:
			case CREATE_DEPENDENCY:
				if (!isRelating) {
					
					dragTarget = (UMLObjectView) resolveUMLSymbolView((Node)e.getTarget());
					isRelating  = (dragTarget != null);
				}
				break;
			default:
				isMoving = false;
				isRelating = false;
				dragTarget = null;
			}
		} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			switch (currentEditorAction) {
			case CREATE_ASSOCIATION:
				if (isRelating) {
					UMLSymbol dragRelease = resolveUMLSymbolObject((Node)e.getPickResult().getIntersectedNode());
					if (dragRelease != null && dragRelease instanceof UMLClassSymbol) {
						UMLRelationSymbol relation = new UMLRelationSymbol(
								dragTarget.getUmlClassSymbol(), 
								(UMLObjectSymbol) dragRelease, 
								UMLRelationType.ASSOCIATION);
						currentDocument.addRelation(relation);
						editorPane.getDocumentViewPane().addUMLSymbol(new UMLRelationView(relation));
					}					
				}
				break;
			case CREATE_DEPENDENCY:
				if (isRelating) {
					UMLSymbol dragRelease = resolveUMLSymbolObject((Node)e.getPickResult().getIntersectedNode());
					if (dragRelease != null && dragRelease instanceof UMLClassSymbol) {
						UMLRelationSymbol relation = new UMLRelationSymbol(
								dragTarget.getUmlClassSymbol(), 
								(UMLObjectSymbol) dragRelease, 
								UMLRelationType.DEPENDENCY);
						currentDocument.addRelation(relation);
						editorPane.getDocumentViewPane().addUMLSymbol(new UMLRelationView(relation));
					}					
				}
				break;
			default:
				break;
			}
			isRelating = false;
			isMoving = false;
			dragTarget = null;
		}
	}
	
	/**
	 * Utility to traverse scene graph and identify UMLSymbol of
	 *   ancestor UMLSymbolView
	 * @author Merv Fansler
	 * @param target initial node to test for ancestor UMLSymbolView
	 * @return UMLSymbol of UMLSymbolView ancestor, otherwise null
	 */
	private UMLSymbol resolveUMLSymbolObject(Node target) {
		UMLSymbolView result = resolveUMLSymbolView(target);

		if (result != null) {
			return result.getUMLSymbol();
		} else {
			return null;
		}
	}
	
	/**
	 * Utility to traverse scene graph and identify ancestor UMLSymbolView
	 * @author Merv Fansler
	 * @param target initial node to test for ancestor UMLSymbolView
	 * @return UMLSymbolView ancestor, otherwise null
	 */
	private UMLSymbolView resolveUMLSymbolView(Node target) {
		UMLSymbolView result;
		
		if (target != null) {
			if (target instanceof UMLSymbolView) {
				result = (UMLSymbolView)target;
			} else if (target instanceof DocumentViewPane) {
				result = null;
			} else {
				result = resolveUMLSymbolView(target.getParent());
			}
		} else {
			result = null;
		}
		
		return result;
	}
}
