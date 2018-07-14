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
		// First, find the lengh and width of pixels that 
		// will be give to each tile.
		int screenWidth = this.getWidth();
		int screenHeight = this.getHeight();
		int width = board.maxX() - board.minX();
		int height = board.maxY() - board.minY();
		int pixelsWide = screenWidth/width;
		int pixelsTall = screenHeight/height;
		
		//TODO make the unrendered pixels spread out well.
		int missingXPixels = screenWidth % width;
		int missingYPixels = screenHeight % height;
		
		//Now, paint each tile based on that length and width.
		for (Tile t : board.tiles){
			GuiTile gt = new GuiTile(t);
			int left = t.x * pixelsWide;
			int right = left + pixelsWide;
			// since screen y increases while going down, this is reversed.
			int top = screenHeight - (t.y + 1) * pixelsTall;
			int bottom = top + pixelsTall;
			Point topLeft = new Point(left,top);
			Point bottomRight = new Point(right,bottom);
			gt.paint(g,topLeft, bottomRight);
		}
	}
	
	public void repaint(){
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args){
		
		Board b = new Board();
		b.tiles.add(new Tile(0,0));
		b.tiles.add(new Tile(0,1));
		b.tiles.add(new Tile(4,4));
		
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
