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

	private int age;

	private int round;

	private int currentPlayer;

	private ArrayList<Player> players = new ArrayList<Player>();

	private ArrayList<Card> trash = new ArrayList<Card>();

	private ArrayList<Card> cardStack;

	public GameState(int age, int round, ArrayList<Player> players, ArrayList<Card> cards) {
		this.players = players;
		this.age = age;
		this.round = round;
		cardStack = cards;
	}

	public int getAge() {
		return age;
	}

	public int getRound() {
		return round;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getTrash() {
		return trash;
	}

	public ArrayList<Card> getCardStack() {
		return cardStack;
	}

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
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setRound(int round) {
		this.round = round;
	}

}
