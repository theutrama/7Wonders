package model.board;

import model.card.Resource;

import model.card.ResourceType;

public class AlexandriaBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** create Alexandria board */
	public AlexandriaBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE), new Resource(2, ResourceType.GLASS) };
	}

	@Override
	public void slot2() {

	}

	/**
	 * Gibt alle 4 Ressourcen zurueck, es kann nur eine verwendet werden
	 * 
	 * @return
	 */
	public ResourceType[] resources() {

		ResourceType temp[] = { ResourceType.WOOD, ResourceType.ORE, ResourceType.BRICK, ResourceType.STONE };
		return temp;
	}

}
