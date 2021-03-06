package Physics;
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
	
	public void paint(Graphics g, Point topLeft, Point bottomRight){
		int width = bottomRight.x - topLeft.x;
		int height = bottomRight.y - topLeft.y;
		g.setColor(Color.CYAN);
		g.fillRect(
				topLeft.x,
				topLeft.y, 
				width,
				height
				);
		g.setColor(Color.LIGHT_GRAY);
		//third-way points on x and y axes:
		int oneThirdAlongWidth = topLeft.x + width/3;
		int twoThirdsAlongWidth = topLeft.x + 2 * width/3;
		int oneThirdAlongHeight = topLeft.y + height/3;
		int twoThirdsAlongHeight = topLeft.y + 2 * height/3;
		//fill center:
		g.fillRect(
				oneThirdAlongWidth,
				oneThirdAlongHeight,
				twoThirdsAlongWidth - oneThirdAlongWidth,
				twoThirdsAlongHeight - oneThirdAlongHeight);
		//fill up, down, left, right:
		if(tile.isUnblocked("Up")) {
			g.fillRect(
					oneThirdAlongWidth,
					topLeft.y, 
					twoThirdsAlongWidth - oneThirdAlongWidth,
					oneThirdAlongHeight - topLeft.y);
		}
		if(tile.isUnblocked("Down")) {
			g.fillRect(
					oneThirdAlongWidth,
					twoThirdsAlongHeight,
					twoThirdsAlongWidth - oneThirdAlongWidth,
					bottomRight.y - twoThirdsAlongHeight);
		}
		if(tile.isUnblocked("Left")) {
			g.fillRect(
					topLeft.x,
					oneThirdAlongHeight, 
					oneThirdAlongWidth - topLeft.x,
					twoThirdsAlongHeight - oneThirdAlongHeight);
		}
		if(tile.isUnblocked("Right")) {
			g.fillRect(
					twoThirdsAlongWidth,
					oneThirdAlongHeight,
					bottomRight.x - twoThirdsAlongWidth,
					twoThirdsAlongHeight - oneThirdAlongHeight);
		}
		g.setColor(Color.black);
		g.drawString("" + tile.id, oneThirdAlongWidth, twoThirdsAlongHeight);
	}
}
