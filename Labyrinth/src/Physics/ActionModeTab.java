package Physics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This tab will display the current ActionMode to the user.
 * 
 * See the ActionMode class.
 * 
 * @author dannyrivers
 *
 */
public class ActionModeTab extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	ArrayList<ActionMode> modes;
	ArrayList<JButton> displays; // Rather than recreating the buttons 
	// each time modes change, we will store all 9 visible buttons and
	// update their text when the list of action modes changes.
	static final int NUM_MODES_DISPLAYED = 9;
	static Color buttonBackground = Color.gray;
	
	public ActionModeTab(){
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.displays = new ArrayList<JButton>();
		for (int i = 1; i < NUM_MODES_DISPLAYED; i ++ ){
			JButton newButton = new JButton("");
			newButton.addActionListener(this);
			this.add(newButton);
			this.displays.add(newButton);
		}
	}
	
	
	public void setModes(ArrayList<ActionMode> modes, int current){
		this.modes = modes;
		for(int i = 0; i < NUM_MODES_DISPLAYED; i ++){
			//Have to go through every button in case we 
			// just reduced the number of modes being displayed.
			String newName = "";
			if(i < modes.size()){
				newName = modes.get(i).getName();
			}
			JButton b = displays.get(i);
			b.setText(newName);
			if(i == current){
				unhighlight(b);
			}
			else{
				highlight(b);
			}
		}
	}
	/**
	 * If this button is highlighted, undo that.
	 * Must be the opposite of highlight().
	 * @param b
	 */
	private void unhighlight(JButton b){
		b.setBackground(buttonBackground);
	}
	/**
	 * Highlight this button.
	 * Must be the opposite of unhighlight().
	 * @param button
	 */
	private void highlight(JButton b){
		b.setBackground(Color.YELLOW);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Find the button that caused the action, 
		// and try to set the mode to that button.
	}
}
