package controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import application.Main;
import application.Utils;
import controller.exceptions.CardOutOfAgeException;
import controller.sound.Sound;
import model.Game;
import model.GameState;
import model.card.Card;
import model.card.Effect;
import model.card.EffectType;
import model.player.Player;
import model.player.ai.ArtInt;
import model.ranking.PlayerStats;
import view.gameboard.GameBoardViewController;
import view.newgame.NewCSVGameViewController;
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
		game.getStates().add(state);
		nextAge(game, state);
		game.getCurrentGameState().setFirstPlayer(0);
		game.getCurrentGameState().setCurrentPlayer(0);
	}

	/**
	 * changes the values of the given game state to signal a new round or finishes the game
	 * 
	 * @param game       game instance
	 * @param state      the game state
	 * @param turnThread the turn thread from {@link GameBoardViewController}, must be cancelled if endGame is executed
	 * @return true if endGame was called
	 */
	public boolean createNextRound(Game game, GameState state) {

		if (state.getRound() == NUM_ROUNDS) {
			if (state.getAge() == NUM_AGES) {
				doConflicts(state);
				endGame(game, state);
				return true;
			} else {
				doConflicts(state);
				nextAge(game, state);
			}
		} else {
			nextRound(game, state);
		}

		return false;

	}

	/**
	 * Goes to the last state of the specified game.<br>
	 * Redo is enabled if the current state is at the beginning of a round.
	 * 
	 * @param game the {@link Game}
	 * @return true if undo was successfull
	 */
	public boolean undo(Game game) {
		if (game.getCurrentState() == 0)
			return false;

		game.setCurrentState(game.getCurrentState() - 1);

		if (!game.getStates().get(game.getCurrentState() + 1).isAtBeginOfRound())
			game.deleteRedoStates();

		game.disableHighscore();
		return true;
	}

	/**
	 * goes to undone state of the specified game
	 * 
	 * @param game the {@link Game}
	 * @return true if redo was successfull
	 */
	public boolean redo(Game game) {
		if (game.getCurrentState() < game.getStates().size() - 1) {
			game.setCurrentState(game.getCurrentState() + 1);
			return true;
		}
		return false;
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
	 * @param game  the game
	 * @param state last game state of the last age
	 */
	public void nextAge(Game game, GameState state) {
		state.setBeginOfRound(true);
		state.setAge(state.getAge() + 1);
		state.setRound(1);
		Stack<Card> ageCards = new Stack<Card>();
		for (Card card : state.getCardStack())
			if (card.getAge() == state.getAge()) {
				ageCards.push(card);
			}

		for (Player player : state.getPlayers()) {

			if (state.getAge() > FIRST_AGE) {
				state.getTrash().add(player.getHand().get(0));
				player.getHand().clear();
			}

			if (player.getBoard().getBoardName().equals("Olympia") && player.getBoard().isFilled(1)) {
				player.setOlympiaUsed(false);
			}

			System.out.println("ageCards: " + ageCards.size() + " " + player.getName());
			for (int i = 0; i < 7; i++) {
				Card card = ageCards.pop();

				player.getHand().add(card); // assign card to player hand
				state.getCardStack().remove(card); // delete card from card stack
			}
		}

		int size = ageCards.size();
		// Add the rest of the cards from this age to the Trash
		for (int i = 0; i < size; i++) {
			Card card = ageCards.pop();
			state.getTrash().add(card);
			state.getCardStack().remove(card);
		}

		int startPlayer = (state.getFirstPlayer() + 1) % state.getPlayers().size();
		state.setFirstPlayer(startPlayer);
		state.setCurrentPlayer(startPlayer);

		game.deleteRedoStates();
	}

	/**
	 * To Load a Game for Two Player!
	 * 
	 * @param filepath Filepath of the csv file
	 * @return boolean weather all is fine...
	 * @throws CardOutOfAgeException If the Card age from the table doesn't suit with age of the loaded card
	 */
	public boolean loadCSV(File file) throws CardOutOfAgeException {
		// Wrong File Type
		if (!file.getName().endsWith(".csv"))
			return false;

		SevenWondersController controller = SevenWondersController.getInstance();
		CardController card_controller = controller.getCardController();
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(file));

			String line = null;
			line = in.readLine(); // age,card
			NewCSVGameViewController view = new NewCSVGameViewController();
			String wonder1 = in.readLine().split(",")[1];
			view.addPlayer("Spieler", Utils.toWonder(wonder1), true);

			ArrayList<Card> cards = new ArrayList<Card>();
			String[] split;
			int ai = 1;
			while ((line = in.readLine()) != null) {
				if (line.contains(",")) {
					split = line.split(",");
					int age = Integer.valueOf(split[0]);

					if (age == 0) {
						String wonder = Utils.toCard(split[1], age);
						view.addPlayer("AI-Spieler" + ai, Utils.toWonder(wonder), true);
						ai++;
					} else {
						String cardname = Utils.toCard(split[1], age);
						Card card = card_controller.getCard(cardname);

						if (age != card.getAge()) {
							throw new CardOutOfAgeException(card, age);
						}

						cards.add(card);
					}
				} else {
					System.out.println("This thing shouldn't never happen?! LINE:" + line);
					return false;
				}
			}

			// Reverse Array to get the right order by taking from the TOP
			Collections.reverse(cards);
			view.setCardStack(cards);

			Main.primaryStage.getScene().setRoot(view);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	/**
	 * sets the current game state to a new game state instance
	 * 
	 * @param game  the current game
	 * @param state the old game state
	 */
	public void nextRound(Game game, GameState state) {
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
		game.deleteRedoStates();

		int startPlayer = (state.getFirstPlayer() + 1) % state.getPlayers().size();
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
		if (Main.primaryStage.getScene().getRoot() instanceof ResultViewController) {
			System.out.println("GameController.endGame ResultViewController is already OPEN!!!");
			return;
		}

		for (Player player : state.getPlayers()) {
			runEffects(player, player.getBoard().getTrade(), state.isTwoPlayers());
			runEffects(player, player.getBoard().getGuilds(), state.isTwoPlayers());
			runEffects(player, player.getBoard().getCivil(), state.isTwoPlayers());
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
				if (!(player instanceof ArtInt))
					Main.getSWController().getRanking().addStats(new PlayerStats(player.getName(), player.getVictoryPoints(), player.getLosePoints(), player.getConflictPoints(), player.getCoins()));
			}
		}
		((GameBoardViewController) Main.primaryStage.getScene().getRoot()).exit();
		Main.getSWController().getSoundController().stopAll();
		Main.primaryStage.getScene().setRoot(new ResultViewController(state.getPlayers()));
		Main.getSWController().getIOController().deleteFile(game.getName());
	}

	/**
	 * calls {@link Effect#run(Player)} for all effects for each given card
	 * 
	 * @param player player
	 * @param cards  list of cards from the player's game board
	 */
	private void runEffects(Player player, ArrayList<Card> cards, boolean twoPlayers) {
		for (Card card : cards) {
			if (card.getEffects() == null)
				continue;
			for (Effect effect : card.getEffects())
				if (effect.getType() == EffectType.AT_MATCH_END)
					effect.run(player, swController.getGame().getCurrentGameState(), twoPlayers);
		}
	}

	/**
	 * evaluates the conflict results of the current game state
	 * 
	 * @param state the current game state
	 */
	private void doConflicts(GameState state) {
		swController.getSoundController().play(Sound.FIGHT);
		if (state.isTwoPlayers())
			doConflict(state.getPlayers().get(0), state.getPlayers().get(1), state.getAge());
		else {
			for (Player player : state.getPlayers()) {
				doConflict(player, swController.getPlayerController().getRightNeighbour(player), state.getAge());
			}
		}

		gbvController.showConflicts();
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

}
