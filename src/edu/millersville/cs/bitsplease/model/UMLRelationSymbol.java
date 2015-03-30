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

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

//PolyLine test
import javafx.scene.shape.Polyline;

public class UMLRelationSymbol extends UMLSymbol {

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
		
		// clicks should NOT be captured based on bounding box
		this.setPickOnBounds(false);
		this.rLine = new Polyline();
		refreshLine();
		getChildren().add(rLine);
		
		rText = new TextField();
		rText.setMouseTransparent(true);
		rText.setFocusTraversable(false);
		rText.setVisible(false);
		rText.setStyle("-fx-border-color: white");
		rText.setPrefWidth(100);
		getChildren().add(rText);
		
		switch (this.relationType) {
		case ASSOCIATION:
			rSymbol = new Polyline();
			//refreshArrow();
			break;
		case DEPENDENCY:
			rSymbol = new Polyline();
			//refreshArrow();
			rLine.getStrokeDashArray().addAll(25d, 10d);
			break;
		case AGGREGATION:
			rSymbol = new Polygon(5.0, 0.0,
					0.0 ,8.0,
					5.0, 16.0,
					10.0, 8.0);
			rSymbol.setFill(Color.WHITE);
			rSymbol.setStroke(Color.BLACK);
			//refreshDiamond();
			break;
		case COMPOSITION:
			rSymbol = new Polygon(5.0, 0.0,
					0.0 ,8.0,
					5.0, 16.0,
					10.0, 8.0);
			//refreshDiamond();
			break;
		case GENERALIZATION:
			rSymbol = new Polygon(12.0, 0.0,
					0.0 ,15.0,
					24.0, 15.0);
			rSymbol.setFill(Color.WHITE);
			rSymbol.setStroke(Color.BLACK);
			//refreshTriangle();
			break;
		default:
			break;
		}
		getChildren().add(rSymbol);
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
		rLine.getPoints().addAll(new Double[]{
				sourceObject.getTopCenter().getX(),
				sourceObject.getTopCenter().getY(),
				targetObject.getBottomCenter().getX(),
				targetObject.getBottomCenter().getY()
			});
		/*Line
		rLine.setStartX(sourceObject.getTopCenter().getX());
		rLine.setStartY(sourceObject.getTopCenter().getY());
		rLine.setEndX(targetObject.getBottomCenter().getX());
		rLine.setEndY(targetObject.getBottomCenter().getY());
		*/
		break;
	case 1:
		rLine.getPoints().addAll(new Double[]{
				sourceObject.getMiddleRight().getX(),
				sourceObject.getMiddleRight().getY(),
				targetObject.getMiddleLeft().getX(),
				targetObject.getMiddleLeft().getY()
			});
		/*Line
		rLine.setStartX(sourceObject.getMiddleRight().getX());
		rLine.setStartY(sourceObject.getMiddleRight().getY());
		rLine.setEndX(targetObject.getMiddleLeft().getX());
		rLine.setEndY(targetObject.getMiddleLeft().getY());
		*/
		break;
	case 2:
		rLine.getPoints().addAll(new Double[]{
				sourceObject.getMiddleLeft().getX(),
				sourceObject.getMiddleLeft().getY(),
				targetObject.getMiddleRight().getX(),
				targetObject.getMiddleRight().getY()
			});
		/*Line
		rLine.setStartX(sourceObject.getMiddleLeft().getX());
		rLine.setStartY(sourceObject.getMiddleLeft().getY());
		rLine.setEndX(targetObject.getMiddleRight().getX());
		rLine.setEndY(targetObject.getMiddleRight().getY());
		*/
		break;
	case 3:
		rLine.getPoints().addAll(new Double[]{
				sourceObject.getBottomCenter().getX(),
				sourceObject.getBottomCenter().getY(),
				targetObject.getTopCenter().getX(),
				targetObject.getTopCenter().getY()
			});
		/*Line 
		rLine.setStartX(sourceObject.getBottomCenter().getX());
		rLine.setStartY(sourceObject.getBottomCenter().getY());
		rLine.setEndX(targetObject.getTopCenter().getX());
		rLine.setEndY(targetObject.getTopCenter().getY());
		*/
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
	
	/*TEMPORARY
	private void refreshArrow(){
		int length = 10;
		Polyline p = (Polyline) rSymbol;
		Point2D lpoint;
		Point2D rpoint;
		double x = rLine.getEndX();
		double y = rLine.getEndY();
		
		if ((x == targetObject.getTopCenter().getX()) && (y == targetObject.getTopCenter().getY())){
			lpoint = new Point2D(x - length, y - length);
			rpoint = new Point2D(x + length, y - length);
		} else if ((x == targetObject.getBottomCenter().getX()) && (y == targetObject.getBottomCenter().getY())){
			lpoint = new Point2D(x - length, y + length);
			rpoint = new Point2D(x + length, y + length);
		} else if ((x == targetObject.getMiddleLeft().getX()) && (y == targetObject.getMiddleLeft().getY())){
			lpoint = new Point2D(x - length, y - length);
			rpoint = new Point2D(x - length, y + length);
		} else {
			lpoint = new Point2D(x + length, y - length);
			rpoint = new Point2D(x + length, y + length);
		}
		
		p.getPoints().setAll(lpoint.getX(), lpoint.getY(),
								x, y,
								rpoint.getX(), rpoint.getY());
	}
	/**
	 * resets the position for the AGGREGATION and COMPOSITION relation types
	 */
	
	/*TEMPORARY
	private void refreshDiamond(){
		Polygon p = (Polygon) rSymbol;
		double x = rLine.getEndX();
		double y = rLine.getEndY();
		p.setRotate(0);
		rSymbol.setLayoutX(x - 5);
		
		if ((x == targetObject.getTopCenter().getX()) && (y == targetObject.getTopCenter().getY())){
			rSymbol.setLayoutY(y);
			rSymbol.setLayoutY(y - 16);
		} else if ((x == targetObject.getBottomCenter().getX()) && (y == targetObject.getBottomCenter().getY())){
			rSymbol.setLayoutY(y);
		} else if ((x == targetObject.getMiddleLeft().getX()) && (y == targetObject.getMiddleLeft().getY())){
			p.setRotate(90);
			rSymbol.setLayoutX(x - 12);
			rSymbol.setLayoutY(y - 8);
		} else {
			p.setRotate(90);
			rSymbol.setLayoutX(x + 3);
			rSymbol.setLayoutY(y - 8);
		}
	}
	
	/**
	 * resets the position of the GENERALIZATION relation type
	 */
	
	/*TEMPORARY
	private void refreshTriangle(){
		Polygon p = (Polygon) rSymbol;
		double x = rLine.getEndX();
		double y = rLine.getEndY();
		p.setRotate(0);
		rSymbol.setLayoutX(x - 12);
		
		if ((x == targetObject.getTopCenter().getX()) && (y == targetObject.getTopCenter().getY())){
			rSymbol.setLayoutY(y);
			rSymbol.setRotate(180);
			rSymbol.setLayoutY(y - 16);
		} else if ((x == targetObject.getBottomCenter().getX()) && (y == targetObject.getBottomCenter().getY())){
			rSymbol.setLayoutY(y);
		} else if ((x == targetObject.getMiddleLeft().getX()) && (y == targetObject.getMiddleLeft().getY())){
			rSymbol.setLayoutY(y - 8);
			p.setRotate(90);
			rSymbol.setLayoutX(x - 18);
		} else {
			p.setRotate(-90);
			rSymbol.setLayoutX(x - 5);
			rSymbol.setLayoutY(y - 8);
		}
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
		
		/*TEMPORARY
		switch (this.relationType) {
		case ASSOCIATION:
			refreshArrow();
			break;
		case DEPENDENCY:
			refreshArrow();
			break;
		case AGGREGATION:
			refreshDiamond();
			break;
		case COMPOSITION:
			refreshDiamond();
			break;
		case GENERALIZATION:
			refreshTriangle();
			break;
		default:
			break;
		}
	
		
		rText.setLayoutX((rLine.getStartX() + rLine.getEndX()) / 2);
		rText.setLayoutY((rLine.getStartY() + rLine.getEndY()) / 2);
		
		*/
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
