package model.board;

import java.io.Serializable;
import java.util.ArrayList;

import application.Main;
import model.card.Card;
import model.card.Resource;
import model.player.Player;
import model.card.CardType;

/** Abstract Wonder Board */
public abstract class WonderBoard implements Serializable {
	private static final long serialVersionUID = 1L;
	/** indicates if each slot (0-2) is filled with a card */
	private boolean[] filled;
	/** saves the age of the card that each slot is filled with */
	private int[] ageOfSlotCards;
	/** card lists on the board */
	private ArrayList<Card> resources, military, trade, guilds, civil, research;
	/** the resource produced by this board */
	protected Resource resource;
	/** required resources to build the slots (0-2) */
	protected Resource[] slotRequirements;
	/** assigned player */
	protected Player player;

	/**
	 * create wonder board
	 */
	public WonderBoard() {
		resources = new ArrayList<>();
		military = new ArrayList<>();
		trade = new ArrayList<>();
		guilds = new ArrayList<>();
		civil = new ArrayList<>();
		research = new ArrayList<>();
		filled = new boolean[3];
		ageOfSlotCards = new int[3];
	}

	/**
	 * getter for one index of {@link #ageOfSlotCards}
	 * 
	 * @param slot slot index
	 * @return
	 */
	public int getAgeOfSlotCards(int slot) {
		return ageOfSlotCards[slot];
	}

	/**
	 * setter for an index of {@link #ageOfSlotCards}
	 * 
	 * @param slot slot index
	 * @param age  age
	 */
	public void setAgeOfSlotCards(int slot, int age) {
		this.ageOfSlotCards[slot] = age;
	}

	/**
	 * getter for the image path
	 * 
	 * @return path of the assigned image file
	 */
	public String getImage() {
		String name = getClass().getSimpleName().replaceAll("Board", "");
		return Main.BOARD_PATH + name.toLowerCase() + ".jpg";
	}

	/**
	 * adds 3 victory points to the assigned player
	 */
	public void slot1() {
		player.addVictoryPoints(3);
	}

	/**
	 * abstract method implemented by all subclasses
	 */
	public abstract void slot2();

	/**
	 * adds 7 victory points to the assigned player
	 */
	public void slot3() {
		player.addVictoryPoints(7);
	}

	/**
	 * getter for {@link #filled} at index slot
	 * 
	 * @param slot slot index
	 * @return filled
	 */
	public boolean isFilled(int slot) {
		return filled[slot];
	}

	/**
	 * setter for an index of {@link #filled}
	 * 
	 * @param slot slot index to be filled
	 */
	public void fill(int slot) {
		filled[slot] = true;
	}

	/**
	 * getter for one index of {@link #slotRequirements}
	 * 
	 * @param slot slot index (0-2)
	 * @return slot requirement
	 */
	public Resource getSlotResquirement(int slot) {
		return slotRequirements[slot];
	}

	/**
	 * getter to find the next unfinished build stage
	 * 
	 * @return the first unfinished build stage of the wonder or -1 if it is finished
	 */
	public int nextSlot() {
		return filled[0] ? (filled[1] ? (filled[2] ? -1 : 2) : 1) : 0;
	}

	/**
	 * getter for the resources needed for the next wonder build stage
	 * 
	 * @return null if the wonder is finished or the resource requirement of the first unfinished build stage
	 */
	public Resource getNextSlotRequirement() {
		int next = nextSlot();
		return next == -1 ? null : slotRequirements[next];
	}

	/**
	 * getter for {@link #resource}
	 * 
	 * @return resource
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * getter for {@link #resources}
	 * 
	 * @return resource cards
	 */
	public ArrayList<Card> getResources() {
		return resources;
	}

	/**
	 * getter for {@link #military}
	 * 
	 * @return military cards
	 */
	public ArrayList<Card> getMilitary() {
		return military;
	}

	/**
	 * getter for {@link #trade}
	 * 
	 * @return trade cards
	 */
	public ArrayList<Card> getTrade() {
		return trade;
	}

	/**
	 * getter for {@link #guilds}
	 * 
	 * @return guild cards
	 */
	public ArrayList<Card> getGuilds() {
		return guilds;
	}

	/**
	 * getter for {@link #civil}
	 * 
	 * @return civil cards
	 */
	public ArrayList<Card> getCivil() {
		return civil;
	}

	/**
	 * getter for {@link #research}
	 * 
	 * @return research cards
	 */
	public ArrayList<Card> getResearch() {
		return research;
	}

	/**
	 * setter for {@link #player}
	 * 
	 * @param player player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * getter for {@link #player}
	 * 
	 * @return assigned player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * getter for the board name
	 * 
	 * @return board name
	 */
	public String getBoardName() {
		return getClass().getSimpleName().replaceAll("Board", "");
	}

	/**
	 * adds a card to the proper card list
	 * 
	 * @param card a card to be added
	 */
	public void addCard(Card card) {
		CardType temp = card.getType();
		switch (temp) {
		case BROWN:
		case GRAY:
			resources.add(card);
			break;
		case YELLOW:
			trade.add(card);
			break;
		case BLUE:
			civil.add(card);
			break;
		case GREEN:
			research.add(card);
			break;
		case RED:
			military.add(card);
			break;
		case PURPLE:
			guilds.add(card);
			break;
		}

	}
}
