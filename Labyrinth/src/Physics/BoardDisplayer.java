package Physics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import LabyrinthOriginal.PushRowMode;


public class BoardDisplayer extends JPanel implements ChangeListener, KeyListener{
	private static final long serialVersionUID = 1L;
	
	private Board board;
	Cursor cursor;
	public Player currentPlayer;
	//The current mode used to determine the meaning of keyboard events
	private int currentActionMode;
	private ArrayList<ActionMode> modes;
	//The character used to switch cursor modes
	private static final char MODESWITCH = ' ';
	
	
	public BoardDisplayer(Board board){
		this.board = board;
		board.add_change_listener(this);
		this.setPreferredSize(new Dimension(600,300));
		this.cursor = new Cursor(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		this.modes = new ArrayList<ActionMode>();
		this.addMode(new CursorMode());
		this.addMode(new PushRowMode(this.board));
		this.addMode(new MovePlayerMode(board, board.getFirstPlayer()));
		this.currentActionMode = 0;
	}
	
	public void addMode(ActionMode m){
		modes.add(m);
	}
	public void removeMode(ActionMode m){
		modes.remove(m);
	}
	
	public void paintComponent(Graphics g){
		paintTiles(g);
		paintCharacters(g);
		paintCursor(g);
	}
	
	private void paintTiles(Graphics g){
		for (Point p : this.board.getTileLocations()){
			Tile t = this.board.tileAt(p);
			GuiTile gt = new GuiTile(t);
			Point[] tlBr = screenBounds(t.location()); //Screen boundaries of this tile
			Point topLeft = tlBr[0];
			Point bottomRight = tlBr[1];
			gt.paint(g,topLeft, bottomRight);
		}
	}
	
	public void paintCharacters(Graphics g){
		for (Player c : board.getPlayers()){
			GuiPlayer gc = new GuiPlayer(c);
			Point[] tlBr = screenBounds(c.location());
			Point topLeft = tlBr[0];
			Point bottomRight = tlBr[1];
			gc.paint(g, topLeft, bottomRight);
		}
	}
	public void paintCursor(Graphics g){
		Point[] tlBr = screenBounds(cursor.location.x, cursor.location.y);
		Point topLeft = tlBr[0];
		Point bottomRight = tlBr[1];
		int width = bottomRight.x - topLeft.x;
		int height = bottomRight.y - topLeft.y;
		//Set the color of the cursor based on the currentActionMode
		// We add 1.0 to avoid integer division and to prevent issues if
		// there are no modes yet.
		float hue = (float)(currentActionMode / (modes.size() + 1.0)); 
		g.setColor(Color.getHSBColor(hue, 0.5f, 1.0f));
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
		
		// an x value of board.minX() should show up at the far left of the screen
		int left = (x - board.minX()) * pixelsWide;
		int right = left + pixelsWide;
		// a y value of board.minY() should show up at the bottom of the screen.
		// Top will be the visible top, and so will have a smaller screen-y than bottom.
		// Since screen y increases as we go down, a y value of board.minY() should have 
		// screen coordinate bounds of (width * pixelsTall) and ( (width+1) * pixelsTall),
		// which are nearly equal to screenHeight.
		int top = screenHeight - (y - board.minY() + 1) * pixelsTall;
		int bottom = top + pixelsTall;
		return new Point[] {new Point(left, top), new Point(right, bottom)};
	}
	
	public Point[] screenBounds(Point p) {
		return screenBounds(p.x,p.y);
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
			currentActionMode ++;
			if(currentActionMode >= modes.size()){
				currentActionMode = 0;
			}
			System.out.println(getMode());
			repaint();
			return true;
		}
		return false;
	}
	
	public ActionMode getMode(){
		return modes.get(currentActionMode);
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
	
	public void getFirstPlayer(){
		currentPlayer = board.getFirstPlayer();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		this.repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(modeSwitch(c)){
			return;
		}
		else{
			getMode().keyPressed(c, cursor);
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
		Tile[] tiles = new Tile[]{
			new Tile(0,0),
			new Tile(0,1),
			new Tile(4,4),
			new Tile(2,2),
		};
		tiles[0].setUnblocked("Up", true);
		tiles[0].setUnblocked("Right", true);
		tiles[3].setUnblocked("Right", true);
		tiles[3].setUnblocked("Left", true);
		tiles[3].setUnblocked("Up", true);
		
		for (Tile t : tiles){
			b.addTile(t);
		}
		
		b.getPlayers().add(new Player(tiles[1],Color.green));
		
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
