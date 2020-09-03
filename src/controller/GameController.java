package controller;

import java.util.ArrayList;
import java.util.Arrays;

import model.Game;
import model.GameState;
import model.board.RhodosBoard;
import model.card.Card;
import model.player.Player;
import view.gameboard.GameBoardViewController;

public class GameController {

	private SevenWondersController swController;

	private GameBoardViewController gbvController;

	public GameController(SevenWondersController swController) {
		this.swController = swController;
	}

	/**
	 * creates a new game with the specified list of players
	 * @param name the game's name
	 * @param players the list of players
	 * @return a new game instance
	 */
	public Game createGame(String name, ArrayList<Player> players) {
		Game game = new Game(createGameFirstRound(players), name);
		game.setCurrentPlayer(players.get(0));
		return game;
	}

	/**
	 * creates a new {@link GameState} instance that represents the initial state of a new game
	 * @param players player list
	 * @return the {@link GameState} object
	 */
	public GameState createGameFirstRound(ArrayList<Player> players) {
		return new GameState(1, 1, players, new ArrayList<Card>(Arrays.asList(swController.getCardController().generateCardStack())));
	}

	/**
	 * Creates a new game state and sets it to the current round of the specified game.<br>
	 * If a player has finished the second stage of the mausoleum {@link Player#isMausoleum() (player attribute)} the method
	 * {@link GameBoardViewController#selectCardFromTrash(Player)} is called<br>
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

		if (previous.getRound() == 6) {
			if (previous.getAge() == 3) {
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
	 * @param game the {@link Game}
	 */
	public void undo(Game game) {

	}

	/**
	 * goes to undone state of the specified game
	 * @param game the {@link Game}
	 */
	public void redo(Game game) {

	}

	/**
	 * setter fot {@link #gbvController}
	 * @param gbvController the view controller
	 */
	public void setGbvController(GameBoardViewController gbvController) {
		this.gbvController = gbvController;
	}

	/**
	 * clones the specified game state and creates a new state of the next age
	 * @param game the game
	 * @param previous last game state of the last age
	 */
	private void nextAge(Game game, GameState previous) {
		GameState state = previous.deepClone();
		state.setAge(previous.getAge() + 1);
		state.setRound(1);
		ArrayList<Card> ageCards = new ArrayList<>();
		for (Card card : state.getCardStack())
			if (card.getAge() == state.getAge())
				ageCards.add(card);

		for (Player player : state.getPlayers()) {

			if (state.getAge() > 1) {
				state.getTrash().add(player.getHand().get(0));
				player.getHand().clear();
			}

			for (int i = 0; i < 7; i++) {
				int index = randInt(0, ageCards.size());
				player.getHand().add(ageCards.get(index));
				ageCards.remove(index);
			}
		}

		state.setCurrentPlayer(0);
		
		game.deleteRedoStates();
		game.getStates().add(state);
		game.setCurrentState(game.getStates().size() - 1);
	}

	/**
	 * 
	 * @param game
	 * @param previous
	 */
	private void nextRound(Game game, GameState previous) {
		GameState state = previous.deepClone();
		state.setRound(state.getRound() + 1);
		if (state.getAge() == 2) {
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
	}

	private void endGame(Game game, GameState state) {
		for (Player player: state.getPlayers()) {
			
		}
	}

	/**
	 * evaluates the conflict results of the current game state
	 * @param state the current game state
	 */
	private void doConflicts(GameState state) {
		for (int i = 0; i < state.getPlayers().size(); i++) {
			doConflict(state.getPlayers().get(i), state.getPlayers().get(i == state.getPlayers().size() - 1 ? 0 : i + 1), state.getAge());
		}
	}

	private void doConflict(Player p1, Player p2, int age) {
		int p1Points = 0, p2Points = 0;
		for (Card card : p1.getBoard().getMilitary()) {
			p1Points += card.getProducing().get(0).getQuantity();
		}
		if (p1.getBoard() instanceof RhodosBoard && p1.getBoard().isFilled(1))
			p1Points += 2;
		for (Card card : p2.getBoard().getMilitary()) {
			p2Points += card.getProducing().get(0).getQuantity();
		}
		if (p2.getBoard() instanceof RhodosBoard && p2.getBoard().isFilled(1))
			p2Points += 2;

		if (p1Points > p2Points) {
			p1.addConflictPoints(getMilitaryForAge(age));
			p2.addLosePoint();
		} else if (p1Points < p2Points) {
			p2.addConflictPoints(getMilitaryForAge(age));
			p1.addLosePoint();
		}
	}

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
