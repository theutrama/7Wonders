package controller;

import model.card.Card;
import model.player.Player;

public class CardController {

	private SevenWondersController swController;

	public CardController(SevenWondersController swController) {
		this.swController=swController;
	}
	
	public void loadCards() {

	}

	public Card[] generateCardStack() {
		return null;
	}

	public Card[] loadCardStack(String filepath) {
		return null;
	}

	/**
	 *  
	 */
	public void sellCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public void placeCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public void setSlotCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public Card getCard(String cardname) {
		return null;
	}

}