/**
 * We should be able to use the PushRowMode to implement the board size restriction.
 * We won't let the user use the push tile mode - we might just want to get rid of
 * it all together... And no other modes allow pushing a tile off the board.
 */
package LabyrinthOriginal;

import Physics.ActionMode;
import Physics.Board;
import Physics.Cursor;
import Physics.Directions;
import Physics.Tile;

public class PushRowMode extends ActionMode {
	Board board;
	Tile extraTile;
	//int tilesWide;
	//int tilesTall;
	
	public PushRowMode(Board b) {
		this.board = b;
	}
	
	/**
	 * Pushes the row or column that the cursor is in in the direction 
	 * specified by the character c, by inserting the extra tile. 
	 * Takes the last tile off the board and stores it as the extra tile.
	 * TODO: Return value.
	 * @param c
	 * @param cursor
	 * return
	 */
	@Override
	public boolean keyPressed(char c, Cursor cursor) {
		int directionPushed = this.getDirection(c);
		this.extraTile = this.board.insert(this.extraTile,
				this.board.getFurthestTile(Directions.opposite(directionPushed), cursor.getLocation()),
				directionPushed);
		this.board.removeTileAt(this.extraTile.location()); 
		/*^^This is not ideal...Maybe we can store the location of the extraTile in 
		 * addition to the tile itself. Note that board.insert() returns a tile, not a location.
		 */
		return false;
	}
	
}
