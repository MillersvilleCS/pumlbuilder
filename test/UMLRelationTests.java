/**
 * @author Merv Fansler
 * @version 0.2.0
 * @since 0.2.0
 */
import static org.junit.Assert.assertEquals;
import javafx.geometry.Point2D;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import pumlbuilder.JavaFXThreadingRule;
import edu.millersville.cs.bitsplease.model.UMLClassSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationSymbol;
import edu.millersville.cs.bitsplease.model.UMLRelationType;


public class UMLRelationTests {
	@Rule
	public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();
	
	UMLRelationSymbol r;
	
	@Before
	public void setUp() throws Exception {
		UMLClassSymbol c1 = new UMLClassSymbol(new Point2D(100,100), 100, 100);
		UMLClassSymbol c2 = new UMLClassSymbol(new Point2D(400,100), 100, 100);
		r = new UMLRelationSymbol(c1, c2, UMLRelationType.ASSOCIATION);
	}

	@Test
	public void testGetFields() {
		assertEquals("Relation object has 6 fields", 6, r.getFields().size());
	}

	@Test
	public void testGetStartPoint() {
		Point2D startPoint = r.getStartPoint();
		assertEquals("Start point is contained in first class", true,
				r.getSourceObject().getBoundsInParent().contains(startPoint));
	}

	@Test
	public void testGetEndPoint() {
		Point2D endPoint = r.getEndPoint();
		assertEquals("End point corresponds to Left Middle point of Target object", endPoint,
				r.getTargetObject().getMiddleLeft());
	}

	@Test
	public void testGetEndOrientation() {
		// since the initial objects are horizontal, the orientation should be zero
		assertEquals("Horizontal relation has orientation of 0 radians", 0d, r.getEndOrientation(), 0.0001);
	}

}
