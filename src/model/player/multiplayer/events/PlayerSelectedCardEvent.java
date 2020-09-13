package model.player.multiplayer.events;

import main.api.events.Event;
import model.card.Card;
import model.player.Player;

@SuppressWarnings("all")
public class PlayerSelectedCardEvent extends Event{

	private Player player;
	private Card card;
	
	public PlayerSelectedCardEvent(Player player, Card card) {
		this.player=player;
		this.card=card;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Card getCard() {
		return this.card;
	}
}
