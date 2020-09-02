package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class HalikarnassusBoard extends WonderBoard {
	
	public HalikarnassusBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.WOOD), new Resource(2, ResourceType.PAPYRUS)};
	}

	@Override
	public void slot2() {
		
	}

}
