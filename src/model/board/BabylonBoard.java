package model.board;

import model.card.Card;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;

public class BabylonBoard extends WonderBoard {
	public static ResourceType[] types = new ResourceType[]{ResourceType.TABLET, ResourceType.COMPASS, ResourceType.GEAR};

	public BabylonBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.BRICK), new Resource(3, ResourceType.WOOD), new Resource(4, ResourceType.BRICK)};
	}
	
	@Override
	public void slot2() {
		// 1 = 1 PUNKT
		// 2 = 4 PUNKT
		// drei verschiedene = 7 SIEGPUNKTE
		// 3 = 9 PUNKT
		
		
		
	}
}
