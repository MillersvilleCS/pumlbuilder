package edu.millersville.cs.bitsplease.view;


import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;

public class ObjectViewPane extends VBox {
	
	private Label ovpLabel = new Label("Selected Object: ");
	private Label xLabel = new Label("X: ");
	private Label yLabel = new Label("Y: ");
	private Label hLabel = new Label("Height: ");
	private Label wLabel = new Label("Width:");
	
	private TextField ovpField = new TextField();
	private TextField xField = new TextField("0");
	private TextField yField = new TextField("0");
	private TextField hField = new TextField("0");
	private TextField wField = new TextField("0");
	
	public ObjectViewPane(){
		setAlignment(Pos.TOP_CENTER);
		getChildren().addAll(ovpLabel, ovpField, xLabel, xField, yLabel, yField, hLabel, hField,
					wLabel, wField);
	}
	
	/**\
	 * 
	 * @return The value of the field containing the name or identifier of a UMLObjectSymbol
	 */
	public String getObjectNameField(){
		return ovpField.getText();
	}

	/**
	 * 
	 * @return the x value of the field representing a UMLObjectSymbol's x value
	 */
	public double getXField(){
		return Double.parseDouble(xField.getText());
	}
	
	
	/**
	 * 
	 * @return the y value of the field representing a UMLObjectSymbol's y value
	 */
	public double getYField(){
		return Double.parseDouble(yField.getText());
	}
	
	/**
	 * 
	 * @return the value of the field representing a UMLObjectSymbol's height
	 */
	public double getHField(){
		return Double.parseDouble(hField.getText());
	}
	
	/**
	 * 
	 * @return the value of the field representing a UMLObjectSymbol's width 
	 */
	public double getWField(){
		return Double.parseDouble(wField.getText());
	}
	
	/**
	 * 
	 * @param name String Name to which to set the currently selected UMLObjectSymbol
	 */
	public void setObjectNameField(String name){
		ovpField.setText(name);
	}
	
	/**
	 * 
	 * @param xValue double value to set the x-value of the currently selected UMLObjectSymbol
	 */
	public void setXField(double xValue){
		xField.setText(Double.toString(xValue));
	}
	
	/**
	 * 
	 * @param yValue double value to set the y-value of the currently selected UMLObjectSymbol
	 */
	public void setYField(double yValue){
		yField.setText(Double.toString(yValue));
	}
	
	/**
	 * 
	 * @param hValue double value to set the height of the currently selected UMLObjectSymbol
	 */
	public void setHField(double hValue){
		hField.setText(Double.toString(hValue));
	}
	
	/**
	 * 
	 * @param wValue double value to set the width of the currently selected UMLObjectSymbol
	 */
	public void setWField(double wValue){
		wField.setText(Double.toString(wValue));
	}

}
