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
	
	public Card(int age, String name, CardType type, ArrayList<Resource> producing, ArrayList<Resource> required, ArrayList<Card> dependencies, ArrayList<Effect> effects) {
		this.age = age;
		this.name = name;
		this.type = type;
		this.producing = producing;
		this.required = required;
		this.dependencies = dependencies;
		this.effects = effects;
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
