/**
 * We should be able to use the PushRowMode to implement the board size restriction.
 * We won't let the user use the push tile mode - we might just want to get rid of
 * it all together... And no other modes allow pushing a tile off the board.
 */
package LabyrinthOriginal;

import java.awt.Point;

import Physics.ActionMode;
import Physics.Board;
import Physics.Cursor;
import Physics.Directions;
import Physics.Tile;

public class PushRowMode extends ActionMode {
	Board board;
	Tile extraTile;
	
	public PushRowMode(Board b) {
		this.board = b;
		this.extraTile = new Tile(-1,-1); //Arbitrary location - not on a board so doesn't matter.
		this.extraTile.setUnblocked("Up", true);
		this.extraTile.setUnblocked("Right", true);
		this.extraTile.setUnblocked("Down", true);
		this.extraTile.setUnblocked("Left", true);
	}
	
	/**
	 * Pushes the row or column that the cursor is in in the direction 
	 * specified by the character c, by inserting the extra tile. 
	 * Takes the last tile off the board and stores it as the extra tile.
	 * TODO: Not sure when, or if, this should return false. Maybe if the cursor is
	 * not on a tile? That shouldn't happen though... Maybe if the extraTile is null?
	 * @param c
	 * @param cursor
	 * return
	 */
	@Override
	public boolean keyPressed(char c, Cursor cursor) {
		int directionPushed = this.getDirection(c);
		Point furthestTileLoc = this.board.getFurthestTile(Directions.opposite(directionPushed),
				cursor.getLocation());
		this.extraTile = this.board.insert(this.extraTile, furthestTileLoc, directionPushed);
		this.board.removeTileAt(this.extraTile.location()); 
		/*^^This is not ideal...Maybe we can store the location of the extraTile in 
		 * addition to the tile itself. Note that board.insert() returns a tile, not a location.
		 */
		return true;
	}
	
}
