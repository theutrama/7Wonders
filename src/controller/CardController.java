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
	 * Matrix-scheme: amount of players when another card needs to be created
	 */
	public void loadCards() {
		countCards.put("academy", new int[]{3,7,0});
		countCards.put("altar", new int[]{3,5,0});
		countCards.put("apothecary", new int[]{3,5,0});
		countCards.put("aqueduct", new int[]{3,7,0});
		countCards.put("archeryrange", new int[]{3,6,0});
		countCards.put("arena", new int[]{3,5,7});
		countCards.put("arsenal", new int[]{3,4,7});
		countCards.put("barracks", new int[]{3,5,0});
		countCards.put("baths", new int[]{3,7,0});
		countCards.put("bazar", new int[]{4,7,0});
		countCards.put("brickyard", new int[]{3,4,0});
		countCards.put("buildersguild", new int[]{0,0,0});
		countCards.put("caravansery", new int[]{3,5,6});
		countCards.put("chamberofcommerce", new int[]{4,6,0});
		countCards.put("circus", new int[]{4,6,6});
		countCards.put("claypit", new int[]{3,0,0});
		countCards.put("claypool", new int[]{3,5,0});
		countCards.put("courthouse", new int[]{3,5,0});
		countCards.put("craftsmensguild", new int[]{0,0,0});
		countCards.put("dispensary", new int[]{3,4,0});
		countCards.put("easttradingpost", new int[]{3,7,0});
		countCards.put("excavation", new int[]{4,0,0});
		countCards.put("forestcave", new int[]{5,0,0});
		countCards.put("fortifications", new int[]{3,7,0});
		countCards.put("forum", new int[]{3,6,7});
		countCards.put("foundry", new int[]{3,4,0});
		countCards.put("gardens", new int[]{3,4,0});
		countCards.put("glassworks1", new int[]{3,6,0});
		countCards.put("glassworks2", new int[]{3,5,0});
		countCards.put("guardtower", new int[]{3,4,0});
		countCards.put("haven", new int[]{3,4,0});
		countCards.put("laboratory", new int[]{3,5,0});
		countCards.put("library", new int[]{3,6,0});
		countCards.put("lighthouse", new int[]{3,6,0});
		countCards.put("lodge", new int[]{3,6,0});
		countCards.put("loom1", new int[]{3,6,0});
		countCards.put("loom2", new int[]{3,5,0});
		countCards.put("lumberyard", new int[]{3,4,0});
		countCards.put("magistratesguild", new int[]{0,0,0});
		countCards.put("marketplace", new int[]{3,6,0});
		countCards.put("mine", new int[]{6,0,0});
		countCards.put("observatory", new int[]{3,7,0});
		countCards.put("orevein", new int[]{3,4,0});
		countCards.put("palace", new int[]{3,7,0});
		countCards.put("pantheon", new int[]{3,6,0});
		countCards.put("pawnshop", new int[]{4,7,0});
		countCards.put("philosophersguild", new int[]{0,0,0});
		countCards.put("press1", new int[]{3,6,0});
		countCards.put("press2", new int[]{3,5,0});
		countCards.put("quarry", new int[]{3,4,0});
		countCards.put("sawmill", new int[]{3,4,0});
		countCards.put("school", new int[]{3,7,0});
		countCards.put("scientistsguild", new int[]{0,0,0});
		countCards.put("scriptorium", new int[]{3,4,0});
		countCards.put("senate", new int[]{3,5,0});
		countCards.put("shipownersguild", new int[]{0,0,0});
		countCards.put("siegeworkshop", new int[]{3,5,0});
		countCards.put("spiesguild", new int[]{0,0,0});
		countCards.put("stables", new int[]{3,5,0});
		countCards.put("statue", new int[]{3,7,0});
		countCards.put("stockade", new int[]{3,7,0});
		countCards.put("stonepit", new int[]{3,5,0});
		countCards.put("strategistsguild", new int[]{0,0,0});
		countCards.put("study", new int[]{3,5,0});
		countCards.put("tavern", new int[]{4,5,7});
		countCards.put("temple", new int[]{3,6,0});
		countCards.put("theater", new int[]{3,6,0});
		countCards.put("timberyard", new int[]{3,0,0});
		countCards.put("townhall", new int[]{3,5,6});
		countCards.put("tradersguild", new int[]{0,0,0});
		countCards.put("trainingground", new int[]{4,6,7});
		countCards.put("treefarm", new int[]{6,0,0});
		countCards.put("university", new int[]{3,4,0});
		countCards.put("vineyard", new int[]{3,6,0});
		countCards.put("walls", new int[]{3,7,0});
		countCards.put("westtradingpost", new int[]{3,7,0});
		countCards.put("workersguild", new int[]{0,0,0});
		countCards.put("workshop", new int[]{3,7,0});
	}

	public Card[] generateCardStack() {
		Card[] cards = new Card[78];
		
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