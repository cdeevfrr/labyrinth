package Physics;
import java.awt.Color;
import java.awt.Point;

public class Player {
	Tile tile;
	Color color;
	
	
	public Player(Tile t, Color c){
		this.tile = t;
		this.color = c;
	}
	
	public void moveTo(Tile t) {
		this.tile = t;
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
	public Point location(){
		return this.tile.location();
	}

}
