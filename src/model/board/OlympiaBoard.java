package model.board;

import model.card.Resource;
import model.card.ResourceType;

/** Olympia Board */
public class OlympiaBoard extends WonderBoard {
	private static final long serialVersionUID = 1L;

	/** create Olympia board */
	public OlympiaBoard() {
		super();
		slotRequirements = new Resource[] { new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.WOOD), new Resource(2, ResourceType.PAPYRUS) };
		this.resource = new Resource(1, ResourceType.WOOD);
	}

	@Override
	public void slot2() {

	}

}
