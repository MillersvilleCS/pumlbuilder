import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;

public class UMLClassAddAttributeTest {
	@Rule
	public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();
	
	@Test
	public void addAttributeTest() {
		UMLClassSymbol c = new UMLClassSymbol();
		c.addAttribute("test1");
		assertEquals("test1", c.getFields().get(3).getValue());
	}
}
