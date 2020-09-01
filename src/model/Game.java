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
	
	public String getName() {
		return name;
	}

}
