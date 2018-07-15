import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author dannyrivers
 *
 * Extend action mode in order to generate custom behavior
 * when a user presses a key.
 * 
 * The physics package will automatically let the user cycle
 * through action modes that you give to the BoardDisplayer.
 * You can create an action mode by overriding this class, and 
 * handle key presses in the keyPressed method of your ActionMode.
 * Then, whenever the user is using your mode, their keypresses
 * will perform the actions you defined.
 * 
 * For example, here is pseudocode from the PushTile ActionMode
 * 
 *  public class PushTileMode extends ActionMode {
 *      @Override
 *  	public boolean keyPressed(char c, Cursor cursor) {
 *          int direction = getDirection(c); //helper method from ActionMode
 *          if (direction != -1){
 *          	// push the row/column the cursor is pointing to
 *              // in the direction specified by direction
 *          }
 *          else{
 *              //custom code for other key pushes (try to use 
 *              // the HashMap 'ActionMode.binding' so that users can choose which
 *              // characters to push in order to implement your behavior.)
 *          }
 *      }
 *  }
 * 
 *  main(){
 *      BoardDisplayer b = new BoardDisplayer();
 *      //other settings or setup for our BoardDisplayer
 *      b.addMode(new PushTileMode());
 *  }
 */
public abstract class ActionMode {
	Map<Character, Integer> binding;
	
	public static final Object[][] bindingsArray = new Object[][] {
		{'w', Directions.direction("Up") },
		{'s', Directions.direction("Down") },
		{'a', Directions.direction("Left") },
		{'d', Directions.direction("Right") },
	};
	
	public static HashMap<Character, Integer> defaultKeyBinding(){
		HashMap<Character, Integer> result = new HashMap<Character, Integer>();
		//The characters used to go to different directions
		for (Object[] pair : bindingsArray){
			char c = (char)(pair[0]);
			int i = (int)(pair[1]);
			result.put(new Character(c),new Integer(i));
		}
		return result;
	}
	
	/**
	 * Perform the actions specified by this character when the
	 * cursor is at this location.
	 * 
	 * Return true if repainting is necessary, false otherwise.
	 * @param c
	 * @param cursorLocation
	 * @return
	 */
	public abstract boolean keyPressed(char c, Cursor cursor);
	
	public void setBinding(Map<Character, Integer> binding){
		this.binding = binding;
	}
	
	public Map<Character, Integer> getBinding(){
		if (binding == null){
			setBinding(defaultKeyBinding());
		}
		return binding;
	}
	
	
	public int getDirection(char c){
		return getBinding().getOrDefault(c, -1);
	}
}
