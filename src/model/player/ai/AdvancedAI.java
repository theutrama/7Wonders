package model.player.ai;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Effect;
import model.card.EffectType;
import model.card.Resource;
import model.player.Player;
import model.player.ai.Move.Action;

public abstract class AdvancedAI extends ArtInt {
	private static final long serialVersionUID = 1L;

	/**
	 * create an advanced AI
	 * 
	 * @param name  name
	 * @param board it's wonder board
	 */
	public AdvancedAI(String name, WonderBoard board) {
		super(name, board);
	}

	/**
	 * overrides ArtInt's method {@link ArtInt#calculateNextMove()}
	 */
	@Override
	public void calculateNextMove() {
		MoveTree tree = generateTree();

		int maxValue = Integer.MIN_VALUE;
		Move maxMove = null;

		if (tree.getChildren().get(0).getChildren().isEmpty()) {
			System.out.println("case 1");
			for (MoveTree child : tree.getChildren()) {
				int value = evaluate(child.getState());
				if (value > maxValue) {
					maxMove = child.getMove();
					maxValue = value;
				}
			}
		} else {
			System.out.println("case 2");
			for (MoveTree child : tree.getChildren()) {
				ArrayList<MoveTree> leaves = child.getLeaves();
				MoveTree worst = null;
				int worstValue = Integer.MAX_VALUE;
				for (MoveTree leaf : leaves) {
					int value = evaluate(leaf.getState());
					if (value < worstValue) {
						worst = leaf;
						worstValue = value;
					}
				}
				if (worst != null && worstValue > maxValue) {
					maxMove = child.getMove();
					maxValue = worstValue;
				}
			}
		}

		this.next = maxMove;
		System.out.println("next move: " + next);
		System.out.println("action: " + next.getAction());
		System.out.println("card: " + next.getCard());
		System.out.println("trade: " + next.getTradeOption());
		System.out.println(this.getHand().toString());
	}

	/**
	 * generates a tree of possible moves
	 * 
	 * @return tree
	 */
	private MoveTree generateTree() {

		ArrayList<MoveTree> leaves = new ArrayList<>();

		MoveTree tree = new MoveTree(Main.getSWController().getGame().getCurrentGameState());
		leaves.add(tree);

		final int numNodes = 1000000;
		final int numMoves = 3;// (int) (Math.log(numNodes) / Math.log(21));

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
						MoveTree newTree = new MoveTree(move, doMove(move, currentPlayer, state));
						newLeaves.add(newTree);
						leaf.addChild(newTree);
						break;
					case TRADE:
						TradeOption trade = Main.getSWController().getPlayerController().getTradeOptions(currentPlayer, handcard.getRequired(), state).get(0);
						Move tradeMove = new Move(handcard, Action.BUILD);
						tradeMove.setTradeOption(trade);
						MoveTree newTree2 = new MoveTree(tradeMove, doMove(tradeMove, currentPlayer, state));
						newLeaves.add(newTree2);
						leaf.addChild(newTree2);
						break;
					default:
						break;
					}

					if (currentPlayer.getBoard().nextSlot() != -1) {
						ArrayList<Resource> slotRequirements = new ArrayList<>(Arrays.asList(currentPlayer.getBoard().getNextSlotRequirement()));
						capability = Main.getSWController().getPlayerController().hasResources(currentPlayer, slotRequirements, state);
						switch (capability) {
						case FREE:
						case OWN_RESOURCE:
							Move move = new Move(handcard, Action.PLACE_SLOT);
							MoveTree newTree = new MoveTree(move, doMove(move, currentPlayer, state));
							newLeaves.add(newTree);
							leaf.addChild(newTree);
							break;
						case TRADE:
							TradeOption trade = Main.getSWController().getPlayerController().getTradeOptions(currentPlayer, slotRequirements, state).get(0);
							Move tradeMove = new Move(handcard, Action.PLACE_SLOT);
							tradeMove.setTradeOption(trade);
							MoveTree newTree2 = new MoveTree(tradeMove, doMove(tradeMove, currentPlayer, state));
							newLeaves.add(newTree2);
							leaf.addChild(newTree2);
							break;
						default:
							break;
						}
					}

					Move sellmove = new Move(handcard, Action.SELL);
					MoveTree selltree = new MoveTree(sellmove, doMove(sellmove, currentPlayer, state));
					newLeaves.add(selltree);
					leaf.addChild(selltree);

					if (!this.isOlympiaUsed()) {
						Move olympiamove = new Move(handcard, Action.OLYMPIA);
						MoveTree olympiatree = new MoveTree(olympiamove, doMove(olympiamove, currentPlayer, state));
						newLeaves.add(olympiatree);
						leaf.addChild(olympiatree);
					}
				}

				leaf.clearState();
			}

			boolean breakAfterwards = false;

			for (MoveTree newLeaf : newLeaves) {
				for (Player player : newLeaf.getState().getPlayers()) {
					if (player.isMausoleum()) {
						Card card = getHalikarnassusCard(player, newLeaf.getState().getTrash());
						newLeaf.getState().getTrash().remove(card);
						player.getBoard().addCard(card);
						if (card.getEffects() != null)
							for (Effect effect : card.getEffects())
								if (effect.getType() == EffectType.WHEN_PLAYED)
									effect.run(player, Main.getSWController().getGame());
						break;
					}
				}

				newLeaf.getState().setCurrentPlayer((newLeaf.getState().getCurrentPlayer() + 1) % newLeaf.getState().getPlayers().size());

				if (newLeaf.getState().getCurrentPlayer() == newLeaf.getState().getFirstPlayer()) {
					if (newLeaf.getState().getRound() < 6)
						Main.getSWController().getGameController().nextRound(Main.getSWController().getGame(), newLeaf.getState());
					else // stop look-ahead at the end of a round
						breakAfterwards = true;
				}
			}

			if (breakAfterwards)
				break;

			leaves = newLeaves;
		}

		return tree;
	}

	/**
	 * the given game state must not be changed! use deepClone() to execute the move on a new game state
	 * 
	 * @param move  the move to be executed
	 * @param state game state
	 * @return a new game state object
	 */
	private GameState doMove(Move move, Player player, GameState state) {
		GameState newState = state.deepClone();
		player = Main.getSWController().getPlayerController().getPlayer(player.getName(), newState);
		switch (move.getAction()) {
		case OLYMPIA:
		case BUILD:
			player.getHand().remove(move.getCard());
			player.getBoard().addCard(move.getCard());
			player.setChooseCard(null);
			if (move.getCard().getEffects() != null)
				for (Effect effect : move.getCard().getEffects())
					if (effect.getType() == EffectType.WHEN_PLAYED)
						effect.run(player, newState, newState.isTwoPlayers());
			if (move.getTradeOption() != null)
				Main.getSWController().getPlayerController().doTrade(player, move.getTradeOption(), newState);
			break;
		case PLACE_SLOT:
			player.getHand().remove(move.getCard());
			player.getBoard().fill(player.getBoard().nextSlot());
			player.setChooseCard(null);
			if (move.getTradeOption() != null)
				Main.getSWController().getPlayerController().doTrade(player, move.getTradeOption(), newState);
			break;
		case SELL:
			player.getHand().remove(move.getCard());
			player.addCoins(3);
			player.setChooseCard(null);
		}

		return newState;
	}

	/**
	 * calculates the value of a game state, depending on AI's level
	 * 
	 * @param state game state
	 * @return value
	 */
	protected abstract int evaluate(GameState state);
}
