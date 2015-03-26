/**
 * @author Kevin Fisher
 * @since February 19, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.model;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.geometry.Point2D;

/*
 * A class model to represent a UML Object symbol
 */
public abstract class UMLObjectSymbol extends UMLSymbol {
	
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
		setOrigin(origin);
	}
	
	public UMLObjectSymbol(String name){
		this.identifier.setValue(name);
	}
	/** 
	 * Construct a UMLObjectSymbol with user-defined height, width
	 * @param double prefHeight height of the UMLObjectSymbol 
	 * @param double prefWidth  width of the UMLObjectSymbol
	 */
	public UMLObjectSymbol(double prefHeight, double prefWidth){
		super();
		this.prefHeight(prefHeight);
		this.prefWidth(prefWidth);
	}
	/**
	 * General Constructor for UMLObjectSymbol
	 * @param origin Coordinates of the UMLObjectSymbol origin 
	 * @param prefHeight height of the UMLObjectSymbol
	 * @param prefWidth width of the UMLObjectSymbol
	 */
	public UMLObjectSymbol(Point2D origin, double prefHeight, double prefWidth){
		super();
		setOrigin(origin);
		this.prefHeight(prefHeight);
		this.prefWidth(prefWidth);
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
	 * 
	 * @return double x the x value of the origin for UMLObjectSymbol
	 */
	public double getX(){
		return this.getLayoutX();
	}
	
	/**
	 * 
	 * @return double y the y value of the origin for UMLObjectSymbol
	 */
	public double getY(){
		return this.getLayoutY();
	}
	
	public Point2D getCenter() {
		return new Point2D(getX() + getWidth()/2, getY() + getHeight()/2);
	}
	
	public Point2D getTopCenter() {
		return new Point2D(getX() + getWidth()/2, getY());
	}
	
	public Point2D getMiddleLeft() {
		return new Point2D(getX(), getY() + getHeight()/2);
	}
	
	public Point2D getMiddleRight() {
		return new Point2D(getX() + getWidth(), getY() + getHeight()/2);
	}
	
	public Point2D getBottomCenter() {
		return new Point2D(getX() + getWidth()/2, getY() + getHeight());
	}
	
	/**
	 * Mutator classes
	 */
	
	/**
	 * 
	 * @param Point2D newPoint updated Point2D origin for UMLObjectSymbol instance 
	 */
	public void setOrigin(Point2D newPoint){
		this.setLayoutX(newPoint.getX());
		this.setLayoutY(newPoint.getY());
	}
	
	public void setSelectedStatus(boolean status){
		this.isSelected = status;
	}
	
	/* (non-Javadoc)
	 * @see edu.millersville.cs.bitsplease.model.UMLSymbol#getFields()
	 */
	@Override
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = super.getFields();
		fields.add(this.layoutXProperty());
		fields.add(this.layoutYProperty());
		fields.add(this.prefWidthProperty());
		fields.add(this.prefHeightProperty());
		return fields;
	}

}
