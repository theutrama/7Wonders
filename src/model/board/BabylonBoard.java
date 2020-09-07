package model.board;

import model.card.Resource;
import model.card.ResourceType;

/** Babylon Board */
public class BabylonBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;
	/** static list of research types */
	public static ResourceType[] types = new ResourceType[] { ResourceType.TABLET, ResourceType.COMPASS, ResourceType.GEAR };

	/** create Babylon board */
	public BabylonBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.BRICK), new Resource(3, ResourceType.WOOD), new Resource(4, ResourceType.BRICK) };
		this.resource = new Resource(1, ResourceType.BRICK);
	}

	@Override
	public void slot2() {
	}
}
