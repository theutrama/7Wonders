package model.player.ai;

import java.io.Serializable;

import controller.utils.TradeOption;
import model.card.Card;

/** suppresses PMD warnings */
@SuppressWarnings("PMD")

/** calculates next move for AI */
public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	/** chosen Card of AI */
	private Card chosen;
	/** calculated action for AI */
	private Action action;
	/** calculated trade option for AI */
	private TradeOption tradeOption;
	/** selected mausoleum card */
	private Card halikarnassusCard;

	/** create new move */
	public Move(Card chosen, Action action) {
		this.chosen = chosen;
		this.action = action;
	}

	/** setter for {@link #tradeOption} */
	public void setTradeOption(TradeOption tradeOption) {
		this.tradeOption = tradeOption;
	}

	/**
	 * getter for {@link #tradeOption}
	 * 
	 * @return tradeOption
	 */
	public TradeOption getTradeOption() {
		return tradeOption;
	}

	/**
	 * setter for {@link #halikarnassusCard}
	 * 
	 * @param halikarnassusCard chosen card
	 */
	public void setHalikarnassusCard(Card halikarnassusCard) {
		this.halikarnassusCard = halikarnassusCard;
	}

	/**
	 * getter for {@link #halikarnassusCard}
	 * 
	 * @return chosen card
	 */
	public Card getHalikarnassusCard() {
		return halikarnassusCard;
	}

	/**
	 * getter for {@link #chosen}
	 * 
	 * @return chosen
	 */
	public Card getCard() {
		return chosen;
	}

	/**
	 * getter for {@link #action}
	 * 
	 * @return action
	 */
	public Action getAction() {
		return action;
	}

	/** enum for Action */
	public enum Action {
		OLYMPIA, BUILD, PLACE_SLOT, SELL;
	}
}