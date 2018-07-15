import java.awt.Point;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;


public class Board {
	// The list of tiles in this board. Tiles store their x,y locations.
	ArrayList<Tile> tiles;
	// listeners listen for changes that need to happen in the interface.
	ArrayList<ChangeListener> listeners;
	ArrayList<Player> characters;
	
	public Board(){
		tiles = new ArrayList<Tile>();
		listeners = new ArrayList<ChangeListener>();
		characters = new ArrayList<Player>();
	}
	
	//TODO make these maxes and mins actually calculate
	// These will help the board be dynamically sized, 
	// and it will automatically show the furthest two tiles that exist.
	public int maxX(){
		int currentMax = 0;
		for (Tile t : tiles) {
			if( t.x > currentMax) {
				currentMax = t.x;
			}
		}
		return currentMax;
	}
	public int maxY(){
		int currentMax = 0;
		for(Tile t : tiles) {
			if(t.y > currentMax) {
				currentMax = t.y;
			}
		}
		return currentMax;
	}
	public int minX(){
		int currentMin = 99999;
		for (Tile t : tiles) {
			if(t.x < currentMin) {
				currentMin = t.x;
			}
		}
		return currentMin;
	}
	public int minY(){
		int currentMin = 99999;
		for (Tile t : tiles) {
			if(t.y < currentMin) {
				currentMin = t.y;
			}
		}
		return currentMin;
	}
	
	public void add_change_listener(ChangeListener c){
		listeners.add(c);
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
	public Tile tileAt(Point p){
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
		Tile overridden = tileAt(newCoords);
		if (overridden != null){
			// Have to push the other tile before setting this tile's location,
			// otherwise tile_at may be invalid for some time.
			Tile result =  push(overridden, direction);
			t.setLocation(newCoords);
			alertListeners();
			return result;
		}
		else{
			t.setLocation(newCoords);
			alertListeners();
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
		Tile overridden = tileAt(location);
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
	
	private void alertListeners(){
		for (ChangeListener l : listeners){
			l.stateChanged(null);
		}
	}
}
