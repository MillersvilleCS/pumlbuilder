package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class UMLUseCaseSymbol extends UMLObjectSymbol {
	
	private StackPane useCase;
	private Ellipse useCaseContainer;
	private TextField useCaseText;
	
	/**
	 * Default constructor
	 */
	public UMLUseCaseSymbol(){
		super();
		
		useCase = new StackPane();
		useCase.setAlignment(Pos.CENTER);
		
		initUseCaseContainer();
		initUseCaseText();
	
		useCase.getChildren().addAll(useCaseContainer, useCaseText);
		
		this.getChildren().add(useCase);
	}
	
	/**
	 * General purpose constructor, creates Use case at specified point
	 * @param origin the point at which the use case will be initialized
	 */
	public UMLUseCaseSymbol(Point2D origin){
		super(origin);
		
		useCase = new StackPane();
		useCase.setAlignment(Pos.CENTER);
		
		initUseCaseContainer();
		initUseCaseText();
		useCaseText.setText("Untitled Use Case");
		
		useCase.getChildren().addAll(useCaseContainer, useCaseText);
		
		this.getChildren().add(useCase);
	}
	
	/**
	 * initialize the use case container
	 */
	private void initUseCaseContainer(){
		useCaseContainer = new Ellipse(65,32.5);
		useCaseContainer.setFill(Color.WHITE);
		useCaseContainer.setStroke(Color.BLACK);
	}
	
	/**
	 * initialize the use case text
	 */
	private void initUseCaseText(){
		useCaseText = new TextField();
		useCaseText.textProperty().bindBidirectional(identifier);
		useCaseText.setMouseTransparent(true);
		useCaseText.setFocusTraversable(false);
		useCaseText.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		useCaseText.setAlignment(Pos.CENTER);
	}
	
	// Getters & Setters 
	
	/**
	 * @return the contents of the Use case text field
	 */
	public String getText(){
		return identifier.getValue();
	}
	
	/**
	 * @return the x-value of the Use case origin
	 */
	public double getX(){
		return super.getX();
	}
	
	/**
	 * @return the y-value of the Use Case origin
	 */
	public double getY(){
		return super.getY();
	}
	
	public void setText(String text){
		identifier.setValue(text);
	}
	
	public void setOrigin(Point2D _origin){
		super.setOrigin(_origin);
	}
	
	/**
	 * Set the useCaseText in the UMLUseCaseSymbol to editable.
	 */
	public void setEditableUMLUseCaseSymbol() {
		useCaseText.setMouseTransparent(false);
		useCaseText.requestFocus();
	}

	/**
	 * Set the useCaseText in the UMLUseCaseSymbol to non-editable.
	 */
	public void setNonEditableUMLUseCaseSymbol() {
		useCaseText.setMouseTransparent(true);
	}
	
	/**
	 * Externalization method for writing this object out to a file
	 * @param out the output stream to write the object to
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeObject(getText());
		
		out.writeDouble(getX());
		out.writeDouble(getY());
		
		
	}
	
	/**
	 * Externalization method for reading this object in from a file.
	 * @param in the input stream to read the object in from
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		
		setText((String)in.readObject());
		setOrigin(new Point2D(in.readDouble(), in.readDouble()));
	}

}
