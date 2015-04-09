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

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;
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
		open.setOnAction(event -> {	loadDocument(); });
		open.setAccelerator(new KeyCodeCombination(KeyCode.O, 
				KeyCombination.SHORTCUT_DOWN));
		
		MenuItem save = new MenuItem("Save");
		save.setOnAction(event-> { saveDocument();});
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
		
		MenuItem saveAs = new MenuItem("Save As");
		
		MenuItem print = new MenuItem("Print");
		print.setOnAction(event ->{ 
			print(documentViewPane);		
		});
		print.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
		
		MenuItem export = new MenuItem("Export...");
		export.setOnAction(ex ->{
			exportDocument(documentViewPane);
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event ->{ System.exit(0); });
		exit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN,
					KeyCombination.SHIFT_DOWN ));
		
		fileMenu.getItems().addAll(newDoc,new SeparatorMenuItem(), open,save, 
				saveAs,new SeparatorMenuItem(),print, new SeparatorMenuItem(),
				export, new SeparatorMenuItem(), exit);
		
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
	 * Handles saving a UML document through FleChooser
	 * @author Kevin Fisher
	 */
	private void saveDocument(){
		FileChooser fileHandler = new FileChooser();
		fileHandler.getExtensionFilters().addAll(new ExtensionFilter("UML Document", "*.uml"),
					new ExtensionFilter("All Files", "*.*"));
		File fileToSave = fileHandler.showSaveDialog(getScene().getWindow());
		
		if(fileToSave == null){
				//User exited filechooser without saving the file
		}else {
			
			if(!fileToSave.getName().endsWith(".uml")){
				fileToSave = new File(fileToSave + ".uml");
			}
			
			try{
				ObjectOutputStream o = new ObjectOutputStream(
						new FileOutputStream(fileToSave));
				
				o.writeObject(documentViewPane.getEntities());
				o.close();
			}catch(IOException e){ e.printStackTrace();}
		}	
	}
	
	private void loadDocument(){
		FileChooser fileHandler = new FileChooser();
		fileHandler.getExtensionFilters().addAll(new ExtensionFilter("UML Document", "*.uml"),
				new ExtensionFilter("All Files", "*.*"));
		File fileToLoad = fileHandler.showOpenDialog(getScene().getWindow());
		
		if(fileToLoad == null){		
			// System.out.println("Hey thanks for wasting my time, cool.");
		
		//Only load .uml files	
		}else if(!fileToLoad.getName().endsWith(".uml")){
			Alert invalidFileType = new Alert(AlertType.ERROR);
			invalidFileType.setTitle("File Open Error");
			invalidFileType.setHeaderText("Invalid File Type");
			invalidFileType.setContentText("Hey next time try loading a file ending in .uml. \n Cool thanks.");
			
			invalidFileType.showAndWait();
			
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
	}
	/**
	 * Handles creation and execution of printing job to print Document
	 * @author Kevin Fisher
	 * @param node the node to be printed
	 */
	private void print(DocumentViewPane node){
	
		Printer printer = Printer.getDefaultPrinter();
		
		
		PrinterJob job = PrinterJob.createPrinterJob(printer);
		if(job == null){
			Alert noPrinterFound = new Alert(AlertType.ERROR);
			noPrinterFound.setTitle("Printer Error");
			noPrinterFound.setHeaderText("No Printer Found");
			noPrinterFound.setContentText("Hold on let me just mail it to you or something.");
			noPrinterFound.setGraphic(new ImageView(this.getClass().getResource("img/printer.png").toString()));
			noPrinterFound.showAndWait();
		}else{
			
			boolean showDialog = job.showPrintDialog(getScene().getWindow());
			
			if(showDialog){
				node.setSelectedUMLSymbol(null);
				//PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER,
					//	PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
				//double scaleX = pageLayout.getPrintableWidth() /
						//node.getBoundsInParent().getWidth();
				//double scaleY = pageLayout.getPrintableHeight() /
						//node.getBoundsInParent().getHeight();
				node.getTransforms().add(new Scale(0.5, 0.5));
				if(job.printPage(node)){
					
					job.endJob();
					//node.getTransforms().removeAll();
				
				}
			}
		}
		System.out.println("Print method reached this");
		
	}
	
	private void exportDocument(DocumentViewPane document){
		
		WritableImage image = document.snapshot(new SnapshotParameters(), null);
		FileChooser fileHandler = new FileChooser();
		fileHandler.setTitle("Export Document");
		fileHandler.getExtensionFilters().addAll(new ExtensionFilter("PNG Image", "*.png"),
				new ExtensionFilter("JPEG Image", "*.jpg"), new ExtensionFilter("PDF Image", "*.pdf"));
		File exportDoc = fileHandler.showSaveDialog(getScene().getWindow());
		
		if(exportDoc != null){
			
			switch(fileHandler.getSelectedExtensionFilter().getDescription()){
			case "PNG Image":
				if(!exportDoc.getPath().endsWith(".png")){
					exportDoc = new File(exportDoc.getPath()+ ".png");
					System.out.println(exportDoc.getPath());
					try{
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", exportDoc);
					}catch(IOException ioe){
						
					}
				}
					
				break;
			case "JPEG Image":
				System.out.println("Selected extension filter is jpeg");
				if(!exportDoc.getPath().endsWith(".jpg")){
					exportDoc = new File(exportDoc.getPath()+".jpg");
	
				}	
				break;
			case "PDF Image":
				if(!exportDoc.getPath().endsWith(".pdf")){
					exportDoc = new File(exportDoc.getPath() + ".pdf");
					System.out.println(exportDoc.getPath());
				}
				break;
			default:
				break;
			}
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
