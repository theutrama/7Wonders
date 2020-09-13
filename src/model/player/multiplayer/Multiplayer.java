package model.player.multiplayer;

import java.util.ArrayList;

import application.Main;
import controller.utils.TradeOption;
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

@SuppressWarnings("all")


/** class for Multiplayer */
public class Multiplayer extends ArtInt{
	private static final int timeout = 1000 * 60 * 5;
	
	private Card selectedCard = null;
	/**
	 * create new Multiplayer
	 * @param name		name of Multiplayer
	 * @param board		WonderBoard for Multiplayer
	 */
	public Multiplayer(String name, WonderBoard board) {
		super(name, board);
	}

	/**
	 * getter for HalikarnassusCard
	 * @param player	player
	 * @param trash		trash card pile
	 * @param state		GameState
	 */
	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash, GameState state) {
		PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
		PlayerHalikarnassusPacket packet = (PlayerHalikarnassusPacket) client.createWaitFor(PlayerHalikarnassusPacket.class).getSync(timeout);
		return Main.getSWController().getGame().getCurrentGameState().getTrash().get(packet.getHalikarnassusIndex());
	}

	/**
	 * getter for SelectedCard
	 */
	@Override
	public Card getSelectedCard() {
		PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
		PlayerSelectedCardPacket packet = (PlayerSelectedCardPacket) client.createWaitFor(PlayerSelectedCardPacket.class).getSync(timeout);
		return this.getHand().get(packet.getHandIndex());
	}

	/**
	 * getter for Action
	 */
	@Override
	public Action getAction() {
		PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
		PlayerActionPacket packet = (PlayerActionPacket) client.createWaitFor(PlayerActionPacket.class).getSync(timeout);
		return packet.getAction();
	}

	/**
	 * getter for TradeOption
	 */
	@Override
	public TradeOption getTradeOption() {
		PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
		PlayerTradeOptionPacket packet = (PlayerTradeOptionPacket) client.createWaitFor(PlayerTradeOptionPacket.class).getSync(timeout);
		return Main.getSWController().getPlayerController().getTradeOptions(this, this.selectedCard.getRequired()).get(packet.getTradeOptionIndex());
	}
	
	/**
	 * calculates next Move
	 */
	@Override
	public void calculateNextMove() {}

}
