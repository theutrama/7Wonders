package controller;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import application.Utils;
import controller.utils.BuildCapability;
import controller.utils.ResourceBundle;
import controller.utils.ResourceTree;
import controller.utils.TradeOption;
import model.GameState;
import model.board.AlexandriaBoard;
import model.board.BabylonBoard;
import model.board.RhodosBoard;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.Difficulty;
import model.player.ai.EasyAI;
import model.player.ai.HardAI;
import model.player.ai.MediumAI;

/** Controller for Players */
public class PlayerController {
	/** main controller */
	private SevenWondersController swController;
	/** wonder board controller */
	private WonderBoardController wbc;

	/**
	 * create player controller
	 * 
	 * @param swController
	 */
	public PlayerController(SevenWondersController swController, WonderBoardController wbc) {
		this.swController = swController;
		this.wbc = wbc;
	}

	/**
	 * add a player and assign wonder board
	 * 
	 * @param playername  player's name
	 * @param wonderboard player's board
	 * @return Player object
	 */
	public Player createPlayer(String playername, String wonderboard) {
		WonderBoard board = this.wbc.createWonderBoard(wonderboard);
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
		ArrayList<Player> list = swController.getGame().getCurrentGameState().getPlayers();

		for (Player player : list) {
			if (player.getName().equalsIgnoreCase(playername)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * get player by name for a given gamestate
	 * 
	 * @param playername player name
	 * @param state      game state
	 * @return player instance or null
	 */
	public Player getPlayer(String playername, GameState state) {
		for (Player player : state.getPlayers())
			if (player.getName().equals(playername))
				return player;
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
	public ArtInt createAI(String playername, String wonderboard, Difficulty difficulty) {
		WonderBoard board = wbc.createWonderBoard(wonderboard);
		System.out.println(difficulty);
		ArtInt artInt = difficulty == Difficulty.EASY ? new EasyAI(playername, board) : (difficulty == Difficulty.MEDIUM ? new MediumAI(playername, board) : new HardAI(playername, board));
		board.setPlayer(artInt);
		return artInt;
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
	 * find neighbour
	 * 
	 * @param state  game state
	 * @param left   = True -> Left Neighbour or False -> Right Neighbour
	 * @param player player
	 * @return neighbour
	 */
	public Player getNeighbour(GameState state, boolean left, Player player) {
		ArrayList<Player> players = state.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(player.getName()))
				return players.get(left ? (i == players.size() - 1 ? 0 : i + 1) : (i == 0 ? players.size() - 1 : i - 1));
		}
		return null;
	}

	/**
	 * find left neighbour
	 * 
	 * @param player player
	 * @return left neighbour
	 */
	public Player getLeftNeighbour(GameState state, Player player) {
		return getNeighbour(state, true, player);
	}

	/**
	 * find right neighbour
	 * 
	 * @param player player
	 * @return right neighbour
	 */
	public Player getRightNeighbour(GameState state, Player player) {
		return getNeighbour(state, false, player);
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
	public int getSciencePoints(int[] amount) {
		int victoryPoints = 0;
		for (int i = 0; i < amount.length; i++) {
			victoryPoints += amount[i] * amount[i];
		}

		while (amount[0] >= 1 && amount[1] >= 1 && amount[2] >= 1) {
			victoryPoints += 7;
			amount[0]--;
			amount[1]--;
			amount[2]--;
		}

		return victoryPoints;
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
	public ResourceBundle getStaticResources(Player player) {
		ResourceBundle result = new ResourceBundle();

		result.add(player.getBoard().getResource());
		result.add(new Resource(player.getCoins(), ResourceType.COINS));

		for (Card card : player.getBoard().getResources()) {
			if (card.getProducing() != null && card.getProducing().size() == 1)
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
	public ResourceTree generateResourceTree(Player player, ResourceBundle staticResources) {
		ResourceTree tree = new ResourceTree(staticResources);
		for (Card card : player.getBoard().getResources()) {
			if (card.getProducing() != null && card.getProducing().size() > 1)
				tree.addResourceOption(card.getProducing());
		}
		for (Card card : player.getBoard().getTrade()) {
			if (card.getProducing() != null && card.getProducing().size() > 1)
				tree.addResourceOption(card.getProducing());
		}
		if (player.getBoard() instanceof AlexandriaBoard && player.getBoard().isFilled(1))
			tree.addResourceOption(
					new ArrayList<>(Arrays.asList(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.STONE))));

		return tree;
	}

	/**
	 * generates the tree of all possible resource combinations, including non-selective cards
	 * 
	 * @param player player
	 * @return a resource tree
	 */
	private ResourceTree generateTradeTree(Player player) {
		final int ONE = 1;

		ResourceTree tree = new ResourceTree(new ResourceBundle(player.getBoard().getResource()));
		for (Card card : player.getBoard().getResources()) {
			if (card.getProducing().size() == ONE)
				for (int i = 0; i < card.getProducing().get(0).getQuantity(); i++)
					tree.addResourceOption(new Resource(1, card.getProducing().get(0).getType()));
			else
				tree.addResourceOption(card.getProducing());
		}
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
		return canBuild(player, card, Main.getSWController().getGame().getCurrentGameState());
	}

	/**
	 * used to find out the way a player can build a card
	 * 
	 * @param player player
	 * @param card   card
	 * @param state  current game state
	 * @return the type of ability to build
	 */
	public BuildCapability canBuild(Player player, Card card, GameState state) {
		if (swController.getCardController().hasCard(player, card.getInternalName()))
			return BuildCapability.NONE;
		if (card.getDependencies() != null && card.getDependencies().length > 0) {
			for (String cardname : card.getDependencies()) {
				if (swController.getCardController().hasCard(player, cardname)) {
					return BuildCapability.FREE;
				}
			}
		}

		return (card.getRequired() == null || card.getRequired().isEmpty()) ? BuildCapability.OWN_RESOURCE : hasResources(player, card.getRequired());
	}

	/**
	 * used to find the way a player can collect the specified list of resources
	 * 
	 * @param player    player
	 * @param resources list of resources
	 * @return the type of ability to collect the resources
	 */
	public BuildCapability hasResources(Player player, ArrayList<Resource> resources) {
		return hasResources(player, resources, Main.getSWController().getGame().getCurrentGameState());
	}

	/**
	 * used to find the way a player can collect the specified list of resources
	 * 
	 * @param player    player
	 * @param resources list of resources
	 * @param state     current game state
	 * @return the type of ability to collect the resources
	 */
	public BuildCapability hasResources(Player player, ArrayList<Resource> resources, GameState state) {
		if (resources.isEmpty())
			return BuildCapability.OWN_RESOURCE;

		ResourceBundle cardRequirement = new ResourceBundle(resources);

		if (cardRequirement.getCoins() > player.getCoins())
			return BuildCapability.NONE;

		ResourceBundle staticResources = getStaticResources(player);

		if (staticResources.greaterOrEqualThan(cardRequirement))
			return BuildCapability.OWN_RESOURCE;

		ArrayList<ResourceBundle> combinations = generateResourceTree(player, staticResources).getAllCombinations();

		removeGOEDuplicates(combinations);

		for (ResourceBundle bundle : combinations) {
			if (bundle.greaterOrEqualThan(cardRequirement))
				return BuildCapability.OWN_RESOURCE;
		}

		return getTradeOptions(player, cardRequirement, state).isEmpty() ? BuildCapability.NONE : BuildCapability.TRADE;
	}

	/**
	 * find all options to trade with the two neighbours to collect the specified resources
	 * 
	 * @param player    player
	 * @param resources required resources
	 * @return list of trade options
	 */
	private ArrayList<TradeOption> getTradeOptions(Player player, ResourceBundle resources, GameState state) {
		ArrayList<ResourceBundle> combinations = generateResourceTree(player, getStaticResources(player)).getAllCombinations();

		removeGOEDuplicates(combinations);

		ArrayList<ResourceBundle> missing = new ArrayList<>();
		for (ResourceBundle bundle : combinations) // get list of missing resource quantities per combination
			missing.add(bundle.getMissing(resources));

		ArrayList<TradeOption> result = new ArrayList<>();

		if (state.isTwoPlayers()) {

			Player neighbour = getNeighbour(state, false, player);
			ArrayList<ResourceBundle> trades = new ArrayList<>();

			generateTradeTree(neighbour).getAllCombinationsAsList().forEach(list -> trades.addAll(allSums(list)));

			for (ResourceBundle missingResources : missing) {
				for (ResourceBundle trade : trades) {
					if (trade.getCostForPlayer(player, true, true) <= player.getCoins() && trade.equals(missingResources))
						result.add(new TradeOption(trade, null, trade.getCostForPlayer(player, true, true), 0));
				}
			}

		} else {

			Player left = getNeighbour(state, true, player);
			Player right = getNeighbour(state, false, player);

			ArrayList<ArrayList<ResourceBundle>> leftTradeLists = generateTradeTree(left).getAllCombinationsAsList();
			ArrayList<ArrayList<ResourceBundle>> rightTradeLists = generateTradeTree(right).getAllCombinationsAsList();

			ArrayList<ResourceBundle> leftTrades = new ArrayList<>(), rightTrades = new ArrayList<>();
			leftTradeLists.forEach(list -> leftTrades.addAll(allSums(list)));
			rightTradeLists.forEach(list -> rightTrades.addAll(allSums(list)));

			removeEqualResources(leftTrades);
			removeEqualResources(rightTrades);

			for (ResourceBundle missingResources : missing) {

				for (ResourceBundle leftTrade : leftTrades)
					if (leftTrade.getCostForPlayer(player, true, false) <= player.getCoins() && leftTrade.equals(missingResources))
						result.add(new TradeOption(leftTrade, null, leftTrade.getCostForPlayer(player, true, false), 0));

				for (ResourceBundle rightTrade : rightTrades)
					if (rightTrade.getCostForPlayer(player, false, false) <= player.getCoins() && rightTrade.equals(missingResources))
						result.add(new TradeOption(null, rightTrade, 0, rightTrade.getCostForPlayer(player, false, false)));

				for (ResourceBundle leftTrade : leftTrades) {
					for (ResourceBundle rightTrade : rightTrades) {
						if (leftTrade.getCostForPlayer(player, true, false) + rightTrade.getCostForPlayer(player, false, false) > player.getCoins())
							continue;

						if (leftTrade.add(rightTrade).equals(missingResources)) // TODO maybe exchange with "greaterOrEqual"
							result.add(new TradeOption(leftTrade, rightTrade, leftTrade.getCostForPlayer(player, true, false), rightTrade.getCostForPlayer(player, false, false)));
					}
				}
			}
		}

		removeEqualTrades(result);

		result.sort((opt1, opt2) -> opt1.getLeftCost() + opt1.getRightCost() - opt2.getLeftCost() - opt2.getRightCost());

		return result;
	}

	/**
	 * find all options to trade with the two neighbours to collect the specified resources
	 * 
	 * @param player    player
	 * @param resources required resources
	 * @param state     current game state
	 * @return list of trade options
	 */
	public ArrayList<TradeOption> getTradeOptions(Player player, ArrayList<Resource> resources, GameState state) {
		return getTradeOptions(player, new ResourceBundle(resources), state);
	}

	/**
	 * find all options to trade with the two neighbours to collect the specified resources
	 * 
	 * @param player    player
	 * @param resources required resources
	 * @return list of trade options
	 */
	public ArrayList<TradeOption> getTradeOptions(Player player, ArrayList<Resource> resources) {
		return getTradeOptions(player, new ResourceBundle(resources), Main.getSWController().getGame().getCurrentGameState());
	}

	/**
	 * executes a trade by adding/removing coins
	 * 
	 * @param state  game state
	 * @param player player that trades
	 * @param trade  the trade
	 */
	public void doTrade(GameState state, Player player, TradeOption trade) {
		if (trade.getLeftCost() > 0) {
			getNeighbour(state, true, player).addCoins(trade.getLeftCost());
			player.addCoins(-trade.getLeftCost());
		}
		if (trade.getRightCost() > 0) {
			getNeighbour(state, false, player).addCoins(trade.getRightCost());
			player.addCoins(-trade.getRightCost());
		}
	}

	/**
	 * executes a trade by adding/removing coins
	 * 
	 * @param player player that trades
	 * @param trade  the trade
	 * @param state  game state
	 */
	public void doTrade(Player player, TradeOption trade, GameState state) {
		if (trade.getLeftCost() > 0) {
			getNeighbour(state, true, player).addCoins(trade.getLeftCost());
			player.addCoins(-trade.getLeftCost());
		}
		if (trade.getRightCost() > 0) {
			getNeighbour(state, false, player).addCoins(trade.getLeftCost());
			player.addCoins(-trade.getRightCost());
		}
	}

	/**
	 * removes all bundles that are greater or equal to any other bundle
	 * 
	 * @param list list of bundles
	 */
	private void removeGOEDuplicates(ArrayList<ResourceBundle> list) {
		ArrayList<ResourceBundle> toRemove = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++)
				if (i != j && list.get(i).greaterOrEqualThan(list.get(j))) {
					toRemove.add(list.get(i));
					break;
				}
		}
		list.removeAll(toRemove);
	}

	/**
	 * removes all bundles that are equal to any other bundle
	 * 
	 * @param list list of resource bundles
	 */
	private void removeEqualResources(ArrayList<ResourceBundle> list) {
		ResourceBundle[] copy = list.toArray(new ResourceBundle[] {});
		list.clear();
		for (ResourceBundle bundle : copy) {
			boolean add = true;
			for (ResourceBundle listBundle : list) {
				if (bundle.equals(listBundle)) {
					add = false;
					break;
				}
			}
			if (add)
				list.add(bundle);
		}
	}

	/**
	 * removes all trade options that are equal to any other option
	 * 
	 * @param trades list of trade options
	 */
	private void removeEqualTrades(ArrayList<TradeOption> trades) {
		TradeOption[] copy = trades.toArray(new TradeOption[] {});
		trades.clear();
		for (TradeOption option : copy) {
			boolean add = true;
			for (TradeOption listOption : trades) {
				if (option.equals(listOption)) {
					add = false;
					break;
				}
			}
			if (add)
				trades.add(option);
		}
	}

	/**
	 * creates a list of all possible sums of the specified resource bundles
	 * 
	 * @param bundles list of resource bundles
	 * @return list of all sums
	 */
	private ArrayList<ResourceBundle> allSums(ArrayList<ResourceBundle> bundles) {
		final int ONE = 1;
		ArrayList<ResourceBundle> bundleSums = new ArrayList<>();
		int max = 1 << bundles.size();

		for (int i = 1; i < max; i++) {
			ResourceBundle res = new ResourceBundle();
			for (int j = 0; j < bundles.size(); j++) {
				if (((i >> j) & 1) == ONE)
					res = res.add(bundles.get(j));
			}
			bundleSums.add(res);
		}

		return bundleSums;
	}
}