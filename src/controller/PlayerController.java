package controller;

import model.board.AlexandriaBoard;
import model.board.BabylonBoard;
import model.board.RhodosBoard;
import model.board.WonderBoard;
import java.util.ArrayList;
import java.util.Arrays;

import application.Utils;
import controller.utils.BuildCapability;
import controller.utils.ResourceBundle;
import controller.utils.ResourceTree;
import controller.utils.TradeOption;
import model.card.Card;
import model.card.Resource;
import model.card.ResourceType;
import model.player.AI;
import model.player.Difficulty;
import model.player.Player;

public class PlayerController {
	/** main controller */
	private SevenWondersController swController;
	/** wonder board controller */
	private WonderBoardController wb;

	/**
	 * create player controller
	 * 
	 * @param swController
	 */
	public PlayerController(SevenWondersController swController) {
		this.swController = swController;
		this.wb = swController.getWonderBoardController();
	}

	/**
	 * add a player and assign wonder board
	 * 
	 * @param playername  player's name
	 * @param wonderboard player's board
	 * @return Player object
	 */
	public Player createPlayer(String playername, String wonderboard) {
		WonderBoard board = this.wb.createWonderBoard(wonderboard);
		Player player = new Player(playername, board);
		board.setPlayer(player);

		return player;
	}

	/**
	 * get player by name
	 * 
	 * @param playername player name
	 * @return player object or null if no player of the current game has this name
	 */
	public Player getPlayer(String playername) {
		int state = swController.getGame().getCurrentState();
		ArrayList<Player> list = swController.getGame().getStates().get(state).getPlayers();

		for (Player player : list) {
			if (player.getName().equalsIgnoreCase(playername)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * create an AI
	 * 
	 * @param playername  name of the AI
	 * @param wonderboard assigned wonder's name
	 * @param difficulty  AI level
	 * @return AI object
	 */
	public AI createAI(String playername, String wonderboard, Difficulty difficulty) {
		WonderBoard board = wb.createWonderBoard(wonderboard);
		AI ai = new AI(difficulty, board);
		return ai;
	}

	/**
	 * make player choose a card
	 * 
	 * @param card   chosen card
	 * @param player player
	 */
	public void chooseCard(Card card, Player player) {
		player.setChooseCard(card);
	}

	/**
	 * find left neighbour
	 * 
	 * @param player player
	 * @return left neighbour
	 */
	public Player getLeftNeighbour(Player player) {
		ArrayList<Player> players = swController.getGame().getStates().get(swController.getGame().getCurrentState()).getPlayers();
		if (players.indexOf(player) == 0) {
			return players.get(players.size() - 1);
		}
		return players.get(players.indexOf(player) - 1);
	}

	/**
	 * find right neighbour
	 * 
	 * @param player player
	 * @return right neighbour
	 */
	public Player getRightNeighbour(Player player) {
		ArrayList<Player> players = swController.getGame().getStates().get(swController.getGame().getCurrentState()).getPlayers();
		if (players.indexOf(player) == players.size() - 1) {
			return players.get(0);
		}
		return players.get(players.indexOf(player) + 1);
	}

	/**
	 * get military points of a player
	 * 
	 * @param player player
	 * @return sum of the military points in the military list
	 */
	public int getMilitaryPoints(Player player) {
		int miliPoints = 0;

		if (player.getBoard() instanceof RhodosBoard) {
			miliPoints += ((RhodosBoard) player.getBoard()).getMilitaryPoints();
		}

		ArrayList<Card> list = player.getBoard().getMilitary();
		for (Card card : list) {
			miliPoints += card.isProducing(ResourceType.MILITARY);
		}
		return miliPoints;
	}

	/**
	 * get science points by quantity of science buildings
	 * 
	 * @param amount array that has one quantity per science symbol
	 * @return points as specified in the game rule
	 */
	private int getSciencePoints(int[] amount) {
		int victory_points = 0;
		for (int i = 0; i < amount.length; i++) {
			victory_points += amount[i] * amount[i];
		}

		while (amount[0] >= 1 && amount[1] >= 1 && amount[2] >= 1) {
			victory_points += 7;
			amount[0]--;
			amount[1]--;
			amount[2]--;
		}

		return victory_points;
	}

	/**
	 * calculates the science points of a player
	 * 
	 * @param player player
	 * @return sciene points depending on the current board
	 */
	public int getSciencePoints(Player player) {
		int[] amount = new int[] { 0, 0, 0 };
		for (int i = 0; i < BabylonBoard.types.length; i++) {
			for (Card card : player.getBoard().getResearch()) {
				if (card.getScienceType() == BabylonBoard.types[i]) {
					amount[i]++;
				}
			}
		}

		boolean hasBabylon = (player.getBoard() instanceof BabylonBoard && ((BabylonBoard) player.getBoard()).isFilled(2));
		boolean hasScientistsGuild = swController.getCardController().hasCard(player, "scientistsguild"); // TODO not internal name

		if (hasBabylon && hasScientistsGuild) {
			int result = 0;
			int[] clone;
			for (int i = 0; i < 3; i++) {
				for (int a = 0; a < 3; a++) {
					int type = getSciencePoints(Utils.addToIndex(1, i, clone = amount.clone()));
					type = getSciencePoints(Utils.addToIndex(1, a, clone));
					result = Math.max(type, result);
				}
			}
			return result;
		} else if (hasBabylon || hasScientistsGuild) {
			int type1 = getSciencePoints(Utils.addToIndex(1, 0, amount.clone()));
			int type2 = getSciencePoints(Utils.addToIndex(1, 1, amount.clone()));
			int type3 = getSciencePoints(Utils.addToIndex(1, 2, amount.clone()));

			return Utils.max(new int[] { type1, type2, type3 });

		}
		return getSciencePoints(amount);
	}

	/**
	 * sum of resources produced by the non-selective cards of a player
	 * 
	 * @param player player
	 * @return sum of static resources
	 */
	private ResourceBundle getStaticResources(Player player) {
		ResourceBundle result = new ResourceBundle();
		result.add(player.getBoard().getResource());
		for (Card card : player.getBoard().getResources()) {
			if (card.getProducing().size() == 1)
				result.add(card.getProducing().get(0));
		}
		return result;
	}

	/**
	 * generates the tree of all possible resource combinations
	 * 
	 * @param player          player
	 * @param staticResources the static amount of non-selective resources
	 * @return a resource tree
	 */
	private ResourceTree generateResourceTree(Player player, ResourceBundle staticResources) {
		ResourceTree tree = new ResourceTree(staticResources);
		for (Card card : player.getBoard().getResources()) {
			if (card.getProducing().size() > 1)
				tree.addResourceOption(card.getProducing());
		}
		for (Card card : player.getBoard().getTrade()) {
			if (card.getProducing().size() > 1)
				tree.addResourceOption(card.getProducing());
		}
		if (player.getBoard() instanceof AlexandriaBoard && player.getBoard().isFilled(1))
			tree.addResourceOption(
					new ArrayList<>(Arrays.asList(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.STONE))));

		return tree;
	}

	/**
	 * used to find out the way a player can build a card
	 * 
	 * @param player player
	 * @param card   card
	 * @return the type of ability to build
	 */
	public BuildCapability canBuild(Player player, Card card) {
		if (card.getDependencies().length > 0) {
			boolean dependencies = true;
			for (String cardname : card.getDependencies()) {
				if (!swController.getCardController().hasCard(player, cardname)) {
					dependencies = false;
					break;
				}
			}
			if (dependencies)
				return BuildCapability.FREE;
		}

		return hasResources(player, card.getRequired());
	}

	/**
	 * used to find the way a player can collect the specified list of resources
	 * 
	 * @param player    player
	 * @param resources list of resources
	 * @return the type of ability to collect the resources
	 */
	public BuildCapability hasResources(Player player, ArrayList<Resource> resources) {
		if (resources.isEmpty())
			return BuildCapability.OWN_RESOURCE;

		ResourceBundle staticResources = getStaticResources(player);
		ResourceBundle cardRequirement = new ResourceBundle(resources);

		if (staticResources.greaterOrEqualThan(cardRequirement))
			return BuildCapability.OWN_RESOURCE;

		ArrayList<ResourceBundle> combinations = generateResourceTree(player, staticResources).getAllCombinations();

		removeDuplicates(combinations);

		for (ResourceBundle bundle : combinations) {
			if (bundle.greaterOrEqualThan(cardRequirement))
				return BuildCapability.OWN_RESOURCE;
		}

		return getTradeOptions(player, cardRequirement).isEmpty() ? BuildCapability.NONE : BuildCapability.TRADE;
	}

	/**
	 * find all options to trade with the two neighbours
	 * 
	 * @param player    player
	 * @param resources required resources
	 * @return list of trade options
	 */
	private ArrayList<TradeOption> getTradeOptions(Player player, ResourceBundle resources) {
		ArrayList<ResourceBundle> combinations = generateResourceTree(player, getStaticResources(player)).getAllCombinations();

		removeDuplicates(combinations);

		ArrayList<ResourceBundle> missing = new ArrayList<>();
		for (ResourceBundle bundle : combinations) // get list of missing resource quantities per combination
			missing.add(bundle.getMissing(resources));

		ArrayList<TradeOption> result = new ArrayList<>();
		Player left = getLeftNeighbour(player), right = getRightNeighbour(player);
		ArrayList<ArrayList<ResourceBundle>> leftTradeLists = generateResourceTree(left, getStaticResources(left)).getAllCombinationsAsList();
		ArrayList<ArrayList<ResourceBundle>> rightTradeLists = generateResourceTree(right, getStaticResources(right)).getAllCombinationsAsList();
		
		ArrayList<ResourceBundle> leftTrades = new ArrayList<>(), rightTrades = new ArrayList<>();
		leftTradeLists.forEach(list -> leftTrades.addAll(allSums(list)));
		rightTradeLists.forEach(list -> rightTrades.addAll(allSums(list)));
		
		removeDuplicates(leftTrades);
		removeDuplicates(rightTrades);

		for (ResourceBundle missingResources : missing) {
			for (ResourceBundle leftTrade : leftTrades) {
				for (ResourceBundle rightTrade : rightTrades) {
					if (leftTrade.getCostForPlayer(player, true) + rightTrade.getCostForPlayer(player, false) > player.getCoins())
						continue;

					if (leftTrade.add(rightTrade).equals(missingResources)) // TODO maybe exchange with "greaterOrEqual"
						result.add(new TradeOption(leftTrade, rightTrade, leftTrade.getCostForPlayer(player, true), leftTrade.getCostForPlayer(player, false)));
				}
			}
		}

		return result;
	}

	/**
	 * find all options to trade with the two neighbours
	 * 
	 * @param player    player
	 * @param resources required resources
	 * @return list of trade options
	 */
	public ArrayList<TradeOption> getTradeOptions(Player player, ArrayList<Resource> resources) {
		return getTradeOptions(player, new ResourceBundle(resources));
	}

	/**
	 * removes all bundles that are greater or equal to any other bundle
	 * 
	 * @param list list of bundles
	 */
	private void removeDuplicates(ArrayList<ResourceBundle> list) {
		ResourceBundle[] copy = list.toArray(new ResourceBundle[] {});
		for (ResourceBundle bundle : copy) {
			int i = 0;
			while (i < list.size()) {
				if (list.get(i).greaterOrEqualThan(bundle))
					list.remove(i);
				else
					i++;
			}
		}
	}

	/**
	 * creates a list of all possible sums of the specified resource bundles
	 * 
	 * @param bundles list of resource bundles
	 * @return list of all sums
	 */
	private ArrayList<ResourceBundle> allSums(ArrayList<ResourceBundle> bundles) {
		ArrayList<ResourceBundle> bundleSums = new ArrayList<>();
		int max = 1 << bundles.size();

		for (int i = 0; i < max; i++) {
			ResourceBundle res = new ResourceBundle();
			for (int j = 0; j < bundles.size(); j++) {
				if (((i >> j) & 1) == 1)
					res = res.add(bundles.get(j));
			}
			bundleSums.add(res);
		}

		return bundleSums;
	}
}