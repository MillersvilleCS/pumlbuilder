/**
 * @author Kevin Fisher
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.model;

import javafx.geometry.Point2D;
import javafx.event.Event;
import javafx.event.EventDispatchChain;

/*
 * A class model to represent a UML Object symbol
 */

//TODO add ArrayList<UMLRelationSymbol> to keep track of relations.
public class UMLObjectSymbol extends UMLSymbol {
	private Point2D origin = new Point2D(0,0);
	private double height = 100;
	private double width = 100;
	private boolean isSelected = false;
	private String identifier = "";
	/**
	 *  Empty UMLObjectSymbol Constructor 
	 */
	public UMLObjectSymbol(){
		super();
	}
	
	/** UMLObjectSymbol Constructor with user-specified origin point
	 * 
	 * @param Point2D origin Coordinate at which to instantiate a UMLObjectSymbol 
	 */
	public UMLObjectSymbol(Point2D origin){
		super();
		this.origin = origin;
	}
	
	public UMLObjectSymbol(String name){
		this.identifier = name;
	}
	/** 
	 * Construct a UMLObjectSymbol with user-defined height, width
	 * @param double prefHeight height of the UMLObjectSymbol 
	 * @param double prefWidth  width of the UMLObjectSymbol
	 */
	public UMLObjectSymbol(double prefHeight, double prefWidth){
		super();
		this.height = prefHeight;
		this.width = prefWidth;
	}
	/**
	 * General Constructor for UMLObjectSymbol
	 * @param origin Coordinates of the UMLObjectSymbol origin 
	 * @param prefHeight height of the UMLObjectSymbol
	 * @param prefWidth width of the UMLObjectSymbol
	 */
	public UMLObjectSymbol(Point2D origin, double prefHeight, double prefWidth){
		super();
		this.origin = origin;
		this.height = prefHeight;
		this.width = prefWidth;
	}
	
	/**
	 * Method to handle event propagation through a chain of UMLSymbol objects
	 * @return the processed event or null if the event has been fully propagated
	 */
	public Event dispatchEvent(Event event, EventDispatchChain tail){
		
		return event;
		
	}
	
	/************************
	 *Class Accessor methods*
	 ************************/
	
	
	/**
	 * @return origin the origin of a UMLObjectSymbol instance
	 */
	public Point2D getOrigin(){
		return origin;
	}
	
	/**
	 * 
	 * @return height the height of a UMLObjectSymbol instance
	 */
	public double getHeight(){
		return height;
	}
	
	/**
	 * 
	 * @return the width of a UMLObjectSymbol instance
	 */
	public double getWidth(){
		return width;
	}
	
	/**
	 * 
	 * @return double x the x value of the origin for UMLObjectSymbol
	 */
	public double getX(){
		return origin.getX();
	}
	
	/**
	 * 
	 * @return double y the y value of the origin for UMLObjectSymbol
	 */
	public double getY(){
		return origin.getY();
	}
	
	public String getName(){
		return this.identifier;
	}
	
	public boolean isSelected(){
		return this.isSelected;
	}
	
	/**
	 * Mutator classes
	 */
	
	public void setName(String name){
		this.identifier = name;
	}
	/**
	 * 
	 * @param double newHeight updated height for UMLObjectSymbol instance
	 */
	public void setHeight(double newHeight){
		this.height = newHeight;
	}
	/**
	 * 
	 * @param double newWidth updated width for UMLObjectSymbol instance
	 */
	public void setWidth(double newWidth){
		this.width = newWidth;
	}
	
	/**
	 * 
	 * @param Point2D newPoint updated Point2D origin for UMLObjectSymbol instance 
	 */
	public void setOrigin(Point2D newPoint){
		this.origin = newPoint;
	}
	
	public void setSelectedStatus(boolean status){
		this.isSelected = status;
	}
	

	

}
