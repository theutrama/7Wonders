package model;

import java.util.ArrayList;

import model.card.Card;
import model.player.Player;

public class Game {

	private int currentState;

	private Player currentPlayer;

	private String name;

	private ArrayList<GameState> states;

	private ArrayList<Card> cardStack;
	
	public Game() {
		
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
	
	public ArrayList<Card> getCardStack() {
		return cardStack;
	}
	
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

}
