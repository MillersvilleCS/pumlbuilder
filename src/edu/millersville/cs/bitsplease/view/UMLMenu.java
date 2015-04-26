/**
 * @author Kevin Fisher
 * @since  April 9, 2015
 * @version 0.2.5
 */
package edu.millersville.cs.bitsplease.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;

import org.fxmisc.undo.UndoManager;

import edu.millersville.cs.bitsplease.PUMLBuilder;
import edu.millersville.cs.bitsplease.model.UMLSymbol;

public class UMLMenu extends MenuBar {
	
	private DocumentViewPane document;
	private UndoManager undoManager;
	private MenuItem undo, redo;
	
	public UMLMenu(){
		super();
		
		this.setUseSystemMenuBar(true);
		Menu fileMenu = new Menu("File");
		
		MenuItem newDoc = new MenuItem("New");
		newDoc.setOnAction(newAction -> {
			document.setSelectedUMLSymbol(null);
			document.removeAllSymbols();
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
			print();		
		});
		print.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
		
		MenuItem export = new MenuItem("Export...");
		export.setOnAction(ex ->{
			exportDocument();
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event ->{ System.exit(0); });
		exit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN,
					KeyCombination.SHIFT_DOWN ));
		
		fileMenu.getItems().addAll(newDoc,new SeparatorMenuItem(), open,save, 
				saveAs,new SeparatorMenuItem(),print, new SeparatorMenuItem(),
				export, new SeparatorMenuItem(), exit);
		
		Menu editMenu = new Menu("Edit");

		undo = new MenuItem("Undo");
		undo.setDisable(true);
		undo.setOnAction(event -> { undoManager.undo(); });
		undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
		
		redo = new MenuItem("Redo");
		redo.setDisable(true);
		redo.setOnAction(event -> { undoManager.redo(); });
		redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN));
		
		editMenu.getItems().addAll(undo,redo);
		
		Menu helpMenu = new Menu("Help");
		
		MenuItem about = new MenuItem("About");
		about.setOnAction(event -> { createAbout(); });
		helpMenu.getItems().add(about);
		
		this.getMenus().addAll(fileMenu, editMenu, helpMenu);
	}
	
	/**
	 * 
	 * @return The document that the menu performs operations on
	 */
	public DocumentViewPane getDocument(){
		return this.document;
	}
	
	/**
	 * 
	 * @param doc The document the menu will perform operations on
	 */
	public void setDocument(DocumentViewPane doc) {
		this.document = doc;
		
	}
	/**
	 * @return the undoManager
	 */
	public UndoManager getUndoManager() {
		return undoManager;
	}

	/**
	 * @param undoManager the undoManager to set
	 */
	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
		if (this.undoManager != null) {
			undo.disableProperty().bind(Bindings.not(this.undoManager.undoAvailableProperty()));
			redo.disableProperty().bind(Bindings.not(this.undoManager.redoAvailableProperty()));
		} else {
			undo.setDisable(false);
			redo.setDisable(false);
		}
	}

	/**
	 * Handles the creation of the About webview
	 */
	private void createAbout(){
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
	}

	/**
	 * Handles creation and execution of printing job to print Document
	 * @author Kevin Fisher
	 */
	private void print(){
	
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
				document.setSelectedUMLSymbol(null);
				document.getTransforms().add(new Scale(0.5, 0.5));
				if(job.printPage(document)){
					job.endJob();
				}
			}
		}
		document.getTransforms().clear();
		System.out.println("Print method reached this");
		
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
				
				o.writeObject(document.getEntities());
				o.close();
			}catch(IOException e){ e.printStackTrace();}
		}	
	}
	
	/**
	 * 
	 */
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
			document.removeAllSymbols();
			try{
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(fileToLoad));
	
				@SuppressWarnings("unchecked")
				ArrayList<UMLSymbol> loadedIn = (ArrayList<UMLSymbol>)in.readObject();
		
				document.setEntities(loadedIn);
				in.close();
		
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	
	/**
	 * 
	 */
	private void exportDocument(){
		
		WritableImage image = document.snapshot(new SnapshotParameters(), null);
		FileChooser fileHandler = new FileChooser();
		fileHandler.setTitle("Export Document");
		fileHandler.getExtensionFilters().add(new ExtensionFilter("PNG Image", "*.png"));
		
		File exportDoc = fileHandler.showSaveDialog(getScene().getWindow());
		
		if(exportDoc != null){
			
				if(!exportDoc.getPath().endsWith(".png")){
					exportDoc = new File(exportDoc.getPath()+ ".png");
				}
					try{
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", exportDoc);
					}catch(IOException ioe){
						
					}
		}
	}
}