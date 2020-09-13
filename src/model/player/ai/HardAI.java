package model.player.ai;

import java.util.ArrayList;

import application.Main;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Resource;
import model.card.ResourceType;
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

//			if (!getBoard().isFilled(0) && capas[0] != BuildCapability.NONE) {
//				value += 9;
//				if (capas[0] == BuildCapability.TRADE)
//					value -= getTradeValue(asList(player.getBoard().getSlotResquirement(0)), player, state) / (player.getCoins() / 4 + 1);
//			}
//			if (!getBoard().isFilled(1) && capas[1] != BuildCapability.NONE) {
//				value += 8;
//				if (capas[1] == BuildCapability.TRADE)
//					value -= getTradeValue(asList(player.getBoard().getSlotResquirement(1)), player, state) / (player.getCoins() / 4 + 1);
//			}
//			if (!getBoard().isFilled(2) && capas[2] != BuildCapability.NONE) {
//				value += 7;
//				if (capas[2] == BuildCapability.TRADE)
//					value -= getTradeValue(asList(player.getBoard().getSlotResquirement(2)), player, state) / (player.getCoins() / 4 + 1);
//			}
			// value += player.getBoard().nextSlot() == -1 ? 10 : player.getBoard().nextSlot() * 4;

//			if (player.getBoard().isFilled(0)) {
//				value += 10;
//				if (player.getBoard().isFilled(1)) {
//					value += 10;
//					if (player.getBoard().isFilled(2)) {
//						value += 10;
//					} else if (capas[2] != BuildCapability.NONE) {
//						value += 7;
//					}
//				} else if (capas[1] != BuildCapability.NONE) {
//					value += 7;
//				}
//			} else if (capas[0] != BuildCapability.NONE) {
//				value += 7;
//			}
//
//			if (!player.getBoard().isFilled(2)) {
//				if (capas[2] != BuildCapability.NONE) {
//					value += 2;
//				}
//				if (!player.getBoard().isFilled(1)) {
//					if (capas[1] != BuildCapability.NONE) {
//						value += 2;
//					}
//					if (!player.getBoard().isFilled(0) && capas[0] != BuildCapability.NONE)
//						value += 2;
//				}
//			}
//
			for (Card card : player.getBoard().getResources()) {
				if (card.getProducing().size() == 1) {
					ResourceType type = card.getProducing().get(0).getType();
					if (type != player.getBoard().getSlotResquirement(0).getType() && type != player.getBoard().getSlotResquirement(1).getType()
							&& type != player.getBoard().getSlotResquirement(2).getType())
						value -= 1;
				}
			}
			
			for (BuildCapability capacity: capas)
				if (capacity != BuildCapability.NONE)
					value += 10;
			
			value -= player.getBoard().nextSlot() == -1 ? 6 : player.getBoard().nextSlot() * 2;

			// Research /////////////////////////////////////////////////////////////

			value -= player.getBoard().getResearch().size() * 3;

			// Civil ////////////////////////////////////////////////////////////////

			value += getCivilPoints(player) / 2;

			// Military /////////////////////////////////////////////////////////////

			if (!player.getBoard().getMilitary().isEmpty())
				value -= 5;

			// Coins ////////////////////////////////////////////////////////////////

//			if (player.getCoins() > 6)
//				value -= player.getCoins() / 2;

			// Neighbour ////////////////////////////////////////////////////////////

//			Player neighbour = Main.getSWController().getPlayerController().getNeighbour(state, true, player);
//			if (neighbour.getBoard().nextSlot() != -1) {
//				switch (Main.getSWController().getPlayerController().hasResources(neighbour, asList(neighbour.getBoard().getNextSlotRequirement()), state)) {
//				case TRADE:
//					value += 3;
//					break;
//				case NONE:
//					value += 5;
//					break;
//				default:
//					break;
//				}
//			}

			for (Card card : player.getHand()) {
				if (Main.getSWController().getPlayerController().canBuild(player, card, state) != BuildCapability.NONE)
					value += 2;
			}

			break;

		// Age 2 //////////////////////////
		case 2:

			// Resources //////////////////////////////////////////////

			BuildCapability[] capas2 = new BuildCapability[3];
			for (int i = 0; i < 3; i++) {
				capas2[i] = Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state);
			}

//			if (player.getBoard().isFilled(0)) {
//				value += 8;
//				if (player.getBoard().isFilled(1)) {
//					value += 8;
//					if (player.getBoard().isFilled(2)) {
//						value += 8;
//					} else if (capas2[2] != BuildCapability.NONE) {
//						value += 7;
//					}
//				} else if (capas2[1] != BuildCapability.NONE) {
//					value += 7;
//				}
//			} else if (capas2[0] != BuildCapability.NONE) {
//				value += 7;
//			}

//			if (!player.getBoard().isFilled(2)) {
//				if (capas2[2] != BuildCapability.NONE) {
//					value += 2;
//				}
//				if (!player.getBoard().isFilled(1)) {
//					if (capas2[1] != BuildCapability.NONE) {
//						value += 2;
//					}
//					if (!player.getBoard().isFilled(0) && capas2[0] != BuildCapability.NONE)
//						value += 2;
//				}
//			}
			
			for (BuildCapability capacity: capas2)
				if (capacity != BuildCapability.NONE)
					value += 10;
			
			value -= player.getBoard().nextSlot() == -1 ? 3 : player.getBoard().nextSlot();

//			for (Card card : player.getBoard().getResources()) {
//				if (card.getProducing().size() == 1) {
//					ResourceType type = card.getProducing().get(0).getType();
//					if (type != player.getBoard().getSlotResquirement(0).getType() && type != player.getBoard().getSlotResquirement(1).getType()
//							&& type != player.getBoard().getSlotResquirement(2).getType())
//						value -= 5;
//				}
//			}

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

//			int[] amount = new int[] { 0, 0, 0 };
//			for (int i = 0; i < BabylonBoard.types.length; i++)
//				for (Card card : player.getBoard().getResearch())
//					if (card.getScienceType() == BabylonBoard.types[i])
//						amount[i]++;
//			value += Math.min(Math.min(amount[0], amount[1]), amount[2]);
//
//			amount = new int[] { 0, 0, 0 };
//			for (int i = 0; i < BabylonBoard.types.length; i++)
//				for (Card card : Main.getSWController().getPlayerController().getNeighbour(state, false, player).getBoard().getResearch())
//					if (card.getScienceType() == BabylonBoard.types[i])
//						amount[i]++;
//			value -= Math.min(Math.min(amount[0], amount[1]), amount[2]) * 5;

			value -= player.getBoard().getResearch().size() * 3;

			// Coins ////////////////////////////////////////////////////

//			if (player.getCoins() > 6)
//				value -= player.getCoins() / 2;
			value += player.getCoins() / 4;

			break;
		case 3: // Age 3 ////////////////////////////////////////////////
			
			value += player.getBoard().isFilled(2) ? 2 : (player.getBoard().isFilled(1) ? 5 : (player.getBoard().isFilled(0) ? 2 : 0));

			int ownPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(player);
			int leftPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, player));
			int rightPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player));
			int maxDiff2 = Math.max(leftPoints2 - ownPoints2, rightPoints2 - ownPoints2);

			if (maxDiff2 < 0 && maxDiff2 >= -3)
				value += 5;
//			else if (maxDiff2 <= 3)
//				value -= 5;
			else
				value -= 7;

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

			value += player.getVictoryPoints();

			break;
		}
		return value;
	}

	/**
	 * higher is worse
	 * 
	 * @param resources list of resources
	 * @param player    this
	 * @param state     game state
	 * @return cost for the required trade
	 */
	private int getTradeValue(ArrayList<Resource> resources, Player player, GameState state) {
		ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(player, resources, state);
		if (trades.isEmpty())
			return 0;
		return trades.get(0).getLeftCost() + trades.get(0).getRightCost();
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

}
