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
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import org.reactfx.ConnectableEventSource;
import org.reactfx.ConnectableEventStream;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import edu.millersville.cs.bitsplease.change.SelectedSymbolChange;
import edu.millersville.cs.bitsplease.change.SymbolListChange;
import edu.millersville.cs.bitsplease.change.UMLDocumentChange;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLInterfaceSymbol;
import edu.millersville.cs.bitsplease.model.UMLObjectSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLSymbol;
import edu.millersville.cs.bitsplease.model.UMLUseCaseSymbol;

/**
 * Document View GUI Component
 * This component provides the main view of the current UML document.
 */
public class DocumentViewPane extends Pane {
	
	// State Variables
	private ObjectProperty<UMLSymbol> selectedUMLSymbol = new SimpleObjectProperty<UMLSymbol>();
	private ArrayList<UMLSymbol> entityList = new ArrayList<UMLSymbol>();
	private EventStream<UMLDocumentChange<?>> documentChanges;
	private ConnectableEventStream<UMLDocumentChange<?>> symbolPropertyChanges = new ConnectableEventSource<>();
	
	/**
	 * Default Constructor
	 */
	public DocumentViewPane() {
		super();
		
		// track changes of selected UMLSymbol
		EventStream<SelectedSymbolChange> selectedSymbolChanges =
			EventStreams.changesOf(getSelectedUMLSymbol()).map(
			c -> new SelectedSymbolChange(c, this)
			);
		
		// change stream for symbol addition and deletion
		EventStream<SymbolListChange> symbolListChanges = 
			EventStreams.changesOf(getChildren()).map(
			c -> {
				c.next();
				return new SymbolListChange((ListChangeListener.Change<UMLSymbol>) c, this);
			});
		
		documentChanges = EventStreams.merge(selectedSymbolChanges, symbolListChanges, symbolPropertyChanges);	
	}
	
	/**
	 * Add UML elements to the view
	 * @param symbol UML element to add to display
	 */
	public void addUMLSymbol(UMLSymbol symbol) {
		this.getChildren().add(symbol);
		entityList.add(symbol);
		setSelectedUMLSymbol(symbol);
		
		symbolPropertyChanges.connectTo(symbol.getChangeStream());
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
		if (toDelete instanceof UMLObjectSymbol) {
			
			UMLRelationSymbol[] associatedRelations = 
					getChildren().filtered(referencesUMLObject((UMLObjectSymbol)toDelete)).toArray(new UMLRelationSymbol[0]);
			
			for (UMLRelationSymbol r : associatedRelations)	{
				removeUMLSymbol((UMLSymbol) r);
			};
		}
		
		if (toDelete.isSelected()) { setSelectedUMLSymbol(null); }
		getChildren().remove(toDelete);
		entityList.remove(toDelete);
	}
	
	/**
	 * Handle removing all entities from the entity list as well as the Scene
	 */
	public void removeAllSymbols() {
		this.getChildren().removeAll(this.getChildren());
		entityList = new ArrayList<UMLSymbol>();
	}
	
	/**
	 * Return a list of all entities held by the Document
	 * @return list of all UMLSymbols held within the Document View Pane
	 */
	public ArrayList<UMLSymbol> getEntities() {
		return this.entityList;
	}
	
	/**
	 * Set a list of all entities held by the Document. Each entity
	 * within the list is added to the Document view.
	 * @param _entityList List of entities to set on the Document.
	 */
	public void setEntities(ArrayList<UMLSymbol> _entityList) {
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
				((UMLClassSymbol) oldSymbol).setNonEditableUMLClassSymbol();
			} else if (oldSymbol instanceof UMLInterfaceSymbol) {
				((UMLInterfaceSymbol) oldSymbol).setNonEditableUMLInterfaceSymbol();
			} else if (oldSymbol instanceof UMLUseCaseSymbol) {
				((UMLUseCaseSymbol) oldSymbol).setNonEditableUMLUseCaseSymbol();
			}
			oldSymbol.requestFocus();
		}
		if (umlSymbol != null) {
			umlSymbol.setSelected(true);
		}
		this.selectedUMLSymbol.setValue(umlSymbol);
	}
	
	/**
	 * @return the documentChanges
	 */
	public EventStream<UMLDocumentChange<?>> getDocumentChanges() {
		return documentChanges;
	}
}