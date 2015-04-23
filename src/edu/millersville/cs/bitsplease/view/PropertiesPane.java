/**
 * @author Merv Fansler	
 * @author Kevin Fisher
 * @since  February 24, 2015
 * @version 0.2.0
 * 
 */

package edu.millersville.cs.bitsplease.view;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;
/**
 * Properties Pane GUI Component
 * This component provides a field editing interface the fields of the 
 * currently selected object.
 */
public class PropertiesPane extends VBox implements ChangeListener<UMLSymbol> {
	
	private Label paneTitle = new Label("Properties");
	private ComboBox<UMLRelationType> relDropDown = new ComboBox<>();
	/**
	 * Constructor
	 */
	public PropertiesPane(ObjectProperty<UMLSymbol> selectedSymbol) {
		super();
		
		getChildren().add(paneTitle);
		this.setSpacing(10d);
		this.setStyle("-fx-background-color: #aaa; -fx-padding: 20; -fx-text-fill: white; -fx-font-weight: bold");
		
		relDropDown.getItems().addAll(UMLRelationType.values());
		relDropDown.getSelectionModel().selectedItemProperty();
		selectedSymbol.addListener(this);
	}
	
	/** 
	 * Method required for implementing ChangeListener<UMLSymbol>
	 * This method enables the Properties Pane to subscribe to a change in selected 
	 * object.
	 * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void changed(ObservableValue<? extends UMLSymbol> observable,
			UMLSymbol oldValue, UMLSymbol newValue) {
		if (oldValue != newValue) {
			this.getChildren().retainAll(paneTitle);
			if(oldValue instanceof UMLRelationSymbol){
				((UMLRelationSymbol) oldValue).getUMLRelationTypeProperty().unbindBidirectional(relDropDown.valueProperty());
			}
		}
		if (newValue != null) {
			newValue.getFields().forEach(p -> {
				TextField tf = new TextField();
				if (p instanceof StringProperty) {
					Bindings.bindBidirectional(tf.textProperty(), (StringProperty) p);
				
				} else if (p instanceof DoubleProperty) {
					Bindings.bindBidirectional(tf.textProperty(), (DoubleProperty) p, new NumberStringConverter());
					tf.setTextFormatter(new TextFormatter<String>(c -> {
						c.setText(c.getText().replaceAll("[^\\d]", ""));
						return c;
					}));
				}else if(p instanceof ObjectProperty<?>){
					
					Bindings.bindBidirectional(relDropDown.valueProperty(), (ObjectProperty)p);
				}
				
				this.getChildren().add(tf);
				
		
			});
			
			if(newValue instanceof UMLRelationSymbol){
				
				this.getChildren().add(relDropDown);
			}
		}
	}

}
