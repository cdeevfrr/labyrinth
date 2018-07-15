import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class BoardDisplayer extends JPanel implements ChangeListener{
	private static final long serialVersionUID = 1L;
	
	private Board board;
	
	public BoardDisplayer(Board board){
		this.board = board;
		board.add_change_listener(this);
		this.setPreferredSize(new Dimension(600,300));
	}
	
	public void paintComponent(Graphics g){
		paintTiles(g);
		paintCharacters(g);
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
		for (Character c : board.characters){
			GuiCharacter gc = new GuiCharacter(c);
			Point[] tlBr = screenBounds(c.x, c.y);
			Point topLeft = tlBr[0];
			Point bottomRight = tlBr[1];
			gc.paint(g, topLeft, bottomRight);
		}
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
		int screenWidth = this.getWidth();
		int screenHeight = this.getHeight();
		int width = board.maxX() - board.minX();
		int height = board.maxY() - board.minY();
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

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Make it so that the board is repainted
		// whenever this is called
		
	}
	
	
	public static void main(String[] args){
		
		Board b = new Board();
		b.tiles.add(new Tile(0,0));
		b.tiles.add(new Tile(0,1));
		b.tiles.add(new Tile(4,4));
		b.tiles.add(new Tile(2,2));
		
		b.characters.add(new Character(2,2,Color.green));
		
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
