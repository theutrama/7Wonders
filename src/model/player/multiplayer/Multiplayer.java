package model.player.multiplayer;

import java.util.ArrayList;

import controller.utils.TradeOption;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.Move.Action;

public class Multiplayer extends ArtInt{

	public Multiplayer(String name, WonderBoard board) {
		super(name, board);
	}

	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash, GameState state) {
		return null;
	}

	@Override
	public Card getSelectedCard() {
		return null;
	}

	@Override
	public Action getAction() {
		return null;
	}

	@Override
	public TradeOption getTradeOption() {
		return null;
	}
	
	@Override
	public void calculateNextMove() {}

}
