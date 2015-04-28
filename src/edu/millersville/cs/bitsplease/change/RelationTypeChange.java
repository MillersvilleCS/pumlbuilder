/**
 * @author Merv Fansler
 * @since April 14, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.change;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;

/**
 * Data structure for changes in UMLRelationSymbol relation type
 */
public class RelationTypeChange extends UMLDocumentChange<UMLRelationType> {
	final UMLRelationSymbol relation;
	
	/**
	 * Constructor for RelationTypeChange
	 * @param c change event for UMLRelationType
	 * @param relation the UMLRelationSymbol undergoing a change
	 */
	public RelationTypeChange(Change<UMLRelationType> c, UMLRelationSymbol relation) {
		super(c.getOldValue(), c.getNewValue());
		this.relation = relation;
	}

	/**
	 * method to redo change
	 */
	@Override
	public void redo() {
		this.relation.setUMLRelationType(newValue);
	}

	/**
	 * method to undo change
	 */
	@Override
	public void undo() {
		this.relation.setUMLRelationType(oldValue);
	}
}
