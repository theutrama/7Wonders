package model.card;

import java.util.ArrayList;

public class Card {

	private int age;

	private String name;

	private CardType type;

	private ArrayList<Resource> producing;

	private ArrayList<Resource> required;

	private ArrayList<Card> dependencies;

	private ArrayList<Effect> effect;

}
