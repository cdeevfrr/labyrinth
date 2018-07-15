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
		g.setColor(Color.GRAY);
		//third-way points on x and y axes:
		int oneThirdAlongWidth = topLeft.x + width/3;
		int twoThirdsAlongWidth = topLeft.x + 2 * width/3;
		int oneThirdAlongHeight = topLeft.y + height/3;
		int twoThirdsAlongHeight = topLeft.y + 2 * height/3;
		//fill center:
		g.fillRect(
				oneThirdAlongWidth,
				oneThirdAlongHeight,
				width/3,
				height/3);
		//fill up, down, left, right:
		if(this.tile.unblocked_directions[Tile.direction("Up")]) {
			g.fillRect(
					oneThirdAlongWidth,
					topLeft.y, 
					width/3,
					height/3);
		}
		if(this.tile.unblocked_directions[Tile.direction("Down")]) {
			g.fillRect(
					oneThirdAlongWidth,
					twoThirdsAlongHeight,
					width/3,
					height/3);
		}
		if(this.tile.unblocked_directions[Tile.direction("Left")]) {
			g.fillRect(
					topLeft.x,
					oneThirdAlongHeight, 
					width/3,
					height/3);
		}
		if(this.tile.unblocked_directions[Tile.direction("Right")]) {
			g.fillRect(
					twoThirdsAlongWidth,
					oneThirdAlongHeight,
					width/3,
					height/3);
		}
	}
}
