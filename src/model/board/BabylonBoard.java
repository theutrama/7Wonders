package model.board;

import model.card.Card;
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
		// 1 = 1 PUNKT
		// 2 = 4 PUNKT
		// drei verschiedene = 7 SIEGPUNKTE
		// 3 = 9 PUNKT
		
		
		
	}
	
	public static void main(String[] args) {
		System.out.println("VICTORY POINTS: "+getBestSciencePoints1(new ResourceType[] {
				ResourceType.TABLET,
				ResourceType.COMPASS,
				ResourceType.GEAR,
				ResourceType.GEAR,
				}));
	}
	
	private static int getBestSciencePoints1(ResourceType[] rs) {
		ResourceType[] types = new ResourceType[]{ResourceType.TABLET, ResourceType.COMPASS, ResourceType.GEAR};
		
		
		int victory_points = 0;
		int[] amount = new int[] {0,0,0};
		for(int i = 0; i < BabylonBoard.types.length; i++) {
			for(ResourceType card : rs) {
				if(card == types[i]) {
					System.out.println("("+BabylonBoard.types.length+") I:"+i);
					amount[i]++;
				}
			}
		}
		
		for(int i = 0; i < amount.length; i++) {
			victory_points = amount[i] * amount[i];
		}
		
		while(amount[0] >= 1 && amount[1] >=1 && amount[2] >=1){
			victory_points += 7;
			amount[0]--;
			amount[1]--;
			amount[2]--;
		}
		
		return victory_points;
	}
	
	private int getBestSciencePoints(Player player) {
		int victory_points = 0;
		int[] amount = new int[] {0,0,0};
		for(int i = 0; i < types.length; i++) {
			for(Card card : player.getBoard().getResearch()) {
				if(card.getScienceType() == types[i]) {
					amount[i]++;
				}
			}
		}
		
		for(int i = 0; i < amount.length; i++) {
			victory_points = amount[i] * amount[i];
		}
		
		while(amount[0] >= 1 && amount[1] >=1 && amount[2] >=1){
			victory_points += 7;
			amount[0]--;
			amount[1]--;
			amount[2]--;
		}
		
		return victory_points;
	}
}
