
/**
 * @author Mike Sims
 * @version 0.3.0
 */

import static org.junit.Assert.*;
import javafx.geometry.Point2D;
import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;

import org.junit.*;

public class ClassTest {

	@Rule
	public JavaFXThreadingRule javafxrule = new JavaFXThreadingRule();
	
	@Test
	public void classInitialiation() {
		UMLClassSymbol c = new UMLClassSymbol(new Point2D(100,100), 400, 600);
		
		assertTrue(100 == c.getX());
		assertTrue(100 == c.getY());
		assertEquals("Untitled", c.getIdentifier());
		
		//Editing Location Test
		c.setOrigin(new Point2D(200,200));

		assertTrue(200 == c.getX());
		assertTrue(200 == c.getY());

		//Editing Text Test
		c.setIdentifier("Changed Class Text");
		
		assertEquals("Changed Class Text", c.getIdentifier());
	}
}