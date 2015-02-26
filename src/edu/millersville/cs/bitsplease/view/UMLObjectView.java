/**
 * @author Joe Martello
 * @since February 25, 2015
 * @version 0.1.0
 */


package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.Group;


public class UMLObjectView {
	private Rectangle umlBox; 
	private Line ltop;
	private Line lbot;
	private Label name;
	private Label attributes;
	private Label functions;
	private Group root;
	
		
	UMLObjectView(UMLClassSymbol umlSymbol, Group g){
		Point2D origin = umlSymbol.getOrigin();
		umlBox = new Rectangle(origin.getX(), origin.getY(), umlSymbol.getWidth(), umlSymbol.getHeight());
		umlBox.setFill(Color.TRANSPARENT);
    umlBox.setStroke(Color.BLACK);
		root = g;
		root.getChildren().add(umlBox);
		
		name = new Label(umlSymbol.getClassName());
		name.setMaxWidth(umlSymbol.getWidth() - 10);
		name.relocate(origin.getX() + 10, origin.getY() + 5);
		root.getChildren().add(name);
		
		double y = name.getBoundsInParent().getMinY() + 20; //will only work for 1 line names
		ltop = new Line(origin.getX(), y, origin.getX() + umlSymbol.getWidth(), y);
		root.getChildren().add(ltop);
		
		
		String label = "";
		int length = 0;
		for (String s: umlSymbol.getAttributes()){
			if (s != null){
				label += s + "\n";
				length += 20;
			}
		}
		if (length == 0)
			length = 20;
		attributes = new Label(label);
		attributes.setMaxWidth(umlSymbol.getWidth() - 10);
		attributes.relocate(origin.getX() + 10, ltop.getEndY() + 5);
		root.getChildren().add(attributes);
		
		y = attributes.getBoundsInParent().getMinY() + length;
		lbot = new Line(origin.getX(), y, origin.getX() + umlSymbol.getWidth(), y);
		root.getChildren().add(lbot);
		
		
		label = "";
		length = 0;
		for (String s: umlSymbol.getFunctions()){
			if (s != null){
				label += s + "\n";
				length += 20;
			}
		}
		functions = new Label(label);
		functions.setMaxWidth(umlSymbol.getWidth() - 10);
		functions.relocate(origin.getX() + 10, lbot.getEndY() + 5);
		root.getChildren().add(functions);
		
	}
	
	
	/**
	 * removes all elements of the UML diagram from the scene
	 */
	public void delete(){
		root.getChildren().remove(umlBox);
		root.getChildren().remove(ltop);
		root.getChildren().remove(lbot);
		root.getChildren().remove(name);
		root.getChildren().remove(attributes);
		root.getChildren().remove(functions);
	}
	
	/**
	 * moves all attributes of the UML diagram to a new location on the GUI
	 * @param Point2D origin coordinate to move the UML diagram to
	 */
	public void UpdatePosition(Point2D newOrigin){
		double newX = newOrigin.getX();
		double newY = newOrigin.getY();
		
		double oldy = umlBox.getY();
		umlBox.setX(newX);
		umlBox.setY(newY);
		
		double ydist = newY + ltop.getStartY() - oldy;
		ltop.setStartX(newX);
		ltop.setStartY(ydist);
		ltop.setEndX(newX + umlBox.getWidth());
		ltop.setEndY(ydist);
		
		ydist = newY + lbot.getStartY() - oldy;
		lbot.setStartX(newX);
		lbot.setStartY(ydist);
		lbot.setEndX(newX + umlBox.getWidth());
		lbot.setEndY(ydist);
		
		ydist = newY + name.getBoundsInParent().getMinY() - oldy;
		name.relocate(newX + 10, ydist);
		
		ydist = newY + attributes.getBoundsInParent().getMinY() - oldy;
		attributes.relocate(newX + 10, ydist);
		
		ydist = newY + functions.getBoundsInParent().getMinY() - oldy;
		functions.relocate(newX + 10, ydist);
	}
	
	/**
	 * 
	 * @param umlSymbol
	 */
	public void UpdateText(UMLClassSymbol umlSymbol){
		
	}
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void resize(double width, double height){
		
		
	}
	
}


