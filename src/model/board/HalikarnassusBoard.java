package model.board;

import model.card.Resource;
import model.card.ResourceType;

/** Halikarnassus Board */
public class HalikarnassusBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** create Halikarnassus board */
	public HalikarnassusBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.BRICK), new Resource(3, ResourceType.ORE), new Resource(2, ResourceType.CLOTH) };
		this.resource = new Resource(1, ResourceType.CLOTH);
	}

	@Override
	public void slot2() {
		player.setMausoleum(true);
	}

}
