/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

public class UMLEditorPane extends BorderPane {

	public UMLEditorPane() {
		super();
		
		this.setTop(createMenu());
		this.setLeft(new ToolBarPane());
		this.setRight(new PropertiesPane());
		this.setCenter(new DocumentViewPane());
	}
	
	private MenuBar createMenu() {
		MenuBar menuBar = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");
		
		menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		
		return menuBar;
	}
	
}
