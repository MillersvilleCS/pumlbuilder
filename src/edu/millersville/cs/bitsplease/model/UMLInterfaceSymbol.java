/***
 * @author Kevin Fisher
 * @since  March 30, 2015
 * @version 0.1.1
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
		
		interfaceText = new TextField();
		renderedInterface = new Circle();

		interfaceContainer.getChildren().addAll(renderedInterface,interfaceText);
		this.getChildren().add(interfaceContainer);
	}
	
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
	
	
	public String getText(){
		return interfaceText.getText();
	}
	
	public double getX(){
		return super.getX();
	}
	
	public double getY() {
		return super.getY();
	}
	
	public void setText(String text){
		interfaceText.setText(text);
	}
	
	public void setOrigin(Point2D _origin){
		super.setOrigin(_origin);
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeObject(identifier.getValue());
		out.writeDouble(getX());
		out.writeDouble(getY());
	
		
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
