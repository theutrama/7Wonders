package model.player.ai;

import application.Main;
import model.board.WonderBoard;

public abstract class AdvancedAI extends ArtInt {

	public AdvancedAI(String name, WonderBoard board) {
		super(name, board);
	}
	
	private MoveTree generateTree() {
		
		MoveTree tree = new MoveTree(Main.getSWController().getGame().getCurrentGameState());
		
		final int numNodes = 1000000;
		int numMoves = (int) (Math.log(numNodes) / Math.log(21));
		numMoves = Math.min(numNodes, (Main.getSWController().getGame().getCurrentGameState().getRound() - 6) * (2));
		
		for (int i = 0 ; i < numMoves; i++) {
			
		}
		
		return null;
	}

	
}
