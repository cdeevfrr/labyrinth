import java.awt.Color;
import java.awt.Point;

public class Player {
	int x;
	int y;
	Color color;
	
	
	public Player(int x, int y, Color c){
		this.x = x;
		this.y = y;
		this.color = c;
	}
	
	public void moveTo(Point p){
		this.x = p.x;
		this.y = p.y;
	}

}
