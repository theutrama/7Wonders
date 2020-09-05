package controller;

import java.util.ArrayList;

import org.junit.Before;

import model.Game;
import model.card.Card;
import model.player.Player;

public class SevenWondersFactory {
	
	
	
	
	public static SevenWondersControllerTest create () {
		SevenWondersControllerTest swct= new SevenWondersControllerTest();
		
		SevenWondersController sevenWondersController = new SevenWondersController();
		PlayerController playerController = sevenWondersController.getPlayerController();
		CardController cardController = sevenWondersController.getCardController();
		GameController gameController = sevenWondersController.getGameController();
		WonderBoardController wwonderBoardController = sevenWondersController.getWonderBoardController();
		IOController ioController = sevenWondersController.getIOController();
		SoundController soundController = sevenWondersController.getSoundController();
		
		
		
		
		 Player player1 = playerController.createPlayer("erster", "Alexandria"); 
		 Player player2 = playerController.createPlayer("zweiter", "Babylon");
		 Player player3 = playerController.createPlayer("dritter", "Ephesos");
		 Player player4 = playerController.createPlayer("vierter", "Gizah");
		 Player player5 = playerController.createPlayer("fünfter", "Halikarnassus");
		 Player player6 = playerController.createPlayer("sechster", "Olympia");
		 Player player7 = playerController.createPlayer("siebter", "Rhodos");
			
		 ArrayList<Player> players = new ArrayList<Player>();
		 players.add(player1);
		 players.add(player2);
		 players.add(player3);
		 players.add(player4);
		 players.add(player5);
		 players.add(player6);
		 players.add(player7);

		 Game game = gameController.createGame("testSpiel1", players);
		 
		 /*
		  *  4th round; currentPlayer Player 4;
		  *  Player1 Cards: circus - 3 military points
		  *  				apothecary - compass
		  *  				laboratory - gear
		  *  				library - tablet
		  *  
		  *  Player2 Cards: archeryrange - 2 military points
		  *  				academy - compass
		  *  				observatory - gear
		  *  				school - tablet
		  *  
		  *  Player3 Cards: barracks - 1 military points
		  *  				lodge - compass
		  *  				study - gear
		  *  				scriptorium - tablet
		  *  
		  *  Player4 Cards: stables - 2 military points
		  *  				lodge - compass
		  *  				observatory - gear
		  *  
		  *  Player5 Cards: archeryrange - 2 military points
		  *  				lodge - compass
		  *  				study - gear
		  *  
		  *  Player6 Cards: circus - 3 military points
		  *  				academy - compass
		  *  				study - gear
		  *  
		  *  Player7 Cards: circus - 3 military points
		  *  				apothecary - compass
		  *  				observatory - gear
		  */
		 gameController.createNextRound(game, game.getCurrentGameState());
		 gameController.createNextRound(game, game.getCurrentGameState());
		 gameController.createNextRound(game, game.getCurrentGameState());
		 game.setCurrentPlayer(player4);
		 
		 Card card13 = cardController.getCard("circus"); //3 military points
		 Card card11 = cardController.getCard("archeryrange"); //2 military points
		 Card card10 = cardController.getCard("barracks"); // 1 military point
		 Card card12 = cardController.getCard("stables"); //2 military points
		 
		 Card card1 = cardController.getCard("apothecary"); //compass
		 Card card2 = cardController.getCard("academy"); //commpass
		 Card card3 = cardController.getCard("lodge"); //commpass
		 
		 Card card4 = cardController.getCard("laboratory"); // gear
		 Card card5 = cardController.getCard("observatory"); // gear
		 Card card6 = cardController.getCard("study"); // gear
		 
		 Card card7 = cardController.getCard("library"); // tablet
		 Card card8 = cardController.getCard("school"); // tablet
		 Card card9 = cardController.getCard("scriptorium"); // tablet
		 
		 player1.getBoard().addCard(card13);
		 player1.getBoard().addCard(card1);
		 player1.getBoard().addCard(card4);
		 player1.getBoard().addCard(card7);
		 
		 player2.getBoard().addCard(card11);
		 player2.getBoard().addCard(card2);
		 player2.getBoard().addCard(card5);
		 player2.getBoard().addCard(card8);

		 player3.getBoard().addCard(card10);
		 player3.getBoard().addCard(card3);
		 player3.getBoard().addCard(card6);
		 player3.getBoard().addCard(card9);
		 
		 player4.getBoard().addCard(card12);
		 player4.getBoard().addCard(card3);
		 player4.getBoard().addCard(card5);
		 
		 player5.getBoard().addCard(card11);
		 player5.getBoard().addCard(card3);
		 player5.getBoard().addCard(card6);
		 
		 player6.getBoard().addCard(card13);
		 player6.getBoard().addCard(card2);
		 player6.getBoard().addCard(card6);
		 
		 player7.getBoard().addCard(card13);
		 player7.getBoard().addCard(card1);
		 player7.getBoard().addCard(card5);
		 
		 
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
