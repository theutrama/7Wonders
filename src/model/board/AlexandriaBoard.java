package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class AlexandriaBoard extends WonderBoard {

	public AlexandriaBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE), new Resource(2, ResourceType.GLASS)};
	}

	@Override
	public void slot2() {
		
	}

}
