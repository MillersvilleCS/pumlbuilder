/**
 * @author Michael Sims
 * @author Merv Fansler
 * @author Joe Martello
 * @author Kevin Fisher
 * @since February 25, 2015
 * @version 0.2.0
 */

package edu.millersville.cs.bitsplease.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

/**
 * A model-view representation of a UML relation symbol.
 * A UMLRelationSymbol object can be represented as any major
 * UML relation types
 */
public class UMLRelationSymbol extends UMLSymbol {

	// Style Constants 
	final double ARROW_SIZE = 10d;
	final double DIAMOND_SIZE = 12d;
	final double SELF_RELATION_SIZE = 60d;
	
	// Model Components
	private	UMLRelationType relationType;
	private UMLObjectSymbol sourceObject;
	private UMLObjectSymbol targetObject;
	
	// View Components
	private Polyline rLine;
	private Shape rSymbol;
	private TextField rText, sourceCardinality, targetCardinality;
	
	/**
	 * Default constructor for externalization
	 */
	public UMLRelationSymbol(){		
		super();
		
		this.setPickOnBounds(false);
		
		this.rLine = new Polyline();
		this.getChildren().add(rLine);
		
		initializeTextFields();
	}
	
	/**
	 * This is the typical constructor to be used when creating new relations using the
	 * GUI interface.
	 * @param sourceObject object to relate from
	 * @param targetObject object to relate to
	 * @param relationType type of relation
	 */
	public UMLRelationSymbol(UMLObjectSymbol sourceObject,
			UMLObjectSymbol targetObject, UMLRelationType relationType){
		super();
		
		this.sourceObject = sourceObject;
		this.targetObject = targetObject;
		this.relationType = relationType;
		
		// set default string to relation type
		identifier.setValue(relationType.toString());
		
		// clicks should NOT be captured based on bounding box
		this.setPickOnBounds(false);
		
		// create, add, and update line
		this.rLine = new Polyline();
		getChildren().add(rLine);
		refreshLine();
		
		initializeTextFields();
		refreshTextLayout();
		
		// create and position relation head symbol
		initSymbol(this.relationType);
	}

	/**
	 * Initializes the label and cardinality of the relation
	 */
	private void initializeTextFields() {
		// create and add identifier field
		rText = new TextField();
		rText.textProperty().bindBidirectional(identifier);
		rText.setFocusTraversable(false);
		rText.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		rText.setPrefWidth(150);
		getChildren().add(rText);
		
		sourceCardinality = new TextField("0..*");
		sourceCardinality.setFocusTraversable(false);
		sourceCardinality.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		sourceCardinality.setPrefWidth(40);
		getChildren().add(sourceCardinality);
		
		targetCardinality = new TextField("1..*");
		targetCardinality.setFocusTraversable(false);
		targetCardinality.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		targetCardinality.setPrefWidth(40);
		getChildren().add(targetCardinality);
	}
	
	/**
	 * Create the appropriate symbol head for the given UMLRelationType
	 * @param relType Type of relation to draw
	 */
	private void initSymbol(UMLRelationType relType){
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
		
		// must have at least 2 points to be valid
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
		
		if (sourceObject != targetObject) {
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
		} else {
			// loop for self-referencing relations
			Point2D startPoint = sourceObject.getBottomCenter();
			Point2D endPoint = sourceObject.getMiddleLeft();
			
			rLine.getPoints().addAll(new Double[]{
					startPoint.getX(),
					startPoint.getY(),
					startPoint.getX(),
					startPoint.getY() + SELF_RELATION_SIZE,
					endPoint.getX() - SELF_RELATION_SIZE,
					startPoint.getY() + SELF_RELATION_SIZE,
					endPoint.getX() - SELF_RELATION_SIZE,
					endPoint.getY(),
					endPoint.getX(),
					endPoint.getY()
			});
		}
	}
	
	/**
	 * @return the enum value representing the relation type
	 */
	public UMLRelationType getUMLRelationType() {
		return this.relationType;
	}
	
	/**
	 * @return the source UMLObjectSymbol
	 */
	public UMLObjectSymbol getSourceObject() {
		return this.sourceObject;
	}
	
	/**
	 * @return the target UMLObjectSymbol
	 */
	public UMLObjectSymbol getTargetObject() {
		return this.targetObject;
	}
	
	/**
	 * @param relType enum value representing the type of relation to create
	 */
	public void setUMLRelationType(UMLRelationType relType) {
		this.relationType = relType;
	}
	
	/**
	 * Sets the source object to a new UMLObjectSymbol
	 * @param source UMLObjectSymbol to be set as sourceObject
	 */
	public void setSourceObject(UMLObjectSymbol source) {
		this.sourceObject = source;
		this.sourceObject.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
			
			@Override
			public void changed(ObservableValue<? extends Bounds> observable,
					Bounds oldValue, Bounds newValue) {
				refresh();
			}
		});
	}
	
	/**
	 * Sets the target object to a new UMLObjectSymbol
	 * @param target the object to relate to
	 */
	public void setTargetObject(UMLObjectSymbol target) {
		this.targetObject = target;
		this.targetObject.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
			
			@Override
			public void changed(ObservableValue<? extends Bounds> observable,
					Bounds oldValue, Bounds newValue) {
				refresh();
			}
		});
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
		
		// refresh text position
		refreshTextLayout();
	}
	
	/***
	 * Computes the layout for the label and cardinality text.
	 */
	private void refreshTextLayout() {
		//cardinality textFields
		int offset = 25;
		double width = sourceCardinality.getPrefWidth();
		
		//slope of the line
		double m = (getEndPoint().getY() - getStartPoint().getY()) / (getEndPoint().getX() - getStartPoint().getX());
		
		if (getEndPoint().getX() - getStartPoint().getX() >= 0){
			sourceCardinality.setLayoutX(getStartPoint().getX());
			sourceCardinality.setLayoutY(m * offset + getStartPoint().getY());
			
			targetCardinality.setLayoutX(getEndPoint().getX() - width);
			targetCardinality.setLayoutY(m * (getEndPoint().getX() - width - getStartPoint().getX()) + getStartPoint().getY());
		} else {
			sourceCardinality.setLayoutX(getStartPoint().getX() - width);
			sourceCardinality.setLayoutY(-m * offset + getStartPoint().getY());
			
			targetCardinality.setLayoutX(getEndPoint().getX() + 5);
			targetCardinality.setLayoutY(m * (getEndPoint().getX() + width - getStartPoint().getX()) + getStartPoint().getY());
		}
		
		//used when slope is undefined
		if (Math.abs(m) > .5){
			if (getStartPoint().getY() < getEndPoint().getY()){
				sourceCardinality.setLayoutY(getStartPoint().getY());
				targetCardinality.setLayoutY(getEndPoint().getY() - offset);
			} else {
				sourceCardinality.setLayoutY(getStartPoint().getY() - offset);
				targetCardinality.setLayoutY(getEndPoint().getY());
			}
		}
		
		// relation textField
		if (sourceObject != targetObject) {
			rText.setLayoutX((getStartPoint().getX() + getEndPoint().getX()) / 2 );
			rText.setLayoutY((getStartPoint().getY() + getEndPoint().getY()) / 2 );
		} else {
			rText.setLayoutX(rLine.getLayoutBounds().getMinX() + (rLine.getLayoutBounds().getWidth())/2);
			rText.setLayoutY(rLine.getLayoutBounds().getMaxY());
			
			//sets cardinality textFields for self relation
			sourceCardinality.setLayoutY(getStartPoint().getY());
			targetCardinality.setLayoutX(getEndPoint().getX() - targetCardinality.getPrefWidth());
		}
	}
	
	/***
	 * This method provides a means for displaying editable fields in the
	 * Properties Pane.
	 * @return a list of all bindable fields of the UML Relation Symbol
	 */
	@Override
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = super.getFields();
		fields.add(sourceObject.getIdentifierProperty());
		fields.add(targetObject.getIdentifierProperty());
		return fields;
	}
	
	/** 
	 * Provides a means for saving this class to file
	 * @param ObjectOuput out an object stream to write to
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeObject(identifier.getValue());
		
		out.writeObject(getSourceObject());
		out.writeObject(getTargetObject());
		out.writeObject(getUMLRelationType());
		
	}

	/**
	 * Provides a means for restoring a saved version of this class
	 * @param ObjectInput in an object stream to read from
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		identifier.setValue((String)in.readObject());
		
		setSourceObject((UMLObjectSymbol)in.readObject());
		setTargetObject((UMLObjectSymbol)in.readObject());
		setUMLRelationType((UMLRelationType)in.readObject());
		
		refreshLine();
		refreshTextLayout();
		initSymbol(relationType);
	}
}

