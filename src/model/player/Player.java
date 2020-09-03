package model.player;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import model.board.WonderBoard;
import model.card.Card;

public class Player {

	private String name;

	private int coins = 0;
	private int victoryPoints = 0;
	private int losePoints = 0;
	private int conflictPoints = 0;

	private boolean mausoleum = false;

	private ArrayList<Card> hand;
	private Card choosenCard;

	private WonderBoard board;

	public Player(String name, WonderBoard board) {
		this.name = name;
		this.board = board;
	}
	
	public void setChooseCard(Card card) {
		this.choosenCard = card;
	}
	
	public Card getChoosenCard() {
		return this.choosenCard;
	}

	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	public void giveCards(Card[] cards) {
		this.hand = new ArrayList<>(Arrays.asList(cards));
	}
	
	public void addCoins(int coins) {
		this.coins += coins;
	}
	
	public void addVictoryPoints(int points) {
		this.victoryPoints += points;
	}
	
	public void addLosePoint() {
		this.losePoints++;
	}
	
	public void addConflictPoints(int points) {
		this.conflictPoints += points;
	}

	public String getName() {
		return this.name;
	}

	public int getCoins() {
		return this.coins;
	}

	public int getVictoryPoints() {
		return this.victoryPoints;
	}

	public int getLosePoints() {
		return this.losePoints;
	}

	public int getConflictPoints() {
		return this.conflictPoints;
	}

	public boolean isMausoleum() {
		return this.mausoleum;
	}

	public WonderBoard getBoard() {
		return this.board;
	}
}
