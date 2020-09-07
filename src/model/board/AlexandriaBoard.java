package model.board;

import model.card.Resource;

import model.card.ResourceType;
/** Alexandria Board */
public class AlexandriaBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** create Alexandria board */
	public AlexandriaBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE), new Resource(2, ResourceType.GLASS) };
		this.resource = new Resource(1, ResourceType.GLASS);
	}

	@Override
	public void slot2() {

	}

	/**
	 * Returns all 4 resources, only one can be used
	 * 
	 * @return temp	all 4 resources
	 */
	public ResourceType[] resources() {

		ResourceType temp[] = { ResourceType.WOOD, ResourceType.ORE, ResourceType.BRICK, ResourceType.STONE };
		return temp;
	}

}
