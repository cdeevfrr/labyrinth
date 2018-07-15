import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;


public class TileTester2 {

	@Test
	public void testCoordsInDirection(){
		Tile t = new Tile(3,7);
		assertEquals(t.coords_in_direction(Tile.direction("Up")), new Point(3,8));
		assertEquals(t.coords_in_direction(Tile.direction("Down")), new Point(3,6));
		assertEquals(t.coords_in_direction(Tile.direction("Left")), new Point(2,7));
		assertEquals(t.coords_in_direction(Tile.direction("Right")), new Point(4,7));
	}

}
