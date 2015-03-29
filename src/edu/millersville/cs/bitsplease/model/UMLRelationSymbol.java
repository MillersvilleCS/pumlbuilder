/**
 * @author Michael Sims
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.shape.Line;

public class UMLRelationSymbol extends UMLSymbol {

	private	UMLRelationType relationType;
	private UMLObjectSymbol sourceObject;
	private UMLObjectSymbol targetObject;
	private Line rLine;
	
	public UMLRelationSymbol(UMLObjectSymbol sourceObject,
			UMLObjectSymbol targetObject, UMLRelationType relationType){
		super();
		
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;
		this.relationType = relationType;
		
		// clicks should NOT be captured based on bounding box
		this.setPickOnBounds(false);
		
		this.rLine = new Line();
		
		switch (this.relationType) {
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
		
		// the follow computes the edge center pair between the two objects
		// which has the shortest distance
		double[] distances = {
				sourceObject.getTopCenter().distance(targetObject.getBottomCenter()),
				sourceObject.getMiddleRight().distance(targetObject.getMiddleLeft()),
				sourceObject.getMiddleLeft().distance(targetObject.getMiddleRight()),
				sourceObject.getBottomCenter().distance(targetObject.getTopCenter())
		};
		
		int minIndex = 0;
		for (int i = 1; i < 4; i++) {
			if (distances[i] < distances[minIndex])
				minIndex = i;
		}
		
		switch (minIndex) {
		case 0:
			rLine.setStartX(sourceObject.getTopCenter().getX());
			rLine.setStartY(sourceObject.getTopCenter().getY());
			rLine.setEndX(targetObject.getBottomCenter().getX());
			rLine.setEndY(targetObject.getBottomCenter().getY());
			break;
		case 1:
			rLine.setStartX(sourceObject.getMiddleRight().getX());
			rLine.setStartY(sourceObject.getMiddleRight().getY());
			rLine.setEndX(targetObject.getMiddleLeft().getX());
			rLine.setEndY(targetObject.getMiddleLeft().getY());
			break;
		case 2:
			rLine.setStartX(sourceObject.getMiddleLeft().getX());
			rLine.setStartY(sourceObject.getMiddleLeft().getY());
			rLine.setEndX(targetObject.getMiddleRight().getX());
			rLine.setEndY(targetObject.getMiddleRight().getY());
			break;
		case 3:
			rLine.setStartX(sourceObject.getBottomCenter().getX());
			rLine.setStartY(sourceObject.getBottomCenter().getY());
			rLine.setEndX(targetObject.getTopCenter().getX());
			rLine.setEndY(targetObject.getTopCenter().getY());
			break;			
		}
	}
	
	public UMLRelationType getUmlRelationType() {
		return this.relationType;
	}
	
	public UMLObjectSymbol getSourceObject() {
		return this.sourceObject;
	}
	
	public UMLObjectSymbol getTargetObject() {
		return this.targetObject;
	}
	
	public void setUmlRelationType(UMLRelationType relType) {
		this.relationType = relType;
	}
	
	public void setSourceObject(UMLObjectSymbol _source) {
		this.sourceObject = _source;
	}
	
	public void setTargetObject(UMLObjectSymbol _target) {
		this.targetObject = _target;
	}

	public void refresh() {
		refreshLine();
	}

	@Override
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = super.getFields();
		fields.add(sourceObject.getIdentifierProperty());
		fields.add(targetObject.getIdentifierProperty());
		return fields;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeObject(identifier.getValue());
		
		out.writeObject(getSourceObject());
		out.writeObject(getTargetObject());
		out.writeObject(getUmlRelationType());
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		identifier.setValue((String)in.readObject());
		
		setSourceObject((UMLObjectSymbol)in.readObject());
		setTargetObject((UMLObjectSymbol)in.readObject());
		setUmlRelationType((UMLRelationType)in.readObject());
		
	}

	
}
