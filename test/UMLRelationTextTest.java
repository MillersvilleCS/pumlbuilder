/**
 * @author Mike Sims
 * @version 0.3.0
 */

import static org.junit.Assert.*;
import javafx.geometry.Point2D;

import org.junit.*;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;


public class UMLRelationTextTest {
	@Rule
	public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();
	
	UMLRelationSymbol depen, assoc, aggre, compo, gener;
	
	//Test dependency relation text field
	@Test
	public void testDependency() throws Exception {
		UMLClassSymbol c1 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c2 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		depen = new UMLRelationSymbol(c1, c2, UMLRelationType.DEPENDENCY);
		
		assertEquals("Untitled dependency", depen.getIdentifier());
		
		//Test Modified text
		depen.setIdentifier("Changed dependency text");
		
		assertEquals("Changed dependency text", depen.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
	//Test association relation text field
	@Test
	public void testAssociation() throws Exception {
		UMLClassSymbol c3 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c4 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		assoc = new UMLRelationSymbol(c3, c4, UMLRelationType.ASSOCIATION);

		assertEquals("Untitled association", assoc.getIdentifier());

		//Test Modified text
		assoc.setIdentifier("Changed association text");
		
		assertEquals("Changed association text", assoc.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
	//Test aggregation relation text field
	@Test
	public void testAggregation() throws Exception {
		UMLClassSymbol c5 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c6 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		aggre = new UMLRelationSymbol(c5, c6, UMLRelationType.AGGREGATION);

		assertEquals("Untitled aggregation", aggre.getIdentifier());

		//Test Modified text
		aggre.setIdentifier("Changed aggregation text");
		
		assertEquals("Changed aggregation text", aggre.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
	//Test composition relation text field
	@Test
	public void testComposition() throws Exception {
		UMLClassSymbol c7 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c8 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		compo = new UMLRelationSymbol(c7, c8, UMLRelationType.COMPOSITION);
		
		assertEquals("Untitled composition", compo.getIdentifier());

		//Test Modified text
		compo.setIdentifier("Changed composition text");
		
		assertEquals("Changed composition text", compo.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
	//Test generalization relation text field
	@Test
	public void testGeneralization() throws Exception {
		UMLClassSymbol c9 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol c10 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		gener = new UMLRelationSymbol(c9, c10, UMLRelationType.GENERALIZATION);
		
		assertEquals("Untitled generalization", gener.getIdentifier());
		
		//Test Modified Text
		gener.setIdentifier("Changed generalization text");
		
		assertEquals("Changed generalization text", gener.getIdentifier());
	}
}
