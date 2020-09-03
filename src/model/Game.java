package model;

import java.util.ArrayList;

import model.player.Player;

public class Game {
	/** current state index */
	private int currentState;
	/** current player */
	private Player currentPlayer;
	/** name */
	private String name;
	/** list of game states */
	private ArrayList<GameState> states;

	/**
	 * creates a new game with an empty states list
	 * @param name name of this game
	 */
	public Game(String name) {
		this.name = name;
		this.currentState = 0;
		states = new ArrayList<>();
	}
	
	public GameState getCurrentGameState() {
		return this.states.get(this.currentState);
	}
	
	/** deletes all game states that were added after the current one */
	public void deleteRedoStates() {
		while (states.size() > currentState + 1)
			states.remove(currentState + 1);
	}

	/**
	 * getter for {@link #name}
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for {@link #currentState}
	 * @return current state index
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * getter for {@link #currentPlayer}
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * getter for {@link #states}
	 * @return states list
	 */
	public ArrayList<GameState> getStates() {
		return states;
	}

	/**
	 * setter for {@link #currentState}
	 * @param currentState current game state index
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	/**
	 * setter for {@link #currentPlayer}
	 * @param currentPlayer current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
