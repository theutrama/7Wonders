package model.board;

import model.card.Resource;
import model.player.Player;
import model.Game;
import model.card.ResourceType;

public class EphesosBoard extends WonderBoard {

	public EphesosBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.WOOD), new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE)};
	}
	
	@Override
	public void slot2() {
		player.addCoins(9);
	}

}
