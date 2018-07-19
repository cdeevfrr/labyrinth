package Physics;
import java.awt.Point;

public class Cursor {
	Point location;
	BoardDisplayer container;
	
	public Cursor(BoardDisplayer c){
		this.container = c;
		this.location = new Point(0,0);
	}
	
	public Point getLocation() {
		return this.location;
	}
	
	public boolean move(int direction){
		if (direction == -1) return false;
		Point newLocation = Directions.move(location, direction);
		if(container.isInBoard(newLocation)){
			location = newLocation;
			container.invalidate();
			container.repaint();
			return true;
		}
		return false;
	}
}
