package LabyrinthOriginal;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import Physics.BoardDisplayer;

public class MainPanel extends JPanel {

	/**
	 * I just used the default here... I don't really know much about serialization yet.
	 */
	private static final long serialVersionUID = 1L;
	
	private BoardDisplayer boardDisplayer;

	public MainPanel(BoardDisplayer bd) {
		this.boardDisplayer = bd;
		this.setLayout(new BorderLayout());
		this.add(this.boardDisplayer, BorderLayout.CENTER);
	}
	
	public BoardDisplayer getBoardDisplayer() {
		return this.boardDisplayer;
	}
}
