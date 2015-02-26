package edu.millersville.cs.bitsplease.view;


import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;

public class ObjectViewPane extends VBox {
	
	private Label ovpLabel = new Label("Selected Object");
	private Label xLabel = new Label("X: ");
	private Label yLabel = new Label("Y: ");
	private Label hLabel = new Label("Height: ");
	private Label wLabel = new Label("Width:");
	
	private TextField xField = new TextField("0");
	private TextField yField = new TextField("0");
	private TextField hField = new TextField("0");
	private TextField wField = new TextField("0");
	
	public ObjectViewPane(){
		setAlignment(Pos.TOP_CENTER);
		getChildren().addAll(ovpLabel, xLabel, xField, yLabel, yField, hLabel, hField,
					wLabel, wField);
	}
	
	public double getXField(){
		return Double.parseDouble(xField.getText());
	}
	
	public double getYField(){
		return Double.parseDouble(yField.getText());
	}
	
	public double getHField(){
		return Double.parseDouble(hField.getText());
	}
	
	public double getWField(){
		return Double.parseDouble(wField.getText());
	}
	public void setXField(double xValue){
		xField.setText(Double.toString(xValue));
	}
	
	public void setYField(double yValue){
		yField.setText(Double.toString(yValue));
	}
	
	public void setHField(double hValue){
		hField.setText(Double.toString(hValue));
	}
	
	public void setWField(double wValue){
		wField.setText(Double.toString(wValue));
	}

}
