/**
 * @author Merv Fansler	
 * @author Kevin Fisher
 * @since  February 24, 2015
 * @version 0.1.0
 * 
 */

package edu.millersville.cs.bitsplease.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;


public class PropertiesPane extends StackPane {
	
	private ObjectViewPane objectPane;
	private RelationViewPane relationPane;
	private Label paneTitle = new Label("Properties");
	
	//State variables
	private VBox activePane;
	
	/**
	 * Constructor
	 */
	public PropertiesPane() {
		
		super();
		
		setAlignment(paneTitle, Pos.TOP_CENTER);
		getChildren().add(paneTitle);
		createObjectPane();
		createRelationPane();
		setActivePane(objectPane);
		
		this.setStyle("-fx-background-color: #aaa; -fx-padding: 20; -fx-text-fill: white; -fx-font-weight: bold");
	}
	
	/**
	 * Method to create Object Properties pane and add it to the Properties Pane
	 */
	public void createObjectPane(){
		objectPane = new ObjectViewPane();
		setMargin(objectPane, new Insets(20));
		getChildren().add(objectPane);
		objectPane.setVisible(false);
	}
	
	/**
	 * Method to create Relation Properties pane and add it to the Properties Pane
	 */
	public void createRelationPane(){
		relationPane = new RelationViewPane();
		setMargin(relationPane, new Insets(20,20,20,20));
		getChildren().add(relationPane);
		relationPane.setVisible(false);
	}
	
	/**
	 * Method to handle updating the properties pane based on the currently selected UMLSymbol.
	 * WARNING: Spaghetti code ahead.
	 * @param uml Currently selected UMLSymbol object passed in by the GUIController
	 */
	public void updatePane(UMLSymbol uml){
		//TODO add support for Interface, etc., clean up terrible terrible code
		
		if(uml instanceof UMLClassSymbol){
			
			objectPane.setObjectNameField(((UMLClassSymbol)uml).getName());
			objectPane.setXField(((UMLClassSymbol)uml).getX());
			objectPane.setYField(((UMLClassSymbol)uml).getY());
			objectPane.setHField(((UMLClassSymbol)uml).getHeight());
			objectPane.setWField(((UMLClassSymbol)uml).getWidth());
			setActivePane(objectPane);
			
		}else if( uml instanceof UMLRelationSymbol){
			relationPane.setSourceField(((UMLRelationSymbol)uml).getSourceObject().getName());
			relationPane.setTargetField(((UMLRelationSymbol)uml).getTargetObject().getName());
			setActivePane(relationPane);
		}else{
			setActivePaneVisible(false);//UMLSymbol is null
		}
	}
	
	/**
	 * 
	 * @return the View Pane that is currently active
	 */
	public VBox getActivePane(){
		return this.activePane;
	}
	/**
	 * Method to handle switching focus between pane objects
	 * @param pane the View pane to be set as the active pane
	 */
	public void setActivePane(VBox pane){
		if(activePane == null){
			activePane = pane;
			activePane.setVisible(true);
		}else if(activePane != pane){
			activePane.setVisible(false);
			activePane = pane;
			activePane.setVisible(true);
		}else{
			activePane.setVisible(true);
		}
	}
	
	public void setActivePaneVisible(boolean vis){
		if(activePane != null){
		activePane.setVisible(vis);
		}
	}

}
