package model.card;

public class Resource {

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
