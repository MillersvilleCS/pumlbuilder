
/**
 * @author Michael Sims
 * @since February 25, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.view;

import javafx.scene.shape.Line;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;
import edu.millersville.cs.bitsplease.model.UMLSymbol;

public class UMLRelationView extends UMLSymbolView {

	private UMLRelationSymbol umlRelationSymbol;
	private Line rLine;
	
	public UMLRelationView(UMLRelationSymbol umlRelation){
		super();
		umlRelationSymbol = umlRelation;

		this.rLine = new Line();
		
		switch (umlRelationSymbol.getRelationType()) {
		case ASSOCIATION:
			break;
		case DEPENDENCY:
			rLine.getStrokeDashArray().addAll(25d, 10d);
			break;
		default:
			break;
		}
		
		refreshLine();
		getChildren().add(rLine);
	}
	
	/**
	 * @param s1 symbol to start line at
	 * @param s2 symbol to end line at
	 * @return shortest line connecting the middle edges of the two UMLObjectSymbols
	 */
	private void refreshLine() {
		UMLObjectSymbol s1 = umlRelationSymbol.getSourceObject(),
						s2 = umlRelationSymbol.getTargetObject();
		
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
			rLine.setStartX(s1.getTopCenter().getX());
			rLine.setStartY(s1.getTopCenter().getY());
			rLine.setEndX(s2.getBottomCenter().getX());
			rLine.setEndY(s2.getBottomCenter().getY());
			break;
		case 1:
			rLine.setStartX(s1.getMiddleRight().getX());
			rLine.setStartY(s1.getMiddleRight().getY());
			rLine.setEndX(s2.getMiddleLeft().getX());
			rLine.setEndY(s2.getMiddleLeft().getY());
			break;
		case 2:
			rLine.setStartX(s1.getMiddleLeft().getX());
			rLine.setStartY(s1.getMiddleLeft().getY());
			rLine.setEndX(s2.getMiddleRight().getX());
			rLine.setEndY(s2.getMiddleRight().getY());
			break;
		case 3:
			rLine.setStartX(s1.getBottomCenter().getX());
			rLine.setStartY(s1.getBottomCenter().getY());
			rLine.setEndX(s2.getTopCenter().getX());
			rLine.setEndY(s2.getTopCenter().getY());
			break;			
		}
	}
	
	public UMLRelationSymbol getUmlRelationSymbol() {
		return umlRelationSymbol;
	}
	
	public UMLRelationType getUmlRelationType() {
		return umlRelationSymbol.getRelationType();
	}
	
	public UMLObjectSymbol getSourceObject() {
		return umlRelationSymbol.getSourceObject();
	}
	
	public UMLObjectSymbol getTargetObject() {
		return umlRelationSymbol.getTargetObject();
	}


	public void refresh() {
		refreshLine();
	}

	@Override
	public UMLSymbol getUMLSymbol() {
		return umlRelationSymbol;
	}
}
