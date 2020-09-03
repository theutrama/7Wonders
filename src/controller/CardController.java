/**
 * The Card-Controller controls the ingame cards.
 */

package controller;

import java.util.HashMap;
import java.util.Map;

import model.card.Card;
import model.player.Player;

public class CardController {
	
	Map<String, int[]> countCards = new HashMap<>();
	
	private SevenWondersController swController;

	public CardController(SevenWondersController swController) {
		this.swController=swController;
		
		
	}
	/**
	 * Loading all ingame cards with their parameters and frequency
	 */
	public void loadCards() {
		countCards.put("lumber_yard", new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
		countCards.put("lumber_yard", new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
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