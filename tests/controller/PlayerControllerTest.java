package controller;

import static org.junit.Assert.*;

import org.junit.Before;

import model.player.Player;
import model.card.Card;

import org.junit.Test;

//
public class PlayerControllerTest {
	
	private SevenWondersController swController;
	private PlayerController pController;
	private CardController cController;
	
	@Before
	public void setUp() {
		swController = new SevenWondersController();
		pController = swController.getPlayerController();
		cController = swController.getCardController();
		
		
	}
	@Test
	 public void createMilitaryPointsTest() {
		
		 
			Player player1 = pController.createPlayer("erster", "Alexandria"); 
			 Player player2 = pController.createPlayer("zweiter", "Babylon");
			 Player player3 = pController.createPlayer("dritter", "Ephesos");
			 Player player4 = pController.createPlayer("vierter", "Gizah");
			 Player player5 = pController.createPlayer("fünfter", "Halikarnassus");
			 //Player player6 = pController.createPlayer("sechster", "Olympia");
			 //Player player7 = pController.createPlayer("siebter", "Rhodos");
			 Card card3 = cController.getCard("circus"); //3 military points
			 Card card2 = cController.getCard("archeryrange"); //2 military points
			 Card card1 = cController.getCard("barracks"); // 1 military point
			 player1.setChooseCard(card1);
			 player2.setChooseCard(card2);
			 player3.setChooseCard(card3);
			 player4.setChooseCard(card1);
			 player4.setChooseCard(card2);
			 player5.setChooseCard(card1);
			 player5.setChooseCard(card2);
			 player5.setChooseCard(card3);
			 
			 //System.out.println(pController.getMilitaryPoints(player1));
			 assertEquals(1, pController.getMilitaryPoints(player1));
			 assertEquals(2, pController.getMilitaryPoints(player2));
			 assertEquals(3, pController.getMilitaryPoints(player3));
			 assertEquals(3, pController.getMilitaryPoints(player4));
			 assertEquals(6, pController.getMilitaryPoints(player5));
			 
		
		 
		 
	 }
	
	public void createSciencePointsTest() {
		Player player1 = pController.createPlayer("erster", "Alexandria"); 
		 Player player2 = pController.createPlayer("zweiter", "Babylon");
		 Player player3 = pController.createPlayer("dritter", "Ephesos");
		 Player player4 = pController.createPlayer("vierter", "Gizah");
		 Player player5 = pController.createPlayer("fünfter", "Halikarnassus");
		 Player player6 = pController.createPlayer("sechster", "Olympia");
		 Player player7 = pController.createPlayer("siebter", "Rhodos");
		 Card card1 = cController.getCard("apothecary"); //compass
		 Card card2 = cController.getCard("academy"); //commpass
		 Card card3 = cController.getCard("lodge"); //commpass
		 
		 Card card4 = cController.getCard("laboratory"); // gear
		 Card card5 = cController.getCard("observatory"); // gear
		 Card card6 = cController.getCard("study"); // gear
		 
		 Card card7 = cController.getCard("library"); // tablet
		 Card card8 = cController.getCard("school"); // tablet
		 Card card9 = cController.getCard("scriptorium"); // tablet
		 
		 player1.setChooseCard(card1);
		 player1.setChooseCard(card2);
		 player1.setChooseCard(card3);
		 player1.setChooseCard(card4);
		 player1.setChooseCard(card5);
		 
		 player2.setChooseCard(card1);
		 player2.setChooseCard(card2);
		 player2.setChooseCard(card3);

		 player3.setChooseCard(card1);
		 player3.setChooseCard(card4);
		 player3.setChooseCard(card7);
		 player3.setChooseCard(card2);
		 player3.setChooseCard(card5);
		 player3.setChooseCard(card8);
		 
		 player4.setChooseCard(card1);
		 player4.setChooseCard(card4);
		 player4.setChooseCard(card7);
		 
		 player5.setChooseCard(card1);
		 
		 player6.setChooseCard(card1);
		 player6.setChooseCard(card4);

		 
		 
		 assertEquals(13, pController.getSciencePoints(player1));
		 assertEquals(9, pController.getSciencePoints(player2));
		 assertEquals(19, pController.getSciencePoints(player3));
		 assertEquals(7, pController.getSciencePoints(player4));
		 assertEquals(1, pController.getSciencePoints(player5));
		 assertEquals(2, pController.getSciencePoints(player6));
		 
	}
	

}
