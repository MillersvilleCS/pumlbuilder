/***
 * @author Merv Fansler
 * @since February 27, 2015
 * @version 0.1.0
 */
package edu.millersville.cs.bitsplease.view;

import edu.millersville.cs.bitsplease.model.UMLSymbol;
import javafx.scene.Group;

public abstract class UMLSymbolView extends Group {

	public abstract UMLSymbol getUMLSymbol();
}
