package Physics;
import java.awt.Point;
import java.util.ArrayList;


public class Tile {
	boolean[] unblocked_directions;
	
	// These are the x and y locations in the board, not on screen.
	int x;
	int y;
	
	public Tile(int x, int y, ArrayList<String> unblocked_directions){
		this.x = x;
		this.y = y;
		this.unblocked_directions = new boolean[] {false, false, false, false};
		for(String d : unblocked_directions) {
			this.unblocked_directions[Directions.direction(d)] = true;
		}
	}
	
	public Tile(int x, int y){
		this(x, y, new ArrayList<String>());
	}
	
	public Tile (Point p){
		this(p.x, p.y);
	}
	
	public Point location(){
		return new Point(this.x, this.y);
	}
	
	public void setLocation(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public boolean isUnblocked(String direction){
		return isUnblocked(Directions.direction(direction));
	}
	public boolean isUnblocked(int direction){
		return unblocked_directions[direction];
	}
	
	public void setUnblocked(String direction, boolean newVal){
		this.setUnblocked(Directions.direction(direction), newVal);
	}
	public void setUnblocked(int direction, boolean newVal){
		this.unblocked_directions[direction] = newVal;
	}
	
	
	public String toString(){
		return "Tile at " + x + "," + y ;
	}
}
