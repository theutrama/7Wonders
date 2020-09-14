package model.player.ai;

import java.util.ArrayList;

import application.Main;
import controller.utils.BuildCapability;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.card.ResourceType;
import model.player.Player;

/** Hard Artificial Intelligence */
public class HardAI extends AdvancedAI {
	private static final long serialVersionUID = 1L;

	private static final String TRADINGPOST = "tradingpost", ALEXANDRIA = "Alexandria", EPHESOS = "Ephesos", HALIKARNASSUS = "Halikarnassus", MARKETPLACE = "marketplace";
	private static final int ONE = 1, TWO = 2, FOURTY = 40;

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
				if (card.getProducing().size() == ONE) {
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
					if (sum == FOURTY)
						sum -= 10;
					value += sum;
				}
			}

//			if (value >= 10) {
//				value -= player.getBoard().nextSlot() == -1 ? 15 : player.getBoard().nextSlot() * 5;
//			}

			value += player.getBoard().nextSlot() == -1 ? 6 : player.getBoard().nextSlot() * 2;

			for (int i = 0; i < 3; i++)
				if (capas[i] != BuildCapability.NONE)
					value += (100 - 10 * i);

			// Research /////////////////////////////////////////////////////////////

			value -= player.getBoard().getResearch().size() * 30;

			// Trade ////////////////////////////////////////////////////////////////

			if (state.isTwoPlayers()) {
				for (Card card : player.getBoard().getTrade()) {
					if (card.getInternalName().contains(TRADINGPOST)) {
						value += 10;
						break;
					}
				}
			} else {
				for (Card card : player.getBoard().getTrade()) {
					if (card.getInternalName().contains(TRADINGPOST)) {
						value += 10;
					}
				}
			}

			if (player.getBoard().getBoardName().equals(ALEXANDRIA) || player.getBoard().getBoardName().equals(EPHESOS) || player.getBoard().getBoardName().equals(HALIKARNASSUS)) {
				for (Card card : player.getBoard().getTrade()) {
					if (card.getInternalName().contains(MARKETPLACE)) {
						value += 5;
						break;
					}
				}
			}

			// Military /////////////////////////////////////////////////////////////

			value -= player.getBoard().getMilitary().size() * 10;

			// Coins ////////////////////////////////////////////////////////////////

			if (player.getCoins() > TWO)
				value -= (player.getCoins() - 2) * 4;

			break;

		// Age 2 //////////////////////////
		case 2:

			// Resources //////////////////////////////////////////////

			BuildCapability[] capas2 = new BuildCapability[3];
			for (int i = 0; i < 3; i++) {
				capas2[i] = Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state);
			}

			if (player.getBoard().isFilled(0)) {
				value += 3;
				if (player.getBoard().isFilled(1)) {
					value += 5;
					if (player.getBoard().isFilled(2))
						value += 7;
					else if (capas2[2] != BuildCapability.NONE)
						value += 7;
				} else if (capas2[1] != BuildCapability.NONE)
					value += 5;
			} else if (capas2[0] != BuildCapability.NONE)
				value += 3;

			// Civil ////////////////////////////////////////////////////

			value += getCivilPoints(player);
			value -= getCivilPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player)) / 2;

			// Military /////////////////////////////////////////////////

			int ownPoints = Main.getSWController().getPlayerController().getMilitaryPoints(player);
			int leftPoints = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, player));
			int rightPoints = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player));
			int maxDiff = Math.max(leftPoints - ownPoints, rightPoints - ownPoints);

			if (maxDiff < 0)
				value += 4;
			else if (maxDiff == ONE)
				value -= 4;
			else if (maxDiff >= TWO)
				value -= 2;

			// Research /////////////////////////////////////////////////

			value -= Main.getSWController().getPlayerController().getSciencePoints(Main.getSWController().getPlayerController().getNeighbour(state, false, player));
			value -= player.getBoard().getResearch().size() * 3;

			// Coins ////////////////////////////////////////////////////

			value += player.getCoins() / 3;

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

			// Neighbour //////////////////////////////////////////////
			Player neighbour = Main.getSWController().getPlayerController().getNeighbour(state, true, player);

			Main.getSWController().getGameController().runEffects(state, neighbour, neighbour.getBoard().getTrade(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(state, neighbour, neighbour.getBoard().getGuilds(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(state, neighbour, neighbour.getBoard().getCivil(), state.isTwoPlayers());
			// conflicts
			player.addVictoryPoints(neighbour.getConflictPoints());
			player.addVictoryPoints(-neighbour.getLosePoints());
			// coins
			player.addVictoryPoints(neighbour.getCoins() / 3);
			// science
			player.addVictoryPoints(Main.getSWController().getPlayerController().getSciencePoints(neighbour));

			value -= neighbour.getVictoryPoints();

			break;
		}
		return value;
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
