package controller.exceptions;

import model.card.Card;

public class CardOutOfAgeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CardOutOfAgeException(Card card, int age) {
		super("The card "+card+"("+card.getAge()+") should have the age "+age+"?!");
	}
	
}
