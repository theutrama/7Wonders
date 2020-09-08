package controller;

import java.util.ArrayList;

import application.Main;
import controller.sound.Sound;
import model.Game;
import model.GameState;
import model.card.Card;
import model.card.Effect;
import model.card.EffectType;
import model.player.Player;
import model.ranking.PlayerStats;
import view.gameboard.GameBoardViewController;
import view.result.ResultViewController;

/**
 * game controller for game controlling
 */
public class GameController {
	/** constants for PMD */
	private static final int NUM_ROUNDS = 6, NUM_AGES = 3, FIRST_AGE = 1, SECOND_AGE = 2;
	/** main controller */
	private SevenWondersController swController;
	/** view controller */
	private GameBoardViewController gbvController;

	/**
	 * create new game controller
	 * 
	 * @param swController main controller
	 */
	public GameController(SevenWondersController swController) {
		this.swController = swController;
	}

	/**
	 * creates a new game with the specified list of players
	 * 
	 * @param name    the game's name
	 * @param players the list of players
	 * @return a new game instance
	 */
	public Game createGame(String name, ArrayList<Player> players) {
		Game game = new Game(name);
		this.swController.setGame(game);
		createGameFirstRound(players, game);
		return game;
	}

	/**
	 * creates a new {@link GameState} instance that represents the initial state of a new game
	 * 
	 * @param players player list
	 */
	public void createGameFirstRound(ArrayList<Player> players, Game game) {
		ArrayList<Card> cardStack = swController.getCardController().generateCardStack(players);
		GameState state = new GameState(0, 1, players, cardStack);
		nextAge(game, state);
		game.getCurrentGameState().setFirstPlayer(0);
		game.getCurrentGameState().setCurrentPlayer(0);
	}

	/**
	 * Creates a new game state and sets it to the current round of the specified game.<br>
	 * If a player has finished the second stage of the mausoleum {@link Player#isMausoleum() (player attribute)} the method {@link GameBoardViewController#selectCardFromTrash(Player)}
	 * is called<br>
	 * and nothing happens.
	 * 
	 * @param game     game instance
	 * @param previous the old game state
	 */
	public void createNextRound(Game game, GameState previous) {

		for (Player player : previous.getPlayers()) {
			if (player.isMausoleum()) {

				player.setMausoleum(false);
				gbvController.selectCardFromTrash(player);

				return;
			}
		}

		if (previous.getRound() == NUM_ROUNDS) {
			if (previous.getAge() == NUM_AGES) {
				doConflicts(previous);
				endGame(game, previous);
			} else {
				doConflicts(previous);
				nextAge(game, previous);
			}
		} else {
			nextRound(game, previous);
		}
	}

	/**
	 * Goes to the last state of the specified game.<br>
	 * Redo is enabled if the current state is at the beginning of a round.
	 * 
	 * @param game the {@link Game}
	 */
	public void undo(Game game) {
		if (game.getCurrentState() == 0)
			return;

		game.setCurrentState(game.getCurrentState() - 1);

		if (!game.getStates().get(game.getCurrentState() + 1).isAtBeginOfRound())
			game.deleteRedoStates();

		game.disableHighscore();
	}

	/**
	 * goes to undone state of the specified game
	 * 
	 * @param game the {@link Game}
	 */
	public void redo(Game game) {
		if (game.getCurrentState() < game.getStates().size() - 1) {
			game.setCurrentState(game.getCurrentState() + 1);
		}
	}

	/**
	 * setter fot {@link #gbvController}
	 * 
	 * @param gbvController the view controller
	 */
	public void setGbvController(GameBoardViewController gbvController) {
		this.gbvController = gbvController;
	}

	/**
	 * clones the specified game state and creates a new state of the next age
	 * 
	 * @param game     the game
	 * @param previous last game state of the last age
	 */
	private void nextAge(Game game, GameState previous) {
		GameState state = previous.deepClone();
		state.setBeginOfRound(true);
		state.setAge(previous.getAge() + 1);
		state.setRound(1);
		ArrayList<Card> ageCards = new ArrayList<>();
		for (Card card : state.getCardStack())
			if (card.getAge() == state.getAge())
				ageCards.add(card);

		for (Player player : state.getPlayers()) {

			if (state.getAge() > FIRST_AGE) {
				state.getTrash().add(player.getHand().get(0));
				player.getHand().clear();
			}

			if (player.getBoard().getBoardName().equals("Olympia") && player.getBoard().isFilled(1)) { // TODO use static value
				player.setOlympiaUsed(false);
			}

			for (int i = 0; i < 7; i++) {
				int index = randInt(0, ageCards.size());

				player.getHand().add(ageCards.get(index)); // assign card to player hand
				state.getCardStack().remove(ageCards.get(index)); // delete card from card stack
				ageCards.remove(index); // remove from help list
			}
		}
		int startPlayer = (previous.getFirstPlayer() + 1) % previous.getPlayers().size();
		state.setFirstPlayer(startPlayer);
		state.setCurrentPlayer(startPlayer);

		game.deleteRedoStates();
		game.getStates().add(state);
		game.setCurrentState(game.getStates().size() - 1);
	}

	/**
	 * sets the current game state to a new game state instance
	 * 
	 * @param game     the current game
	 * @param previous the old game state
	 */
	private void nextRound(Game game, GameState previous) {
		GameState state = previous.deepClone();
		state.setBeginOfRound(true);
		state.setRound(state.getRound() + 1);
		if (state.getAge() == SECOND_AGE) {
			ArrayList<Card> firstPlayerHand = state.getPlayers().get(0).getHand();
			for (int i = 0; i < state.getPlayers().size() - 1; i++)
				state.getPlayers().get(i).setHand(state.getPlayers().get(i + 1).getHand());
			state.getPlayers().get(state.getPlayers().size() - 1).setHand(firstPlayerHand);
		} else {
			ArrayList<Card> lastPlayerHand = state.getPlayers().get(state.getPlayers().size() - 1).getHand();
			for (int i = state.getPlayers().size() - 1; i > 0; i--)
				state.getPlayers().get(i).setHand(state.getPlayers().get(i - 1).getHand());
			state.getPlayers().get(0).setHand(lastPlayerHand);
		}
		state.setCurrentPlayer(0);

		game.deleteRedoStates();
		game.getStates().add(state);
		game.setCurrentState(game.getStates().size() - 1);
		
		int startPlayer = (previous.getFirstPlayer() + 1) % previous.getPlayers().size();
		state.setFirstPlayer(startPlayer);
		state.setCurrentPlayer(startPlayer);
	}

	/**
	 * evaluates the victory points of all players and displays a new result view that shows the result
	 * 
	 * @param game  current game
	 * @param state current game state
	 */
	private void endGame(Game game, GameState state) {

		for (Player player : state.getPlayers()) {
			runEffects(player, player.getBoard().getTrade());
			runEffects(player, player.getBoard().getGuilds());
			runEffects(player, player.getBoard().getCivil());
			// conflicts
			player.addVictoryPoints(player.getConflictPoints());
			player.addVictoryPoints(-player.getLosePoints());
			// coins
			player.addVictoryPoints(player.getCoins() / 3);
			// science
			player.addVictoryPoints(swController.getPlayerController().getSciencePoints(player));
		}

		// update highscore list
		if (game.highscoreAllowed()) {
			for (Player player : state.getPlayers()) {
				Main.getSWController().getRanking().addStats(new PlayerStats(player.getName(), player.getVictoryPoints(), player.getLosePoints(), player.getConflictPoints(), player.getCoins()));
			}
		}
		((GameBoardViewController) Main.primaryStage.getScene().getRoot()).exit();
		Main.primaryStage.getScene().setRoot(new ResultViewController(state.getPlayers()));
	}
	
	/**
	 * calls {@link Effect#run(Player)} for all effects for each given card
	 * 
	 * @param player player
	 * @param cards  list of cards from the player's game board
	 */
	private void runEffects(Player player, ArrayList<Card> cards) {
		for (Card card: cards) {
			if (card.getEffects() == null)
				continue;
			for (Effect effect: card.getEffects())
				if (effect.getType() == EffectType.AT_MATCH_END)
					effect.run(player, swController.getPlayerController());
		}
	}

	/**
	 * evaluates the conflict results of the current game state
	 * 
	 * @param state the current game state
	 */
	private void doConflicts(GameState state) {
		swController.getSoundController().play(Sound.FIGHT);
		for (Player player : state.getPlayers()) {
			doConflict(player, swController.getPlayerController().getRightNeighbour(player), state.getAge());
		}
	}

	/**
	 * adds conflict or lose points depending on the militaty points of the two players
	 * 
	 * @param p1  first player
	 * @param p2  second player
	 * @param age the current age, affects the conflict points gained per victory
	 */
	private void doConflict(Player player1, Player player2, int age) {
		int p1Points = swController.getPlayerController().getMilitaryPoints(player1), p2Points = swController.getPlayerController().getMilitaryPoints(player2);

		if (p1Points > p2Points) {
			player1.addConflictPoints(getMilitaryForAge(age));
			player2.addLosePoint();
		} else if (p1Points < p2Points) {
			player2.addConflictPoints(getMilitaryForAge(age));
			player1.addLosePoint();
		}
	}

	/**
	 * @param age the age
	 * @return amount of victory points gained at the specified age
	 */
	private static int getMilitaryForAge(int age) {
		return 2 * age - 1;
	}

	/**
	 * @param low  lower border
	 * @param high upper border
	 * @return random integer between low and high, but maximum value is (high - 1)
	 */
	private static int randInt(int low, int high) {
		return (int) (Math.random() * (high - low)) + low;
	}

}
