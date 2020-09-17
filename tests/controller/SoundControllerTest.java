package controller;

import org.junit.Test;

import controller.sound.Sound;
import javafx.embed.swing.JFXPanel;

/**
 * sound controller test
 */
public class SoundControllerTest {
	/**
	 * sound controller test method
	 */
	@Test
	public void test() {
		new JFXPanel();
		SoundController controller = new SoundController();
		controller.isMuted();
		controller.mute();
		controller.play(Sound.COIN);
		controller.play(Sound.COIN, true);
		controller.isPlaying("coin");
		controller.mute();
		controller.play(Sound.COIN);
		controller.stopAll();
		controller.play(Sound.COIN, true);
		controller.stop(Sound.COIN);
		controller.setVolume(1);
	}
}
