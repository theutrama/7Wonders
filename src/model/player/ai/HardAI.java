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

			for (Card card : player.getBoard().getResources()) {
				if (card.getProducing().size() == 1) {
					ResourceType type = card.getProducing().get(0).getType();
					if (type == player.getBoard().getSlotResquirement(0).getType() || type == player.getBoard().getSlotResquirement(1).getType()
							|| type == player.getBoard().getSlotResquirement(2).getType())
						value += 10;
				} else {
					ResourceType type1 = card.getProducing().get(0).getType(), type2 = card.getProducing().get(1).getType();
					int sum = 0;
					if (type1 == player.getBoard().getSlotResquirement(0).getType() || type1 == player.getBoard().getSlotResquirement(1).getType()
							|| type1 == player.getBoard().getSlotResquirement(2).getType())
						sum += 20;
					if (type2 == player.getBoard().getSlotResquirement(0).getType() || type2 == player.getBoard().getSlotResquirement(1).getType()
							|| type2 == player.getBoard().getSlotResquirement(2).getType())
						sum += 20;
					if (sum == 40)
						sum -= 10;
					value += sum;
				}
			}

			for (int i = 0; i < 3; i++)
				if (capas[i] != BuildCapability.NONE)
					value += (100 - 10 * i);

			value -= player.getBoard().nextSlot() == -1 ? 60 : player.getBoard().nextSlot() * 20;

			// Research /////////////////////////////////////////////////////////////

			value -= player.getBoard().getResearch().size() * 30;

			// Trade ////////////////////////////////////////////////////////////////
			
			if (state.isTwoPlayers()) {
				for (Card card: player.getBoard().getTrade()) {
					if (card.getInternalName().contains("tradingpost")) {
						value += 10;
						break;
					}
				}
			} else {
				for (Card card: player.getBoard().getTrade()) {
					if (card.getInternalName().contains("tradingpost")) {
						value += 10;
					}
				}
			}
			
			if (player.getBoard().getBoardName().equals("Alexandria") || player.getBoard().getBoardName().equals("Ephesos") || player.getBoard().getBoardName().equals("Halikarnassus")) {
				for (Card card: player.getBoard().getTrade()) {
					if (card.getInternalName().contains("marketplace")) {
						value += 5;
						break;
					}
				}
			}

			// Military /////////////////////////////////////////////////////////////

			value -= player.getBoard().getMilitary().size() * 20;

			// Coins ////////////////////////////////////////////////////////////////

			// Neighbour ////////////////////////////////////////////////////////////

			for (Card card : player.getHand()) {
				if (Main.getSWController().getPlayerController().canBuild(player, card, state) != BuildCapability.NONE)
					value += 20;
			}

			break;

		// Age 2 //////////////////////////
		case 2:

			// Resources //////////////////////////////////////////////

			BuildCapability[] capas2 = new BuildCapability[3];
			for (int i = 0; i < 3; i++) {
				capas2[i] = Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state);
			}

			for (BuildCapability capacity : capas2)
				if (capacity != BuildCapability.NONE)
					value += 4;

			value -= player.getBoard().nextSlot() == -1 ? 3 : player.getBoard().nextSlot();

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

			value -= player.getBoard().getResearch().size() * 3;

			// Coins ////////////////////////////////////////////////////

			value += player.getCoins() / 4;

			break;
		case 3: // Age 3 ////////////////////////////////////////////////

			int ownPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(player);
			int leftPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, player));
			int rightPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player));
			int maxDiff2 = Math.max(leftPoints2 - ownPoints2, rightPoints2 - ownPoints2);

			if (maxDiff2 < 0 && maxDiff2 >= -3)
				value += 5;
			else
				value -= 7;

			// Victory points //////////////////////////////////////////

			Main.getSWController().getGameController().runEffects(state, player, player.getBoard().getTrade(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(state, player, player.getBoard().getGuilds(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(state, player, player.getBoard().getCivil(), state.isTwoPlayers());
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
