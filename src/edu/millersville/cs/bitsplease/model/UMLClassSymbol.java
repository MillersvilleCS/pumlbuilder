/**
 * @author Kevin Fisher
 * @since  February 19, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.model;




public class UMLClassSymbol extends UMLObjectSymbol {

	private String className = "Untitled Class";
	private String[] classAttributes = new String[10];
	private String[] classFunctions = new String[10];
	
	/**
	 * Empty UMLClassSymbol constructor
	 */
	public UMLClassSymbol(){
		super();
	}
	
	/**
	 * 
	 * Constructor Methods
	 * 
	 */
	
	/**
	 * className Constructor
	 * @param className title of the UML classbox
	 */
	public UMLClassSymbol(String className){
		super();
		this.className = className;
	}
	
	
	public UMLClassSymbol(String className, String[] attr){
		super();
		this.className = className;
		this.classAttributes = attr;
	}
	
	public UMLClassSymbol(String className, String[] attr, String[] func){
		super();
		this.className = className;
		this.classAttributes = attr;
		this.classFunctions = func;
	}
	
	/**
	 * Accessor Methods
	 */
	
	public String getClassName(){
		return this.className;
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
		
}
