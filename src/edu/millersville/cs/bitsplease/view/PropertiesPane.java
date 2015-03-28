/**
 * @author Merv Fansler	
 * @author Kevin Fisher
 * @since  February 24, 2015
 * @version 0.1.1
 * 
 */

package edu.millersville.cs.bitsplease.view;


import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import edu.millersville.cs.bitsplease.model.UMLSymbol;


public class PropertiesPane extends VBox implements ChangeListener<UMLSymbol> {
	
	private Label paneTitle = new Label("Properties");
	
	/**
	 * Constructor
	 */
	public PropertiesPane(ObjectProperty<UMLSymbol> selectedSymbol) {
		
		super();
		
		getChildren().add(paneTitle);
		this.setSpacing(10d);
		this.setStyle("-fx-background-color: #aaa; -fx-padding: 20; -fx-text-fill: white; -fx-font-weight: bold");
	
		selectedSymbol.addListener(this);
	}
	
	@Override
	public void changed(ObservableValue<? extends UMLSymbol> observable,
			UMLSymbol oldValue, UMLSymbol newValue) {
		if (oldValue != newValue) {
			this.getChildren().retainAll(paneTitle);
		}
		if (newValue != null) {
			newValue.getFields().forEach(p -> {
				TextField tf = new TextField();
				if (p instanceof StringProperty) {
					Bindings.bindBidirectional(tf.textProperty(), (StringProperty) p);
				} else if (p instanceof DoubleProperty) {
					Bindings.bindBidirectional(tf.textProperty(), (DoubleProperty) p, new NumberStringConverter());
				}
				
				this.getChildren().add(tf);
			});
		}
	}

}
