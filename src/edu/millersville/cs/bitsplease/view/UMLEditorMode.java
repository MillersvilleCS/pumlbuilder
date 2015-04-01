/**
 * @author Merv Fansler
 * @since February 25, 2015
 * @version 0.2.0
 */

package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLRelationType;

/**
 * Enumerates possible actions that can be carried out in the DocumentViewPane.
 */
public enum UMLEditorMode {
	SELECT ("/img/Select.png","Move/Select"),
	CREATE_CLASS ("/img/Class.png", "New Class"),
	CREATE_INTERFACE ("/img/Interface.png", "New Interface"),
	CREATE_USER ("/img/User.png", "New User"),
	CREATE_USE_CASE ("/img/UseCase.png", "New Use Case"),
	CREATE_ASSOCIATION ("/img/Association.png","New Association", UMLRelationType.ASSOCIATION),
	CREATE_DEPENDENCY ("/img/Dependency.png", "New Dependency", UMLRelationType.DEPENDENCY),
	CREATE_AGGREGATION ("/img/Aggregation.png", "New Aggregation", UMLRelationType.AGGREGATION),
	CREATE_COMPOSITION ("/img/Composition.png", "New Composition", UMLRelationType.COMPOSITION),
	CREATE_GENERALIZATION ("/img/Generalization.png", "New Generalization", UMLRelationType.GENERALIZATION),
	DELETE ("/img/Delete.png", "Delete Object");
	
	private final String imagePath;
	private final String tooltipText;
	private final UMLRelationType relationType;
	
	UMLEditorMode(String imagePath, String tooltipText) {
		this.imagePath = imagePath;
		this.tooltipText = tooltipText;
		relationType = null;
	}
	
	UMLEditorMode(String imagePath, String tooltipText, UMLRelationType relationType) {
		this.imagePath = imagePath;
		this.tooltipText = tooltipText;		
		this.relationType = relationType;
	}

	/**
	 * @return the relationType
	 */
	public UMLRelationType getRelationType() {
		return relationType;
	}

	/**
	 * @return the tooltipText
	 */
	public String getTooltipText() {
		return tooltipText;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
}
