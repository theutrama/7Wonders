package controller;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import controller.sound.Sound;

public class SoundControllerTest extends ApplicationTest {
	
	/** test the sound controller */
	@Test
	public void test() {
		SoundController controller = new SoundController();
		controller.isMuted();
		controller.mute();
		controller.play(Sound.COIN);
		controller.play(Sound.COIN, true);
		controller.mute();
		controller.play(Sound.COIN);
		controller.stop(Sound.COIN);
		controller.play(Sound.COIN, true);
		controller.play(Sound.BACKGROUND_MENU);
		controller.setVolume(0);
		controller.stopAll();
	}

}
