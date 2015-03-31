

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Rule;
import pumlbuilder.JavaFXThreadingRule;

import edu.millersville.cs.bitsplease.model.UMLClassSymbol;

public class ClassIsSelectedTest {

	@Rule
	public JavaFXThreadingRule javafxThread = new JavaFXThreadingRule();
	@Test
	public void ClassSelectedTest() {
		UMLClassSymbol c = new UMLClassSymbol();
		c.setSelected(true);
		assertTrue(c.isSelected());
		
		c.setSelected(false);
		assertFalse(c.isSelected());
		
		
	}
	@Test
	public void setClassIDTest(){
		UMLClassSymbol c = new UMLClassSymbol();
		c.setIdentifier("Test");
		assertEquals("Test", c.getIdentifier());
		
	}

}
