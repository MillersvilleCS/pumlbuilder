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
		rel.setUMLRelationType(UMLRelationType.ASSOCIATION);
		assertEquals(UMLRelationType.ASSOCIATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUMLRelationType());
		
		rel.setUMLRelationType(UMLRelationType.AGGREGATION);
		assertEquals(UMLRelationType.AGGREGATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUMLRelationType());
		
		rel.setUMLRelationType(UMLRelationType.COMPOSITION);
		assertEquals(UMLRelationType.COMPOSITION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUMLRelationType());
		
		rel.setUMLRelationType(UMLRelationType.DEPENDENCY);
		assertEquals(UMLRelationType.DEPENDENCY, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.GENERALIZATION, rel.getUMLRelationType());
		
		rel.setUMLRelationType(UMLRelationType.GENERALIZATION);
		assertEquals(UMLRelationType.GENERALIZATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.AGGREGATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.ASSOCIATION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.COMPOSITION, rel.getUMLRelationType());
		assertNotEquals(UMLRelationType.DEPENDENCY, rel.getUMLRelationType());
		
	}
}
