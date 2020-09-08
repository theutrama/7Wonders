package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import application.Main;
import javafx.application.Platform;
import model.GameState;


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
		// hier lag unser fehler... wir hatten vor swc in der n�chsten zeile noch
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
	 * tests undo redo methods
	 */
	@Test
	public void undoRedoTest() {

		GameState g1 = swc.getGame().getCurrentGameState();
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.undo(swc.getGame());
		assertEquals(g1, swc.getGame().getCurrentGameState());

		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		GameState g2 = swc.getGame().getCurrentGameState();
		gC.undo(swc.getGame());
		gC.redo(swc.getGame());
		System.out.println("test:" + swc.getGame().getCurrentGameState());
		assertEquals(g2, swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		GameState g4 = swc.getGame().getCurrentGameState();
		gC.undo(swc.getGame());
		// g3 = 3rd round 1st age
		GameState g3 = swc.getGame().getCurrentGameState();
		
		//pC.chooseCard(cC.getCard(swc.getGame().getCurrentGameState().getCardStack(),
			//	swc.getGame().getCurrentGameState().getCardStack().get(2).getName()), pC.getPlayer("erster"));
		swc.getGame().getCurrentState(); //Passiert in der view
		swc.getGame().setCurrentState(swc.getGame().getCurrentState()+1);
		//System.out.println("CurrentState:" + swc.getGame().getCurrentState());
		//System.out.println(swc.getGame().getStates().size()-1);
		//gC.redo(swc.getGame());
		//System.out.println("Round:"+ swc.getGame().getCurrentGameState().getRound());
		assertEquals(g4, swc.getGame().getCurrentGameState());
		//System.out.println(swc.getGame().getCurrentGameState().getPlayer().getHand().get(0).getDescription());
		//System.out.println("TEst");
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		//System.out.println("test2");
		//System.out.println("Gibt Karte aus:" + pC.getPlayer("erster").getHand().get(0).getDescription());
		//System.out.println("Gibt Runde aus:" + swc.getGame().getCurrentGameState().getRound());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		//System.out.println("Gibt Karte aus:" + pC.getPlayer("erster").getHand().get(0));
		//System.out.println("Gibt Runde aus:" + swc.getGame().getCurrentGameState().getRound());
		// 6th round 1st age
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());

		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());

		GameState g7 = swc.getGame().getCurrentGameState();
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		// 3rd round 2nd age
		gC.undo(swc.getGame());
		gC.undo(swc.getGame());
		gC.undo(swc.getGame());
		gC.undo(swc.getGame());
		// 6th round 1st age
		// komischer Fehler...
		assertEquals(g7, swc.getGame().getCurrentGameState());
	}
	/**
	 * nextRount test methods
	 */
	@Test
	public void createNextRoundTest() {
		Platform.startup(() -> {
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
			GameState round1age2 = swc.getGame().getCurrentGameState();
			assertEquals(round1age2.getAge(), 2);
			assertEquals(round1age2.getRound(), 1);
		});
		Platform.exit();

	}
	// create game correctly adding players/ boards
	// ...
}
