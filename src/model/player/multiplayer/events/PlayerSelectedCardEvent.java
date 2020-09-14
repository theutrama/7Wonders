package model.player.multiplayer.events;

import main.api.events.Event;
import model.card.Card;
import model.player.Player;

/** SelectedCardEvent on Player */
@SuppressWarnings("all")
public class PlayerSelectedCardEvent extends Event{

	/** the player */
	private Player player;
	/** the card */
	private Card card;
	
	/**
	 * create new PlayerSelectedCardEvent
	 * @param player	the player
	 * @param card		the card
	 */
	public PlayerSelectedCardEvent(Player player, Card card) {
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
