package Physics;
import java.awt.Point;
import java.util.ArrayList;


public class Tile {
	public static final String[] DIRECTIONS = {"Up", "Right", "Down", "Left"};
	boolean[] unblocked_directions;
	
	// These are the x and y locations in the board, not on screen.
	int x;
	int y;
	
	public Tile(int x, int y, ArrayList<String> unblocked_directions){
		this.x = x;
		this.y = y;
		this.unblocked_directions = new boolean[] {false, false, false, false};
		for(String d : Tile.DIRECTIONS) {
			if(unblocked_directions.contains(d)) {
				this.unblocked_directions[Tile.direction(d)] = true;
			}
		}
	}
	
	public Tile(int x, int y){
		this(x, y, new ArrayList<String>());
	}
	
	public Tile (Point p){
		this(p.x, p.y);
	}
	
	public void setLocation(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	/**
	 * Find the direction number associated with this string
	 * Returns -1 if the string is incorrect.
	 * @param directionString
	 * @return
	 */
	public static int direction(String directionString){
		int index = 0;
		while (index < DIRECTIONS.length){
			if (DIRECTIONS[index].equals(directionString)){
				return index;
			}
			index ++;
		}
		return -1;
	}
	
	public Point coords_in_direction(int direction){
		if(direction < 0 || direction >= DIRECTIONS.length){
			throw new RuntimeException("Invalid direction:" + direction);
		}
		switch (DIRECTIONS[direction]){
		case "Up": 
			return new Point(x, y+1);
		case "Down": // down
			return new Point(x, y-1);
		case "Left": // left
			return new Point(x-1, y);
		case "Right": // right
			return new Point(x+1, y);
		default:
			throw new RuntimeException(
					"A direction was given without specifying"
					+ "the change in coordinates for that direction: " 
					+ DIRECTIONS[direction]  
			);
		}
	}
	
	
	public String toString(){
		return "Tile at " + x + "," + y ;
	}
}
