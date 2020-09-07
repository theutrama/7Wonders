package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;

import model.player.Player;
import model.card.Card;
import model.card.Resource;
import model.card.ResourceType;

import org.junit.Test;
import controller.SevenWondersFactory;

/**
 * 
 * create and get Controllers
 *
 */
public class PlayerControllerTest {

	private SevenWondersController swc;
	private PlayerController pController;
	private CardController cController;

	@Before
	public void setUp() {
		SevenWondersController swc = SevenWondersFactory.create();
		pController = swc.getPlayerController();
		cController = swc.getCardController();

	}

	/**
	 * test military points
	 */
	@Test
	public void createMilitaryPointsTest() {

		assertEquals(3, pController.getMilitaryPoints(pController.getPlayer("erster")));
		assertEquals(2, pController.getMilitaryPoints(pController.getPlayer("zweiter")));
		assertEquals(1, pController.getMilitaryPoints(pController.getPlayer("dritter")));
		assertEquals(2, pController.getMilitaryPoints(pController.getPlayer("vierter")));
		assertEquals(2, pController.getMilitaryPoints(pController.getPlayer("fünfter")));
		assertEquals(3, pController.getMilitaryPoints(pController.getPlayer("sechster")));
		assertEquals(5, pController.getMilitaryPoints(pController.getPlayer("siebter")));

	}

	@Test
	
	/**
	 * test science points
	 */
	public void createSciencePointsTest() {
		
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("erster")));
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("zweiter")));
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("dritter")));
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("vierter")));
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("fünfter")));
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("sechster")));
		 assertEquals(7, pController.getSciencePoints(pController.getPlayer("siebter")));
		
		 
	}

	public void createGetPlayerTest() {
		Player player1 = pController.createPlayer("erster", "Alexandria");
		// assertNotEqual
	}

}
