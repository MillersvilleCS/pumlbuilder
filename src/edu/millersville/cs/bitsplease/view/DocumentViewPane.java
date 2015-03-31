/**
 * @author Merv Fansler
 * @author Josh Wakefield
 * @since February 25, 2015
 * @version 0.2.0
 */

package edu.millersville.cs.bitsplease.view;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;

/**
 * Document View GUI Component
 * This component provides the main view of the current UML document.
 */
public class DocumentViewPane extends Pane {

	// State Variables
	private ObjectProperty<UMLSymbol> selectedUMLSymbol = new SimpleObjectProperty<UMLSymbol>();
	private ArrayList<UMLSymbol> entityList = new ArrayList<UMLSymbol>();
	
	/**
	 * Default Constructor
	 */
	public DocumentViewPane() {
		super();
	}
	
	/**
	 * Add UML elements to the view
	 * @param symbol UML element to add to display
	 */
	public void addUMLSymbol(UMLSymbol symbol) {
		this.getChildren().add(symbol);
		entityList.add(symbol);
	}
	
	/**
	 * Method to refresh relations attached to given object
	 * @param obj the object which has changed
	 */
	public void refreshRelations(UMLObjectSymbol obj) {
		for (Node relView : getChildren().filtered(referencesUMLObject(obj))) {
			((UMLRelationSymbol)relView).refresh();
		}
	}
	
	/**
	 * Remove a UML element symbol from the Document
	 * @param toDelete UMLSymbol to be removed from the Document
	 */
	public void removeUMLSymbol(UMLSymbol toDelete) {
		
		// remove all relation symbols that references an object being removed
		if (toDelete instanceof UMLClassSymbol) {
			getChildren().removeIf(
					referencesUMLObject((UMLObjectSymbol) toDelete));
		}
		
		getChildren().remove(toDelete);
		entityList.remove(toDelete);
	}
	
	/**
	 * Return a list of all entities held by the Document
	 * @return list of all UMLSymbols held within the Document View Pane
	 */
	public ArrayList<UMLSymbol> getEntities(){
		return this.entityList;
	}
	
	/**
	 * Set a list of all entities held by the Document. Each entity
	 * within the list is added to the Document view.
	 * @param _entityList List of entities to set on the Document.
	 */
	public void setEntities(ArrayList<UMLSymbol> _entityList){
		_entityList.forEach( uml -> addUMLSymbol(uml));
	}
	
	/**
	 * Provides a means for finding relations of a given object
	 * @param obj an object
	 * @return predicate that selects for relating UMLRelationViews
	 */
	private Predicate<Node> referencesUMLObject (UMLObjectSymbol obj) {
		return n -> n instanceof UMLRelationSymbol && 
				(((UMLRelationSymbol)n).getSourceObject() == obj ||
				((UMLRelationSymbol)n).getTargetObject() == obj);
	}

	/**
	 * @return the selectedUMLSymbol
	 */
	public ObjectProperty<UMLSymbol> getSelectedUMLSymbol() {
		return selectedUMLSymbol;
	}

	/**
	 * @param selectedUMLSymbol the selectedUMLSymbol to set
	 */
	public void setSelectedUMLSymbol(UMLSymbol umlSymbol) {
		UMLSymbol oldSymbol = this.selectedUMLSymbol.getValue();
		if (oldSymbol != null) {
			oldSymbol.setSelected(false);
			if (oldSymbol instanceof UMLClassSymbol)  {
				((UMLClassSymbol) oldSymbol).setNonEditableUMLSymbol();
			}
			oldSymbol.requestFocus();
		}
		if (umlSymbol != null) {
			umlSymbol.setSelected(true);
		}
		this.selectedUMLSymbol.setValue(umlSymbol);
	}
}
