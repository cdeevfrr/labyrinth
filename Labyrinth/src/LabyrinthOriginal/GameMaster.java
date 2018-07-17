package LabyrinthOriginal;

import Physics.Board;
import Physics.BoardDisplayer;
import Physics.Player;
import Physics.Tile;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

public class GameMaster {
	
	public static void gameStartUp() {
		Board b = newBoard(5,5);
		
		b.addPlayer(new Player(2,2,Color.green));
		
		JFrame f = new JFrame();
		f.setContentPane(new BoardDisplayer(b));
		f.pack();
		//center the window
		f.setLocationRelativeTo(null);
		//Quit the program when this frame is closed.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	
	}
	
	public static final Integer[] DISTRIBUTION = {0,0,5,2,0}; //0,1,2,3,4
	public static final int TILESIDES = DISTRIBUTION.length - 1;
	
	public static Board newBoard(int tilesWide, int tilesTall) {
		int totalTiles = tilesWide * tilesTall;
		Board board = new Board();
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		//Filling the ArrayList in with tiles:
		while(tiles.size() < totalTiles) {
			for(int numBranches = 0; numBranches < DISTRIBUTION.length; numBranches++){
				for(int repeat = 0; repeat < DISTRIBUTION[numBranches]; repeat++) {
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
	 * Makes a tile, then add unblocked directions to the tile.
	 * The number of unblocked directions is given by numBranches.
	 * @param numBranches
	 * @return
	 */
	public static Tile makeTile(int numBranches) {
		Tile tile = new Tile(0,0);
		ArrayList<Integer> order = new ArrayList<Integer>(TILESIDES);
		for(int i=0; i < TILESIDES; i++) {
			order.add(i);
		}
		Collections.shuffle(order);
		for(int i = 0; i < numBranches; i++) {
			tile.setUnblocked(order.get(i), true);
		}
		return tile;
	}
	//Make a shuffle board method.
	
	//Do we have a rotate tile method?
	
	public static void main(String[] args) {
		GameMaster.gameStartUp();
		}
	}
