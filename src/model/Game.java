package model;

import java.util.ArrayList;

/** Contains Game data for SevenWonders game */
public class Game {
	/** current state index */
	private int currentState;
	/** name */
	private String name;
	/** list of game states */
	private ArrayList<GameState> states;
	/** true if the score is still valid */
	private boolean allowHighscore = true;

	/**
	 * creates a new game with an empty states list
	 * 
	 * @param name name of this game
	 */
	public Game(String name) {
		this.name = name;
		this.currentState = 0;
		states = new ArrayList<>();
	}

	/**
	 * get current game state object
	 * 
	 * @return current game state
	 */
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
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for {@link #currentState}
	 * 
	 * @return current state index
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * getter for {@link #states}
	 * 
	 * @return states list
	 */
	public ArrayList<GameState> getStates() {
		return states;
	}

	/**
	 * setter for {@link #currentState}
	 * 
	 * @param currentState current game state index
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	/**
	 * Returns if score is still valid
	 * 
	 * @return {@link #allowHighscore}
	 */
	public boolean highscoreAllowed() {
		return allowHighscore;
	}

	/**
	 * signal that someone has cheated
	 */
	public void disableHighscore() {
		allowHighscore = false;
	}

}
