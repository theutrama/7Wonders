package controller;

import org.junit.Before;
import org.junit.Test;

/** test for WonderBoard Controller */
public class WonderBoardControllerTest {
	private SevenWondersController swController;
	private WonderBoardController wbController;
	
	/** sets up test for WonderBoard Controller */
	@Before
	public void setUp() {
		swController = new SevenWondersController();
		wbController = swController.getWonderBoardController();
	}
	
	/**
	 * tests if cards are placed correctly on board
	 */
	@Test
	public void WonderBoardTest() {
		
	}
	
}
