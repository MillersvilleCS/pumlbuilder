/**
 * @author Merv Fansler
 * @since March 30, 2015
 * @version 0.2.0
 */
package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;

/**
 * Class for UML User Symbols
 */
public class UMLUserSymbol extends UMLObjectSymbol {

	final double ICON_SIZE = 20d;
	
	private VBox vBox = new VBox();
	private Group userIcon;
	private TextField userName;
	
	/**
	 * Empty Constructor for UML User Symbol
	 */
	public UMLUserSymbol() {
		super();
		
		identifier.setValue("User");
		
		initUserIcon();
		initUserNameTextField();
		
		vBox.getChildren().addAll(userIcon, userName);
		vBox.setAlignment(Pos.CENTER);
		getChildren().add(vBox);
	}

	/**
	 * Constructor for UML User Symbol
	 * @param origin the position to create the object at
	 */
	public UMLUserSymbol(Point2D origin) {
		super(origin);
		
		identifier.setValue("User");
		
		initUserIcon();
		initUserNameTextField();

		vBox.getChildren().addAll(userIcon, userName);
		vBox.setAlignment(Pos.CENTER);
		getChildren().add(vBox);
	}

	/**
	 * initializes the text field for the symbol
	 */
	private void initUserNameTextField() {
		userName = new TextField();
		userName.setStyle("-fx-border-color:transparent;-fx-background-color:transparent");
		userName.setPrefWidth(100);
		userName.setAlignment(Pos.BASELINE_CENTER);
		userName.textProperty().bindBidirectional(identifier);
		userName.setFocusTraversable(false);
	}

	/**
	 * initializes the user icon part of this symbol
	 */
	private void initUserIcon() {
		userIcon = new Group();
		
		Circle head = new Circle(0d, -0.5*ICON_SIZE, 0.25*ICON_SIZE);
		Polyline body = new Polyline(0d, -0.5*ICON_SIZE,
									 0d, 0d,
									 -0.5*ICON_SIZE, 0.5*ICON_SIZE,
									 0d, 0d,
									 0.5*ICON_SIZE, 0.5*ICON_SIZE,
									 0d, 0d,
									 0d, ICON_SIZE,
									 -0.5*ICON_SIZE, 1.5*ICON_SIZE,
									 0d, ICON_SIZE,
									 0.5*ICON_SIZE, 1.5*ICON_SIZE);
		
		userIcon.getChildren().addAll(head,body);
	}

	/**
	 * Provides a means for restoring a saved version of this class
	 * @param ObjectInput in an object stream to read from
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		try {
			identifier.setValue((String) in.readObject());
			setOrigin(new Point2D(in.readDouble(),in.readDouble()));
		} catch (Exception x) {
			System.err.println("Invalid UML File format");
		}
	}

	/** 
	 * Provides a means for saving this class to file
	 * @param ObjectOuput out an object stream to write to
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(identifier.getValue());
		out.writeDouble(getX());
		out.writeDouble(getY());
	}
}
