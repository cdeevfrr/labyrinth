import java.awt.Point;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;


public class Board {
	// The list of tiles in this board. Tiles store their x,y locations.
	ArrayList<Tile> tiles;
	// listeners listen for changes that need to happen in the interface.
	ArrayList<ChangeListener> listeners;
	
	public Board(){
		tiles = new ArrayList<Tile>();
		listeners = new ArrayList<ChangeListener>();
	}
	
	public void add_change_listener(ChangeListener c){
		listeners.add(c);
	}
	
	//TODO make these maxes and mins actually calculate
	public int maxX(){
		return 5;
	}
	public int maxY(){
		return 5;
	}
	public int minX(){
		return 0;
	}
	public int minY(){
		return 0;
	}
	
	//TODO implement this to run better than O(n)
	/**
	 * Find the tile at x,y
	 * Should eventually be implemented to be O(1) time.
	 * May return null if no such tile exists.
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile tileAt(int x, int y){
		for (Tile t : tiles){
			if (t.x == x && t.y == y){
				return t;
			}
		}
		return null;
	}
	public Tile tile_at(Point p){
		return tileAt(p.x, p.y);
	}
	
	/**
	 * Push the tile t in the direction specified, where 
	 * direction numbers come from the Tile.DIRECTIONS.
	 * 
	 * Return the last moved tile, so that if something goes off the board it 
	 * will be returned.
	 * 
	 * For implementation, I'm thinking that we should make a method on Tile,
	 * tile.move(direction), which returns the x,y coordinates to the 'direction'
	 * of this tile. That way, the Board class doesn't have to know anything
	 * about the meaning of the direction int (and if we change directions in 
	 * the future, all changes can be local to the tile class).
	 * 
	 * Once the board knows the new x,y coords, it can find the tile at that
	 * spot, move it in the same direction (recursive call), and then come
	 * back and move this tile.
	 * @param t
	 * @param direction
	 * @return
	 */
	public Tile push(Tile t, int direction){
		Point newCoords = t.coords_in_direction(direction);
		Tile overridden = tile_at(newCoords);
		if (overridden != null){
			// Have to push the other tile before setting this tile's location,
			// otherwise tile_at may be invalid for some time.
			Tile result =  push(overridden, direction);
			t.setLocation(newCoords);
			return result;
		}
		else{
			t.setLocation(newCoords);
			return t;
		}
	}
	
	/**
	 * Add the given tile to the board, pushing existing tiles
	 * in the specified direction.
	 * 
	 * Return the last tile moved, so that if something goes off the board,
	 * it will be the returned tile.
	 * @param t
	 * @param direction
	 * @return
	 */
	public Tile insert(Tile t, Point location, int direction){
		Tile overridden = tile_at(location);
		if (overridden != null){
			Tile result = push(overridden, direction);
			t.setLocation(location);
			return result;
		}
		else{
			t.setLocation(location);
			return t;
		}
	}
	
	public void alert_listeners(){
		for (ChangeListener l : listeners){
			l.stateChanged(null);
		}
	}
}
