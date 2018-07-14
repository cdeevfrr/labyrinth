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
		for (Tile t : tiles) {;
			if( t.x > currentMax) {
				currentMax = t.x;
			}
		}
		return currentMax;
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
	
	public void add_change_listener(ChangeListener c){
		listeners.add(c);
	}
	
	public void alert_listeners(){
		for (ChangeListener l : listeners){
			l.stateChanged(null);
		}
	}
}
