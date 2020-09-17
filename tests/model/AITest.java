package model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import controller.SevenWondersController;
import controller.SevenWondersFactory;
import model.board.WonderBoard;
import model.card.Card;
import model.card.CardType;
import model.card.ResourceType;
import model.player.ai.EasyAI;
import model.player.ai.HardAI;
import model.player.ai.MediumAI;

/** tests the Artificial Intelligence */
public class AITest {

	private GameState state;

	/** generate game */
	@Before
	public void setup() {
		SevenWondersController controller = SevenWondersFactory.create();
		state = controller.getGame().getCurrentGameState();
		while (state.getPlayers().size() > 2)
			state.getPlayers().remove(2);
	}

	/** do AI test */
	@Test
	public void testAdvancedAI() {
		WonderBoard board = state.getPlayers().get(0).getBoard();
		
		state.setRound(6);
		testAll(board);
		
		for (int i = 0; i < 3; i++)
			state.getTrash().add(new Card(ResourceType.GEAR, 1, "sample card", "sample card", CardType.GREEN, null, null, null, null));
		
		state.setRound(1);
		testAll(board);
		state.setRound(6);
		testAll(board);
		state.setAge(2);
		testAll(board);
		state.setAge(3);
		testAll(board);
	}

	/**
	 * test all AIs
	 * 
	 * @param board
	 */
	private void testAll(WonderBoard board) {
		HardAI hard = new HardAI("hard ai", board);
		hard.setMausoleum(true);
		hard.setOlympiaUsed(false);
		hard.setHand(state.getPlayers().get(0).getHand());
		state.getPlayers().remove(0);
		board.setPlayer(hard);
		state.getPlayers().add(0, hard);
		hard.calculateNextMove();
		hard.getAction();
		hard.getBoard().setAgeOfSlotCards(0, 0);
		hard.calculateNextMove();
		hard.getAction();
		hard.getBoard().setAgeOfSlotCards(1, 1);
		hard.calculateNextMove();
		hard.getAction();
		hard.getHalikarnassusCard(hard, state.getTrash(), state);
		hard.getSelectedCard();
		hard.getTradeOption();

		MediumAI medium = new MediumAI("medium", board);
		medium.setHand(state.getPlayers().get(0).getHand());
		state.getPlayers().remove(0);
		board.setPlayer(medium);
		state.getPlayers().add(0, medium);
		medium.calculateNextMove();
		medium.getAction();
		medium.getHalikarnassusCard(medium, state.getTrash(), state);
		medium.getSelectedCard();
		medium.getTradeOption();

		EasyAI easy = new EasyAI("medium", board);
		easy.setHand(state.getPlayers().get(1).getHand());
		state.getPlayers().remove(1);
		board.setPlayer(easy);
		state.getPlayers().add(1, easy);
		easy.calculateNextMove();
		easy.getAction();
		easy.getHalikarnassusCard(easy, state.getTrash(), state);
		easy.getSelectedCard();
		easy.getTradeOption();
	}

	/**
	 * creates a new ArrayList that contains the specified objects
	 * 
	 * @param <T>     type
	 * @param objects list of objects
	 * @return Arraylist that contains all objects
	 */
	@SafeVarargs
	public static <T> ArrayList<T> createList(T... objects) {
		return new ArrayList<>(Arrays.asList(objects));
	}
}
