package model.board;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import model.card.Card;
import model.card.Resource;
import model.player.Player;
import model.card.CardType;

public abstract class WonderBoard implements Serializable{
	/** indicates if each slot (0-2) is filled with a card */
	private boolean[] filled;
	/** card lists on the board */
	private ArrayList<Card> resources, military, trade, guilds, civil, research;
	/** the resource produced by this board */
	private Resource resource;
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
	}
	
	/**
	 * getter for the image path
	 * @return path of the assigned image file
	 */
	public String getImage() {
		String name = getClass().getSimpleName().replaceAll("Board", "");
		return "src"+File.separator + "view" + File.separator + "images" + File.separator + "boards" + File.separator + name.toLowerCase() + ".jpg";
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
	 * @param slot slot index
	 * @return filled
	 */
	public boolean isFilled(int slot) {
		return filled[slot];
	}
	
	public Resource getSlotResquirement(int slot) {
		return slotRequirements[slot];
	}
	
	/**
	 * getter to find the next unfinished build stage
	 * @return the first unfinished build stage of the wonder or -1 if it is finished
	 */
	public int nextSlot() {
		return filled[0] ? (filled[1] ? (filled[2] ? -1 : 2) : 1) : 0;
	}
	
	/**
	 * getter for the resources needed for the next wonder build stage
	 * @return null if the wonder is finished or the resource requirement of the first unfinished build stage
	 */
	public Resource getNextSlotRequirement() {
		int next = nextSlot();
		return next == -1 ? null : slotRequirements[next];
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public ArrayList<Card> getResources() {
		return resources;
	}
	
	public ArrayList<Card> getMilitary() {
		return military;
	}
	
	public ArrayList<Card> getTrade() {
		return trade;
	}
	
	public ArrayList<Card> getGuilds() {
		return guilds;
	}
	
	public ArrayList<Card> getCivil() {
		return civil;
	}
	
	public ArrayList<Card> getResearch() {
		return research;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public String getBoardName() {
		return getClass().getSimpleName().replaceAll("Board", "");
	}
	
	public void addCard(Card card) {
		CardType temp= card.getType();
		switch(temp) {
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
