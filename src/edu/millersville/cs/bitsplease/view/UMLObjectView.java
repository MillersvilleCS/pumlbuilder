/**
 * @author Joe Martello
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.0
 */


package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;


public class UMLObjectView extends Group {
	private UMLClassSymbol umlClassSymbol;
	private Rectangle umlBox;
	private Line ltop;
	private Line lbot;
	private Label name;
	private Label attributes;
	private Label functions;
	
		/**
		 * UMLObjectSymbol Constructor displaying UMLClassSymbol location and attributes
		 * This constructor assumes that the width and height are large enough to encompass all of its attributes
		 * @param umlClassSymbol that will be displayed
		 */
	public UMLObjectView(UMLClassSymbol umlSymbol){
		super();
		this.umlClassSymbol = umlSymbol;
		
		Point2D origin = umlSymbol.getOrigin();
		
		this.setLayoutX(origin.getX());
		this.setLayoutY(origin.getY());
		
		umlBox = new Rectangle(0, 0, umlSymbol.getWidth(), umlSymbol.getHeight());
		umlBox.setFill(Color.TRANSPARENT);
		umlBox.setStroke(Color.BLACK);
		this.getChildren().add(umlBox);
		
		double y = 20;
		name = new Label(" " + umlSymbol.getClassName());
		name.setMaxWidth(umlBox.getWidth() - 10);
		name.setPrefHeight(y);
		this.getChildren().add(name);
		
		y = name.getBoundsInParent().getMinY() + name.getPrefHeight(); //will only work for 1 line names
		ltop = new Line(0, y, umlBox.getWidth(), y);
		this.getChildren().add(ltop);
		
		
		String label = " ";
		int length = 0;
		for (String s: umlSymbol.getAttributes()){
			if (s != null){
				label += s + "\n ";
				length += 20;
			}
		}
		if (length == 0)
			length = 20;
		attributes = new Label(label);
		attributes.setMaxWidth(umlSymbol.getWidth() - 10);
		attributes.setLayoutY(ltop.getStartY());
		attributes.setPrefHeight(length);
		this.getChildren().add(attributes);
		
		y = attributes.getBoundsInParent().getMinY() + attributes.getPrefHeight();
		lbot = new Line(0, y, umlSymbol.getWidth(), y);
		this.getChildren().add(lbot);
		
		
		label = " ";
		length = 0;
		for (String s: umlSymbol.getFunctions()){
			if (s != null){
				label += s + "\n";
				length += 20;
			}
		}
		functions = new Label(label);
		functions.setMaxWidth(umlSymbol.getWidth() - 10);
		functions.setPrefHeight(length);
		functions.setLayoutY(lbot.getStartY());
		this.getChildren().add(functions);
		
		//this.getChildren().forEach(n -> n.setMouseTransparent(true));
	}
	
	
	/**
	 * removes all elements of the UML diagram from the scene
	 */
	public void delete(){
		this.getChildren().removeAll(umlBox, ltop, lbot, name, attributes, functions);
	}
	
	/**
	 * moves all attributes of the UML diagram to a new location on the GUI and resets its translate values
	 * @param Point2D origin coordinate to move the UML diagram to
	 */
	public void UpdatePosition(Point2D newOrigin){
		this.setLayoutX(newOrigin.getX());
		this.setLayoutY(newOrigin.getY());
		
		this.setTranslateX(0);
		this.setTranslateY(0);
	}
	
	/**
	 * 
	 * @param umlSymbol
	 */
	public void UpdateText(UMLClassSymbol umlSymbol){
		
	}
	/**
	 * 
	 * @param width width of the classbox being drawn
	 * @param height height of the classbox being drawn
	 */
	public void resize(double width, double height){
		umlBox.setWidth(width);
		name.setMaxWidth(width);
		attributes.setMaxWidth(width);
		functions.setMaxWidth(width);
		ltop.setEndX(width);
		lbot.setEndX(width);
		
		umlBox.setHeight(height);
		
		if (functions.getLayoutY() + functions.getPrefHeight() > height)
			functions.setVisible(false);
		else
			functions.setVisible(true);
		
		if (attributes.getLayoutY() + attributes.getPrefHeight() > height)
			attributes.setVisible(false);
		else
			attributes.setVisible(true);
		
		if (name.getLayoutY() + name.getPrefHeight() > height)
			name.setVisible(false);
		else
			name.setVisible(true);
		
		if (ltop.getStartY() > height)
			ltop.setVisible(false);
		else
			ltop.setVisible(true);
		
		
		if (lbot.getStartY() > height)
			lbot.setVisible(false);
		else
			lbot.setVisible(true);
		
	}

	/**
	 * @return the umlClassSymbol
	 */
	public UMLClassSymbol getUmlClassSymbol() {
		return umlClassSymbol;
	}
}


