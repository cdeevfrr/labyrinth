import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;


public class DirectionTester {

	@Test
	public void testDirections(){
		Point p = new Point(3,7);
		assertEquals(Directions.move(p,Directions.direction("Up")), new Point(3,8));
		assertEquals(Directions.move(p,Directions.direction("Down")), new Point(3,6));
		assertEquals(Directions.move(p,Directions.direction("Left")), new Point(2,7));
		assertEquals(Directions.move(p,Directions.direction("Right")), new Point(4,7));
		p = new Point(-2,0);
		assertEquals(Directions.move(p,Directions.direction("Up")), new Point(-2,1));
		assertEquals(Directions.move(p,Directions.direction("Down")), new Point(-2,-1));
		assertEquals(Directions.move(p,Directions.direction("Left")), new Point(-3,0));
		assertEquals(Directions.move(p,Directions.direction("Right")), new Point(-1,0));
	}

}
