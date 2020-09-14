package model.player.multiplayer.events;

import main.api.events.Event;
import model.card.Card;
import model.player.Player;

/** HalikarnassusEvent on Player */
@SuppressWarnings("all")
public class PlayerHalikarnassusEvent extends Event{

	/** the player */
	private Player player;
	/** the card */
	private Card card;
	
	/**
	 * create new PlayerHalikarnassusEvent
	 * @param player	the player
	 * @param card		the card
	 */
	public PlayerHalikarnassusEvent(Player player, Card card) {
		this.player=player;
		this.card=card;
	}
	
	/**
	 * getter for {@link #player}
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * getter for {@link #card}
	 * @return card
	 */
	public Card getCard() {
		return this.card;
	}
}
