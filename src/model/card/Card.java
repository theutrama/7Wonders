package model.card;

import java.util.ArrayList;

public class Card {

	private int age;

	private String name;

	private CardType type;

	private ArrayList<Resource> producing;

	private ArrayList<Resource> required;

	private ArrayList<Card> dependencies;

	private ArrayList<Effect> effects;
	
	public Card() {
		
	}
	
	public int getAge() {
		return age;
	}
	
	public String getName() {
		return name;
	}
	
	public CardType getType() {
		return type;
	}
	
	public ArrayList<Resource> getProducing() {
		return producing;
	}
	
	public ArrayList<Resource> getRequired() {
		return required;
	}
	
	public ArrayList<Card> getDependencies() {
		return dependencies;
	}
	
	public ArrayList<Effect> getEffects() {
		return effects;
	}

}
