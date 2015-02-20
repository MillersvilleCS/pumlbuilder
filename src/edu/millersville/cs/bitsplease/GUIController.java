/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease;

import edu.millersville.cs.bitsplease.model.UMLSymbol;

public class GUIController {
	private UMLSymbol selectedUMLSymbol;

	/**
	 * @return the selectedUMLSymbol
	 */
	public UMLSymbol getSelectedUMLSymbol() {
		return selectedUMLSymbol;
	}

	/**
	 * @param selectedUMLSymbol the selectedUMLSymbol to set
	 */
	public void setSelectedUMLSymbol(UMLSymbol selectedUMLSymbol) {
		this.selectedUMLSymbol = selectedUMLSymbol;
	}
	
}
