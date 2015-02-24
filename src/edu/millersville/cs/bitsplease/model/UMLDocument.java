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
 * @return
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
 * 
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

public UMLObjectSymbol getSelectedObject(){
	return this.selectedObject;
}

public void setFileName(String fileName){
	this.fileName = fileName;
}

public void setObjectList(ArrayList<UMLSymbol> obj){
	this.objectList = obj;
}

public void setSelectedObject(UMLObjectSymbol object){
	this.selectedObject = object;
	this.selectedObject.setSelectedStatus(true);
}

public void deselectObject(){
	this.selectedObject.setSelectedStatus(false);
	this.selectedObject = null;
}

public UMLRelationSymbol createRelation(UMLRelationType relationType,
		UMLObjectSymbol source, UMLObjectSymbol target){
	
	return new UMLRelationSymbol(source, target, relationType); 
}

public UMLObjectSymbol createObject(Point2D origin, double height, double width){
	return new UMLObjectSymbol(origin, height, width);
}

}
