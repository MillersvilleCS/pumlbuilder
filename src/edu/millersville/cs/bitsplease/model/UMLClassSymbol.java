/**
 * @author Kevin Fisher
 * @author Joe Martello
 * @author Merv Fansler
 * @author Josh Wakefield
 * @since February 25, 2015
 * @version 0.2.0
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
import javafx.geometry.Pos;
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
		name.setAlignment(Pos.CENTER);
		name.setStyle("-fx-border-color:white; -fx-font-weight: bold;");
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
		name.setStyle("-fx-border-color: white; -fx-font-weight: bold;");
		name.setAlignment(Pos.CENTER);
		name.setMouseTransparent(true);
		name.setFocusTraversable(false);
		
		attributes = new VBox();
		operations = new VBox();
		
		Arrays.asList("+attribute:type","+attribute:type").forEach(a -> addAttribute(a));
		Arrays.asList("-operation():type","-operation():type").forEach(f -> addOperation(f));
		
		Separator s1 = new Separator(),
				  s2 = new Separator();
		s1.setStyle("-fx-background-color: #000");
		s2.setStyle("-fx-background-color: #000");
		
		classBox.getChildren().addAll(name,s1,attributes,s2,operations);
		this.getChildren().add(classBox);
	}
	/**
	 * Add a Operation element to a UMLClassSymbol
	 * @param operationName Operation to add to the UMLCLassObject
	 */
	public void addOperation(String operationName) {
		TextField t = new TextField(operationName);
		t.setStyle("-fx-border-color: white;");
		t.setMouseTransparent(true);
		t.setFocusTraversable(false);
		operations.getChildren().add(t);
	}
	/**
	 * Set the operations of a class by passing in a list
	 * @param ops list of operations to set for the UMLClassSymbol
	 */
	public void setOperations(ArrayList<String> ops){
		ops.forEach(op -> {
			addOperation(op);
		});
	}
	
	/**
	 * Delete the last operation element from the UMLClassObject
	 */
	public void deleteOperation(){
		if (!operations.getChildren().isEmpty())
			operations.getChildren().remove(operations.getChildren().size() - 1);
	}	
	/**
	 * Add an attribute element to the UMLClassObject
	 * @param attributeName attribute to add to the UMLClassObject
	 */
	public void addAttribute(String attributeName) {
		TextField t = new TextField(attributeName);
		t.setStyle("-fx-border-color: white");
		t.setMouseTransparent(true);
		t.setFocusTraversable(false);
		attributes.getChildren().add(t);
	}	
	/**
	 * Set the attributes of a class by passing in a List
	 * @param attr List of attributes to set for the UMLClassObject
	 */
	public void setAttributes(ArrayList<String> attr){
		attr.forEach(a -> {
			addAttribute(a);
		});
	}
	/**
	 * Delete the last attribute element from the UMLClassObject
	 */
	public void deleteAttribute(){
		if (!attributes.getChildren().isEmpty())
			attributes.getChildren().remove(attributes.getChildren().size() - 1);
	}
	/**
	 * Set all TextFields in the UMLClassSymbol to editable.
	 */
	public void setEditableUMLClassSymbol() {
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
	 * Set all TextFields in the UMLClassSymbol to non-editable.
	 */
	public void setNonEditableUMLClassSymbol() {
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
	 * Get a list of all existing fields within a UMLClassSymbol
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
	/**
	 * Defines how this object can be written out to a file
	 * @param out the Object output stream used to write the object out to a file
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
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
		
		out.writeObject(attr);
		
		List<String> ops = new ArrayList<String>();
		operations.getChildren().forEach(op -> {
				ops.add(((TextField)op).textProperty().getValue());
		});
		
		out.writeObject(ops);	
	}
	/**
	 * Defines how the object will be read in from a file
	 * @param in the input stream that will read the object in from a file
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
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
		setAttributes(attr);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> ops = (ArrayList<String>)in.readObject();
		setOperations(ops);	
	}
}