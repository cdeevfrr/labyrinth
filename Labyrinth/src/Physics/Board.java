package Physics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.event.ChangeListener;


public class Board {
	// The list of tiles in this board. Tiles store their x,y locations.
	private HashMap<Point, Tile> tiles;
	// listeners listen for changes that need to happen in the interface.
	private ArrayList<ChangeListener> listeners;
	private ArrayList<Player> players;
	
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
	
	public void addTile(Tile t) {
		this.tiles.put(t.location(),t);
	};
	
	public void addTiles(Tile[] tiles) {
		for(Tile tile : tiles) {
			this.addTile(tile);
		}
	}
	
	public void removeTileAt(Point p) {
		this.tiles.remove(p);
	}
	
	/**
	 * TODO: Try not to ask a tile for its location.
	 * @param t
	 */
	public void removeTile(Tile t) {
		removeTileAt(t.location());
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
	 * @return Returns the last tile moved.
	 */
	public Tile push(Tile t, int direction){
		Point newCoords = Directions.move(t.location(),direction);
		Tile overridden = tileAt(newCoords);
		Tile result = t;
		if (overridden != null){
			// Have to push the other tile before setting this tile's location,
			// otherwise tile_at may be invalid for some time.
			result =  push(overridden, direction);
		}
		this.changeTileLocation(t, newCoords);
		alertListeners();
		return result;
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
		Tile result = t;
		if (overridden != null){
			result = push(overridden, direction);
		}
		this.changeTileLocation(t, location);
		return result;
	}
	
	/**
	 * Finds the furthest tile from the point p in the specified direction.
	 * @param direction
	 * @param p
	 * @return
	 */
	public Point getFurthestTile(int direction, Point p) {
		Point newCoords = Directions.move(p,direction);
		Tile nextTile = this.tileAt(newCoords);
		Point result;
		if(nextTile == null) {
			result = p;
		} else {
			result = this.getFurthestTile(direction, newCoords);
		}
		return result;
	}
	
	public void changeTileLocation(Tile t, Point newLocation) {
		Point oldLocation = t.location();
		t.setLocation(newLocation);
		if(this.tileAt(newLocation)!=null) {
			throw new RuntimeException("Can't move a tile that way, a tile already exists there:" + this.tileAt(newLocation));
		}
		this.tiles.remove(oldLocation,t);
		this.tiles.put(newLocation, t);
	}
	
	public void setTiles(HashMap<Point,Tile> t) {
		this.tiles = t;
	}
	
	public void setTiles(ArrayList<Tile> tiles) {
		for(Tile tile : tiles) {
			this.tiles.put(tile.location(), tile);
		}
	}
	
	public Set<Point> getTileLocations() {
		return this.tiles.keySet();
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
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
