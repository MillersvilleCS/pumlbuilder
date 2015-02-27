/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.model;

import javafx.event.Event;
import javafx.event.EventDispatcher;
import javafx.event.EventDispatchChain;

public class UMLRelationSymbol extends UMLSymbol {
	
	private	UMLRelationType relationType;
	private UMLObjectSymbol sourceObject;
	private UMLObjectSymbol targetObject;
	
	/**
	 * General constructor for UML relation symbols.
	 * 
	 * @param sourceObject UML object directed from
	 * @param targetObject UML object directed to
	 * @param relationType type of relation between objects
	 */
	public UMLRelationSymbol(UMLObjectSymbol sourceObject,
			UMLObjectSymbol targetObject, UMLRelationType relationType) {
		super();
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;
		this.relationType = relationType;
	}

	/**
	 * Constructs UML association relation symbol.
	 * 
	 * @param sourceObject UML object directed from
	 * @param targetObject UML object directed to
	 * 
	 */
	public UMLRelationSymbol(UMLObjectSymbol sourceObject,
			UMLObjectSymbol targetObject) {
		super();
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;
		this.relationType = UMLRelationType.ASSOCIATION;
	}



	/********************************************************************/
	// Getters & Setters
	
	/**
	 * @return the relationType
	 */
	public UMLRelationType getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType the type of relation to set
	 */
	public void setRelationType(UMLRelationType relationType) {
		this.relationType = relationType;
	}

	/**
	 * @return the UMLObjectSymbol that the relation originated from
	 */
	public UMLObjectSymbol getSourceObject() {
		return sourceObject;
	}

	/**
	 * @param sourceObject the object the relation should direct from
	 */
	public void setSourceObject(UMLObjectSymbol sourceObject) {
		this.sourceObject = sourceObject;
	}

	/**
	 * @return the targetObject
	 */
	public UMLObjectSymbol getTargetObject() {
		return targetObject;
	}

	/**
	 * @param targetObject the object the relation should direct to
	 */
	public void setTargetObject(UMLObjectSymbol targetObject) {
		this.targetObject = targetObject;
	}
	 
	
	public Event dispatchEvent(Event event, EventDispatchChain tail){
		return event;
	}
}
