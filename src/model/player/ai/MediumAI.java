package model.player.ai;

import java.util.ArrayList;

import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;

public class MediumAI extends AdvancedAI {
	private static final long serialVersionUID = 1L;

	public MediumAI(String name, WonderBoard board) {
		super(name, board);
	}

	@Override
	protected int evaluate(GameState state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash, GameState state) {
		// TODO Auto-generated method stub
		return null;
	}

}
