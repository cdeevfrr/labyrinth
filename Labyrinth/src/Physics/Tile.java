package Physics;
import java.awt.Point;


public class Tile {
	boolean[] unblocked_directions;
	
	// These are the x and y locations in the board, not on screen.
	int x;
	int y;
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
		this.unblocked_directions = new boolean[] {false, false, false, false};
	}
	
	public Tile (Point p){
		this(p.x, p.y);
	}
	
	/**
	 * For now, generates a tile with 4 unblocked directions.
	 * TODO: Make this function actually generate a random tile,
	 * given some distribution of number of branches.
	 * Will eventually have a parameter distribution.
	 * @param p
	 * @return
	 */
	public static Tile generateRandomTile(Point p){
		Tile t = new Tile(p);
		t.setUnblocked("Up", true);
		t.setUnblocked("Right", true);
		t.setUnblocked("Down", true);
		t.setUnblocked("Left", true);
		return t;
	}
	
	
	public Point location(){
		return new Point(this.x, this.y);
	}
	
	/* It would be nice if we could restrict this method to only be accessible to
	 * Board method change tile location. Changing the location of a tile without 
	 * changing its key in the tiles HashMap creates bugs!
	 */
	 public void setLocation(Point p){
		this.x = p.x;
		this.y = p.y;
	 }
	
	 /**
	  * Rotates the unblocked directions of the tile.
	  */
	 public void rotate() {
		 boolean[] oldUD = this.unblocked_directions;
		 int arrayLength = oldUD.length;
		 boolean[] newUD = new boolean[arrayLength];
		 for(int i=0; i<arrayLength; i++) {
			 newUD[(i+1) % arrayLength] = oldUD[i];
		 }
		 this.unblocked_directions = newUD;
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
