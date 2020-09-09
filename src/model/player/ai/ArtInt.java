package model.player.ai;

import java.util.ArrayList;

import application.Main;
import controller.PlayerController;
import controller.utils.BuildCapability;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Effect;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;
import model.player.ai.Move.Action;

@SuppressWarnings("PMD.UnusedLocalVariable")

/** Artificial Intelligence for SevenWonders */
public class ArtInt extends Player {
	/** level of skill */
	private Difficulty difficulty;
	private PlayerController pcon;

	/**
	 * creates a new AI using the given board
	 * @param difficulty level
	 * @param board wonder board
	 */
	public ArtInt(Difficulty difficulty, WonderBoard board) {
		this("KI",difficulty,board);
	}
	
	/**
	 * creates a new AI using the given board
	 * @param name custom KI Name
	 * @param difficulty level
	 * @param board wonder board
	 */
	public ArtInt(String name, Difficulty difficulty, WonderBoard board) {
		super(name + " - " + difficulty.toString(), board);
		this.difficulty = difficulty;
		this.pcon = Main.getSWController().getPlayerController();
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public Action getAction() {
		return null;
	}
	
	public int choseCard() {
		return 0;
	}
	
	public int AlphaBeta(int tiefe, int alpha, int beta) {
		if(tiefe == 0)return 0;
		ArrayList<Move> moves = generateMoves();
		
		for(Move move : moves) {
			doMove(move);
		}
		
		return 0;
	}
	
	public int doMove(Move move) {
		Card card = move.getCard();
		Action action = move.getAction();
		
		switch(action) {
		case BUILD:
			BuildCapability capa = pcon.canBuild(this, card);
			
			int v = 0;
			switch(capa) {
			case FREE:
				v +=2;
			case OWN_RESOURCE:
				v += 1;
			case TRADE: 
				
				switch(card.getType()) {
				//CIVIL
				case BLUE: 
					v+= card.getvPoints();
					break;
				//RESOURCE
				case BROWN: 
				case GRAY: 
					WonderBoard board = getBoard();
					int next_slot = board.nextSlot();
					
					if(next_slot != -1) {
						Resource requirement = board.getSlotResquirement(next_slot);
						
						for(Resource rs : card.getProducing()) {
							if(rs.getType() == requirement.getType()) {
								v+=requirement.getQuantity();
							}
						}
					}
					break;
				//SCIENCE
				case GREEN: break;
				//GUILD
				case PURPLE: break;
				//MILITARY
				case RED:
					Player leftN = pcon.getLeftNeighbour(this);
					Player rightN = pcon.getRightNeighbour(this);
					
					int military = pcon.getMilitaryPoints(this);
					int left_mili = pcon.getMilitaryPoints(leftN);
					int right_mili = pcon.getMilitaryPoints(rightN);
					
					if(left_mili >= military || right_mili >= military) {
						v+=card.getProducing().get(0).getQuantity();
					}
					break;
				//TRADING
				case YELLOW: 
					
//					Game copy = (Game) Main.getSWController().getGame().cl
					ArrayList<Effect> effects = card.getEffects();
					for(Effect effect : effects) {
						switch(effect.getType()) {
						case AT_MATCH_END: break;
						case WHEN_PLAYED: 
							
//							effect.run(player, game, twoPlayers);
							
							
							break;
						}
					}
					
					
					break;
				}
				break;
			case NONE: return Integer.MIN_VALUE;
			}
			break;
		case PLACE:
		
			break;
		case SELL: 
			
			break;
		}
		
		
		return 0;
	}
	
	public ArrayList<Move> generateMoves() {
		ArrayList<Move> list = new ArrayList<Move>();
		
		for(int i = 0; i < this.getHand().size(); i++) {
			for(Action action : Move.Action.values()) {
				list.add(new Move(this.getHand().get(i), action));
			}
		}
		
		return list;
	}
}
