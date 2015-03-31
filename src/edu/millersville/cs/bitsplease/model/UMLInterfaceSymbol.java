/***
 * @author Kevin Fisher
 * @since  March 30, 2015
 * @version 0.2.0
 */
		
package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class UMLInterfaceSymbol extends UMLObjectSymbol{
	
	private TextField interfaceText;
	private Circle renderedInterface;
	
	/**
	 * Default constructor, used for externalization
	 */
	public UMLInterfaceSymbol(){
		super();
		VBox interfaceContainer = new VBox();
		interfaceContainer.setAlignment(Pos.CENTER);
		
		interfaceText = new TextField();
		interfaceText.textProperty().bindBidirectional(identifier);
		interfaceText.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		interfaceText.setMouseTransparent(true);
		interfaceText.setFocusTraversable(false);
		interfaceText.setAlignment(Pos.CENTER);
		
		renderedInterface = new Circle(10.0);
		renderedInterface.setFill(Color.WHITE);
		renderedInterface.setStroke(Color.BLACK);

		interfaceContainer.getChildren().addAll(interfaceText, renderedInterface);
		this.getChildren().add(interfaceContainer);
	}
	
	/**
	 * UMLSymbol constructor for displaying at a specified point.
	 * This constructor assumes that the point passed in was received as user input
	 * @param origin Point at which the UMLInterfaceSymbol will be created 
	 */
	public UMLInterfaceSymbol(Point2D origin){
		super(origin);
		
		VBox interfaceContainer = new VBox();
		interfaceContainer.setAlignment(Pos.CENTER);
	
		interfaceText = new TextField();
		interfaceText.textProperty().bindBidirectional(identifier);
		interfaceText.setText("<<Untitled>>");
		interfaceText.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		interfaceText.setMouseTransparent(true);
		interfaceText.setFocusTraversable(false);
		interfaceText.setAlignment(Pos.CENTER);
		
		renderedInterface = new Circle(10.0);
		renderedInterface.setFill(Color.WHITE);
		renderedInterface.setStroke(Color.BLACK);
		
	
		
		interfaceContainer.getChildren().addAll(interfaceText, renderedInterface);
		
		this.getChildren().add(interfaceContainer);
	}
	
	/**
	 * @return the Interface object text
	 */
	public String getText(){
		return interfaceText.getText();
	}
	
	/**
	 * @return the value of the origins x-coordinate
	 */
	public double getX(){
		return super.getX();
	}
	
	/**
	 * @return the value of the origins y-coordinate
	 */
	public double getY() {
		return super.getY();
	}
	
	/**
	 * 
	 * @param text the text to set to the Interface text field
	 */
	public void setText(String text){
		interfaceText.setText(text);
	}
	
	/** 
	 * @param origin the point at which the interface will rendered
	 */
	public void setOrigin(Point2D _origin){
		super.setOrigin(_origin);
	}
	
	/**
	 * Defines how the object will be written to a file
	 * @param out Object output stream that allows the object to be written to a file
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeObject(identifier.getValue());
		out.writeDouble(getX());
		out.writeDouble(getY());
	
		
		
	}

	/**
	 * Defines how the object will be read in from a File
	 * @param in Object input Stream to read the object in from a file
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		
		identifier.setValue((String)in.readObject());
		setOrigin(new Point2D(in.readDouble(), in.readDouble()));
		
		
	}

}
