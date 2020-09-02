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

	private int stackIndex;

	private ArrayList<Player> players;

	private ArrayList<Card> trash;
	
	public GameState() {
		
	}
	
	public int getAge() {
		return age;
	}
	
	public int getRound() {
		return round;
	}
	
	public int getStackIndex() {
		return stackIndex;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Card> getTrash() {
		return trash;
	}
	
	public GameState deepClone() throws IOException, ClassNotFoundException {
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
	}

}
