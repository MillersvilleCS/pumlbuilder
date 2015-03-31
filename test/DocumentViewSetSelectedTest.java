import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLInterfaceSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLUserSymbol;
import edu.millersville.cs.bitsplease.view.DocumentViewPane;

public class DocumentViewSetSelectedTest {
	
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void setSelectedUMSymbolTest(){
		
		DocumentViewPane d = new DocumentViewPane();
		UMLClassSymbol c = new UMLClassSymbol();
		d.addUMLSymbol(c);
		d.setSelectedUMLSymbol(c);
		assertEquals(c, d.getSelectedUMLSymbol().getValue());
		
		UMLRelationSymbol r = new UMLRelationSymbol();
		
		d.addUMLSymbol(r);
		d.setSelectedUMLSymbol(r);
		assertEquals(r, d.getSelectedUMLSymbol().getValue());
		assertNotEquals(c, d.getSelectedUMLSymbol().getValue());
		
		
		UMLInterfaceSymbol i = new UMLInterfaceSymbol();
		UMLInterfaceSymbol in = new UMLInterfaceSymbol();
		
		d.addUMLSymbol(i);
		d.addUMLSymbol(in);
		d.setSelectedUMLSymbol(i);
		assertEquals(i, d.getSelectedUMLSymbol().getValue());
		assertNotEquals(in, d.getSelectedUMLSymbol().getValue());
		assertNotEquals(r, d.getSelectedUMLSymbol().getValue());
		
		UMLUserSymbol user = new UMLUserSymbol();
		d.addUMLSymbol(user);
		d.setSelectedUMLSymbol(user);
		assertEquals(user, d.getSelectedUMLSymbol().getValue());
		assertNotEquals(i, d.getSelectedUMLSymbol().getValue());
		
	}

}
