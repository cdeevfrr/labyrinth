package Physics;
public class CursorMode extends ActionMode{
	@Override
	public boolean keyPressed(char c, Cursor cursor) {
		int direction = getDirection(c);
		if( direction == -1){
			return false;
		}
		return cursor.move(direction);
	}
}
