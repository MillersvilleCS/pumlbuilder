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
	
	public UMLClassSymbol(String className){
		super();
		this.className = className;
	}
	
	public UMLClassSymbol(String className, String[] attr){
		super();
		this.className = className;
		this.classAttributes = attr;
	}
	
}
