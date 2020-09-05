package controller;

import org.junit.Before;

import model.card.Card;
import model.player.Player;

public class SevenWondersFactory {
	
	
	
	
	public static SevenWondersControllerTest create () {
		SevenWondersControllerTest swct= new SevenWondersControllerTest();
		
		SevenWondersController swController = new SevenWondersController();
		PlayerController pController = swController.getPlayerController();
		CardController cController = swController.getCardController();
		
		
		 Player player1 = pController.createPlayer("erster", "Alexandria"); 
		 Player player2 = pController.createPlayer("zweiter", "Babylon");
		 Player player3 = pController.createPlayer("dritter", "Ephesos");
		 Player player4 = pController.createPlayer("vierter", "Gizah");
		 Player player5 = pController.createPlayer("fünfter", "Halikarnassus");
		 Player player6 = pController.createPlayer("sechster", "Olympia");
		 Player player7 = pController.createPlayer("siebter", "Rhodos");
		 Card card13 = cController.getCard("circus"); //3 military points
		 Card card11 = cController.getCard("archeryrange"); //2 military points
		 Card card10 = cController.getCard("barracks"); // 1 military point
		 Card card12 = cController.getCard("stables"); //2 military points
		 
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
		 player2.setChooseCard(card2);
		 player3.setChooseCard(card3);
		 player4.setChooseCard(card1);
		 player4.setChooseCard(card2);
		 player5.setChooseCard(card1);
		 player5.setChooseCard(card2);
		 player5.setChooseCard(card3);
		return swct;
	}
	
		
		
	

}
