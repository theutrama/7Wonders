package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class RhodosBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** military Points added by slot two */
	private int militaryPoints = 2;

	/** create Rhodos board */
	public RhodosBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.WOOD), new Resource(3, ResourceType.BRICK), new Resource(4, ResourceType.ORE) };
		this.resource = new Resource(1, ResourceType.ORE);
	}

	@Override
	public void slot2() {
	}

	/**
	 * zero if slot 2 is not filled, {@link #militaryPoints} otherwise
	 * 
	 * @return military points gained by this board
	 */
	public int getMilitaryPoints() {
		if (this.isFilled(2))
			return this.militaryPoints;
		else
			return 0;
	}
}
