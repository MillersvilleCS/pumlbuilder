/***
 * @author Kevin Fisher
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;

public class RelationViewPane extends VBox{
	
	private Label rvpLabel = new Label("Current Relation");
	private Label sourceLabel = new Label("Source Object");
	private Label targetLabel = new Label("Target Object");
	private Label relTypeLabel = new Label("Relation Type");
	
	private TextField sourceField = new TextField();
	private TextField targetField = new TextField();
	private TextField relTypeField = new TextField();
	
	public RelationViewPane(){
		setAlignment(Pos.TOP_CENTER);
		getChildren().addAll(rvpLabel, sourceLabel, sourceField, targetLabel, targetField, relTypeLabel, relTypeField);
	}
	
	public String getSourceClassName(){
		return sourceField.getText();
	}
	
	public String getTargetClassName(){
		return targetField.getText();
	}
	
	public String getRelTypeField(){
		return relTypeField.getText();
	}
	
	public void setSourceField(String source){
		sourceField.setText(source);
	}
	
	public void setTargetField(String target){
		targetField.setText(target);
	}
	
	public void setrelTypeField(String relType){
		relTypeField.setText(relType);
	}

}
