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
		//fill center:
		g.fillRect(
				topLeft.x + width/3,
				topLeft.y + height/3,
				width/3,
				height/3);
		//fill up, down, left, right:
		if(this.tile.unblocked_directions[Tile.direction("Up")]) {
			g.fillRect(
					topLeft.x + width/3,
					topLeft.y, 
					width/3,
					height/3);
		}
		if(this.tile.unblocked_directions[Tile.direction("Down")]) {
			g.fillRect(
					topLeft.x + width/3,
					topLeft.y + 2 * height/3, 
					width/3,
					height/3);
		}
		if(this.tile.unblocked_directions[Tile.direction("Left")]) {
			g.fillRect(
					topLeft.x,
					topLeft.y + height/3, 
					width/3,
					height/3);
		}
		if(this.tile.unblocked_directions[Tile.direction("Right")]) {
			g.fillRect(
					topLeft.x + 2 * width/3,
					topLeft.y + height/3, 
					width/3,
					height/3);
		}
	}
}

//Want to make a 3x3 rectangle, paint based on the unblocked directions of the tile.
//Still have the same inputs to the function paint.
//Fill center. Fill each unblocked direction.



