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
	private final int offset = 20;
	
		/**
		 * UMLObjectSymbol Constructor displaying UMLClassSymbol location and attributes
		 * This constructor assumes that the width and height are large enough to encompass all of its attributes
		 * @param umlClassSymbol that will be displayed
		 */
	public UMLClassSymbol(Point2D origin, double height, double width){
		super(origin, height, width);
		
		umlVBox = new VBox();
		umlVBox.setStyle("-fx-border-color: #000; -fx-background-color: white");
		umlVBox.setPrefSize(width, height);
		this.getChildren().add(umlVBox);
		
		name = new TextField(getIdentifier());
		name.setMaxWidth(width);
		name.setPrefHeight(offset);
		name.setStyle("-fx-border-color: white");
		name.setEditable(false);
		name.setMouseTransparent(true);
		umlVBox.getChildren().add(name);
		
		ltop = new Line(0, 0, width, 0);
		umlVBox.getChildren().add(ltop);
		
		attributes = new ArrayList<TextField>(0);
		// TODO: need to add means to set attributes
		for (String s: Arrays.asList("Attribute1","Attribute2")){
			if (s != null){
				TextField t = new TextField(s);
				t.setStyle("-fx-border-color: white; -fx-highlight-fill: none");
				t.setEditable(false);
				t.setMouseTransparent(true);
				t.setMaxWidth(getWidth());
				t.setPrefHeight(offset);
				attributes.add(t);
				umlVBox.getChildren().add(attributes.get(attributes.size() - 1));
			}
		}
		
		lbot = new Line(0, 0, getWidth(), 0);
		umlVBox.getChildren().add(lbot);
		
		functions = new ArrayList<TextField>(0);
		for (String s: Arrays.asList("Operation1()","Operation2()")){
			if (s != null){
				TextField t = new TextField(s);
				t.setStyle("-fx-border-color: white");
				t.setEditable(false);
				t.setMouseTransparent(true);
				t.setMaxWidth(getWidth());
				t.setPrefHeight(offset);
				functions.add(t);
				umlVBox.getChildren().add(functions.get(functions.size() - 1));
			}
		}
	}
	
	/**
	 * removes all elements of the UML diagram from the scene
	 */
	public void delete(){
		this.getChildren().removeAll(umlVBox, ltop, lbot, name);
		for (TextField t: attributes)
			this.getChildren().remove(t);
		for (TextField t: functions)
			this.getChildren().remove(t);
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
		
		//umlBox.setWidth(width);
		umlVBox.setPrefSize(width, height);
		name.setMaxWidth(width);
		ltop.setEndX(width);
		lbot.setEndX(width);
		
		//umlBox.setHeight(height);
		
		for (TextField t: attributes){
			t.setMaxWidth(width);
			if (t.getLayoutY() + t.getPrefHeight() > height)
				t.setVisible(false);
			else
				t.setVisible(true);
		}
		for (TextField t: functions){
			t.setMaxWidth(width);
			if (t.getLayoutY() + t.getPrefHeight() > height)
				t.setVisible(false);
			else
				t.setVisible(true);
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
