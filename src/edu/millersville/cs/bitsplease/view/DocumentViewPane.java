/**
 * @author Merv Fansler
 * @author Josh Wakefield
 * @since February 25, 2015
 * @version 0.1.1
 */

package edu.millersville.cs.bitsplease.view;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLInterfaceSymbol;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;

public class DocumentViewPane extends Pane {

	private ObjectProperty<UMLSymbol> selectedUMLSymbol = new SimpleObjectProperty<UMLSymbol>();
	private ArrayList<UMLSymbol> entityList = new ArrayList<UMLSymbol>();
	/**
	 * Constructor
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
		_entityList.forEach( uml -> {
			
			if (uml instanceof UMLClassSymbol){
				addUMLSymbol((UMLClassSymbol)uml);
			}else if(uml instanceof UMLRelationSymbol){
				addUMLSymbol((UMLRelationSymbol)uml);
			}else if(uml instanceof UMLInterfaceSymbol){
				addUMLSymbol((UMLInterfaceSymbol)uml);
			}
			
		});
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
				((UMLClassSymbol) oldSymbol).togglenoneditableUMLSymbol();
			}
			oldSymbol.requestFocus();
		}
		if (umlSymbol != null) {
			umlSymbol.setSelected(true);
		}
		this.selectedUMLSymbol.setValue(umlSymbol);
	}
}
