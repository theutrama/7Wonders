package model.card;

import java.io.Serializable;

public class Resource implements Serializable{
	private static final long serialVersionUID = 2837901052020132762L;

	private int quantity;

	private ResourceType type;

	public Resource(int quantity, ResourceType type) {
		this.quantity = quantity;
		this.type = type;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public ResourceType getType() {
		return type;
	}

}
