package model.player.ai;

import model.card.Card;

@SuppressWarnings("PMD")

public class Move {

	private Card chosen;
	private Action action;
	
	public Move(Card chosen, Action action) {
		this.chosen = chosen;
		this.action = action;
	}
	
	public Card getCard() {
		return chosen;
	}
	
	public Action getAction() {
		return action;
	}
	
	public enum Action{
		BUILD,
		PLACE,
		SELL;
	}
}