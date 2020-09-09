package model.player.ai;

import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import controller.PlayerController;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import model.GameState;
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
	private Move next;

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
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public Move getMove() {
		return next;
	}
	
	public Action getAction() {
		return next.getAction();
	}
	
	public Card getChosenCard() {
		return next.getCard();
	}
	
	public Move findMove() {
		ArrayList<Move> moves = generateMoves();
		
		int v = 0;
		int value = doMove(moves.get(0));
		Move best = moves.get(0);
		Move move;
		for(int i = 1; i < moves.size(); i++) {
			move = moves.get(i);
			v = doMove(move);
			
			if(v > value) {
				value = v;
				best = move;
				System.out.println("FOUND BEST "+v+" "+move.getCard()+":"+move.getAction().name());
			}
		}
		
		next = best;
		return best;
	}
	
	public int doMove(Move move) {
		PlayerController pcon = Main.getSWController().getPlayerController();
		Card card = move.getCard();
		BuildCapability capa;
		Action action = move.getAction();
		int v = 0;
		
		switch(action) {
		case BUILD:
			capa = pcon.canBuild(this, card);
			
			switch(capa) {
			case FREE:
				v +=2;
			case TRADE: 
				//MUSS NOCH GEMACHT WERDEN!

				//MUSS NOCH GEMACHT WERDEN!
				ArrayList<TradeOption> trade = pcon.getTradeOptions(this, card.getRequired());
				
				if(trade.isEmpty()) {
					return Integer.MIN_VALUE;
				}else {
					move.setTradeOption(trade.get(0));
					v+=2;
				}
			case OWN_RESOURCE:
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
				case GREEN: 
					
					
					break;
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
					GameState copy = Main.getSWController().getGame().getCurrentGameState().deepClone();
					ArrayList<Effect> effects = card.getEffects();
					if(effects!=null) {
						for(Effect effect : effects) {
							switch(effect.getType()) {
							case AT_MATCH_END: 
							case WHEN_PLAYED: 
								Player player = copy.getPlayer();
								
								//Do Effect!
								effect.run(player, copy, copy.isTwoPlayers());
								
								//Check changes
								v += player.getVictoryPoints() - getVictoryPoints();
								v += player.getCoins() - getCoins();
								break;
							}
						}
					}
					
					
					break;
				}
				break;
			case NONE: return Integer.MIN_VALUE;
			}
			break;
		case PLACE_SLOT:
			WonderBoard board = getBoard();
			int next = board.nextSlot();
			
			if(next!=-1) {
				Resource requirement = board.getSlotResquirement(next);
				capa = pcon.hasResources(this, new ArrayList<Resource>(Arrays.asList(requirement)));
				
				switch(capa) {
				case FREE: 
					v+=5;
				case OWN_RESOURCE:
					v+=4;
				case TRADE: 
					//MUSS NOCH GEMACHT WERDEN!
					ArrayList<TradeOption> trade = pcon.getTradeOptions(this, card.getRequired());
					
					if(trade.isEmpty()) {
						return Integer.MIN_VALUE;
					}else {
						move.setTradeOption(trade.get(0));
						v+=2;
					}
					
					break;
				case NONE: return Integer.MIN_VALUE;
				}
			}
			break;
		case SELL: 
			v+=2;
			break;
		}
		
		
		return v;
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
