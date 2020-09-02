package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class OlympiaBoard extends WonderBoard {

	public OlympiaBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.BRICK), new Resource(3, ResourceType.ORE), new Resource(2, ResourceType.CLOTH)};
	}
	
	@Override
	public void slot2() {
		
	}

}
