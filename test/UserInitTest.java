import static org.junit.Assert.*;
import javafx.geometry.Point2D;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLUserSymbol;

import org.junit.Rule;
import org.junit.Test;

public class UserInitTest {
	
	@Rule
	public JavaFXThreadingRule javafxrule = new JavaFXThreadingRule();
	@Test
	public void UserInitializingTest(){
		
		UMLUserSymbol user = new UMLUserSymbol(new Point2D(10,10));
		assertTrue(10 == user.getX());
		assertTrue(10 == user.getY());
		assertEquals("User", user.getIdentifier());
		
		user.setIdentifier("User1");
		assertEquals("User1", user.getIdentifier());
		
		user.setOrigin(new Point2D(20,100));
		assertTrue(20 == user.getX());
		assertTrue(100 == user.getY());
		
		
	}

}
