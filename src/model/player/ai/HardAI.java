package model.player.ai;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import model.GameState;
import model.board.BabylonBoard;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Resource;
import model.player.Player;

/** Hard Artificial Intelligence */
public class HardAI extends AdvancedAI {
	private static final long serialVersionUID = 1L;

	/**
	 * create a hard AI
	 * 
	 * @param name  name
	 * @param board assigned wonder board
	 */
	public HardAI(String name, WonderBoard board) {
		super(name, board);
	}

	/**
	 * get the value for a specific game state
	 */
	@Override
	protected int evaluate(GameState state, String playername) {
		Player player = Main.getSWController().getPlayerController().getPlayer(playername, state);
		int value = 0;
		switch (state.getAge()) {
		case 1:

			// Wonder ////////////////////////////////////////////
			BuildCapability[] capas = new BuildCapability[3];
			for (int i = 0; i < 3; i++) {
				capas[i] = Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state);
			}
			if (!getBoard().isFilled(0) && capas[0] != BuildCapability.NONE) {
				value += 9;
				if (capas[0] == BuildCapability.TRADE)
					value -= getTradeValue(asList(player.getBoard().getSlotResquirement(0)), player, state) / (player.getCoins() / 4 + 1);
			}
			if (!getBoard().isFilled(1) && capas[1] != BuildCapability.NONE) {
				value += 8;
				if (capas[1] == BuildCapability.TRADE)
					value -= getTradeValue(asList(player.getBoard().getSlotResquirement(1)), player, state) / (player.getCoins() / 4 + 1);
			}
			if (!getBoard().isFilled(2) && capas[2] != BuildCapability.NONE) {
				value += 7;
				if (capas[2] == BuildCapability.TRADE)
					value -= getTradeValue(asList(player.getBoard().getSlotResquirement(2)), player, state) / (player.getCoins() / 4 + 1);
			}

			value += player.getBoard().nextSlot() == -1 ? 10 : player.getBoard().nextSlot() * 4;

			// Science //////////////////////////////////////////////////////////////

			value += player.getBoard().getResearch().size();

			// Civil ////////////////////////////////////////////////////////////////

			value += getCivilPoints(player) * 2 / 5;

			// Military /////////////////////////////////////////////////////////////

			if (!player.getBoard().getMilitary().isEmpty())
				value++;

			// Coins ////////////////////////////////////////////////////////////////

			value += player.getCoins() / 10;

			// Neighbour ////////////////////////////////////////////////////////////

			Player neighbour = Main.getSWController().getPlayerController().getNeighbour(state, true, player);
			if (neighbour.getBoard().nextSlot() != -1) {
				switch (Main.getSWController().getPlayerController().hasResources(neighbour, asList(neighbour.getBoard().getNextSlotRequirement()), state)) {
				case TRADE:
					value += 3;
					break;
				case NONE:
					value += 5;
					break;
				default:
					break;
				}
			}

			break;

		// Age 2 //////////////////////////
		case 2:

			// Resources //////////////////////////////////////////////

			if (player.getBoard().nextSlot() != -1) {
				switch (Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getNextSlotRequirement()), state)) {
				case OWN_RESOURCE:
					value += 7;
					break;
				case TRADE:
					value += player.getCoins() / getTradeValue(asList(player.getBoard().getNextSlotRequirement()), player, state);
					break;
				default:
					break;
				}

				value += player.getBoard().nextSlot() * 4;
			} else
				value += 12;

			// Civil ////////////////////////////////////////////////////

			value += getCivilPoints(player);

			// Military /////////////////////////////////////////////////

			int ownPoints = Main.getSWController().getPlayerController().getMilitaryPoints(player);
			int leftPoints = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, player));
			int rightPoints = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player));
			int maxDiff = Math.max(leftPoints - ownPoints, rightPoints - ownPoints);

			if (maxDiff < 0)
				value += 2;
			else if (maxDiff == 1)
				value -= 1;
			else if (maxDiff >= 2)
				value -= 2;

			// Research /////////////////////////////////////////////////

			int[] amount = new int[] { 0, 0, 0 };
			for (int i = 0; i < BabylonBoard.types.length; i++)
				for (Card card : player.getBoard().getResearch())
					if (card.getScienceType() == BabylonBoard.types[i])
						amount[i]++;
			value += Math.min(Math.min(amount[0], amount[1]), amount[2]);

			amount = new int[] { 0, 0, 0 };
			for (int i = 0; i < BabylonBoard.types.length; i++)
				for (Card card : Main.getSWController().getPlayerController().getNeighbour(state, false, player).getBoard().getResearch())
					if (card.getScienceType() == BabylonBoard.types[i])
						amount[i]++;
			value -= Math.min(Math.min(amount[0], amount[1]), amount[2]) * 5;

			// Coins ////////////////////////////////////////////////////

			if (player.getCoins() < 6) {
				value += (player.getCoins() - 7) * 2 / 3;
			}

			break;
		case 3: // Age 3 ////////////////////////////////////////////////

			int ownPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(player);
			int leftPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, player));
			int rightPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player));
			int maxDiff2 = Math.max(leftPoints2 - ownPoints2, rightPoints2 - ownPoints2);

			if (maxDiff2 < 0 && maxDiff2 >= -3)
				value += 2;
			else if (maxDiff2 <= 3)
				value -= 5;

			// Victory points //////////////////////////////////////////

			Main.getSWController().getGameController().runEffects(player, player.getBoard().getTrade(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(player, player.getBoard().getGuilds(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(player, player.getBoard().getCivil(), state.isTwoPlayers());
			// conflicts
			player.addVictoryPoints(player.getConflictPoints());
			player.addVictoryPoints(-player.getLosePoints());
			// coins
			player.addVictoryPoints(player.getCoins() / 3);
			// science
			player.addVictoryPoints(Main.getSWController().getPlayerController().getSciencePoints(player));

			value += player.getVictoryPoints() * (state.isTwoPlayers() ? 1 : 3 / 4);

			break;
		}
		return value;
	}

	/**
	 * higher is worse
	 * 
	 * @param resources  list of resources
	 * @param player this
	 * @param state      game state
	 * @return cost for the required trade
	 */
	private int getTradeValue(ArrayList<Resource> resources, Player player, GameState state) {
		ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(player, resources, state);
		if (trades.isEmpty())
			return 0;
		return trades.get(0).getLeftCost() + trades.get(0).getRightCost();
	}

	/**
	 * calculates the sum of all built civil points
	 * 
	 * @param player player
	 * @return sum of civil points
	 */
	private int getCivilPoints(Player player) {
		int sum = 0;
		for (Card card : player.getBoard().getCivil())
			sum += card.getvPoints();
		return sum;
	}

	/**
	 * select Halikarnassus card
	 */
	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash, GameState state) {
		if (trash.isEmpty())
			return null;
		int maxValue = Integer.MIN_VALUE, maxIndex = 0;
		for (int i = 0; i < trash.size(); i++) {
			GameState newState = state.deepClone();
			Main.getSWController().getPlayerController().getPlayer(player.getName(), newState).getBoard().addCard(trash.get(i));
			int value = evaluate(newState, player.getName());
			if (value > maxValue) {
				maxValue = value;
				maxIndex = i;
			}
		}
		return trash.get(maxIndex);
	}

	/**
	 * creates Arraylist from vararg
	 * 
	 * @param bundles
	 * @return
	 */
	private static ArrayList<Resource> asList(Resource... bundles) {
		return new ArrayList<>(Arrays.asList(bundles));
	}

}
