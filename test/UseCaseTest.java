
/**
 * @author Mike Sims
 * @version 0.3.0
 */

import static org.junit.Assert.*;
import javafx.geometry.Point2D;
import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLUseCaseSymbol;

import org.junit.*;

public class UseCaseTest {

	@Rule
	public JavaFXThreadingRule javafxrule = new JavaFXThreadingRule();
	
	UMLUseCaseSymbol useCaseInit, useCaseLocation, useCaseText;
	
	//Initialization Test
	@Before
	public void UseCaseInitializing() {
		useCaseInit = new UMLUseCaseSymbol(new Point2D(100,100));
	}
	
	@Test
	public void UseCaseInitialzingTest() {
		assertTrue(100 == useCaseInit.getX());
		assertTrue(100 == useCaseInit.getY());
		assertEquals("Untitled Use Case", useCaseInit.getIdentifier());
	}
	
	//Editing Location Test
	@Before
	public void UseCaseEditingLocation() {
		useCaseLocation = new UMLUseCaseSymbol(new Point2D(100,100));
		useCaseLocation.setOrigin(new Point2D(200,200));
	}
	
	@Test
	public void UseCaseLocationTest() {
		assertTrue(200 == useCaseLocation.getX());
		assertTrue(200 == useCaseLocation.getY());
	}
	
	//Editing Text Test
	@Before
	public void UseCaseEditingText() {
		useCaseText = new UMLUseCaseSymbol(new Point2D(100,100));
		useCaseText.setIdentifier("Changed Use Case Text");
	}
	
	@Test
	public void UseCaseTextTest() {
		assertEquals("Changed Use Case Text", useCaseText.getIdentifier());
	}
}
