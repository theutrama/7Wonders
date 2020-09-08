package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.Main;

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
		// hier lag unser fehler... wir hatten vor swc in der n�chsten zeile noch
		// SevenWondersController stehen
		// und haben uns damit ein neues objekt erzeugt... logisch, dass es dann null
		// ist :D
		swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		cC = swc.getCardController();
		pC = swc.getPlayerController();
		//Main.TEST = true;
	}

	@Test
	public void createHasCardTest() {
		assertEquals(true,cC.hasCard(swc.getPlayerController().getPlayer("erster"), "library"));
		assertEquals(false,cC.hasCard(swc.getPlayerController().getPlayer("erster"), "academy"));
	}
	
	

}
