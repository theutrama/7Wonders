package model.player.ai;

import java.io.Serializable;

import controller.utils.TradeOption;
import model.card.Card;

/** suppresses PMD warnings */
@SuppressWarnings("PMD")

/** calculates next move for AI */
public class Move implements Serializable {

	/** chosen Card of AI */
	private Card chosen;
	/** calculated action for AI */
	private Action action;
	/** calculated trade option for AI */
	private TradeOption tradeOption;
	
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
	 * @return tradeOption
	 */
	public TradeOption getTradeOption() {
		return tradeOption;
	}
	
	/**
	 * getter for {@link #chosen}
	 * @return chosen
	 */
	public Card getCard() {
		return chosen;
	}
	
	/**
	 * getter for {@link #action}
	 * @return action
	 */
	public Action getAction() {
		return action;
	}
	
	/** enum for Action */
	public enum Action{
		BUILD,
		PLACE_SLOT,
		SELL;
	}
}