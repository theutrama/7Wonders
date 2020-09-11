package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import application.Main;
import javafx.application.Platform;
import model.GameState;

/** tests Game Controller */
public class GameControllerTest {

	private SevenWondersController swc;
	private GameController gC;
	private PlayerController pC;
	private CardController cC;
	
	/**
	 * Setup test methods
	 */
	@Before
	public void setUp() {
		// hier lag unser fehler... wir hatten vor swc in der naechsten zeile noch
		// SevenWondersController stehen
		// und haben uns damit ein neues objekt erzeugt... logisch, dass es dann null
		// ist :D
		swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		cC = swc.getCardController();
		pC = swc.getPlayerController();
		Main.TEST = true;

	}
	
	/**
	 * nextRount test methods
	 */
	@Test
	public void createNextRoundTest() {
		
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			GameState round6age1 = swc.getGame().getCurrentGameState();
			assertEquals(round6age1.getAge(), 1);
			assertEquals(round6age1.getRound(), 6);
			
			
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			GameState round1age2 = swc.getGame().getCurrentGameState();
			assertEquals(round1age2.getAge(), 2);
			assertEquals(round1age2.getRound(), 1);
			
		

	}
	// create game correctly adding players/ boards
	// ...
}
