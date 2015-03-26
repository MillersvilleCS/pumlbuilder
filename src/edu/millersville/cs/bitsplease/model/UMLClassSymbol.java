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

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class UMLClassSymbol extends UMLObjectSymbol {
	private Rectangle umlBox;
	private Line ltop;
	private Line lbot;
	private Label name;
	private List<Label> attributes;
	private List<Label> functions;
	private final int offset = 20;
	
		/**
		 * UMLObjectSymbol Constructor displaying UMLClassSymbol location and attributes
		 * This constructor assumes that the width and height are large enough to encompass all of its attributes
		 * @param umlClassSymbol that will be displayed
		 */
	public UMLClassSymbol(Point2D origin, double height, double width){
		super(origin, height, width);
		
		umlBox = new Rectangle(0, 0, width, height);
		umlBox.setFill(Color.TRANSPARENT);
		umlBox.setStroke(Color.BLACK);
		this.getChildren().add(umlBox);
		
		double y = 20;
		name = new Label(" " + getIdentifier());
		name.setMaxWidth(umlBox.getWidth() - 10);
		name.setPrefHeight(y);
		this.getChildren().add(name);
		
		y = name.getBoundsInParent().getMinY() + name.getPrefHeight(); //will only work for 1 line names
		ltop = new Line(0, 0, umlBox.getWidth(), 0);
		ltop.setLayoutY(name.getPrefHeight());
		this.getChildren().add(ltop);
		
		attributes = new ArrayList<Label>(0);
		int length = 0;
		// TODO: need to add means to set attributes
		for (String s: Arrays.asList("Attribute1","Attribute2")){
			if (s != null){
				Label l = new Label(" " + s);
				l.setMaxWidth(getWidth() - 10);
				l.setLayoutY(ltop.getStartY() + length + offset);
				l.setPrefHeight(offset);
				length += offset;
				attributes.add(l);
				this.getChildren().add(attributes.get(attributes.size() - 1));
			}
		}
		
		if (attributes.size() == 0)
			length = 20;
		
		lbot = new Line(0, 0, getWidth(), 0);
		lbot.setLayoutY(ltop.getLayoutY() + length);
		this.getChildren().add(lbot);
		
		functions = new ArrayList<Label>(0);
		length = 0;
		for (String s: Arrays.asList("Operation1()","Operation2()")){
			if (s != null){
				Label l = new Label(" " + s);
				l.setMaxWidth(getWidth() - 10);
				l.setLayoutY(lbot.getLayoutY() + length);
				l.setPrefHeight(offset);
				length += offset;
				functions.add(l);
				this.getChildren().add(functions.get(functions.size() - 1));
			}
		}
	}
	
	/**
	 * removes all elements of the UML diagram from the scene
	 */
	public void delete(){
		this.getChildren().removeAll(umlBox, ltop, lbot, name);
		for (Label l: attributes)
			this.getChildren().remove(l);
		for (Label l: functions)
			this.getChildren().remove(l);
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
//	
//	/**
//	 * updates the graphical title on the UMLSymbol
//	 */
//	public void UpdateTitle(){
//		name.setText(" " + getIdentifier());
//	}
//	
//	/**
//	 * updates the selected attribute or will add an attribute if it is the last one in the list
//	 * @param index index of the attribute to be updated in the view
//	 */
//	public void UpdateAttributes(int index){
//		if (index > attributes.size() - 1){
//				addAttribute(index);
//		} else {
//			attributes.get(index).setText(" " + umlClassSymbol.getAttributes()[index]);
//		}
//	}
	
	/**
	 * adds an attribute label to the end of the attributes section of the UMLClassSymbol
	 * @param i index of the attribute to be added
	 */
//	private void addAttribute(String attributeText){
//		//TODO make it generic to work with constructor too
//		
//		Label l = new Label(" " + attributeText);
//		l.setMaxWidth(getWidth() - 10);
//		l.setLayoutY(lbot.getLayoutY());
//		l.setPrefHeight(offset);
//		attributes.add(l);
//		this.getChildren().add(attributes.get(attributes.size() - 1));
//		
//		int length = offset * attributes.size();
//		lbot.setLayoutY(ltop.getLayoutY() + length);
//		length = 0;
//		for (Label f: functions){
//			f.setLayoutY(lbot.getLayoutY() + length);
//			length += offset;
//		}
//		
//	}
	
	
//	/**
//	 * 
//	 * @param s String to be found in UMLClassSymbol
//	 * @return the index of the first instance of the given string in the UMLClassSymbol, -1 if not found
//	 */
//	public int getAttributeIndex(String s){
//		for (int i = 0; i < umlClassSymbol.getAttributes().length; i ++){
//			if (umlClassSymbol.getAttributes()[i] == s)
//				return i;
//		}
//		return -1;
//	}
	
	/**
	 * updates the height and width of the UMLClassSymbol to the size of its ClassSymbol
	 */
	public void resize(){
		double width = getWidth();
		double height = getHeight();
		
		umlBox.setWidth(width);
		name.setMaxWidth(width);
		ltop.setEndX(width);
		lbot.setEndX(width);
		
		umlBox.setHeight(height);
		
		for (Label l: attributes){
			l.setMaxWidth(width);
			if (l.getLayoutY() + l.getPrefHeight() > height)
				l.setVisible(false);
			else
				l.setVisible(true);
		}
		for (Label l: functions){
			l.setMaxWidth(width);
			if (l.getLayoutY() + l.getPrefHeight() > height)
				l.setVisible(false);
			else
				l.setVisible(true);
		}
		
		
		if (name.getLayoutY() + name.getPrefHeight() > height)
			name.setVisible(false);
		else
			name.setVisible(true);
		
		if (ltop.getLayoutY() > height)
			ltop.setVisible(false);
		else
			ltop.setVisible(true);
		
		if (ltop.getLayoutY() + lbot.getLayoutY() > height)
			lbot.setVisible(false);
		else
			lbot.setVisible(true);
		
	}
//	
//	public void refreshSymbolPosition() {
//		Point2D origin = umlClassSymbol.getOrigin();
//		
//		this.setLayoutX(origin.getX());
//		this.setLayoutY(origin.getY());
//	}
}
