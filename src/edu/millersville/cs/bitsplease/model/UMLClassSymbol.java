/**
 * @author Kevin Fisher
 * @author Joe Martello
 * @author Merv Fansler
 * @author Josh Wakefield
 * @since February 25, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	 * Default constructor for externalization purposes
	 */
	public UMLClassSymbol(){
		super();
		
		classBox = new VBox();
		classBox.setStyle("-fx-border-color: #000; -fx-background-color: white; -fx-width: 100%; -fx-height: 100%;");
		
		name = new TextField();
		name.textProperty().bindBidirectional(identifier);
		name.setStyle("-fx-border-color:white;");
		name.setMouseTransparent(true);
		name.setFocusTraversable(false);
		
		attributes = new VBox();
		operations = new VBox();
		
		Separator s1 = new Separator(),
				  s2 = new Separator();
		s1.setStyle("-fx-background-color: #000;");
		s2.setStyle("-fx-background-color: #000;");
		
		classBox.getChildren().addAll(name,s1,attributes,s2,operations);
		this.getChildren().add(classBox);
		
				  
	}
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
	
	/**
	 * 
	 * @param operationName 
	 */
	public void addOperation(String operationName) {
		TextField t = new TextField(operationName);
		t.setStyle("-fx-border-color: white");
		t.setMouseTransparent(true);
		t.setFocusTraversable(false);
		operations.getChildren().add(t);
	}
	
	public void setOperations(ArrayList<String> ops){
		ops.forEach(op -> {
			addOperation(op);
		});
	}
	
	/**
	 * 
	 * @param attributeName
	 */
	public void addAttribute(String attributeName) {
		TextField t = new TextField(attributeName);
		t.setStyle("-fx-border-color: white");
		t.setMouseTransparent(true);
		t.setFocusTraversable(false);
		attributes.getChildren().add(t);
	}
	
	public void setAttributes(ArrayList<String> attr){
		attr.forEach(a -> {
			addAttribute(a);
		});
	}

	/**
	 * This method sets all TextFields in the UMLClassSymbol to editable.
	 */
	public void setEditableUMLSymbol() {
		name.setMouseTransparent(false);
		
		attributes.getChildren().forEach(attr -> {
			if (attr instanceof TextField) {
				attr.setMouseTransparent(false);
			}
		});
		
		operations.getChildren().forEach(oper -> {
			if (oper instanceof TextField) {
				oper.setMouseTransparent(false);
			}
		});
	}

	/**
	 * This method sets all TextFields in the UMLClassSymbol to non-editable.
	 */
	public void setNonEditableUMLSymbol() {
		name.setMouseTransparent(true);
			
		attributes.getChildren().forEach(attr -> {
			if (attr instanceof TextField) {
				attr.setMouseTransparent(true);
			}
		});
			
		operations.getChildren().forEach(oper -> {
			if (oper instanceof TextField) {
				oper.setMouseTransparent(true);
			}
		});
	}
	
	/**
	 * 
	 * @return An iterable list of the fields contained within the class
	 */
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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(identifier.getValue());
		out.writeDouble(getX());
		out.writeDouble(getY());
		out.writeDouble(getPrefHeight());
		out.writeDouble(getPrefWidth());
		
		List<String> attr = new ArrayList<String>();
		attributes.getChildren().forEach(a -> {
			attr.add(((TextField)a).textProperty().getValue());	
		});
		
		System.out.println("Attr array size : " + attr.size());
		System.out.println("Attribute 1: " + attr.get(0));
		System.out.println("Attribute 2: " + attr.get(1));
		
		out.writeObject(attr);
		
		List<String> ops = new ArrayList<String>();
		
		operations.getChildren().forEach(op -> {
			
				ops.add(((TextField)op).textProperty().getValue());
		});
		
		System.out.println("Ops array size: " + ops.size());
		System.out.println("Operation 1: " + ops.get(0));
		System.out.println("Operation 2: " + ops.get(1));
		
		out.writeObject(ops);
	
		
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		identifier.setValue((String)in.readObject());
		double x = in.readDouble();
		double y = in.readDouble();
		setOrigin(new Point2D(x,y));
		
		setPrefHeight(in.readDouble());
		setPrefWidth(in.readDouble());
		
		@SuppressWarnings("unchecked")
		ArrayList<String> attr = (ArrayList<String>)in.readObject();
		System.out.println("Loaded in attr array, size: " + attr.size());
		System.out.println("Attributes: ");
		for(String s : attr){
			System.out.println(s);
		}
		setAttributes(attr);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> ops = (ArrayList<String>)in.readObject();
		System.out.println("Loaded in ops array, size: " + ops.size());
		System.out.println("Operations: ");
		for(String op : ops){
			System.out.println(op);
		}
		setOperations(ops);
		
		
		
		
	}
}
