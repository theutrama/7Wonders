package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class GizahBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** create Gizah board */
	public GizahBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.STONE), new Resource(3, ResourceType.WOOD), new Resource(4, ResourceType.STONE) };
	}

	@Override
	public void slot2() {
		player.addVictoryPoints(5);
	}

}
