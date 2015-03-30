/**
 * @author Michael Sims
 * @author Merv Fansler
 * @author Joe Martello
 * @since February 25, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class UMLRelationSymbol extends UMLSymbol {

	final double ARROW_SIZE = 10d;
	final double DIAMOND_SIZE = 12d;
	
	private	UMLRelationType relationType;
	private UMLObjectSymbol sourceObject;
	private UMLObjectSymbol targetObject;
	private Polyline rLine;
	private Shape rSymbol;
	private TextField rText;
	
	public UMLRelationSymbol(UMLObjectSymbol sourceObject,
			UMLObjectSymbol targetObject, UMLRelationType relationType){
		super();
		
		
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;
		this.relationType = relationType;
		identifier.setValue(relationType.toString());
		// clicks should NOT be captured based on bounding box
		this.setPickOnBounds(false);
		
		// create, add, and update line
		this.rLine = new Polyline();
		refreshLine();
		getChildren().add(rLine);
		
		// create and add identifier field
		rText = new TextField();
		rText.textProperty().bindBidirectional(identifier);
		rText.setMouseTransparent(true);
		rText.setFocusTraversable(false);
		rText.setVisible(true);
		rText.setStyle("-fx-border-color: white");
		rText.setPrefWidth(100);
		getChildren().add(rText);
		
		// create and position relation head symbol
		switch (this.relationType) {
		case ASSOCIATION:
			rSymbol = new Polyline(-0.5*ARROW_SIZE, ARROW_SIZE,
									0.5*ARROW_SIZE, 0d, 
								   -0.5*ARROW_SIZE, -ARROW_SIZE);
			refreshArrow();
			break;
		case DEPENDENCY:
			rSymbol = new Polyline(-0.5*ARROW_SIZE, ARROW_SIZE,
									0.5*ARROW_SIZE, 0d, 
								   -0.5*ARROW_SIZE, -ARROW_SIZE);
			refreshArrow();
			rLine.getStrokeDashArray().addAll(25d, 10d);
			break;
		case AGGREGATION:
			rSymbol = new Polygon(DIAMOND_SIZE, 0,
					 			  0, 0.5*DIAMOND_SIZE,
					 			  -DIAMOND_SIZE, 0,
					 			  0, -0.5*DIAMOND_SIZE);
			rSymbol.setFill(Color.WHITE);
			rSymbol.setStroke(Color.BLACK);
			refreshDiamond();
			break;
		case COMPOSITION:
			rSymbol = new Polygon(DIAMOND_SIZE, 0,
		 			  			  0, 0.5*DIAMOND_SIZE,
		 			  			  -DIAMOND_SIZE, 0,
		 			  			  0, -0.5*DIAMOND_SIZE);
			refreshDiamond();
			break;
		case GENERALIZATION:
			rSymbol = new Polygon(-0.5*ARROW_SIZE, ARROW_SIZE,
								   0.5*ARROW_SIZE, 0d, 
								  -0.5*ARROW_SIZE, -ARROW_SIZE);
			rSymbol.setFill(Color.WHITE);
			rSymbol.setStroke(Color.BLACK);
			refreshArrow();
			break;
		default:
			break;
		}
		
		// add symbol to scene
		getChildren().add(rSymbol);
	}
	
	/**
	 * @return Point2D position of the first point of the relation line
	 */
	public Point2D getStartPoint() {
		List<Double> points = rLine.getPoints();
		if (points.size() < 2) {
			return null;
		} else {
			return new Point2D(points.get(0),points.get(1));
		}
	}
	
	/**
	 * @return Point2D position of the last point of the relation line
	 */
	public Point2D getEndPoint() {
		List<Double> points = rLine.getPoints();
		int size = points.size();
		if (size < 2) {
			return null;
		} else {
			return new Point2D(points.get(size-2),points.get(size-1));
		}
	}
	
	/**
	 * @return orientation of last segment of the line in radians; 
	 * Positive x-axis returns 0, positive y-axis returns PI/4, negative y-axis returns -PI/4; 
	 * Invalid lines return 0
	 */
	public double getEndOrientation() {
		List<Double> points = rLine.getPoints();
		int size = points.size();
		
		// must have at least 2 points to be 
		if (size < 4) {
			return 0d;
		} else {
			return Math.atan2(points.get(size-1)-points.get(size-3), points.get(size-2)-points.get(size-4));
		}
	}
	
	/***
	 * Refreshes the line component of the relation
	 */
	private void refreshLine() {
		
		rLine.getPoints().clear();
		
		// the following computes the edge center pair between the two objects
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
				rLine.getPoints().addAll(new Double[]{
						sourceObject.getTopCenter().getX(),
						sourceObject.getTopCenter().getY(),
						targetObject.getBottomCenter().getX(),
						targetObject.getBottomCenter().getY()
				});
				break;
			case 1:
				rLine.getPoints().addAll(new Double[]{
						sourceObject.getMiddleRight().getX(),
						sourceObject.getMiddleRight().getY(),
						targetObject.getMiddleLeft().getX(),
						targetObject.getMiddleLeft().getY()
				});
				break;
			case 2:
				rLine.getPoints().addAll(new Double[]{
						sourceObject.getMiddleLeft().getX(),
						sourceObject.getMiddleLeft().getY(),
						targetObject.getMiddleRight().getX(),
						targetObject.getMiddleRight().getY()
				});
				break;
			case 3:
				rLine.getPoints().addAll(new Double[]{
						sourceObject.getBottomCenter().getX(),
						sourceObject.getBottomCenter().getY(),
						targetObject.getTopCenter().getX(),
						targetObject.getTopCenter().getY()
				});
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

	/**
	 * resets the position of the arrowhead based off of the line's position
	 */
	private void refreshArrow(){
		rSymbol.setRotate(Math.toDegrees(getEndOrientation()));
		rSymbol.setTranslateX(getEndPoint().getX() - 0.5*ARROW_SIZE*Math.cos(getEndOrientation()));
		rSymbol.setTranslateY(getEndPoint().getY() - 0.5*ARROW_SIZE*Math.sin(getEndOrientation()));
	}
	
	/**
	 * resets the position for the AGGREGATION and COMPOSITION relation types
	 */
	private void refreshDiamond(){
		rSymbol.setRotate(Math.toDegrees(getEndOrientation()));
		rSymbol.setTranslateX(getEndPoint().getX() - DIAMOND_SIZE*Math.cos(getEndOrientation()));
		rSymbol.setTranslateY(getEndPoint().getY() - DIAMOND_SIZE*Math.sin(getEndOrientation()));
	}
	
	/**
	 * makes the textField visible and editable to the user
	 */
	public void editText(){
		rText.setMouseTransparent(false);
		rText.setVisible(true);
	}
	
	/**
	 * prevents the textField from being edited
	 */
	public void stopEdit(){
		rText.setMouseTransparent(true);
	}
	
	/**
	 * resets the contents of the textField and makes it invisible to the user
	 */
	public void deleteText(){
		rText.setText("");
		rText.setMouseTransparent(true);
		rText.setVisible(false);
	}
	
	/**
	 * calls all of the related functions to update the relation line and symbols
	 */
	public void refresh() {
		refreshLine();
		
		// refresh symbol head
		switch (this.relationType) {
		case ASSOCIATION:
		case DEPENDENCY:
		case GENERALIZATION:
			refreshArrow();
			break;
		case AGGREGATION:
		case COMPOSITION:
			refreshDiamond();
			break;
		default:
			break;
		}
		
		rText.setLayoutX((rLine.getPoints().get(0) + rLine.getPoints().get(2)) / 2);
		rText.setLayoutY((rLine.getPoints().get(1) + rLine.getPoints().get(3)) / 2);
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
