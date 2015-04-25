/**
 * 
 */
package edu.millersville.cs.bitsplease.change;

import org.reactfx.Change;

import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;

/**
 * @author Mervin
 *
 */
public class RelationTypeChange extends UMLDocumentChange<UMLRelationType> {
	final UMLRelationSymbol relation;
	
	public RelationTypeChange(Change<UMLRelationType> c, UMLRelationSymbol relation) {
		super(c.getOldValue(), c.getNewValue());
		this.relation = relation;
	}

	@Override
	public void redo() {
		this.relation.setUMLRelationType(newValue);
	}

	@Override
	public void undo() {
		this.relation.setUMLRelationType(oldValue);
	}
}
