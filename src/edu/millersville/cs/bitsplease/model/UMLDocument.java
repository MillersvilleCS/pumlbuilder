/**
 * @author Kevin Fisher
 * @since February 23, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease.model;

import java.util.ArrayList;
import javafx.geometry.Point2D;

public class UMLDocument {
	
private ArrayList<UMLSymbol> objectList;
private String fileName;
private UMLObjectSymbol selectedObject;

public UMLDocument(){
	this.objectList = new ArrayList<UMLSymbol>();
	this.fileName = "Untitled Document";
}

public UMLDocument(String file){
	this.objectList = new ArrayList<UMLSymbol>();
	this.fileName = file;
}

public UMLDocument(ArrayList<UMLSymbol> objects){
	this.objectList = objects;
	this.fileName = "Untitled Document";
}

public UMLDocument(ArrayList<UMLSymbol> objects, String file){
	this.objectList = objects;
	this.fileName = file;
}

/**
 * Accessor Methods
 */

/**
 * 
 * @return List of UMLSymbols currently held by the UMLDocument object
 */
public ArrayList<UMLSymbol> getObjectList(){
	return this.objectList;
}

public String getFileName(){
	return this.fileName;
}

/**
 * Filters object list and returns a new ArrayList of UMLObjectSymbol instances
 * @return ArrayList<UMLObjectSymbol> objects List of all UMLObjectSymbol instances within the UMLDocument
 */
public ArrayList<UMLObjectSymbol> getObjects(){
	
	ArrayList<UMLObjectSymbol> objects = new ArrayList<UMLObjectSymbol>();
	
	for(UMLSymbol obj:this.objectList){
		if(obj instanceof UMLObjectSymbol){
			objects.add((UMLObjectSymbol)obj);
		}
	}
	return objects;
}

/**
 * Method to filter list of UMLSymbols to return 
 * all instances of UMLRelationSymbols held by a UMLDocument.
 * @return ArrayList<UMLObjectSymbol> relations: List of all UMLRelationSymbol instances in UMLDocument.
 */
public ArrayList<UMLRelationSymbol> getRelations(){
	
	ArrayList<UMLRelationSymbol> relations = new ArrayList<UMLRelationSymbol>();
	
	for(UMLSymbol rel:this.objectList){
		if(rel instanceof UMLRelationSymbol){
			relations.add((UMLRelationSymbol) rel);
		}
	}
	return relations;
}

/**
 * 
 * @return currently selected UMLSymbol
 */
public UMLObjectSymbol getSelectedObject(){
	return this.selectedObject;
}

/**
 * 
 * @param fileName plaintext name of UMLDocument
 */
public void setFileName(String fileName){
	this.fileName = fileName;
}

/**
 * Assign a list of UMLSymbols to current UMLDocument object
 * @param obj list of UMLSymbols to assign to current UMLDocument object
 */
public void setObjectList(ArrayList<UMLSymbol> obj){
	this.objectList = obj;
}

/******************************************
 * List operations
 ******************************************/
/**
 * 
 * @param relationType Type of relation to assign to newly created relation
 * @param source UMLObjectSymbol from which the relation originates
 * @param target UMLObjectSymbol to which the relation points
 */
public void addRelation(UMLRelationType relationType,
		UMLObjectSymbol source, UMLObjectSymbol target){
	
	 objectList.add(new UMLRelationSymbol(source, target, relationType)); 
}

/**
 * Add a UMLObjectSymbol to the UMLDocument
 * @param origin point at which to instantiate object symbol
 * @param height height of instantiated object symbol.
 * @param width width of instantiated object symbol.
 */
public void addObject(Point2D origin, double height, double width){
	
	objectList.add(new UMLObjectSymbol(origin, height, width));
}
/**
 * Remove UMLSymbol from UMLDocuments symbol list at specified index.
 * @param i index at which to remove UMLSymbol.
 */
public void removeSymbol(int i){
	objectList.remove(i);
}

public void addClass(double x, double y, int prefWidth, int prefHeight) {
	objectList.add(new UMLClassSymbol(new Point2D(x, y), prefWidth, prefHeight));
}

public void addClass(UMLClassSymbol umlClassSymbol) {
	objectList.add(umlClassSymbol);
}

}
