package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.Main;

/** tests Card Controller */
public class CardControllerTest {
	
	private SevenWondersController swc;
	private GameController gC;
	private PlayerController pC;
	private CardController cC;
	
	/**
	 * Setup test methods
	 */
	@Before
	public void setUp() {
		
		swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		cC = swc.getCardController();
		pC = swc.getPlayerController();
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
	
	

}
