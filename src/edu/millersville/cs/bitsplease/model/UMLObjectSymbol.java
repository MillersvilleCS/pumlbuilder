/**
 * @author Kevin Fisher
 * @since February 19, 2015
 * @version 0.2.0
 */

package edu.millersville.cs.bitsplease.model;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import edu.millersville.cs.bitsplease.change.ObjectXChange;
import edu.millersville.cs.bitsplease.change.ObjectYChange;
import edu.millersville.cs.bitsplease.change.UMLDocumentChange;

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
	
	/**
	 * @return the point at the origin (top left) of the UMLObjectSymbol
	 */
	public Point2D getOrigin() {
		return new Point2D(getX(), getY());
	}
	
	/**
	 * @return the point at the center of the UMLObjectSymbol
	 */
	public Point2D getCenter() {
		return new Point2D(getX() + getLayoutBounds().getWidth()/2, getY() + getLayoutBounds().getHeight()/2);
	}
	
	/**
	 * @return the point at the center of the top of the UMLObjectSymbol
	 */
	public Point2D getTopCenter() {
		return new Point2D(getX() + getLayoutBounds().getWidth()/2, getY());
	}
	
	/**
	 * @return the point at the center of the left side of the UMLObjectSymbol
	 */
	public Point2D getMiddleLeft() {
		return new Point2D(getX(), getY() + getLayoutBounds().getHeight()/2);
	}
	
	/**
	 * @return the point at the center of the right sie of the UMLObjectSymbol
	 */
	public Point2D getMiddleRight() {
		return new Point2D(getX() + getLayoutBounds().getWidth(), getY() + getLayoutBounds().getHeight()/2);
	}
	
	/**
	 * @return the point at the center of the bottom of the UMLObjectSymbol
	 */
	public Point2D getBottomCenter() {
		return new Point2D(getX() + getLayoutBounds().getWidth()/2, getY() + getLayoutBounds().getHeight());
	}
	
	/**
	 * Mutator classes
	 */
	
	/**
	 * 
	 * @param Point2D newPoint updated Point2D origin for UMLObjectSymbol instance 
	 */
	public void setOrigin(Point2D newPoint) {
		this.setLayoutX(newPoint.getX());
		this.setLayoutY(newPoint.getY());
	}
	
	/**
	 * @param status the status to set the isSelected 
	 */
	public void setSelectedStatus(boolean status){
		this.isSelected = status;
	}
	
	/**
	 * @see edu.millersville.cs.bitsplease.model.UMLSymbol#getFields()
	 */
	@Override
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = super.getFields();
		
		fields.add(this.layoutXProperty());
		fields.add(this.layoutYProperty());
		return fields;
	}
	
	@Override
	public EventStream<UMLDocumentChange<?>> getChangeStream() {
		// X Changes
		EventStream<ObjectXChange> xChanges = 
			EventStreams.changesOf(this.layoutXProperty()).map(
				c -> new ObjectXChange(c, this) 
				);
		//symbolPropertyChanges.connectTo(objectXChanges);
		
		// Y Changes
		EventStream<ObjectYChange> yChanges = 
			EventStreams.changesOf(this.layoutYProperty()).map(
				c -> new ObjectYChange(c, this) 
				);
		//symbolPropertyChanges.connectTo(objectYChanges);
		
		return EventStreams.merge(super.getChangeStream(), xChanges, yChanges);
	}

}
