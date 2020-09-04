package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class EphesosBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** create Ephesos baord */
	public EphesosBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.WOOD), new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE) };
	}

	@Override
	public void slot2() {
		player.addCoins(9);
	}

}
