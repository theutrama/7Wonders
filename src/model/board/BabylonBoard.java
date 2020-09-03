package model.board;

import java.util.ArrayList;

import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;

public class BabylonBoard extends WonderBoard {
	private static ResourceType[] types = new ResourceType[]{ResourceType.TABLET, ResourceType.COMPASS, ResourceType.GEAR};

	public BabylonBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.BRICK), new Resource(3, ResourceType.WOOD), new Resource(4, ResourceType.BRICK)};
	}
	
	@Override
	public void slot2() {
		// drei verschiedene = 7 SIEGPUNKTE
		// 1 = 1 PUNKT
		// 2 = 4 PUNKT
		// 3 = 9 PUNKT
		
		
		
	}
	
	private void getBestSciencePoints(Player player) {
//		ArrayList<Card>
	}
}
