package Physics;
import java.awt.Point;


public class PushTileMode extends ActionMode {
	public Board board;
	public PushTileMode (Board b){
		this.board = b;
	}
	/**
	 * Push the tile under the cursor in the direction specified by character c
	 * Return true if successful, false if this push is invalid.
	 * 
	 * @param e
	 */
	private boolean push(char c, Point cursorLocation){
		int direction = getDirection(c);
		Tile t = board.tileAt(cursorLocation);
		if (t != null){
			board.push(t, direction);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyPressed(char c, Cursor cursor) {
		if (push(c, cursor.location)){
			return cursor.move(getDirection(c));
		}
		return false;
	}
}
