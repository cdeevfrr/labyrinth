/**
 * We should be able to use the PushRowMode to implement the board size restriction.
 * We won't let the user use the push tile mode - we might just want to get rid of
 * it all together... And no other modes allow pushing a tile off the board.
 */
package LabyrinthOriginal;

import Physics.ActionMode;
import Physics.Board;
import Physics.Cursor;

public class PushRowMode extends ActionMode {
	Board board;
	int tilesWide;
	int tilesTall;
	
	@Override
	public boolean keyPressed(char c, Cursor cursor) {
		// TODO Auto-generated method stub
		return false;
	}
	
/**
 * Fields: We'll need a board, of course. But maybe it would be better to have 
 * a gamemaster instead?? Because we will need a board size, and that is given by
 * the gamemaster, not the board....
 * The board displayer is what cycles thru the modes.
 * Err... maybe the board size can just be a field? and then when in 
 * game master, we can initialize it with the correct dimensions.
 */
	
}
