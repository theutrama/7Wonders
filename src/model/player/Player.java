package model.player;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import model.board.WonderBoard;
import model.card.Card;

public class Player {

	private String name;

	private int coins;

	private int victoryPoints;

	private int losePoints;

	private int conflictPoints;

	private boolean mausoleum;

	private ArrayList<Card> hand;

	private WonderBoard board;

	public Player(String name, WonderBoard board) {
		this.name = name;
		this.board = board;
		mausoleum = false;
	}

	public void giveCards(Card[] cards) {
		hand = new ArrayList<>(Arrays.asList(cards));
	}
	
	public void addCoins(int coins) {
		this.coins += coins;
	}
	
	public void addVictoryPoints(int points) {
		victoryPoints += points;
	}
	
	public void addLosePoint() {
		losePoints++;
	}
	
	public void addConflictPoints(int points) {
		conflictPoints += points;
	}

	public String getName() {
		return name;
	}

	public int getCoins() {
		return coins;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public int getLosePoints() {
		return losePoints;
	}

	public int getConflictPoints() {
		return conflictPoints;
	}

	public boolean isMausoleum() {
		return mausoleum;
	}

	public WonderBoard getBoard() {
		return board;
	}
}
