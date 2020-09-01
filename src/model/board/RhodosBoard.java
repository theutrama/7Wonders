package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class RhodosBoard extends WonderBoard {

	public RhodosBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.WOOD), new Resource(3, ResourceType.BRICK), new Resource(4, ResourceType.ORE)};
	}
	
	@Override
	public void slot2() {
		
	}

}
