package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javafx.application.Platform;
import model.GameState;

public class GameControllerTest {

	private SevenWondersController swc;
	private GameController gC;
	private PlayerController pC;
	private CardController cC;

	@Before
	public void setUp() {
		// hier lag unser fehler... wir hatten vor swc in der n�chsten zeile noch
		// SevenWondersController stehen
		// und haben uns damit ein neues objekt erzeugt... logisch, dass es dann null
		// ist :D
		swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		cC = swc.getCardController();
		
	}

	
	@Test
	public void undoRedoTest() {

		GameState g1 = swc.getGame().getCurrentGameState();
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.undo(swc.getGame());
		assertEquals(g1, swc.getGame().getCurrentGameState());
		
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.undo(swc.getGame());
		//g3 = 3rd round 1st age
		GameState g3 = swc.getGame().getCurrentGameState();
		pC.chooseCard(cC.getCard(swc.getGame().getCurrentGameState().getPlayer().getHand(), swc.getGame().getStates().get(swc.getGame().getCurrentState()).getPlayer().getHand().get(2).toString()), pC.getPlayer("erster"));
		//card already chosen -> redo does nothing
		assertEquals(g3, swc.getGame().getCurrentGameState());
		
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		//6th round 1st age
		GameState g2 = swc.getGame().getCurrentGameState();
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
		//komischer Fehler...
		assertEquals(g2, swc.getGame().getCurrentGameState());
	}

	@Test
	public void createNextRoundTest() {
		Platform.startup(() -> {
			gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
		GameState round7age1 = swc.getGame().getCurrentGameState();
		assertEquals(round7age1.getAge(), 7);
		});
		
		
	}
	// create game correctly adding players/ boards
	// ...
}
