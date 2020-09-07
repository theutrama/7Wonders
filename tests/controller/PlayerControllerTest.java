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
	private WonderBoardController wbController;

	@Before
	public void setUp() {
		SevenWondersController swc = SevenWondersFactory.create();
		pController = swc.getPlayerController();
		cController = swc.getCardController();
		wbController = swc.getWonderBoardController();
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

	
	
	/**
	 * test science points
	 */
	@Test
	public void createSciencePointsTest() {
		
		 assertEquals(10, pController.getSciencePoints(pController.getPlayer("erster")));
		 assertEquals(18, pController.getSciencePoints(pController.getPlayer("zweiter")));
		 assertEquals(2, pController.getSciencePoints(pController.getPlayer("dritter")));
		 assertEquals(1, pController.getSciencePoints(pController.getPlayer("vierter")));
		 assertEquals(9, pController.getSciencePoints(pController.getPlayer("fünfter")));
		 assertEquals(4, pController.getSciencePoints(pController.getPlayer("sechster")));
		 assertEquals(48, pController.getSciencePoints(pController.getPlayer("siebter")));
		
		 
	}
	
}
