/**
 * @author Merv Fansler
 * @author Kevin Fisher
 * @author Mike Sims
 * @author Josh Wakefield
 * @since February 19, 2015
 * @version 0.2.0
 */

package edu.millersville.cs.bitsplease.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import edu.millersville.cs.bitsplease.PUMLBuilder;
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
		this.setTop(createMenu());
		
		// create and add Toolbar Pane
		toolbarPane = new ToolbarPane();
		this.setLeft(toolbarPane);
		
		// create and add Properties Pane
		propertiesPane = new PropertiesPane(documentViewPane.getSelectedUMLSymbol());
		this.setRight(propertiesPane);
	}
	
	/**
	 * initialization method for MenuBar
	 * @return instance of MenuBar to be attached to scene
	 */
	private MenuBar createMenu() {
		MenuBar menuBar = new MenuBar();
		menuBar.setUseSystemMenuBar(true);
		
		Menu fileMenu = new Menu("File");
		
		MenuItem newDoc = new MenuItem("New");
		newDoc.setOnAction(newAction ->{
			documentViewPane.removeAllSymbols();
		});
		
		MenuItem open = new MenuItem("Open");
		open.setOnAction(event -> {
			
			FileChooser fileHandler = new FileChooser();
			fileHandler.getExtensionFilters().addAll(new ExtensionFilter("UML Document", "*.uml"),
					new ExtensionFilter("All Files", "*.*"));
			File fileToLoad = fileHandler.showOpenDialog(getScene().getWindow());
			
			if(fileToLoad == null){
				
				// System.out.println("Hey thanks for wasting my time, cool.");
				
			}else{
				//first remove all node currently occupying the Document View
				documentViewPane.removeAllSymbols();
				
				try{
					ObjectInputStream in = new ObjectInputStream(
							new FileInputStream(fileToLoad));
			
					@SuppressWarnings("unchecked")
					ArrayList<UMLSymbol> loadedIn = (ArrayList<UMLSymbol>)in.readObject();
			
					documentViewPane.setEntities(loadedIn);
					in.close();
			
				}catch(Exception e){e.printStackTrace();}
			}
		});
		open.setAccelerator(new KeyCodeCombination(KeyCode.O, 
				KeyCombination.SHORTCUT_DOWN));
		
		MenuItem save = new MenuItem("Save");
		save.setOnAction(event -> {
			FileChooser fileHandler = new FileChooser();
			fileHandler.getExtensionFilters().addAll(new ExtensionFilter("UML Document", "*.uml"),
						new ExtensionFilter("All Files", "*.*"));
			File fileToSave = fileHandler.showSaveDialog(getScene().getWindow());
			
			if(fileToSave == null){
				//System.out.println("Hey cool that's great.");
			}else{
			
				try{
					ObjectOutputStream o = new ObjectOutputStream(
							new FileOutputStream(fileToSave));
					
					o.writeObject(documentViewPane.getEntities());
					o.close();
				}catch(IOException e){ e.printStackTrace();}
			}	
		});
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
		
		MenuItem saveAs = new MenuItem("Save As");
		MenuItem print = new MenuItem("Print");
		print.setOnAction(event ->{ 
		
		});
		MenuItem exportAs = new MenuItem("Export As...");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event ->{ System.exit(0); });
		exit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN,
					KeyCombination.SHIFT_DOWN ));
		
		fileMenu.getItems().addAll(newDoc,new SeparatorMenuItem(), open,save, 
				saveAs,new SeparatorMenuItem(),print, new SeparatorMenuItem(),
				exportAs, new SeparatorMenuItem(), exit);
		
		Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");
		
		MenuItem about = new MenuItem("About");
		about.setOnAction(event -> {
				final Stage dialog = new Stage(StageStyle.UTILITY);
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(getScene().getWindow());
                dialog.setResizable(false);
                
                // create view for about content
                WebView aboutPage = new WebView();
                
                // load content into view
                String aboutURL = PUMLBuilder.class.getResource("/html/about.html").toExternalForm();
                aboutPage.getEngine().load(aboutURL);
                
                // load view into window
                Scene dialogScene = new Scene(aboutPage, 400, 300);
                dialog.setScene(dialogScene);
                
                // display window
                dialog.show();
			});
		helpMenu.getItems().add(about);
		
		menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		
		return menuBar;
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
	@Override
	public void handle(MouseEvent e) {
		
		if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
			switch (toolbarPane.getCurrentEditorMode().getValue()) {
			case CREATE_CLASS:

				UMLClassSymbol c = new UMLClassSymbol(new Point2D(e.getX(),e.getY()), 100, 100);
				documentViewPane.addUMLSymbol(c);
				documentViewPane.setSelectedUMLSymbol(c);
				
				break;
			case SELECT: // selecting items
				documentViewPane.setSelectedUMLSymbol(resolveUMLSymbolParent((Node) e.getTarget()));
				
				if (documentViewPane.getSelectedUMLSymbol().getValue() instanceof UMLClassSymbol) {
					UMLClassSymbol toEdit = (UMLClassSymbol) resolveUMLSymbolParent((Node) e.getTarget());
					documentViewPane.setSelectedUMLSymbol(toEdit);
					if (toEdit.isSelected()) {
						if (e.getClickCount() == 2) {
							toEdit.setEditableUMLSymbol();
						}
					}
				}
				
				break;
			case CREATE_INTERFACE:
					
				UMLInterfaceSymbol i = new UMLInterfaceSymbol(new Point2D(e.getX() -85, e.getY() -40));
				documentViewPane.addUMLSymbol(i);
				documentViewPane.setSelectedUMLSymbol(i);
				break;
			case CREATE_USER:
				UMLUserSymbol u = new UMLUserSymbol(new Point2D(e.getX(),e.getY()));
				documentViewPane.addUMLSymbol(u);
				documentViewPane.setSelectedUMLSymbol(u);
				break;
			case CREATE_USE_CASE:
				UMLUseCaseSymbol use = new UMLUseCaseSymbol(new Point2D(e.getX(), e.getY()));
				documentViewPane.addUMLSymbol(use);
				documentViewPane.setSelectedUMLSymbol(use);
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
