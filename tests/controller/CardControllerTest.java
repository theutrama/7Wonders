package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.Main;
import model.card.Card;

/** tests Card Controller */
public class CardControllerTest {
	
	private SevenWondersController swc;
	private GameController gC;
	private PlayerController pC;
	private CardController cC;
	private WonderBoardController wbc;
	
	/**
	 * Setup test methods
	 */
	@Before
	public void setUp() {
		
		swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		cC = swc.getCardController();
		pC = swc.getPlayerController();
		wbc = swc.getWonderBoardController();
		Main.TEST = true;
	}
	
	/** tests if first player has card */

	@Test
	public void createHasCardTest() {
		assertEquals(true,cC.hasCard(swc.getPlayerController().getPlayer("erster"), "library"));
		assertEquals(false,cC.hasCard(swc.getPlayerController().getPlayer("erster"), "academy"));
	}
	
	/** tests if first player can sell cards */
	@Test
	public void createSellCardTest() {
		//Wenn bei getCard ein falscher name angegeben wird kommt null zurück 
		cC.sellCard(cC.getCard(pC.getPlayer("erster").getHand(), "lodge"), pC.getPlayer("erster"));
		assertEquals(pC.getPlayer("erster").getCoins(),6);
		cC.sellCard(cC.getCard(pC.getPlayer("erster").getHand(), "study"), pC.getPlayer("erster"));
		assertEquals(pC.getPlayer("erster").getCoins(),9);
		
	}
	
	/**
	 * tests if card gets placed correctly
	 */
	@Test
	public void placeCardTest() {
		int countScienceCards = pC.getPlayer("erster").getBoard().getResearch().size();
		Card randomCard = cC.getCard("library");
		cC.placeCard(randomCard, pC.getPlayer("erster"), null);
		assertEquals(pC.getPlayer("erster").getBoard().getResearch().get(countScienceCards), randomCard);
	}

}
