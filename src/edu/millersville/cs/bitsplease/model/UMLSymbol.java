/***
 * @author Merv Fansler
 * @author Kevin Fisher
 * @since February 27, 2015
 * @version 0.3.0
 */
package edu.millersville.cs.bitsplease.model;

import java.io.Externalizable;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import edu.millersville.cs.bitsplease.change.StringPropertyChange;
import edu.millersville.cs.bitsplease.change.UMLDocumentChange;

/**
 * 
 * Abstract class serves as a generic representation of all UML Symbols that will
 * exist within the Penultimate UML Builder. All symbols contain a field list, an
 * identifier, and an isSelected flag.
 *
 */
public abstract class UMLSymbol extends Region implements Externalizable{
	protected boolean isSelected = false;
	protected StringProperty identifier = new SimpleStringProperty("Untitled");
	
	private DropShadow dropShadow = new DropShadow(6,Color.MEDIUMORCHID);
	
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier.getValue();
	}

	/**
	 * @return a StringProperty of the identifier
	 */
	public StringProperty getIdentifierProperty() {
		return identifier;
	}

	/**
	 * retrieves the fields of the object that are necessary for saving
	 * @return list of the fields
	 */
	public ObservableList<Property<? extends Object>> getFields() {
		ObservableList<Property<? extends Object>> fields = FXCollections.observableArrayList();
		fields.add(identifier);
		return fields;
	}

	/**
	 * provides an EventStream of changes that the object can undergo
	 * @return EventStream of UMLDocumentChanges
	 */
	public EventStream<UMLDocumentChange<?>> getChangeStream() {
		return EventStreams.changesOf(this.identifier).map(
			c -> new StringPropertyChange(c, this.identifier)
			);
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		if (isSelected) {
			this.setEffect(dropShadow);
		} else {
			this.setEffect(null);
		}
		this.isSelected = isSelected;
	}
	
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier.setValue(identifier);
	}
}
