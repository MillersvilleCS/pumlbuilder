/**
 * @author Merv Fansler	Kevin Fisher
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import edu.millersville.cs.bitsplease.model.UMLSymbol;

public class PropertiesPane extends StackPane {
	private ObjectViewPane objectPane;
	private RelationViewPane relationPane;
	private VBox activePane;
	private Label paneTitle = new Label("Properties");
	/**
	 * 
	 */
	public PropertiesPane() {
		super();
		
		setAlignment(paneTitle, Pos.TOP_CENTER);
		getChildren().add(paneTitle);
		createObjectPane();
		createRelationPane();
		setActivePane(objectPane);
		setActivePane(relationPane);
		

		
		
		this.setStyle("-fx-background-color: #444; -fx-padding: 20");
	}
	
	/**
	 * Method to create Object Properties pane and add it to the Stack Pane
	 */
	public void createObjectPane(){
		objectPane = new ObjectViewPane();
		setMargin(objectPane, new Insets(20,20,20,20));
		getChildren().add(objectPane);
		objectPane.setVisible(false);
	}
	
	public void createRelationPane(){
		relationPane = new RelationViewPane();
		setMargin(relationPane, new Insets(20,20,20,20));
		getChildren().add(relationPane);
		relationPane.setVisible(false);
	}
	
	public void updatePane(UMLSymbol uml){
		//TODO implement action Listener
	}
	
	public VBox getActivePane(){
		return this.activePane;
	}
	
	public void setActivePane(VBox pane){
		if(activePane == null){
			activePane = pane;
			activePane.setVisible(true);
		}else if(activePane != pane){
			activePane.setVisible(false);
			activePane = pane;
			activePane.setVisible(true);
		}else{
			System.out.println("Selected pane already active");
		}
	}

}
