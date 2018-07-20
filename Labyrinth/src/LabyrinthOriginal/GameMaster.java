package LabyrinthOriginal;

import Physics.Board;
import Physics.BoardDisplayer;
import Physics.MovePlayerMode;
import Physics.Player;
import Physics.Tile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An instance of the GameMaster is one game. It also has responsibility to show the game to the user
 * and let the user interact with it.
 * A game has settings (difficulty, tile distribution, etc), and a board (which keeps track of most
 * of the info about the current state of the game).
 * and needs a boardDisplayer, main panel, etc to interact with the user.
 * @author lindsayeddy
 *
 */
public class GameMaster {

	public Integer[] tileBranchDistribution;
	private BoardDisplayer boardDisplayer;
	private Board board;
	private JPanel mainPanel;
	
	public GameMaster(Integer[] distribution, int tilesWide, int tilesTall) {
		this.tileBranchDistribution = distribution;
		this.board = newBoard(tilesWide, tilesTall);
		//for now:
		this.board.addPlayer(new Player(this.board.tileAt(0,0),Color.yellow));
		this.boardDisplayer = new BoardDisplayer(this.board);
		this.mainPanel = new JPanel();
	}
	
	/**
	 * The number of sides per tile (eg, square has 4 sides).
	 * @return
	 */
	public int tilesides() {
		return this.tileBranchDistribution.length - 1;
	}
	
	public void gameStartUp() {
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.add(this.boardDisplayer, BorderLayout.CENTER);
		//Add playerModes to the board displayer for every player on the board
		for(Player player : board.getPlayers()) {
			this.boardDisplayer.addMode(new MovePlayerMode(board,player));
		}
		
		JFrame f = new JFrame();
		f.setContentPane(this.mainPanel);
		f.pack();
		//center the window
		f.setLocationRelativeTo(null);
		//Quit the program when this frame is closed.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	
	}
	
	/**
	 * Makes a new board given height and width parameters. 
	 * distribution and (indirectly) tilesides also influence 
	 * the board construction.
	 * numBranches := The number of unblocked directions.
	 * @param tilesWide
	 * @param tilesTall
	 * @return
	 */
	public Board newBoard(int tilesWide, int tilesTall) {
		int totalTiles = tilesWide * tilesTall;
		Board board = new Board();
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		//Filling the ArrayList in with tiles:
		while(tiles.size() < totalTiles) {
			for(int numBranches = 0; numBranches < this.tileBranchDistribution.length; numBranches++){
				for(int repeat = 0; repeat < this.tileBranchDistribution[numBranches]; repeat++) {
					Tile tile = makeTile(numBranches);
					tiles.add(tile);
				}
			}
		}
		Collections.shuffle(tiles);
		//Trims size of the tiles list to totalTiles:
		while(tiles.size() > totalTiles) {
			tiles.remove(tiles.size()-1);
		}
		int i = 0;
		for(int y=0; y < tilesTall; y++) {
			for(int x = 0; x < tilesWide; x++) {
				Point point = new Point(x,y);
				tiles.get(i).setLocation(point);
				i++;
			}
		}
		board.setTiles(tiles);
		return board;
	}
	
	/**
	 * Makes a tile, then add (random) unblocked directions to the tile.
	 * The number of unblocked directions is given by numBranches.
	 * @param numBranches
	 * @return
	 */
	public Tile makeTile(int numBranches) {
		Tile tile = new Tile(0,0);
		ArrayList<Integer> order = new ArrayList<Integer>(this.tilesides());
		for(int i=0; i < this.tilesides(); i++) {
			order.add(i);
		}
		Collections.shuffle(order);
		for(int i = 0; i < numBranches; i++) {
			tile.setUnblocked(order.get(i), true);
		}
		return tile;
	}
	
	//Make a shuffle board method.
	
	public static void main(String[] args) {
		Integer[] distribution = {0,0,3,7,0};
		GameMaster game = new GameMaster(distribution,9,9);
		game.gameStartUp();
		}
	}
