package Physics;


import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BoardTester {
	Board b;
	Tile t1;
	Tile t2;
	Tile t3;
	Tile t4;
	Tile t5;

	@Before
	public void setUp() throws Exception {
		/*  
		 * Initial board state
		 * 
		 *   T1  T2
		 *   T3  T4
		 *   T5
		 *   
		 */   
		b = new Board();
		t1 = new Tile(0,2);
		t2 = new Tile(2,2);
		t3 = new Tile(0,1);
		t4 = new Tile(2,1);
		t5 = new Tile(0,0);
		b.tiles.add(t1);
		b.tiles.add(t2);
		b.tiles.add(t3);
		b.tiles.add(t4);
		b.tiles.add(t5);
	}

	@After
	public void tearDown() throws Exception {
		b = new Board();
	}
	@Test
	public void testTileAt(){
		
	}

	@Test
	public void testPushing() {
		//We will push 3 times, first moving the first column down,
		// then moving t1 right, then moving t1 right again.
		
		Tile result = b.push(t1, Directions.direction("Down"));
		assertEquals(result, t5);
		//first row goes down
		assertEquals(t1.x, 0);
		assertEquals(t3.x, 0);
		assertEquals(t5.x, 0);
		assertEquals(t1.y, 1);
		assertEquals(t3.y, 0);
		assertEquals(t5.y, -1);
		
		//Other tiles aren't affected
		assertEquals(t4.x, 2);
		assertEquals(t4.y, 1);
		assertEquals(t2.x, 2);
		assertEquals(t2.x, 2);
		
		result = b.push(t1, Directions.direction("Right"));
		assertEquals(result, t1);
		//T1 goes right
		assertEquals(t1.x, 1);
		assertEquals(t1.y, 1);

		//Other tiles aren't affected
		assertEquals(t3.y, 0);
		assertEquals(t3.x, 0);
		assertEquals(t5.y, -1);
		assertEquals(t5.x, 0);
		assertEquals(t4.x, 2);
		assertEquals(t4.y, 1);
		assertEquals(t2.x, 2);
		assertEquals(t2.x, 2);
		
		result = b.push(t1, Directions.direction("Right"));
		assertEquals(result, t4);
		//T1 goes right
		assertEquals(t1.x, 2);
		assertEquals(t1.y, 1);

		//T4 also goes right
		assertEquals(t4.x, 3);
		assertEquals(t4.y, 1);
		
		//Other tiles aren't affected
		assertEquals(t3.y, 0);
		assertEquals(t3.x, 0);
		assertEquals(t5.y, -1);
		assertEquals(t5.x, 0);
		assertEquals(t2.x, 2);
		assertEquals(t2.x, 2);
	}
	
	@Test
	public void testInserting() {
		Tile t6 = new Tile(0,0);
		Tile result = b.insert(t6, new Point(0,2), Directions.direction("Down"));
		assertEquals(result, t5);
		//t6 is inserted
		assertEquals(t6.x, 0);
		assertEquals(t6.y, 2);
		
		//first row goes down
		assertEquals(t1.x, 0);
		assertEquals(t3.x, 0);
		assertEquals(t5.x, 0);
		assertEquals(t1.y, 1);
		assertEquals(t3.y, 0);
		assertEquals(t5.y, -1);

		//Other tiles aren't affected
		assertEquals(t4.x, 2);
		assertEquals(t4.y, 1);
		assertEquals(t2.x, 2);
		assertEquals(t2.x, 2);
	}

}
