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
	
	UMLRelationSymbol depen, depenEdit,
					  assoc, assocEdit,
					  aggre, aggreEdit,
					  compo, compoEdit,
					  gener, generEdit;
	
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
	
	//Test editing dependency relation text field
	@Before
	public void editDependency() throws Exception {
		UMLClassSymbol cEdit1 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol cEdit2 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		depenEdit = new UMLRelationSymbol(cEdit1, cEdit2, UMLRelationType.DEPENDENCY);
		depenEdit.setIdentifier("Changed dependency text");
	}
	
	@Test
	public void testEditedTextDependency() {
		assertEquals("Changed dependency text", depenEdit.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
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
	
	//Test editing dependency relation text field
	@Before
	public void editAssociation() throws Exception {
		UMLClassSymbol cEdit3 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol cEdit4 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		assocEdit = new UMLRelationSymbol(cEdit3, cEdit4, UMLRelationType.ASSOCIATION);
		assocEdit.setIdentifier("Changed association text");
	}
	
	@Test
	public void testEditedTextAssociation() {
		assertEquals("Changed association text", assocEdit.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
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
	
	//Test editing aggregation relation text field
	@Before
	public void editAggregation() throws Exception {
		UMLClassSymbol cEdit5 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol cEdit6 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		aggreEdit = new UMLRelationSymbol(cEdit5, cEdit6, UMLRelationType.AGGREGATION);
		aggreEdit.setIdentifier("Changed aggregation text");
	}
	
	@Test
	public void testEditedTextAggregation() {
		assertEquals("Changed aggregation text", aggreEdit.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
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
	
	//Test editing aggregation relation text field
	@Before
	public void editComposition() throws Exception {
		UMLClassSymbol cEdit7 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol cEdit8 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		compoEdit = new UMLRelationSymbol(cEdit7, cEdit8, UMLRelationType.COMPOSITION);
		compoEdit.setIdentifier("Changed composition text");
	}
	
	@Test
	public void testEditedTextComposition() {
		assertEquals("Changed composition text", compoEdit.getIdentifier());
	}
	
	
	//-----------------------------------------------------------------------
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
	
	//Test editing generalization relation text field
	@Before
	public void editGeneralization() throws Exception {
		UMLClassSymbol cEdit9 = new UMLClassSymbol(new Point2D(0,100), 100, 100);
		UMLClassSymbol cEdit10 = new UMLClassSymbol(new Point2D(10,100), 100, 100);
		generEdit = new UMLRelationSymbol(cEdit9, cEdit10, UMLRelationType.GENERALIZATION);
		generEdit.setIdentifier("Changed generalization text");
	}
	
	@Test
	public void testEditedTextGeneralization() {
		assertEquals("Changed generalization text", generEdit.getIdentifier());
	}
}
