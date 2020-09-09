package model.player.ai;

import java.io.Serializable;

import controller.utils.TradeOption;
import model.card.Card;

@SuppressWarnings("PMD")

public class Move implements Serializable {

	private Card chosen;
	private Action action;
	private TradeOption tradeOption;
	
	public Move(Card chosen, Action action) {
		this.chosen = chosen;
		this.action = action;
	}
	
	public void setTradeOption(TradeOption tradeOption) {
		this.tradeOption = tradeOption;
	}
	
	public TradeOption getTradeOption() {
		return tradeOption;
	}
	
	public Card getCard() {
		return chosen;
	}
	
	public Action getAction() {
		return action;
	}
	
	public enum Action{
		BUILD,
		PLACE_SLOT,
		SELL;
	}
}