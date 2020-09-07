package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.GameState;

public class GameControllerTest {

	private SevenWondersController swc;
	private GameController gC;
	
	@Before
	public void setUp() {
		SevenWondersController swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		
		
		
	}
	
	@Test
	public void createNextRoundTest() {
	
		
			try {
				GameState g1 = swc.getGame().getCurrentGameState();
				gC.createNextRound(swc.getGame(), swc.getGame().getCurrentGameState());
				gC.undo(swc.getGame());
				assertEquals (g1, swc.getGame().getCurrentGameState());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	//create game correctly adding players/ boards
	//...
}
