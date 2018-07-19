package LabyrinthOriginal;

import Physics.Board;
import Physics.BoardDisplayer;
import Physics.MovePlayerMode;
import Physics.Player;
import Physics.Tile;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

public class GameMaster {

	public Integer[] tileBranchDistribution;
	private BoardDisplayer boardDisplayer;
	
	/**
	 * The number of sides per tile (eg, square has 4 sides).
	 * @return
	 */
	public int tilesides() {
		return this.tileBranchDistribution.length - 1;
	}
	
	/**
	 * Creates the board, player(s), and the GUI window.
	 * Might want to make a default gameStartUp with default distribution 
	 * and board size...
	 */
	public void gameStartUp(Integer[] distribution, int tilesWide, int tilesTall) {
		this.tileBranchDistribution = distribution;
		Board b = newBoard(tilesWide, tilesTall);
		b.addPlayer(new Player(b.tileAt(0,tilesWide-1),Color.yellow)); //tl
		b.addPlayer(new Player(b.tileAt(tilesWide-1,tilesTall-1),Color.blue)); //tr
		b.addPlayer(new Player(b.tileAt(tilesWide-1,0),Color.red)); //br
		b.addPlayer(new Player(b.tileAt(0,0),Color.green)); //bl
		this.boardDisplayer = new BoardDisplayer(b);
		for(Player player : b.getPlayers()) {
			this.boardDisplayer.addMode(new MovePlayerMode(b,player));
		}
		//Add playerModes to the board displayer for every player on the board
		
		JFrame f = new JFrame();
		f.setContentPane(this.boardDisplayer);
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
		GameMaster game = new GameMaster();
		Integer[] distribution = {0,0,3,7,0};
		game.gameStartUp(distribution,9,9);
		}
	}
