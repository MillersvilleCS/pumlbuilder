
/**
 * @author Michael Sims
 * @since February 25, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;
import javafx.scene.control.Control;
import javafx.scene.shape.Line;

public class UMLRelationView extends Control {

	private UMLRelationSymbol umlRelationSymbol;
	private UMLRelationType umlRelationType;
	private Line rline;
	
	UMLRelationView(UMLRelationSymbol umlRelation){
		super();
		this.umlRelationSymbol = umlRelation;
		
		UMLRelationType umlRelationType = umlRelation.getRelationType();
		
		if(umlRelationType == UMLRelationType.ASSOCIATION){
			rline = new Line();
		}
		else if(umlRelationType == UMLRelationType.DEPENDENCY){
			rline = new Line(20, 80, 270, 80);
			rline.getStrokeDashArray().addAll(25d, 10d);
		}
		
	}
	
	public UMLRelationSymbol getUmlRelationSymbol() {
		return umlRelationSymbol;
	}
	
	public UMLRelationType getUmlRelationType() {
		return umlRelationType;
	}
	
}
