import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class BoardDisplayer extends JPanel implements ChangeListener, KeyListener{
	private static final long serialVersionUID = 1L;
	
	private Board board;
	private Point cursorLocation;
	//The current mode used to determine the meaning of keyboard events
	private ActionMode actionMode;
	private enum ActionMode{
		MOVECURSOR,
		PUSH,
		INSERT
	}
	//The character used to switch cursor modes
	private static final char MODESWITCH = ' ';
	
	//The characters used to go to different directions
	public static final Object[][] bindingsArray = new Object[][] {
		{'w', Tile.direction("Up") },
		{'s', Tile.direction("Down") },
		{'a', Tile.direction("Left") },
		{'d', Tile.direction("Right") },
	};
	
	//Turn the bindingsArray in to a hash for easy access.
	public static HashMap<Character, Integer> keyBindings = new HashMap<Character, Integer>();
	static{
		for (Object[] pair : bindingsArray){
			char c = (char)(pair[0]);
			int i = (int)(pair[1]);
			keyBindings.put(new Character(c),new Integer(i));
		}
	}
	
	
	public BoardDisplayer(Board board){
		this.board = board;
		board.add_change_listener(this);
		this.actionMode = ActionMode.MOVECURSOR;
		this.setPreferredSize(new Dimension(600,300));
		this.cursorLocation = new Point(0,0);
		this.addKeyListener(this);
		this.setFocusable(true);
	}
	
	public void paintComponent(Graphics g){
		paintTiles(g);
		paintCharacters(g);
		paintCursor(g);
	}
	
	private void paintTiles(Graphics g){
		for (Tile t : board.tiles){
			GuiTile gt = new GuiTile(t);
			Point[] tlBr = screenBounds(t.x, t.y);
			Point topLeft = tlBr[0];
			Point bottomRight = tlBr[1];
			gt.paint(g,topLeft, bottomRight);
		}
	}
	
	public void paintCharacters(Graphics g){
		for (Player c : board.characters){
			GuiCharacter gc = new GuiCharacter(c);
			Point[] tlBr = screenBounds(c.x, c.y);
			Point topLeft = tlBr[0];
			Point bottomRight = tlBr[1];
			gc.paint(g, topLeft, bottomRight);
		}
	}
	public void paintCursor(Graphics g){
		Point[] tlBr = screenBounds(cursorLocation.x, cursorLocation.y);
		Point topLeft = tlBr[0];
		Point bottomRight = tlBr[1];
		int width = bottomRight.x - topLeft.x;
		int height = bottomRight.y - topLeft.y;
		//Set the color of the cursor based on the current actionMode
		switch(actionMode){
		case MOVECURSOR:
			g.setColor(Color.black);
			break;
		case INSERT:
			g.setColor(Color.blue);
			break;
		case PUSH:
			g.setColor(Color.GRAY);
			break;
		}
		g.drawRect(topLeft.x, topLeft.y, width, height);
	}
	
	/**
	 * Find the top left and bottom right points that bound
	 * an object at this x,y location in the board.
	 * 
	 * TODO memoize this to make it faster, but take care in case
	 * the board size changes (getWidth might change, for example).
	 * @param x
	 * @param y
	 * @return
	 */
	public Point[] screenBounds(int x, int y){
		//width is the number of tiles
		//screenWidth is the number of pixels on the screen
		//pixelsWide is number of pixels in a tile's width
		int screenWidth = this.getWidth();
		int screenHeight = this.getHeight();
		int width = board.maxX() - board.minX() + 1;
		int height = board.maxY() - board.minY() + 1;
		int pixelsWide = screenWidth/width;
		int pixelsTall = screenHeight/height;

		//TODO make the unrendered pixels spread out well.
		int missingXPixels = screenWidth % width;
		int missingYPixels = screenHeight % height;
		
		int left = x * pixelsWide;
		int right = left + pixelsWide;
		// since screen y increases while going down, this is reversed.
		int top = screenHeight - (y + 1) * pixelsTall;
		int bottom = top + pixelsTall;
		return new Point[] {new Point(left, top), new Point(right, bottom)};
	}
	
	public boolean isInBoard(Point p){
		return p.x <= board.maxX() 
				&& p.x >= board.minX()
				&& p.y <= board.maxY()
				&& p.y >= board.minY();
	}
	
	/**
	 * If c is a mode-switching character, switch the action mode
	 * and return true.
	 * Otherwise, return false.
	 * @param c
	 * @return
	 */
	private boolean modeSwitch(char c){
		if (c == MODESWITCH){
			switch(actionMode){
			case MOVECURSOR:
				actionMode = ActionMode.PUSH;
				break;
			case PUSH:
				actionMode = ActionMode.INSERT;
				break;
			case INSERT:
				actionMode = ActionMode.MOVECURSOR;
				break;
			}
			System.out.println(actionMode);
			repaint();
		}
		return false;
	}
	
	/**
	 * Move the cursor in the direction specified by c.
	 * Return true or false based on successful movement.
	 * @param c
	 */
	private boolean moveCursor(char c){
		int direction = keyBindings.getOrDefault(c, -1);
		if (direction == -1) return false;
		//TODO see issue #13 on Github, this needs to be abstracted out of the Tile class.
		Point newLocation = (new Tile(cursorLocation)).coords_in_direction(direction);
		if(isInBoard(newLocation)){
			cursorLocation = newLocation;
			this.invalidate();
			this.repaint();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Push the tile under the cursor in the direction specified by character c
	 * Return the tile returned by push.
	 * 
	 * TODO implement this
	 * @param e
	 */
	private Tile push(char c){
		int direction = keyBindings.getOrDefault(c, -1);
		return null;
	}
	
	/**
	 * Insert the held tile into the current location 
	 * in the direction specified by c.
	 * Return the tile returned by insert.
	 * 
	 * TODO implement this
	 * @param c
	 */
	private Tile insert(char c){
		return null;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		this.repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		System.out.println(c);
		if(modeSwitch(c)){
			return;
		}
		else{
			switch(actionMode){
			case MOVECURSOR:
				moveCursor(c);
				break;
			case PUSH:
				push(c);
				break;
			case INSERT:
				insert(c);
				break;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	public static void main(String[] args){
		Board b = new Board();
		b.tiles.add(new Tile(0,0));
		b.tiles.add(new Tile(0,1));
		b.tiles.add(new Tile(4,4));
		b.tiles.add(new Tile(2,2));
		
		b.characters.add(new Player(2,2,Color.green));
		
		JFrame f = new JFrame();
		f.setContentPane(new BoardDisplayer(b));
		f.pack();
		//center the window
		f.setLocationRelativeTo(null);
		//Quit the program when this frame is closed.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
}
