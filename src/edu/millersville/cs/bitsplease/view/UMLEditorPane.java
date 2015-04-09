/**
 * @author Merv Fansler
 * @author Kevin Fisher
 * @author Mike Sims
 * @author Josh Wakefield
 * @since February 19, 2015
 * @version 0.2.0
 */

package edu.millersville.cs.bitsplease.view;


import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import edu.millersville.cs.bitsplease.model.*;

/***
 * Primary GUI component where all user interact occurs. All other view
 * components are owned by UMLEditorPane.
 */
public class UMLEditorPane extends BorderPane implements EventHandler<MouseEvent> {
	
	// Subcomponents
	private ToolbarPane toolbarPane;
	private DocumentViewPane documentViewPane;
	private PropertiesPane propertiesPane;
	
	// Drag State Variables
	private Boolean isMoving = false;
	private UMLObjectSymbol dragTarget;
	private double dragOffsetX = 0.0;
	private double dragOffsetY = 0.0;

	private boolean isRelating = false;
	
	/**
	 * Default Construct for UMLEditor Pane
	 */
	public UMLEditorPane() {
		super();
		
		// create Document View Pane
		documentViewPane = new DocumentViewPane();
		this.setCenter(documentViewPane);
		documentViewPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		documentViewPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
		documentViewPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
		
		// create and add Menu
		UMLMenu pumlMenu = new UMLMenu();
		pumlMenu.setDocument(documentViewPane);
		this.setTop(pumlMenu);
		
		// create and add Toolbar Pane
		toolbarPane = new ToolbarPane();
		this.setLeft(toolbarPane);
		
		// create and add Properties Pane
		propertiesPane = new PropertiesPane(documentViewPane.getSelectedUMLSymbol());
		this.setRight(propertiesPane);
	}
	
	/**
	 * initialization method for contextMenu
	 * @return instance of context Menu to be displayed when you right click on an object
	 */
	private ContextMenu createContextMenu() {
		
		ContextMenu contextMenu = new ContextMenu();
		
		//create generic contextMenu items
		MenuItem cut = new MenuItem("Cut");
		MenuItem copy = new MenuItem("Copy");
		MenuItem paste = new MenuItem("Paste");
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(event -> { 
			documentViewPane.removeUMLSymbol(documentViewPane.getSelectedUMLSymbol().getValue());
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event -> { contextMenu.hide(); });
		exit.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
		
		//Used to determine if the selected object is a UMLCLassSymbol and if it is then
		//adds the menu options that are specific to a UMLClassSymbol to the context menu
		//otherwise those options are not added to the context menu
		if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLClassSymbol) {
			
			UMLClassSymbol targetObj = (UMLClassSymbol) documentViewPane.getSelectedUMLSymbol().getValue();
		
			MenuItem addAttr = new MenuItem("Add Attribute");
			addAttr.setOnAction(event -> { targetObj.addAttribute("+attribute:type"); });
			
			MenuItem deleteAttr = new MenuItem("Delete Attribute");
			deleteAttr.setOnAction(event -> { targetObj.deleteAttribute(); });
			
			MenuItem addOper = new MenuItem("Add Operation");
			addOper.setOnAction(event -> { targetObj.addOperation("-operation():type"); });
			
			MenuItem deleteOper = new MenuItem("Delete Operation");
			deleteOper.setOnAction(event -> { targetObj.deleteOperation(); });
			
			//add all items to the contextMenu for a UMLClassSymbol
			contextMenu.getItems().addAll(cut, copy, paste, new SeparatorMenuItem(), 
					delete, new SeparatorMenuItem(), addAttr, deleteAttr, new SeparatorMenuItem(),
					addOper, deleteOper, new SeparatorMenuItem(), exit);
		
		} else {
		
			//add all items to the contextMenu for non-UMLClassSymbol's
			contextMenu.getItems().addAll(cut, copy, paste, new SeparatorMenuItem(), 
					delete, new SeparatorMenuItem(), exit);
			
		}
		
		return contextMenu;
	}
	
	/**
	 * @return reference to ToolbarPane instance
	 */
	public ToolbarPane getToolBarPane() {
		return toolbarPane;
	}
	
	/**
	 * @return reference to DocumentViewPane instance
	 */
	public DocumentViewPane getDocumentViewPane() {
		return documentViewPane;
	}
	
	/**
	 * @return reference to PropertiesPane instance
	 */
	public PropertiesPane getPropertiesPane() {
		return propertiesPane;
	}
	
	/** 
	 * Provides MouseEvent handling for all subcomponent panes
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@SuppressWarnings("static-access")
	@Override
	public void handle(MouseEvent e) {
		
		if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case CREATE_CLASS:
				
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLClassSymbol c = new UMLClassSymbol(new Point2D(e.getX(),e.getY()), 100, 100);
					documentViewPane.addUMLSymbol(c);
					documentViewPane.setSelectedUMLSymbol(c);
				}
				
				break;
			case SELECT: // selecting items
				ContextMenu test = createContextMenu();
				documentViewPane.setSelectedUMLSymbol(resolveUMLSymbolParent((Node) e.getTarget()));
				
				if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLClassSymbol) {
					UMLClassSymbol toEdit = (UMLClassSymbol) resolveUMLSymbolParent((Node) e.getTarget());
					documentViewPane.setSelectedUMLSymbol(toEdit);
					if (toEdit.isSelected()) {
						if (e.getClickCount() == 2) {
							toEdit.setEditableUMLClassSymbol();
						}
						if (e.getButton().equals(e.getButton().SECONDARY)) {
							test.show(documentViewPane, e.getScreenX(), e.getScreenY());
						}
					}
				} else if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLInterfaceSymbol) {
					UMLInterfaceSymbol toEdit = (UMLInterfaceSymbol) resolveUMLSymbolParent((Node) e.getTarget());
					documentViewPane.setSelectedUMLSymbol(toEdit);
					if (toEdit.isSelected()) {
						if (e.getClickCount() == 2) {
							toEdit.setEditableUMLInterfaceSymbol();
						}
						if (e.getButton().equals(e.getButton().SECONDARY)) {
							test.show(documentViewPane, e.getScreenX(), e.getScreenY());
						}
					}
				} else if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLUseCaseSymbol) {
					UMLUseCaseSymbol toEdit = (UMLUseCaseSymbol) resolveUMLSymbolParent((Node) e.getTarget());
					documentViewPane.setSelectedUMLSymbol(toEdit);
					if (toEdit.isSelected()) {
						if (e.getClickCount() == 2) {
							toEdit.setEditableUMLUseCaseSymbol();
						}
						if (e.getButton().equals(e.getButton().SECONDARY)) {
							test.show(documentViewPane, e.getScreenX(), e.getScreenY());
						}
					}
				} else if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLUserSymbol) {
					if (e.getButton().equals(e.getButton().SECONDARY)) {
						test.show(documentViewPane, e.getScreenX(), e.getScreenY());
					}
				}
				
				break;
			case CREATE_INTERFACE:
				
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLInterfaceSymbol i = new UMLInterfaceSymbol(new Point2D(e.getX() -85, e.getY() -40));
					documentViewPane.addUMLSymbol(i);
					documentViewPane.setSelectedUMLSymbol(i);
				}
				
				break;
			case CREATE_USER:
				
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLUserSymbol u = new UMLUserSymbol(new Point2D(e.getX(),e.getY()));
					documentViewPane.addUMLSymbol(u);
					documentViewPane.setSelectedUMLSymbol(u);
				}
				
				break;
			case CREATE_USE_CASE:
			
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLUseCaseSymbol use = new UMLUseCaseSymbol(new Point2D(e.getX(), e.getY()));
					documentViewPane.addUMLSymbol(use);
					documentViewPane.setSelectedUMLSymbol(use);
				}
				
				break;
			case DELETE:
				documentViewPane.setSelectedUMLSymbol(null);
				UMLSymbol toDelete = resolveUMLSymbolParent((Node)e.getTarget());
				if (toDelete != null) {
					documentViewPane.removeUMLSymbol(toDelete);
					documentViewPane.getChildren().remove(toDelete);
					
					// destroy event, since target object is now removed
					e.consume();
				}
				break;
			default:
				break;
			}
		} else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case SELECT:
				if (!isMoving) {
					dragTarget = resolveUMLObjectSymbolParent((Node)e.getTarget());
					
					if (dragTarget != null) { // begin dragging object
						documentViewPane.setSelectedUMLSymbol(dragTarget);
						
						// compute offsets, so drag does not snap to origin
						dragOffsetX = ((UMLObjectSymbol)documentViewPane.getSelectedUMLSymbol().getValue()).getX() - e.getX();
						dragOffsetY = ((UMLObjectSymbol)documentViewPane.getSelectedUMLSymbol().getValue()).getY() - e.getY();
						
						// set state
						isMoving = true;
					}
				} else {
					dragTarget.setLayoutX(e.getX() + dragOffsetX);
					dragTarget.setLayoutY(e.getY() + dragOffsetY);
					//TODO: this could be made automatic with binding
					documentViewPane.refreshRelations((UMLObjectSymbol) documentViewPane.getSelectedUMLSymbol().getValue());
				}
				break;
			case CREATE_ASSOCIATION:
			case CREATE_AGGREGATION:
			case CREATE_COMPOSITION:
			case CREATE_GENERALIZATION:
			case CREATE_DEPENDENCY:
				if (!isRelating) {
					
					dragTarget = resolveUMLObjectSymbolParent((Node)e.getTarget());
					isRelating  = (dragTarget != null);
				}
				break;
			default:
				isMoving = false;
				isRelating = false;
				dragTarget = null;
			}
		} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case CREATE_ASSOCIATION:
			case CREATE_DEPENDENCY:
			case CREATE_AGGREGATION:
			case CREATE_COMPOSITION:
			case CREATE_GENERALIZATION:
				if (isRelating) {
					UMLObjectSymbol dragRelease = resolveUMLObjectSymbolParent((Node)e.getPickResult().getIntersectedNode());
					if (dragRelease != null) {
						documentViewPane.addUMLSymbol(
								new UMLRelationSymbol(dragTarget, 
										dragRelease, 
										toolbarPane.getCurrentEditorMode().getValue().getRelationType()));
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
	 * Utility to traverse scene graph and identify ancestor UMLSymbol
	 * @author Merv Fansler
	 * @param target initial node to test for ancestor UMLSymbol
	 * @return UMLSymbol ancestor, otherwise null
	 */
	private UMLSymbol resolveUMLSymbolParent(Node target) {
		UMLSymbol result;
		
		if (target != null) {
			if (target instanceof UMLSymbol) {
				result = (UMLSymbol)target;
			} else if (target instanceof DocumentViewPane) {
				result = null;
			} else {
				result = resolveUMLSymbolParent(target.getParent());
			}
		} else {
			result = null;
		}
		
		return result;
	}
	
	/**
	 * Utility to traverse scene graph and identify ancestor UMLObjectSymbol
	 * @author Merv Fansler
	 * @param target initial node to test for ancestor UMLObjectSymbol
	 * @return UMLSymbol ancestor, otherwise null
	 */
	private UMLObjectSymbol resolveUMLObjectSymbolParent(Node target) {
		UMLObjectSymbol result;
		
		if (target != null) {
			if (target instanceof UMLObjectSymbol) {
				result = (UMLObjectSymbol)target;
			} else if (target instanceof DocumentViewPane) {
				result = null;
			} else {
				result = resolveUMLObjectSymbolParent(target.getParent());
			}
		} else {
			result = null;
		}
		
		return result;
	}
	
}