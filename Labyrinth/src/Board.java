import java.util.ArrayList;

import javax.swing.event.ChangeListener;


public class Board {
	// The list of tiles in this board. Tiles store their x,y locations.
	ArrayList<Tile> tiles;
	// listeners listen for changes that need to happen in the interface.
	ArrayList<ChangeListener> listeners;
	ArrayList<Character> characters;
	
	public Board(){
		tiles = new ArrayList<Tile>();
		listeners = new ArrayList<ChangeListener>();
		characters = new ArrayList<Character>();
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
	
	public void alert_listeners(){
		for (ChangeListener l : listeners){
			l.stateChanged(null);
		}
	}
}
