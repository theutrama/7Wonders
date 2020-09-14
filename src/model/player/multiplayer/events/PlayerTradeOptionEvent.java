package model.player.multiplayer.events;

import main.api.events.Event;
import model.card.Card;
import model.player.Player;

/** TradeOptionEvent on Player */
@SuppressWarnings("all")
public class PlayerTradeOptionEvent extends Event{

	/** the player */
	private Player player;
	/** the optionIndex */
	private int optionIndex;
	
	/**
	 * create new PlayerTradeOptionEvent
	 * @param player			the player
	 * @param optionIndex		the optionIndex
	 */
	public PlayerTradeOptionEvent(Player player, int optionIndex) {
		this.player=player;
		this.optionIndex=optionIndex;
	}
	
	/**
	 * getter for {@link #player}
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * getter for {@link #optionIndex}
	 * @return optionIndex
	 */
	public int getOptionIndex() {
		return this.optionIndex;
	}
}
