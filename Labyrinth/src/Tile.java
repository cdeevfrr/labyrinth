
public class Tile {
	public static final String[] Directions = {"Up", "Down", "Left", "Right"};
	boolean[] unblocked_directions;
	
	// These are the x and y locations in the board, not on screen.
	int x;
	int y;
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public static void main(String[] args){
		
	}
}
