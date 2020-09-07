package controller;

import org.junit.Before;

public class WonderBoardControllerTest {
	private SevenWondersController swController;
	private WonderBoardController wbController;
	
	@Before
	public void setUp() {
		swController = new SevenWondersController();
		wbController = swController.getWonderBoardController();
	}
	
	// what to test on wonderBoard ?
	// resources created by board correctly set (resources, military, guilds, civil, research) ?
	// slots filled ?
	// 
	
}
