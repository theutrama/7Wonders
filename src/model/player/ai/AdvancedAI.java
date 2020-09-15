package model.player.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import controller.GameController;
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

/** abstract class of easy, medium and hard AI */
@SuppressWarnings("PMD")
public abstract class AdvancedAI extends ArtInt {
	/** UID of serial version */
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
			System.err.println("[Advanced AI] case 1");
			for (MoveTree child : tree.getChildren()) {
				int value = evaluate(child.getState(), this.getName());
				if (value > maxValue) {
					maxMove = child.getMove();
					maxValue = value;
				}
			}
		} else {
			MoveTree movetree = minimax(tree).tree;
			while (movetree.getParent().getParent() != null) {
				movetree = movetree.getParent();
			}
			maxMove = movetree.getMove();
		}

		this.next = maxMove;

		System.out.println("[" + getClass().getSimpleName() + "] action: " + next.getAction() + "  card: " + next.getCard() + "  trade: " + next.getTradeOption());
	}

	/**
	 * minimax algorithm
	 * 
	 * @param tree root
	 * @return result bundle
	 */
	private MinimaxResult minimax(MoveTree tree) {
		if (tree.getChildren().isEmpty())
			return new MinimaxResult(tree, evaluate(tree.getState(), this.getName()));
		if (tree.getState().getPlayer().getName().equals(this.getName())) { // this layer is max layer
			MinimaxResult maxValue = null;
			for (MoveTree child : tree.getChildren()) {
				MinimaxResult value = minimax(child);
				if (maxValue == null || value.value > maxValue.value)
					maxValue = value;
			}
			return maxValue;
		} else { // this layer is min layer
			MinimaxResult minValue = null;
			for (MoveTree child : tree.getChildren()) {
				MinimaxResult value = minimax(child);
				if (minValue == null || value.value < minValue.value)
					minValue = value;
			}
			return minValue;
		}
	}

	/** inner class to save the resulting values of minimax algorithm */
	private static class MinimaxResult {
		private MoveTree tree;
		private int value;

		/**
		 * create result
		 * 
		 * @param tree  sets {@link #tree}
		 * @param value sets {@link #value}
		 */
		public MinimaxResult(MoveTree tree, int value) {
			this.tree = tree;
			this.value = value;
		}
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

		Flag flag = new Flag(), finished = new Flag();

		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!finished.value)
					flag.value = true;
			}
		}, 9000);

		while (true) {
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

				if (flag.value) {
					for (MoveTree child : leaves)
						child.getChildren().clear();
					return tree;
				}
			}

			boolean breakAfterwards = false;

			for (MoveTree newLeaf : newLeaves) {
				for (Player player : newLeaf.getState().getPlayers()) {
					if (player.isMausoleum()) {
						Card card = getHalikarnassusCard(player, newLeaf.getState().getTrash(), newLeaf.getState());
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
					if (newLeaf.getState().getRound() < GameController.NUM_ROUNDS)
						Main.getSWController().getGameController().nextRound(Main.getSWController().getGame(), newLeaf.getState());
					else // stop look-ahead at the end of age
						breakAfterwards = true;
				}
			}

			leaves.clear();
			leaves.addAll(newLeaves);

			if (breakAfterwards)
				break;
		}

		finished.value = true;

		return tree;
	}

	/** inner class to allow the timer to change a variable */
	private static class Flag {
		private boolean value = false;
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
			switch (player.getBoard().nextSlot()) {
			case 0:
				player.getBoard().slot1();
				break;
			case 1:
				player.getBoard().slot2();
				break;
			case 2:
				player.getBoard().slot3();
				break;
			}
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
	 * @param state      game state
	 * @param playername player name
	 * @return value
	 */
	protected abstract int evaluate(GameState state, String playername);

	/**
	 * calculates the sum of all built civil points
	 * 
	 * @param player player
	 * @return sum of civil points
	 */
	protected int getCivilPoints(Player player) {
		int sum = 0;
		for (Card card : player.getBoard().getCivil())
			sum += card.getvPoints();
		return sum;
	}

	/**
	 * creates Arraylist from vararg
	 * 
	 * @param bundles list of resources
	 * @return vararg as arraylist
	 */
	protected static ArrayList<Resource> asList(Resource... bundles) {
		return new ArrayList<>(Arrays.asList(bundles));
	}
}
