/**
 * @author Kevin Fisher
 * @since  February 19, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.model;

import javafx.geometry.Point2D;



public class UMLClassSymbol extends UMLObjectSymbol {
	
	private String[] classAttributes = new String[10];
	private String[] classFunctions = new String[10];
	
	/**
	 * @param prefHeight
	 * @param prefWidth
	 */
	public UMLClassSymbol(double prefHeight, double prefWidth) {
		super(prefHeight, prefWidth);
	}

	/**
	 * @param origin
	 * @param prefHeight
	 * @param prefWidth
	 */
	public UMLClassSymbol(Point2D origin, double prefHeight, double prefWidth) {
		super(origin, prefHeight, prefWidth);
	}

	/**
	 * @param origin
	 */
	public UMLClassSymbol(Point2D origin) {
		super(origin);
	}

	/**
	 * Empty UMLClassSymbol constructor
	 */
	public UMLClassSymbol(){
		super();
	}
	
	/**
	 * @param className title of the UML classbox
	 */
	public UMLClassSymbol(String className){
		super(className);
	}
	
	public UMLClassSymbol(String className, String[] attr){
		super(className);
		this.classAttributes = attr;
	}
	
	public UMLClassSymbol(String className, String[] attr, String[] func){
		super(className);
		this.classAttributes = attr;
		this.classFunctions = func;
	}
	
	/**
	 * Accessor Methods
	 */
	
	/** 
	 * @return className String representing the name of the class object.
	 */
	public String getClassName(){
		return super.getName();
	}
	
	/**
	 * Get a single attribute from UMLClassSymbol object. Returns null if attribute does not exist.
	 * @param attribute attribute to get retrieved
	 * @return s matching String within the classAttribute array, or null if does not exist
	 */
	public String getAttribute(String attribute){
		
		for(String s: this.classAttributes){
			if(s == attribute){
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Get all attributes held within a UMLClassSymbol object as an array of Strings.
	 * @return classAttributes an array of attributes held by the UMLClassSymbol object
	 */
	public String[] getAttributes(){
		return this.classAttributes;
	}
	
	/**
	 * @param function function name to search for 
	 * @return s function name, or null if it does not exist
	 */
	public String getFunction(String function){
		
		for(String s:this.classFunctions){
			if(s == function){
				return s;
			}
		}
		return null;
	}
	
	/**
	 * @return classFunctions array of class functions held within a UMLClassSymbol object
	 */
	public String[] getFunctions(){
		return this.classFunctions;
	}
	
	/**
	 * @return double height height of the classbox to be drawn
	 */
	public double getHeight(){
		return super.getHeight();
	}
	
	/**
	 * @return double width width of the classbox to be drawn
	 */
	public double getWidth(){
		return super.getWidth();
	}
	
	/**
	 * @return double x value of x coordinate in UMLObjectSymbol origin point
	 */
	public double getX(){
		return super.getX();
	}
	
	/**
	 * @return double y value of y coordinate in UMLObjectSymbol origin point
	 */
	public double getY(){
		return super.getY();
	}
	
	/**
	 * @return Point2D origin point representing the origin of the classbox to be drawn.
	 */
	public Point2D getOrigin(){
		return super.getOrigin();
	}
	
	/**
	 * 
	 * Mutator Methods
	 * 
	 */
	
	/**
	 * 
	 * @param name name to assign to UMLClassObject instance
	 */
	public void setClassName(String name){
		super.setName(name);
	}
	
	/**
	 * set an individual attribute within a UMLClassObject instance
	 * @param attr attribute to set 
	 */
	public void setAttribute(String attr){
		//TODO add ability to add individual attributes
	}
	
	/**
	 * 
	 * @param attr array of attributes to assign to class attribute array
	 */
	public void setAttributes(String[] attr){
		this.classAttributes = attr;
	}
	
	/**
	 * 
	 * @param function function to add to UMLClassSymbol object
	 */
	public void setFunction(String function){
		//TODO add ability to set individual functions.
	}
	
	/**
	 * 
	 * @param func array of functions to assign to UMLClassSymbol object
	 */
	public void setFunctions(String[] func){
		this.classFunctions = func;
	}
	
	public void setOrigin(Point2D p){
		super.setOrigin(p);
	}
	
	public void setX(double x){
		
	}
	
	public void setY(double y){
		
	}
	
	
	public void setHeight(double h){
		super.setHeight(h);
	}
	
	public void setWidth(double w){
		super.setWidth(w);
	}
}
