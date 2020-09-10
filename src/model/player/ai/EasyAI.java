package model.player.ai;

import application.Main;
import model.board.WonderBoard;
import model.player.Player;

public class EasyAI extends ArtInt{

	
	public EasyAI(String name, WonderBoard board) {
		super(name, board);
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * calculates next move
	 * Priority age1: 	get resources (max 1 from every resource, max 2 cards in total) prio wonderboard
	 * 					adasfasf
	 * 			age2: 	take card with highest amount of victory points
	 * 			age3: 	take card with highest amount of victory points based on cards on board
	 */
	public void calculateNextMove() {
		
		//Main.getSWController().getGame()
		
	}
	
}
