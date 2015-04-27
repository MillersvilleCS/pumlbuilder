
/**
 * @author Mike Sims
 * @version 0.3.0
 */

import static org.junit.Assert.*;
import javafx.geometry.Point2D;
import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLInterfaceSymbol;

import org.junit.*;

public class InterfaceTest {

	@Rule
	public JavaFXThreadingRule javafxrule = new JavaFXThreadingRule();
	
	UMLInterfaceSymbol interfaceInit, interfaceLocation, interfaceText;
	
	//Initialization Test
	@Before
	public void UseCaseInitializing() {
		interfaceInit = new UMLInterfaceSymbol(new Point2D(100,100));
	}
	
	@Test
	public void UseCaseInitialzingTest() {
		assertTrue(100 == interfaceInit.getX());
		assertTrue(100 == interfaceInit.getY());
		assertEquals("<<Untitled>>", interfaceInit.getIdentifier());
	}
	
	//Editing Location Test
	@Before
	public void UseCaseEditingLocation() {
		interfaceLocation = new UMLInterfaceSymbol(new Point2D(100,100));
		interfaceLocation.setOrigin(new Point2D(200,200));
	}
	
	@Test
	public void UseCaseLocationTest() {
		assertTrue(200 == interfaceLocation.getX());
		assertTrue(200 == interfaceLocation.getY());
	}
	
	//Editing Text Test
	@Before
	public void UseCaseEditingText() {
		interfaceText = new UMLInterfaceSymbol(new Point2D(100,100));
		interfaceText.setIdentifier("Changed Use Case Text");
	}
	
	@Test
	public void UseCaseTextTest() {
		assertEquals("Changed Use Case Text", interfaceText.getIdentifier());
	}
}
