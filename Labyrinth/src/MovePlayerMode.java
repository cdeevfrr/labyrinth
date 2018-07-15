

public class MovePlayerMode extends ActionMode{
	Board board;
	Player player;
	
	public MovePlayerMode(Board b, Player p){
		this.board = b;
		this.player = p;
	}
	/**
	 * Move the player in the direction specified by character c,
	 * or else do nothing.
	 * @param c
	 * @return
	 */
	private boolean movePlayer(char c){
		int direction = getDirection(c);
		if(direction != -1){
			return board.movePlayer(player, direction);
		}
		return false;
	}

	@Override
	public boolean keyPressed(char c, Cursor cursor) {
		return movePlayer(c);
	}
}
