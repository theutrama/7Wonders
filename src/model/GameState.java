package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.card.Card;
import model.player.Player;

public class GameState {
	/** current age */
	private int age;
	/** current round */
	private int round;
	/** current player */
	private int currentPlayer;
	/** list of players */
	private ArrayList<Player> players = new ArrayList<Player>();
	/** list of discarded cards */
	private ArrayList<Card> trash = new ArrayList<Card>();
	/** list of unused cards */
	private ArrayList<Card> cardStack;
	/** true if no action was done in this round */
	private boolean beginOfRound;

	/**
	 * not used other than the begin of a game
	 * @param age current age
	 * @param round current round
	 * @param players player list
	 * @param cards initial card stack
	 */
	public GameState(int age, int round, ArrayList<Player> players, ArrayList<Card> cards) {
		this.players = players;
		this.age = age;
		this.round = round;
		cardStack = cards;
		beginOfRound = true;
	}

	/**
	 * getter for {@link #age}
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * getter for {@link #round}
	 * @return round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * setter for {@link #currentPlayer}
	 * @param currentPlayer current player index
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * getter for {@link #currentPlayer}
	 * @return current player index
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * getter for {@link #players}
	 * @return player list
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * getter for {@link #trash}
	 * @return trash cards
	 */
	public ArrayList<Card> getTrash() {
		return trash;
	}

	/**
	 * getter for {@link #cardStack}
	 * @return card stack
	 */
	public ArrayList<Card> getCardStack() {
		return cardStack;
	}

	/**
	 * deep clones this game state
	 * @return new game state instance that is equal to this one, but has no common references
	 */
	public GameState deepClone() {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(this);
			objOut.flush();
			objOut.close();
			byteOut.close();
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream objIn = new ObjectInputStream(byteIn);
			GameState copy = (GameState) objIn.readObject();
			byteIn.close();
			objIn.close();
			return copy;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * setter for {@link #age}
	 * @param age age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * setter for {@link #round}
	 * @param round round
	 */
	public void setRound(int round) {
		this.round = round;
	}
	
	/**
	 * setter for {@link #beginOfRound}
	 * @param begin begin of round
	 */
	public void setBeginOfRound(boolean begin) {
		beginOfRound = begin;
	}
	
	/** sets {@link #beginOfRound} to false */
	public void beginRound() {
		beginOfRound = false;
	}
	
	/**
	 * getter for {@link #beginOfRound}
	 * @return begin of round
	 */
	public boolean isAtBeginOfRound() {
		return beginOfRound;
	}

}
