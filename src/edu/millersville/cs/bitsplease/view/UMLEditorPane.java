/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;

/***
 * Primary GUI component where all user interact occurs. All other view
 * components are owned by UMLEditorPane.
 */
public class UMLEditorPane extends BorderPane implements EventHandler<MouseEvent> {

	private ToolbarPane toolbarPane;
	private DocumentViewPane documentViewPane;
	private PropertiesPane propertiesPane;
	
	// Drag State Variables
	private Boolean isMoving = false;
	private UMLObjectView dragTarget;
	private double dragOffsetX = 0.0;
	private double dragOffsetY = 0.0;

	private boolean isRelating = false;
	
	public UMLEditorPane() {
		super();
		
		documentViewPane = new DocumentViewPane();
		this.setCenter(documentViewPane);
		documentViewPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		documentViewPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
		documentViewPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
		
		this.setTop(createMenu());
		
		toolbarPane = new ToolbarPane();
		this.setLeft(toolbarPane);
		
		propertiesPane = new PropertiesPane();
		this.setRight(propertiesPane);
	}
	
	private MenuBar createMenu() {
		MenuBar menuBar = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				System.exit(0);
			}
		});
		
		fileMenu.getItems().add(exit);
		Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");
		
		menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		
		return menuBar;
	}
	
	public ToolbarPane getToolBarPane() {
		return toolbarPane;
	}
	
	public DocumentViewPane getDocumentViewPane() {
		return documentViewPane;
	}
	
	public PropertiesPane getPropertiesPane() {
		return propertiesPane;
	}
	
	/** 
	 * Provides MouseEvent handling for panes
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(MouseEvent e) {
		
		if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case CREATE_CLASS:

				UMLClassSymbol c = new UMLClassSymbol(new Point2D(e.getX(),e.getY()), 100, 100);
				UMLObjectView objView = new UMLObjectView(c);
				documentViewPane.addUMLSymbol(objView);
				documentViewPane.setSelectedUMLSymbol(objView.getUmlClassSymbol());
				
				break;
			case SELECT: // selecting items
				documentViewPane.setSelectedUMLSymbol(resolveUMLSymbolObject((Node) e.getTarget()));
				
				break;
			case DELETE:
				documentViewPane.setSelectedUMLSymbol(null);
				UMLSymbolView toDelete = resolveUMLSymbolView((Node)e.getTarget());
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
					dragTarget = (UMLObjectView) resolveUMLSymbolView((Node)e.getTarget());
					
					if (dragTarget != null) { // begin dragging object
						documentViewPane.setSelectedUMLSymbol(dragTarget.getUmlClassSymbol());
						
						// compute offsets, so drag does not snap to origin
						dragOffsetX = ((UMLObjectSymbol)documentViewPane.getSelectedUMLSymbol().getValue()).getX() - e.getX();
						dragOffsetY = ((UMLObjectSymbol)documentViewPane.getSelectedUMLSymbol().getValue()).getY() - e.getY();
						
						// set state
						isMoving = true;
					}
				} else {
					((UMLClassSymbol) documentViewPane.getSelectedUMLSymbol().getValue()).setOrigin(new Point2D(e.getX() + dragOffsetX, e.getY() + dragOffsetY));
					dragTarget.refreshSymbolPosition();
					propertiesPane.updatePane(documentViewPane.getSelectedUMLSymbol().getValue());
					documentViewPane.refreshRelations((UMLObjectSymbol) documentViewPane.getSelectedUMLSymbol().getValue());
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
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case CREATE_ASSOCIATION:
				if (isRelating) {
					UMLSymbol dragRelease = resolveUMLSymbolObject((Node)e.getPickResult().getIntersectedNode());
					if (dragRelease != null && dragRelease instanceof UMLClassSymbol) {
						UMLRelationSymbol relation = new UMLRelationSymbol(
								dragTarget.getUmlClassSymbol(), 
								(UMLObjectSymbol) dragRelease, 
								UMLRelationType.ASSOCIATION);
						documentViewPane.addUMLSymbol(new UMLRelationView(relation));
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
						documentViewPane.addUMLSymbol(new UMLRelationView(relation));
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
