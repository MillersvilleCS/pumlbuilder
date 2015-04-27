/**
 * @author Mike Sims
 * @version 0.3.0
 */

import static org.junit.Assert.*;
import javafx.geometry.Point2D;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;


public class UMLRelationTextTest {
	@Rule
	public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();
	
	UMLRelationSymbol depen, assoc, aggre, compo, gener;
	
	//Test dependency relation text field
	@Before
	public void setUpDependency() throws Exception {
		UMLClassSymbol c1 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c2 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		depen = new UMLRelationSymbol(c1, c2, UMLRelationType.DEPENDENCY);
	}
	
	@Test
	public void testTextFieldDependency() {
		assertEquals("Untitled dependency", depen.getIdentifier());
	}
	
	//Test association relation text field
	@Before
	public void setUpAssociation() throws Exception {
		UMLClassSymbol c3 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c4 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		assoc = new UMLRelationSymbol(c3, c4, UMLRelationType.ASSOCIATION);
	}
	
	@Test
	public void testTextFieldAssociation() {
		assertEquals("Untitled association", assoc.getIdentifier());
	}
	
	//Test aggregation relation text field
	@Before
	public void setUpAggregation() throws Exception {
		UMLClassSymbol c5 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c6 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		aggre = new UMLRelationSymbol(c5, c6, UMLRelationType.AGGREGATION);
	}
	
	@Test
	public void testTextFieldAggregation() {
		assertEquals("Untitled aggregation", aggre.getIdentifier());
	}
	
	//Test composition relation text field
	@Before
	public void setUpComposition() throws Exception {
		UMLClassSymbol c7 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c8 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		compo = new UMLRelationSymbol(c7, c8, UMLRelationType.COMPOSITION);
	}
	
	@Test
	public void testTextFieldComposition() {
		assertEquals("Untitled composition", compo.getIdentifier());
	}
	
	//Test generalization relation text field
	@Before
	public void setUpGeneralization() throws Exception {
		UMLClassSymbol c9 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c10 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		gener = new UMLRelationSymbol(c9, c10, UMLRelationType.GENERALIZATION);
	}
	
	@Test
	public void testTextFieldGeneralization() {
		assertEquals("Untitled generalization", gener.getIdentifier());
	}
}
