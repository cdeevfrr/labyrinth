import java.util.HashMap;
import java.util.Map;


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
