package controller;

import org.junit.Test;

import controller.sound.Sound;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * sound controller test
 */
public class SoundControllerTest extends Application {

	/**
	 * sound controller test method
	 */
	@Test
	public void test() {
//		SwingUtilities.invokeLater(() -> { new JFXPanel(); flag.countDown(); });
		launch();
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("start");
		Platform.exit();
	}
}
