/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/***
 * Primary GUI component where all user interact occurs. All other view
 * components are owned by UMLEditorPane.
 */
public class UMLEditorPane extends BorderPane {

	private ToolbarPane toolbarPane;
	private DocumentViewPane documentViewPane;
	private PropertiesPane propertiesPane;
	
	public UMLEditorPane() {
		super();
		
		documentViewPane = new DocumentViewPane();
		this.setCenter(documentViewPane);
		
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
}
