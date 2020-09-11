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

public class HardAI extends AdvancedAI {
	private static final long serialVersionUID = 1L;

	public HardAI(String name, WonderBoard board) {
		super(name, board);
	}

	@Override
	protected int evaluate(GameState state) {
		Player thisPlayer = Main.getSWController().getPlayerController().getPlayer(this.getName());
		int value = 0;
		switch (state.getAge()) {
		case 1:

			// Wonder ////////////////////////////////////////////
			BuildCapability[] capas = new BuildCapability[3];
			for (int i = 0; i < 3; i++) {
				capas[i] = Main.getSWController().getPlayerController().hasResources(thisPlayer, asList(thisPlayer.getBoard().getSlotResquirement(i)), state);
			}
			if (!getBoard().isFilled(0) && capas[0] != BuildCapability.NONE) {
				value += 9;
				if (capas[0] == BuildCapability.TRADE)
					value -= getTradeValue(asList(thisPlayer.getBoard().getSlotResquirement(0)), thisPlayer, state) / (thisPlayer.getCoins() / 4 + 1);
			}
			if (!getBoard().isFilled(1) && capas[1] != BuildCapability.NONE) {
				value += 8;
				if (capas[1] == BuildCapability.TRADE)
					value -= getTradeValue(asList(thisPlayer.getBoard().getSlotResquirement(1)), thisPlayer, state) / (thisPlayer.getCoins() / 4 + 1);
			}
			if (!getBoard().isFilled(2) && capas[2] != BuildCapability.NONE) {
				value += 7;
				if (capas[2] == BuildCapability.TRADE)
					value -= getTradeValue(asList(thisPlayer.getBoard().getSlotResquirement(2)), thisPlayer, state) / (thisPlayer.getCoins() / 4 + 1);
			}
			
			value += thisPlayer.getBoard().nextSlot() == -1 ? 1 : thisPlayer.getBoard().nextSlot();

			// Science //////////////////////////////////////////////////////////////

			value += thisPlayer.getBoard().getResearch().size();

			// Civil ////////////////////////////////////////////////////////////////

			value += getCivilPoints(thisPlayer) * 2 / 5;

			// Military /////////////////////////////////////////////////////////////

			if (!thisPlayer.getBoard().getMilitary().isEmpty())
				value++;

			// Coins ////////////////////////////////////////////////////////////////

			value += thisPlayer.getCoins() / 10;

			// Neighbour ////////////////////////////////////////////////////////////

			Player neighbour = Main.getSWController().getPlayerController().getNeighbour(state, true, thisPlayer);
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
			
			if (thisPlayer.getBoard().nextSlot() != -1) {
				switch (Main.getSWController().getPlayerController().hasResources(thisPlayer, asList(thisPlayer.getBoard().getNextSlotRequirement()), state)) {
				case OWN_RESOURCE:
					value += 7;
					break;
				case TRADE:
					value += thisPlayer.getCoins() / getTradeValue(asList(thisPlayer.getBoard().getNextSlotRequirement()), thisPlayer, state);
					break;
				default:
					break;
				}
				
				value += thisPlayer.getBoard().nextSlot() * 2;
			} else
				value += 6;

			// Civil ////////////////////////////////////////////////////

			value += getCivilPoints(thisPlayer);

			// Military /////////////////////////////////////////////////

			int ownPoints = Main.getSWController().getPlayerController().getMilitaryPoints(thisPlayer);
			int leftPoints = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, thisPlayer));
			int rightPoints = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, thisPlayer));
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
				for (Card card : thisPlayer.getBoard().getResearch())
					if (card.getScienceType() == BabylonBoard.types[i])
						amount[i]++;
			value += Math.min(Math.min(amount[0], amount[1]), amount[2]);
			
			amount = new int[] { 0, 0, 0 };
			for (int i = 0; i < BabylonBoard.types.length; i++)
				for (Card card : Main.getSWController().getPlayerController().getNeighbour(state, false, thisPlayer).getBoard().getResearch())
					if (card.getScienceType() == BabylonBoard.types[i])
						amount[i]++;
			value -= Math.min(Math.min(amount[0], amount[1]), amount[2]) * 5;

			// Coins ////////////////////////////////////////////////////

			if (thisPlayer.getCoins() < 6) {
				value += (thisPlayer.getCoins() - 7) * 2 / 3;
			}

			break;
		case 3: // Age 3 ////////////////////////////////////////////////
			
			int ownPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(thisPlayer);
			int leftPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, true, thisPlayer));
			int rightPoints2 = Main.getSWController().getPlayerController().getMilitaryPoints(Main.getSWController().getPlayerController().getNeighbour(state, false, thisPlayer));
			int maxDiff2 = Math.max(leftPoints2 - ownPoints2, rightPoints2 - ownPoints2);

			if (maxDiff2 < 0 && maxDiff2 >= -3)
				value += 2;
			else if (maxDiff2 <= 3)
				value -= 5;
			
			// Victory points //////////////////////////////////////////
			
			Main.getSWController().getGameController().runEffects(thisPlayer, thisPlayer.getBoard().getTrade(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(thisPlayer, thisPlayer.getBoard().getGuilds(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(thisPlayer, thisPlayer.getBoard().getCivil(), state.isTwoPlayers());
			// conflicts
			thisPlayer.addVictoryPoints(thisPlayer.getConflictPoints());
			thisPlayer.addVictoryPoints(-thisPlayer.getLosePoints());
			// coins
			thisPlayer.addVictoryPoints(thisPlayer.getCoins() / 3);
			// science
			thisPlayer.addVictoryPoints(Main.getSWController().getPlayerController().getSciencePoints(thisPlayer));
			
			value += thisPlayer.getVictoryPoints() * (state.isTwoPlayers() ? 1 : 3 / 4);
			
			break;
		}
		return value;
	}

	/**
	 * higher is worse
	 * 
	 * @param resources  list of resources
	 * @param thisPlayer this
	 * @param state      game state
	 * @return cost for the required trade
	 */
	private int getTradeValue(ArrayList<Resource> resources, Player thisPlayer, GameState state) {
		ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(thisPlayer, resources, state);
		if (trades.isEmpty())
			return 0;
		return trades.get(0).getLeftCost() + trades.get(0).getRightCost();
	}

	private int getCivilPoints(Player player) {
		int sum = 0;
		for (Card card : player.getBoard().getCivil())
			sum += card.getvPoints();
		return sum;
	}

	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash) {
		// TODO Auto-generated method stub
		return null;
	}

	private static ArrayList<Resource> asList(Resource... bundles) {
		return new ArrayList<>(Arrays.asList(bundles));
	}

}
