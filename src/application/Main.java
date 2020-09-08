package application;

import java.io.File;

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

	public static Stage primaryStage;
	private static SevenWondersController swController;
	private static SoundController soundController;
	
	public static final String DEFAULT_PATH = "src"+File.separator + "view" + File.separator + "images" + File.separator;
	public static final String TOKENS_PATH = DEFAULT_PATH + "tokens" + File.separator;
	public static final String BOARD_PATH = DEFAULT_PATH + "boards" + File.separator;
	public static final String CARDS_PATH = DEFAULT_PATH + "cards" + File.separator;
	public static final String SOUNDS_PATH = "src" + File.separator + "controller" + File.separator + "sound" + File.separator + "sounds" + File.separator;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("../view/images/7wonders_small.png")));

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
			primaryStage.setOnCloseRequest(event -> swController.getIOController().saveRanking());
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
