package Physics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.ChangeListener;


public class Board {
	// The list of tiles in this board. Tiles store their x,y locations.
	HashMap<Point, Tile> tiles;
	// listeners listen for changes that need to happen in the interface.
	ArrayList<ChangeListener> listeners;
	ArrayList<Player> players;
	
	public Board(){
		tiles = new HashMap<Point, Tile>();
		listeners = new ArrayList<ChangeListener>();
		players = new ArrayList<Player>();
	}
	
	// These will help the board be dynamically sized, 
	// and it will automatically show the furthest two tiles that exist.
	public int maxX(){
		int currentMax = 0;
		for (Point p : tiles.keySet()) {
			if( p.x > currentMax) {
				currentMax = p.x;
			}
		}
		return currentMax;
	}
	public int maxY(){
		int currentMax = 0;
		for(Point p : tiles.keySet()) {
			if(p.y > currentMax) {
				currentMax = p.y;
			}
		}
		return currentMax;
	}
	public int minX(){
		int currentMin = 99999;
		for (Point p : tiles.keySet()) {
			if(p.x < currentMin) {
				currentMin = p.x;
			}
		}
		return currentMin;
	}
	public int minY(){
		int currentMin = 99999;
		for (Point p : tiles.keySet()) {
			if(p.y < currentMin) {
				currentMin = p.y;
			}
		}
		return currentMin;
	}
	
	public void add_change_listener(ChangeListener c){
		listeners.add(c);
	}
	
	/**
	 * Find the tile at x,y
	 * Should run in O(1) time.
	 * Returns null if no such tile exists.
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile tileAt(int x, int y){
		return tileAt(new Point(x,y));
	}
	
	public Tile tileAt(Point p){
		return tiles.get(p);
	}
	
	/**
	 * Push the tile t in the direction specified, where 
	 * direction numbers come from the the Directions class.
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
		Point newCoords = Directions.move(t.location(),direction);
		Tile overridden = tileAt(newCoords);
		if (overridden != null){
			// Have to push the other tile before setting this tile's location,
			// otherwise tile_at may be invalid for some time.
			Tile result =  push(overridden, direction);
			this.changeTileLocation(t, newCoords);
			alertListeners();
			return result;
		}
		else{
			this.changeTileLocation(t, newCoords);
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
			this.changeTileLocation(t, location);
			return result;
		}
		else{
			this.changeTileLocation(t, location);
			return t;
		}
	}
	
	public void changeTileLocation(Tile t, Point newLocation) {
		Point oldLocation = t.location();
		t.setLocation(newLocation);
		this.getTiles().put(newLocation, t);
		this.getTiles().remove(oldLocation,t);
	}
	
	public void setTiles(HashMap<Point,Tile> t) {
		this.tiles = t;
	}
	
	public void setTiles(ArrayList<Tile> tiles) {
		for(Tile tile : tiles) {
			this.getTiles().put(tile.location(), tile);
		}
	}
	
	public HashMap<Point,Tile> getTiles() {
		return this.tiles;
	}
	
	public void addPlayer(Player p) {
		this.players.add(p);
	}
	
	/**
	 * Try to move the player in this direction.
	 * If it worked, return true, otherwise false.
	 * @param p
	 * @param direction
	 * @return
	 */
	public boolean movePlayer(Player p, int direction){
		if (checkMove(p, direction)){
			Point newLocation = Directions.move(p.location(),direction);
			p.moveTo(this.tileAt(newLocation));
			alertListeners();
			return true;
		}
		return false;
	}
	
	public boolean checkMove(Player p, int direction){
		Point newLocation = Directions.move(p.location(),direction);
		Tile fromTile = p.getTile();
		//This probably is no longer necessary (b/c players must be on a tile):
		if (fromTile == null) { 
			return true;
		}
		//
		Tile toTile = tileAt(newLocation);
		if(toTile == null)return false;
		if(!fromTile.isUnblocked(direction)) return false;
		if(!toTile.isUnblocked(Directions.opposite(direction))) return false;
		return true;
	}
	public Player getFirstPlayer(){
		if (players.isEmpty()){
			throw new RuntimeException("Cannot get player because the board has no players");
		}
		return players.get(0);
	}
	
	
	public Player nextPlayer(Player p){
		if(!players.contains(p)){
			throw new RuntimeException("Tried to find the player after a non-existent player");
		}
		int index = players.indexOf(p) + 1;
		if (index >= players.size()){
			index = 0;
		}
		return players.get(index);
	}
	
	private void alertListeners(){
		for (ChangeListener l : listeners){
			l.stateChanged(null);
		}
	}
}
