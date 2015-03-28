/**
 * @author Kevin Fisher
 * @author Joe Martello
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.model;

import java.util.Arrays;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UMLClassSymbol extends UMLObjectSymbol {
	private VBox classBox, attributes, operations;
	private TextField name;
	
	/**
	 * UMLObjectSymbol Constructor displaying UMLClassSymbol location and attributes
	 * This constructor assumes that the width and height are large enough to encompass all of its attributes
	 * @param umlClassSymbol that will be displayed
	 */
	public UMLClassSymbol(Point2D origin, double height, double width){
		super(origin, height, width);
		
		classBox = new VBox();
		classBox.setStyle("-fx-border-color: #000; -fx-background-color: white; -fx-width: 100%; -fx-height: 100%;");
		
		name = new TextField();
		name.textProperty().bindBidirectional(identifier);
		name.setStyle("-fx-border-color: white");
		name.setMouseTransparent(true);
		name.setFocusTraversable(false);
		
		attributes = new VBox();
		operations = new VBox();
		
		Arrays.asList("+attribute1:type","+attribute2:type").forEach(a -> addAttribute(a));
		Arrays.asList("-operation():type","-operation2():type").forEach(f -> addOperation(f));
		
		Separator s1 = new Separator(),
				  s2 = new Separator();
		s1.setStyle("-fx-background-color: #000");
		s2.setStyle("-fx-background-color: #000");
		
		classBox.getChildren().addAll(name,s1,attributes,s2,operations);
		this.getChildren().add(classBox);
	}
	
	private void addOperation(String operationName) {
		TextField t = new TextField(operationName);
		t.setStyle("-fx-border-color: white");
		t.setMouseTransparent(true);
		t.setFocusTraversable(false);
		operations.getChildren().add(t);
	}

	private void addAttribute(String attributeName) {
		TextField t = new TextField(attributeName);
		t.setStyle("-fx-border-color: white");
		t.setMouseTransparent(true);
		t.setFocusTraversable(false);
		attributes.getChildren().add(t);
	}

	@Override
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = super.getFields();
		attributes.getChildren().forEach(a -> {
			if (a instanceof TextField) {
				fields.add(((TextField)a).textProperty());
			}
		});
		operations.getChildren().forEach(a -> {
			if (a instanceof TextField) {
				fields.add(((TextField)a).textProperty());
			}
		});
		return fields;
	}
}
