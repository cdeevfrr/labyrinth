package Physics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class GuiPlayer {
	public Player character;
	public GuiPlayer(Player c){
		this.character = c;
	}
	
	public void paint(Graphics g, Point topLeft, Point bottomRight){
		int x = topLeft.x;
		int y = topLeft.y;
		int width = bottomRight.x - topLeft.x;
		int height = bottomRight.y - topLeft.y;
		g.setColor(character.color);
		//TODO make a better character
		int buffer = 10;
		g.fillRect(x + buffer, y + buffer, width - 2*buffer, height - 2*buffer);
	}
}
