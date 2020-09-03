package controller;

import java.util.ArrayList;
import java.util.Arrays;

import model.Game;
import model.GameState;
import model.card.Card;
import model.player.Player;
import view.gameboard.GameBoardViewController;

public class GameController {

	private SevenWondersController swController;

	private GameBoardViewController gbvController;

	public GameController(SevenWondersController swController) {
		this.swController = swController;
	}

	public Game createGame(String name, ArrayList<Player> players) {
		Game game = new Game(createGameFirstRound(players), name);
		game.setCurrentPlayer(players.get(0));
		return game;
	}

	public GameState createGameFirstRound(ArrayList<Player> players) {
		return new GameState(1, 1, players, new ArrayList<Card>(Arrays.asList(swController.getCardController().generateCardStack())));
	}

	/**
	 * Creates a new game state and sets it to the current round of the specified game.<br>
	 * If a player has finished the second stage of the mausoleum {@link Player#isMausoleum() (player attribute)} the method
	 * {@link GameBoardViewController#selectCardFromTrash(Player)} is called<br>
	 * and nothing happens.
	 * 
	 * @param game game instance
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
				endGame(game, previous);
			} else {
				doConflicts(previous);
				nextAge(game, previous);
			}
		} else {
			nextRound(game, previous);
		}
	}

	public void undo(Game game) {

	}

	public void redo(Game game) {

	}

	public void setGbvController(GameBoardViewController gbvController) {
		this.gbvController = gbvController;
	}

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
	}

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
	}

	private void endGame(Game game, GameState state) {
		// TODO compute result and show GameResultView
	}

	/**
	 * 
	 * @param state
	 */
	private void doConflicts(GameState state) {
		
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
