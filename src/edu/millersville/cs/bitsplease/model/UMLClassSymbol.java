/**
 * @author Kevin Fisher
 * @author Joe Martello
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class UMLClassSymbol extends UMLObjectSymbol {
	private VBox umlVBox;
	private Line ltop;
	private Line lbot;
	private TextField name;
	private List<TextField> attributes;
	private List<TextField> functions;
	
	/**
	 * UMLObjectSymbol Constructor displaying UMLClassSymbol location and attributes
	 * This constructor assumes that the width and height are large enough to encompass all of its attributes
	 * @param umlClassSymbol that will be displayed
	 */
	public UMLClassSymbol(Point2D origin, double height, double width){
		super(origin, height, width);
		
		umlVBox = new VBox();
		umlVBox.setStyle("-fx-border-color: #000; -fx-background-color: white; -fx-width: 100%; -fx-height: 100%");
		this.getChildren().add(umlVBox);
		
		name = new TextField();
		name.textProperty().bindBidirectional(identifier);
		name.setStyle("-fx-border-color: white");
		name.setMouseTransparent(true);
		umlVBox.getChildren().add(name);
		
		ltop = new Line();
		ltop.endXProperty().bind(umlVBox.widthProperty());
		umlVBox.getChildren().add(ltop);
		
		attributes = new ArrayList<TextField>();
		for (String s: Arrays.asList("Attribute1","Attribute2")){
			TextField t = new TextField(s);
			t.setStyle("-fx-border-color: white; -fx-highlight-fill: none");
			t.setMouseTransparent(true);
			attributes.add(t);
		}
		umlVBox.getChildren().addAll(attributes);
		
		lbot = new Line();
		lbot.endXProperty().bind(umlVBox.widthProperty());
		umlVBox.getChildren().add(lbot);
		
		functions = new ArrayList<TextField>();
		for (String s: Arrays.asList("Operation1()","Operation2()")){
			TextField t = new TextField(s);
			t.setStyle("-fx-border-color: white");
			t.setMouseTransparent(true);
			functions.add(t);
		}
		umlVBox.getChildren().addAll(functions);
	}
	
	@Override
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = super.getFields();
		attributes.forEach(a -> fields.add(a.textProperty()));
		functions.forEach(f -> fields.add(f.textProperty()));
		return fields;
	}
}
