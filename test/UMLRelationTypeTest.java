import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Rule;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;
public class UMLRelationTypeTest {

	@Rule
	public JavaFXThreadingRule javaFXRule = new JavaFXThreadingRule();
	
	@Test
	public void testUMLRelationType(){
		
		UMLRelationSymbol rel = new UMLRelationSymbol();
		rel.setUmlRelationType(UMLRelationType.ASSOCIATION);
		assertEquals(UMLRelationType.ASSOCIATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUmlRelationType());
		
		rel.setUmlRelationType(UMLRelationType.AGGREGATION);
		assertEquals(UMLRelationType.AGGREGATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUmlRelationType());
		
		rel.setUmlRelationType(UMLRelationType.COMPOSITION);
		assertEquals(UMLRelationType.COMPOSITION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUmlRelationType());
		
		rel.setUmlRelationType(UMLRelationType.DEPENDENCY);
		assertEquals(UMLRelationType.DEPENDENCY, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUmlRelationType());
		
		rel.setUmlRelationType(UMLRelationType.GENERALIZATION);
		assertEquals(UMLRelationType.GENERALIZATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUmlRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUmlRelationType());
		
	}
}
