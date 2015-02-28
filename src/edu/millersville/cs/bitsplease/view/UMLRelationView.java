
/**
 * @author Michael Sims
 * @since February 25, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import com.sun.javafx.geom.Line2D;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;

public class UMLRelationView extends Group {

	private UMLRelationSymbol umlRelationSymbol;
	private Line rLine;
	
	UMLRelationView(UMLRelationSymbol umlRelation){
		super();
		umlRelationSymbol = umlRelation;

		this.rLine = getShortestLine(umlRelationSymbol.getSourceObject(), umlRelationSymbol.getTargetObject());
		
		switch (umlRelationSymbol.getRelationType()) {
		case ASSOCIATION:
			break;
		case DEPENDENCY:
			rLine.getStrokeDashArray().addAll(25d, 10d);
			break;
		default:
			break;
		}
		
		getChildren().add(rLine);
	}
	
	
	/**
	 * @param s1 symbol to start line at
	 * @param s2 symbol to end line at
	 * @return shortest line connecting the middle edges of the two UMLObjectSymbols
	 */
	private Line getShortestLine(UMLObjectSymbol s1, UMLObjectSymbol s2) {
		Line l = new Line();
		
		double[] distances = {
				s1.getTopCenter().distance(s2.getBottomCenter()),
				s1.getMiddleRight().distance(s2.getMiddleLeft()),
				s1.getMiddleLeft().distance(s2.getMiddleRight()),
				s1.getBottomCenter().distance(s2.getTopCenter())
		};
		
		int minIndex = 0;
		for (int i = 1; i < 4; i++) {
			if (distances[i] < distances[minIndex])
				minIndex = i;
		}
		
		switch (minIndex) {
		case 0:
			l.setStartX(s1.getTopCenter().getX());
			l.setStartY(s1.getTopCenter().getY());
			l.setEndX(s2.getBottomCenter().getX());
			l.setEndY(s2.getBottomCenter().getY());
			break;
		case 1:
			l.setStartX(s1.getMiddleRight().getX());
			l.setStartY(s1.getMiddleRight().getY());
			l.setEndX(s2.getMiddleLeft().getX());
			l.setEndY(s2.getMiddleLeft().getY());
			break;
		case 2:
			l.setStartX(s1.getMiddleLeft().getX());
			l.setStartY(s1.getMiddleLeft().getY());
			l.setEndX(s2.getMiddleRight().getX());
			l.setEndY(s2.getMiddleRight().getY());
			break;
		case 3:
			l.setStartX(s1.getBottomCenter().getX());
			l.setStartY(s1.getBottomCenter().getY());
			l.setEndX(s2.getTopCenter().getX());
			l.setEndY(s2.getTopCenter().getY());
			break;			
		}
		
		return l;
	}
	
	public UMLRelationSymbol getUmlRelationSymbol() {
		return umlRelationSymbol;
	}
	
	public UMLRelationType getUmlRelationType() {
		return umlRelationSymbol.getRelationType();
	}
	
}
