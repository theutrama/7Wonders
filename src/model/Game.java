package model;

import java.util.ArrayList;

import model.player.Player;

public class Game {

	private int currentState;

	private Player currentPlayer;

	private String name;

	private ArrayList<GameState> states;

	public Game(GameState initialState, String name) {
		this.name = name;
		states = new ArrayList<>();
		states.add(initialState);
	}
	
	/**
	 * deletes all game states that were added after the current one
	 */
	public void deleteRedoStates() {
		while (states.size() > currentState + 1)
			states.remove(currentState + 1);
	}

	public String getName() {
		return name;
	}

	public int getCurrentState() {
		return currentState;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public ArrayList<GameState> getStates() {
		return states;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
