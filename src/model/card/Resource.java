package model.card;

import java.io.Serializable;

public class Resource implements Serializable{
	private static final long serialVersionUID = 2837901052020132762L;
	/** amount of resources produced or required */
	private int quantity;
	/** type of resource */
	private ResourceType type;

	/**
	 * create a resource
	 * @param quantity set {@link #quantity}
	 * @param type set {@link #type}
	 */
	public Resource(int quantity, ResourceType type) {
		this.quantity = quantity;
		this.type = type;
	}
	
	/**
	 * getter for {@link #quantity}
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * getter for {@link #type}
	 * @return resource type
	 */
	public ResourceType getType() {
		return type;
	}

}
