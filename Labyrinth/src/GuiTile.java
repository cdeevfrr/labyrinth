import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class GuiTile{
	
	private static final long serialVersionUID = 1L;
	
	Tile tile;

	public GuiTile(Tile t){
		this.tile = t;
	}
	
	public void paint(Graphics g, Point topLeftPoint, Point bottomRightPoint){
		int width = bottomRightPoint.x - topLeftPoint.x;
		int height = bottomRightPoint.y - topLeftPoint.y;
		g.setColor(Color.CYAN);
		g.drawRect(
				topLeftPoint.x,
				topLeftPoint.y, 
				width,
				height
				);
	}
}
