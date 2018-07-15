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
	
	public void alert_listeners(){
		for (ChangeListener l : listeners){
			l.stateChanged(null);
		}
	}
}
