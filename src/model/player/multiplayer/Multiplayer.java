package model.player.multiplayer;

import java.util.ArrayList;


import controller.utils.TradeOption;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.Move.Action;

@SuppressWarnings({"javadoc", "all", "PMD"})


/** class for Multiplayer */
public class Multiplayer extends ArtInt{

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
		return null;
	}

	/**
	 * getter for SelectedCard
	 */
	@Override
	public Card getSelectedCard() {
		return null;
	}

	/**
	 * getter for Action
	 */
	@Override
	public Action getAction() {
		return null;
	}

	/**
	 * getter for TradeOption
	 */
	@Override
	public TradeOption getTradeOption() {
		return null;
	}
	
	/**
	 * calculates next Move
	 */
	@Override
	public void calculateNextMove() {}

}
