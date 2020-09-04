package model.card;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable {
	/** age */
	private int age;
	/** displayed name */
	private String name;
	/** internal name */
	private String internalName;
	/** card type */
	private CardType type;
	/** the scienece type, NONE if {@link #type} is not {@link CardType#GREEN} */
	private ResourceType scienceType;
	/** list of resources this card produces */
	private ArrayList<Resource> producing;
	/** list of resources required to build this card */
	private ArrayList<Resource> required;
	/** list of cards that have to be built to build this card for free */
	private String[] dependencies;
	/** effects on the player */
	private ArrayList<Effect> effects;

	/**
	 * create a card
	 * 
	 * @param age          age
	 * @param name         diplayed name
	 * @param internalName name
	 * @param type         card type
	 * @param producing    sets {@link #producing}
	 * @param required     sets {@link #required}
	 * @param dependencies sets {@link #dependencies}
	 * @param effects      sets {@link #effects}
	 */
	public Card(int age, String name, String internalName, CardType type, ArrayList<Resource> producing, ArrayList<Resource> required, String[] dependencies,
			ArrayList<Effect> effects) {
		this(ResourceType.NONE, age, name, internalName, type, producing, required, dependencies, effects);
	}

	/**
	 * create a science card
	 * 
	 * @param scienceType  sciene type
	 * @param age          age
	 * @param name         diplayed name
	 * @param internalName name
	 * @param type         card type
	 * @param producing    sets {@link #producing}
	 * @param required     sets {@link #required}
	 * @param dependencies sets {@link #dependencies}
	 * @param effects      sets {@link #effects}
	 */
	public Card(ResourceType scienceType, int age, String name, String internalName, CardType type, ArrayList<Resource> producing, ArrayList<Resource> required,
			String[] dependencies, ArrayList<Effect> effects) {
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

	/**
	 * create new card equal to an existing card
	 * 
	 * @param toCopy existing card
	 */
	public Card(Card toCopy) {
		this.age = toCopy.age;
		this.scienceType = toCopy.scienceType;
		this.name = toCopy.name;
		this.internalName = toCopy.internalName;
		this.type = toCopy.type;
		this.producing = toCopy.producing;
		this.required = toCopy.required;
		this.dependencies = toCopy.dependencies;
		this.effects = toCopy.effects;
	}

	/**
	 * getter for pathname of the image of this card
	 * 
	 * @return the pathname to an image file
	 */
	public String getImage() {
		return "src" + File.separator + "view" + File.separator + "images" + File.separator + "cards" + File.separator + internalName.toLowerCase() + ".png";
	}

	/**
	 * used to check if a card produces a specified resource
	 * 
	 * @param type resource type
	 * @return the amount of resources produced by this card
	 */
	public int isProducing(ResourceType type) {
		for (Resource rs : getProducing())
			if (rs.getType() == type)
				return rs.getQuantity();
		return 0;
	}

	/**
	 * getter for {@link #scienceType}
	 * 
	 * @return science type
	 */
	public ResourceType getScienceType() {
		return this.scienceType;
	}

	/**
	 * determine the science ability
	 * 
	 * @return {@link #scienceType} not equal to {@link ResourceType#NONE}
	 */
	public boolean isScienceCard() {
		return getScienceType() != ResourceType.NONE;
	}

	/**
	 * getter for {@link #age}
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * getter for {@link #name}
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for {@link #type}
	 * @return card type
	 */
	public CardType getType() {
		return type;
	}

	/**
	 * getter for {@link #producing}
	 * @return produced resources
	 */
	public ArrayList<Resource> getProducing() {
		return producing;
	}

	/**
	 * getter for {@link #required}
	 * @return required resources
	 */
	public ArrayList<Resource> getRequired() {
		return required;
	}

	/**
	 * getter for {@link #dependencies}
	 * @return card dependencies
	 */
	public String[] getDependencies() {
		return dependencies;
	}

	/**
	 * getter for {@link #effects}
	 * @return effect list
	 */
	public ArrayList<Effect> getEffects() {
		return effects;
	}

	/**
	 * getter for {@link #internalName}
	 * @return internal name
	 */
	public String getInternalName() {
		return internalName;
	}

}
