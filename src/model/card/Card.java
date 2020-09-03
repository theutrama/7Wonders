package model.card;

import java.util.ArrayList;

public class Card implements Cloneable {

	private int age;

	private String name;
	private String internalName;

	private CardType type;
	private ResourceType scienceType;

	private ArrayList<Resource> producing;
	private ArrayList<Resource> required;
	private String[] dependencies;
	private ArrayList<Effect> effects;

	public Card(int age, String name, String internalName, CardType type, ArrayList<Resource> producing, ArrayList<Resource> required,
			String[] dependencies, ArrayList<Effect> effects) {
		this(ResourceType.NONE, age, name, internalName, type, producing, required, dependencies, effects);
	}

	public Card(ResourceType scienceType, int age, String name, String internalName,CardType type, ArrayList<Resource> producing,
			ArrayList<Resource> required, String[] dependencies, ArrayList<Effect> effects) {
		this.age = age;
		this.scienceType = scienceType;
		this.name = name;
		this.internalName = internalName;
		this.type = type;
		this.producing = producing;
		this.required = required;
		this.dependencies = dependencies;
		this.effects = effects;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	
	public int isProducing(ResourceType type) {
		for(Resource rs : getProducing())
			if(rs.getType() == type)return rs.getQuantity();
		return 0;
	}

	public ResourceType getScienceType() {
		return this.scienceType;
	}

	public boolean isScienceCard() {
		return getScienceType() != ResourceType.NONE;
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

	public String[] getDependencies() {
		return dependencies;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

}
