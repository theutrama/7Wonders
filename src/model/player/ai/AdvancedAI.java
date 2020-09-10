package model.player.ai;

import java.util.ArrayList;

import application.Main;
import controller.utils.BuildCapability;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;

public abstract class AdvancedAI extends ArtInt {

	public AdvancedAI(String name, WonderBoard board) {
		super(name, board);
	}
	
	private MoveTree generateTree() {
		
		ArrayList<MoveTree> leaves = new ArrayList<>();
		
		MoveTree tree = new MoveTree(Main.getSWController().getGame().getCurrentGameState());
		leaves.add(tree);
		
		final int numNodes = 1000000;
		int numMoves = (int) (Math.log(numNodes) / Math.log(21));
		numMoves = Math.min(numNodes, (Main.getSWController().getGame().getCurrentGameState().getRound() - 6) * (2));
		
		for (int i = 0 ; i < numMoves; i++) {
			ArrayList<MoveTree> newLeaves = new ArrayList<>();
			for (MoveTree leaf: leaves) {
				GameState state = leaf.getState();
				Player currentPlayer = state.getPlayer();
				
				for (Card handcard: currentPlayer.getHand()) {
					BuildCapability capability = Main.getSWController().getPlayerController().canBuild(currentPlayer, handcard, state);
				}
				
				leaf.clearState();
			}
		}
		
		return null;
	}

	
}
