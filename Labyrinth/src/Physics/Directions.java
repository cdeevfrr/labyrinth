package Physics;
import java.awt.Point;


public class Directions {
	public static final String[] DIRECTIONSLIST = {"Up", "Right", "Down", "Left"};
	
	public static String direction(int directionNumber){
		if (directionNumber < 0 || directionNumber >= DIRECTIONSLIST.length){
			throw new RuntimeException("Tried to find the direction of an invalid number:" + directionNumber);
		}
		return DIRECTIONSLIST[directionNumber];
	}
	
	/**
	 * Find the direction number associated with this string
	 * Returns -1 if the string is incorrect.
	 * @param directionString
	 * @return
	 */
	public static int direction(String directionString){
		int index = 0;
		while (index < DIRECTIONSLIST.length){
			if (DIRECTIONSLIST[index].equals(directionString)){
				return index;
			}
			index ++;
		}
		return -1;
	}
	
	public static int opposite(int direction){
		switch(direction){
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 0;
		case 3:
			return 1;
		default:
			return -1;
		}
	}
	public String opposite(String direction){
		//convert the string to int, opposite it, then back to string.
		return direction(opposite(direction(direction)));
	}
	
	public static Point move(Point location, int direction){
		int x = location.x;
		int y = location.y;
		if(direction < 0 || direction >= DIRECTIONSLIST.length){
			throw new RuntimeException("Invalid direction:" + direction);
		}
		switch (DIRECTIONSLIST[direction]){
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
					"A direction was given, but the "
					+ "the change in coordinates for that direction have not been specified yet: " 
					+ DIRECTIONSLIST[direction]  
			);
		}
	}

}
