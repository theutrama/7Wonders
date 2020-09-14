package model.player.multiplayer;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import controller.utils.TradeOption;
import main.api.events.EventHandler;
import main.api.events.events.PacketReceiveEvent;
import main.client.PlayerClient;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.Move.Action;
import model.player.multiplayer.packets.PlayerActionPacket;
import model.player.multiplayer.packets.PlayerHalikarnassusPacket;
import model.player.multiplayer.packets.PlayerSelectedCardPacket;
import model.player.multiplayer.packets.PlayerTradeOptionPacket;
import view.gameboard.GameBoardViewController;
import view.multiplayer.lobby.LobbyViewController;

@SuppressWarnings("all")


/** class for Multiplayer */
public class Multiplayer extends ArtInt{
	private static final int timeout = 1000 * 60 * 5;
	
	private Card selectedCard = null;
	private Action action;
	/**
	 * create new Multiplayer
	 * @param name		name of Multiplayer
	 * @param board		WonderBoard for Multiplayer
	 */
	public Multiplayer(String name, WonderBoard board) {
		super(name, board);
	}
	
	public void stop() {
		Main.getSWController().setGame(null);
		LobbyViewController view = null;
		if(Main.primaryStage.getScene().getRoot() instanceof GameBoardViewController)
			((GameBoardViewController)Main.primaryStage.getScene().getRoot()).exit();
		Main.getSWController().getMultiplayerController().close();
		Main.primaryStage.getScene().setRoot(view=new LobbyViewController());
		view.error("Spiel wurde abgebrochen da ein Spieler nicht reagiert hat!");
	}
	
	/**
	 * getter for HalikarnassusCard
	 * @param player	player
	 * @param trash		trash card pile
	 * @param state		GameState
	 */
	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash, GameState state) {
		try {
			PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
			PlayerHalikarnassusPacket packet = (PlayerHalikarnassusPacket) client.createWaitFor(PlayerHalikarnassusPacket.class).getSync(timeout);
			return Main.getSWController().getGame().getCurrentGameState().getTrash().get(packet.getHalikarnassusIndex());
		}catch(RuntimeException e) {
			stop();
		}
		return null;
	}

	/**
	 * getter for SelectedCard
	 */
	@Override
	public Card getSelectedCard() {
		try {
			PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
			PlayerSelectedCardPacket packet = (PlayerSelectedCardPacket) client.createWaitFor(PlayerSelectedCardPacket.class).getSync(timeout);
			this.selectedCard = this.getHand().get(packet.getHandIndex());
			return this.selectedCard;
		}catch(RuntimeException e) {
			stop();
		}
		return null;
	}

	/**
	 * getter for Action
	 */
	@Override
	public Action getAction() {
		try {
			PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
			PlayerActionPacket packet = (PlayerActionPacket) client.createWaitFor(PlayerActionPacket.class).getSync(timeout);
			this.action = packet.getAction();
			return packet.getAction();
		}catch(RuntimeException e) {
			stop();
		}
		return null;
	}

	/**
	 * getter for TradeOption
	 */
	@Override
	public TradeOption getTradeOption() {
		try {
			PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
			PlayerTradeOptionPacket packet = (PlayerTradeOptionPacket) client.createWaitFor(PlayerTradeOptionPacket.class).getSync(timeout);
			
			if(this.action == Action.PLACE_SLOT) {
				return Main.getSWController().getPlayerController().getTradeOptions(this,new ArrayList<>(Arrays.asList(getBoard().getSlotResquirement(getBoard().nextSlot())))).get(packet.getTradeOptionIndex());
			}else {
				return Main.getSWController().getPlayerController().getTradeOptions(this, this.selectedCard.getRequired()).get(packet.getTradeOptionIndex());
			}
		}catch(RuntimeException e) {
			stop();
		}
		return null;
	}
	
	/**
	 * calculates next Move
	 */
	@Override
	public void calculateNextMove() {}

}
