package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import controller.SevenWondersController;
import controller.SoundController;
import controller.sound.Sound;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.menu.MainMenuViewController;

public class Main extends Application {
	public static boolean TEST = false;

	public static ClassLoader cldr;
	public static Stage primaryStage;
	private static SevenWondersController swController;
	private static SoundController soundController;
	
	public static final String DEFAULT_PATH = "view/images/";
	public static final String TOKENS_PATH = DEFAULT_PATH + "tokens/";
	public static final String BOARD_PATH = DEFAULT_PATH + "boards/";
	public static final String CARDS_PATH = DEFAULT_PATH + "cards/";
	public static final String SOUNDS_PATH = "controller/sound/sounds/";
	
	@Override
	public void start(Stage primaryStage) {
		cldr = this.getClass().getClassLoader();
		primaryStage.getIcons().add(new Image(cldr.getResourceAsStream(DEFAULT_PATH+"7wonders_small.png")));

		Main.primaryStage = primaryStage;
		swController = SevenWondersController.getInstance();
		soundController = swController.getSoundController();
		swController.getIOController().loadRanking();

		try {
			MainMenuViewController mainMenuViewController = new MainMenuViewController();
			Scene scene = new Scene(mainMenuViewController, 1000, 800);
			primaryStage.setScene(scene);
			primaryStage.setFullScreenExitHint("");
			primaryStage.show();
			primaryStage.setFullScreen(true);
			primaryStage.setOnCloseRequest(event -> {swController.getIOController().saveRanking(); System.exit(0);});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SevenWondersController getSWController() {
		return swController;
	}

	public static void setSwController(SevenWondersController swController) {
		Main.swController = swController;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
