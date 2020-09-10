package model.player.ai;

import controller.utils.TradeOption;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;
import model.player.ai.Move.Action;

/** Artificial Intelligence for SevenWonders */
public abstract class ArtInt extends Player {
	private static final long serialVersionUID = 1L;
	
	/** set by {@link #calculateNextMove()}, contains the next generated move to be executed */
	protected Move next;

	/**
	 * create AI
	 * @param name name of this player
	 * @param board assigned board
	 */
	public ArtInt(String name, WonderBoard board) {
		super(name, board);
		// TODO Auto-generated constructor stub
	}

	/**
	 * getter for action of next
	 * 
	 * @return next.getAction()
	 */
	public Action getAction() {
		return next.getAction();
	}

	/**
	 * getter for chosen Card of next
	 * 
	 * @return next.getCard()
	 */
	public Card getSelectedCard() {
		return next.getCard();
	}

	/**
	 * get chosen Card
	 * 
	 * @return chosen card
	 */
	public Card getHalikarnassusCard() {
		return next.getHalikarnassusCard();
	}

	/**
	 * get chosen trade
	 * 
	 * @return trade
	 */
	public TradeOption getTradeOption() {
		return next.getTradeOption();
	}

	/**
	 * sets {@link #next} to the next move
	 */
	public abstract void calculateNextMove();
}
