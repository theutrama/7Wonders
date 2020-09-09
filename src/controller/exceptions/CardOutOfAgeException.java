package controller.exceptions;

import model.card.Card;

/** throws exception if Card is out of age */
public class CardOutOfAgeException extends Exception{

	/** serial version UID */
	private static final long serialVersionUID = 1L;

	/** creates new CarsOutOfAgeException */
	public CardOutOfAgeException(Card card, int age) {
		super("The card "+card+"("+card.getAge()+") should have the age "+age+"?!");
	}
	
}
