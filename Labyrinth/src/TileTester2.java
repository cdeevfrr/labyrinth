import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;


public class TileTester2 {

	@Test
	public void testCoordsInDirection(){
		Tile t = new Tile(3,7);
		assertEquals(t.coords_in_direction(0), new Point(3,8));
		assertEquals(t.coords_in_direction(1), new Point(3,6));
		assertEquals(t.coords_in_direction(2), new Point(2,7));
		assertEquals(t.coords_in_direction(3), new Point(4,7));
	}

}
