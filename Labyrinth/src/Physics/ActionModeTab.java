package Physics;

import java.awt.Color;
import java.awt.Graphics;
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
	int currentMode; //the current mode being used. Painting may be 
	// incorrect if this is not set.
	static final int NUM_MODES_DISPLAYED = 9;
	static Color buttonForeground = Color.gray;
	BoardDisplayer boardDisplayer;
	
	public ActionModeTab(BoardDisplayer b){
		this.boardDisplayer = b;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.displays = new ArrayList<JButton>();
		for (int i = 0; i < NUM_MODES_DISPLAYED; i ++ ){
			JButton newButton = new JButton("");
			newButton.addActionListener(this);
			this.add(newButton);
			this.displays.add(newButton);
		}
		this.currentMode = -1;
	}
	
	
	public void setModes(ArrayList<ActionMode> modes){
		this.modes = modes;
	}
	
	@Override
	public void paintComponent(Graphics g){
		updateButtons();
		super.paintComponent(g);
	}
	
	private void updateButtons(){
		for(int i = 0; i < NUM_MODES_DISPLAYED; i ++){
			//Have to go through every button in case we 
			// just reduced the number of modes being displayed.
			String newName = "";
			if(i < modes.size()){
				newName = modes.get(i).getName();
			}
			JButton b = displays.get(i);
			b.setText(newName);
			if(i == currentMode){
				highlight(displays.get(i));
			}
			else{
				unhighlight(displays.get(i));
			}
		}
	}
	
	public void setCurrentMode(int current){
		this.currentMode = current;
	}
	/**
	 * If this button is highlighted, undo that.
	 * Must be the opposite of highlight().
	 * @param b
	 */
	private void unhighlight(JButton b){
		b.setForeground(buttonForeground);
	}
	/**
	 * Highlight this button.
	 * Must be the opposite of unhighlight().
	 * @param button
	 */
	private void highlight(JButton b){
		b.setForeground(Color.GREEN.darker());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Find the button that caused the action, 
		// and try to set the mode to that button.
		int sourceButton = displays.indexOf(e.getSource());
		boardDisplayer.setMode(sourceButton);
	}
}
