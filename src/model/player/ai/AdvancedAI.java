package model.player.ai;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Resource;
import model.player.Player;
import model.player.ai.Move.Action;

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

		for (int i = 0; i < numMoves; i++) {
			ArrayList<MoveTree> newLeaves = new ArrayList<>();
			for (MoveTree leaf : leaves) {
				GameState state = leaf.getState();
				Player currentPlayer = state.getPlayer();

				for (Card handcard : currentPlayer.getHand()) {
					BuildCapability capability = Main.getSWController().getPlayerController().canBuild(currentPlayer, handcard, state);
					switch (capability) {
					case FREE:
					case OWN_RESOURCE:
						Move move = new Move(handcard, Action.BUILD);
						MoveTree newTree = new MoveTree(move, doMove(move, state));
						newLeaves.add(newTree);
						leaf.addChild(newTree);
						break;
					case TRADE:
						TradeOption trade = Main.getSWController().getPlayerController().getTradeOptions(currentPlayer, handcard.getRequired(), state).get(0);
						Move tradeMove = new Move(handcard, Action.BUILD);
						tradeMove.setTradeOption(trade);
						MoveTree newTree2 = new MoveTree(tradeMove, doMove(tradeMove, state));
						newLeaves.add(newTree2);
						leaf.addChild(newTree2);
						break;
					default:
						break;
					}
					
					ArrayList<Resource> slotRequirements = new ArrayList<>(Arrays.asList(currentPlayer.getBoard().getNextSlotRequirement()));
					capability = Main.getSWController().getPlayerController().hasResources(currentPlayer, slotRequirements, state);
					switch (capability) {
					case FREE:
					case OWN_RESOURCE:
						Move move = new Move(handcard, Action.PLACE_SLOT);
						MoveTree newTree = new MoveTree(move, doMove(move, state));
						newLeaves.add(newTree);
						leaf.addChild(newTree);
						break;
					case TRADE:
						TradeOption trade = Main.getSWController().getPlayerController().getTradeOptions(currentPlayer, slotRequirements, state).get(0);
						Move tradeMove = new Move(handcard, Action.PLACE_SLOT);
						tradeMove.setTradeOption(trade);
						MoveTree newTree2 = new MoveTree(tradeMove, doMove(tradeMove, state));
						newLeaves.add(newTree2);
						leaf.addChild(newTree2);
						break;
					default:
						break;
					}
					
					Move sellmove = new Move(handcard, Action.SELL);
					MoveTree selltree = new MoveTree(sellmove, doMove(sellmove, state));
					newLeaves.add(selltree);
					leaf.addChild(selltree);
					
					if (!this.isOlympiaUsed()) {
						Move olympiamove = new Move(handcard, Action.OLYMPIA);
						MoveTree olympiatree = new MoveTree(olympiamove, doMove(olympiamove, state));
						newLeaves.add(olympiatree);
						leaf.addChild(olympiatree);
					}
				}

				leaf.clearState();
			}
			
			leaves = newLeaves;
		}

		return tree;
	}

	private GameState doMove(Move move, GameState state) {
		return null;
	}
	
	protected abstract int evaluate(GameState state);

}
