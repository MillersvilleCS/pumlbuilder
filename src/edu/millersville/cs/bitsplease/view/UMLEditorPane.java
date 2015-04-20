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

import org.fxmisc.undo.UndoManager;
import org.fxmisc.undo.UndoManagerFactory;
import org.reactfx.Change;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLInterfaceSymbol;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.model.UMLUseCaseSymbol;
import edu.millersville.cs.bitsplease.model.UMLUserSymbol;

/***
 * Primary GUI component where all user interact occurs. All other view
 * components are owned by UMLEditorPane.
 */
public class UMLEditorPane extends BorderPane implements EventHandler<MouseEvent> {
	
	// Subcomponents
	private ToolbarPane toolbarPane;
	private DocumentViewPane documentViewPane;
	private PropertiesPane propertiesPane;
	private UndoManager undoManager;
	
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
		
		// track changes of selected UMLSymbol
		EventStream<Change<UMLSymbol>> selectedStream = EventStreams.changesOf(documentViewPane.getSelectedUMLSymbol());
		undoManager = UndoManagerFactory.unlimitedHistoryUndoManager(
				selectedStream,
				c -> documentViewPane.setSelectedUMLSymbol(c.getNewValue()),
				c -> documentViewPane.setSelectedUMLSymbol(c.getOldValue())
				);
		
		// create and add Menu
		UMLMenu pumlMenu = new UMLMenu();
		pumlMenu.setDocument(documentViewPane);
		pumlMenu.setUndoManager(undoManager);
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
	private ContextMenu createEditingContextMenu() {
		
ContextMenu editingContextMenu = new ContextMenu();
		
		//create generic contextMenu items
		//MenuItem cut = new MenuItem("Cut");
		//MenuItem copy = new MenuItem("Copy");
		//MenuItem paste = new MenuItem("Paste");
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(event -> { 
			documentViewPane.removeUMLSymbol(documentViewPane.getSelectedUMLSymbol().getValue());
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event -> { editingContextMenu.hide(); });
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
			editingContextMenu.getItems().addAll(delete, new SeparatorMenuItem(), addAttr, deleteAttr, new SeparatorMenuItem(),
					addOper, deleteOper, new SeparatorMenuItem(), exit);
		
		} else {
		
			//add all items to the contextMenu for non-UMLClassSymbol's
			editingContextMenu.getItems().addAll(delete, new SeparatorMenuItem(), exit);
			
		}
		
		return editingContextMenu;
	}
	
	private ContextMenu createObjsContextMenu(MouseEvent e) {
		ContextMenu createObjContextMenu = new ContextMenu();
		
		//create contextMenu items
		MenuItem createClass = new MenuItem("Create Class");
		createClass.setOnAction(event -> {
			UMLClassSymbol c = new UMLClassSymbol(new Point2D(e.getX()-85,e.getY()-60), 100, 100);
			documentViewPane.addUMLSymbol(c);
			documentViewPane.setSelectedUMLSymbol(c);
		});
		
		MenuItem createInterface = new MenuItem("Create Interface");
		createInterface.setOnAction(event -> {
			UMLInterfaceSymbol i = new UMLInterfaceSymbol(new Point2D(e.getX()-85, e.getY()-30));
			documentViewPane.addUMLSymbol(i);
			documentViewPane.setSelectedUMLSymbol(i);
		});
		
		MenuItem createUseCase = new MenuItem("Create Use Case");
		createUseCase.setOnAction(event -> {
			UMLUseCaseSymbol use = new UMLUseCaseSymbol(new Point2D(e.getX()-85, e.getY()-30));
			documentViewPane.addUMLSymbol(use);
			documentViewPane.setSelectedUMLSymbol(use);
		});
		
		MenuItem createUser = new MenuItem("Create User");
		createUser.setOnAction(event -> {
			UMLUserSymbol u = new UMLUserSymbol(new Point2D(e.getX()-45,e.getY()-15));
			documentViewPane.addUMLSymbol(u);
			documentViewPane.setSelectedUMLSymbol(u);
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event -> { createObjContextMenu.hide(); });
		exit.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
		
		createObjContextMenu.getItems().addAll(createClass, new SeparatorMenuItem(),
				createInterface, new SeparatorMenuItem(), createUseCase, 
				new SeparatorMenuItem(), createUser, new SeparatorMenuItem(), exit);
		
		return createObjContextMenu;
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
		
		ContextMenu createObjsContextMenu = createObjsContextMenu(e);
		
		if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case CREATE_CLASS:
				
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLClassSymbol c = new UMLClassSymbol(new Point2D(e.getX()-85,e.getY()-60), 100, 100);
					documentViewPane.addUMLSymbol(c);
					documentViewPane.setSelectedUMLSymbol(c);
				} else {
					createObjsContextMenu.show(documentViewPane, e.getScreenX(), e.getScreenY());
				}
				
				break;
			case SELECT: // selecting items
				ContextMenu editingContextMenu = createEditingContextMenu();
				documentViewPane.setSelectedUMLSymbol(resolveUMLSymbolParent((Node) e.getTarget()));
				
				if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLClassSymbol) {
					UMLClassSymbol toEdit = (UMLClassSymbol) resolveUMLSymbolParent((Node) e.getTarget());
					documentViewPane.setSelectedUMLSymbol(toEdit);
					if (toEdit.isSelected()) {
						if (e.getClickCount() == 2) {
							toEdit.setEditableUMLClassSymbol();
						}
						if (e.getButton().equals(e.getButton().SECONDARY)) {
							editingContextMenu.show(toEdit, e.getScreenX(), e.getScreenY());
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
							editingContextMenu.show(toEdit, e.getScreenX(), e.getScreenY());
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
							editingContextMenu.show(toEdit, e.getScreenX(), e.getScreenY());
						}
					}
				} else if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLUserSymbol) {
					if (e.getButton().equals(e.getButton().SECONDARY)) {
						editingContextMenu.show(documentViewPane.getSelectedUMLSymbol().getValue(), e.getScreenX(), e.getScreenY());
					}
				} else if (e.getTarget().equals(documentViewPane)) {
					if (e.getButton().equals(e.getButton().SECONDARY)) {
						createObjsContextMenu.show(documentViewPane, e.getScreenX(), e.getScreenY());
					}
				}
				break;
			case CREATE_INTERFACE:
				
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLInterfaceSymbol i = new UMLInterfaceSymbol(new Point2D(e.getX() -85, e.getY() -40));
					documentViewPane.addUMLSymbol(i);
					documentViewPane.setSelectedUMLSymbol(i);
				} else {
					createObjsContextMenu.show(documentViewPane, e.getScreenX(), e.getScreenY());
				}
				
				break;
			case CREATE_USER:
				
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLUserSymbol u = new UMLUserSymbol(new Point2D(e.getX()-50,e.getY()-20));
					documentViewPane.addUMLSymbol(u);
					documentViewPane.setSelectedUMLSymbol(u);
				} else {
					createObjsContextMenu.show(documentViewPane, e.getScreenX(), e.getScreenY());
				}
				
				break;
			case CREATE_USE_CASE:
			
				if (e.getButton().equals(e.getButton().PRIMARY)) {
					UMLUseCaseSymbol use = new UMLUseCaseSymbol(new Point2D(e.getX()-85, e.getY()-30));
					documentViewPane.addUMLSymbol(use);
					documentViewPane.setSelectedUMLSymbol(use);
				} else {
					createObjsContextMenu.show(documentViewPane, e.getScreenX(), e.getScreenY());
				}
				
				break;
			case DELETE:
				documentViewPane.setSelectedUMLSymbol(null);
				UMLSymbol toDelete = resolveUMLSymbolParent((Node)e.getTarget());
				if (toDelete != null) {
					if (e.getButton().equals(e.getButton().PRIMARY)) {
						documentViewPane.removeUMLSymbol(toDelete);
						documentViewPane.getChildren().remove(toDelete);
					}
					
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
					if (e.getButton().equals(e.getButton().PRIMARY)) {
						if (dragTarget != null) { // begin dragging object
							documentViewPane.setSelectedUMLSymbol(dragTarget);
							
							// compute offsets, so drag does not snap to origin
							dragOffsetX = ((UMLObjectSymbol)documentViewPane.getSelectedUMLSymbol().getValue()).getX() - e.getX();
							dragOffsetY = ((UMLObjectSymbol)documentViewPane.getSelectedUMLSymbol().getValue()).getY() - e.getY();
							
							// set state
							isMoving = true;
						}
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
					if (e.getButton().equals(e.getButton().PRIMARY)) {
						dragTarget = resolveUMLObjectSymbolParent((Node)e.getTarget());
						isRelating  = (dragTarget != null);
					}
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