/***
 * @author Merv Fansler
 * @since February 27, 2015
 * @version 0.1.1
 */
package edu.millersville.cs.bitsplease.model;

import javafx.scene.Group;

public abstract class UMLSymbol extends Group {
	protected boolean isSelected = false;
	protected String identifier = "Untitled";
	
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}
	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
