package application;

import controller.SevenWondersController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.menu.MainMenuViewController;

public class Main extends Application {
	public static boolean TEST = false;

	// public static ClassLoader cldr = Main.class.getClassLoader();
	public static Stage primaryStage;
	private static SevenWondersController swController;

	public static final String DEFAULT_PATH = "view/images/";
	public static final String TOKENS_PATH = DEFAULT_PATH + "tokens/";
	public static final String BOARD_PATH = DEFAULT_PATH + "boards/";
	public static final String CARDS_PATH = DEFAULT_PATH + "cards/";
	public static final String SOUNDS_PATH = "controller/sound/sounds/";

	@Override
	public void start(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(Main.class.getClassLoader().getResourceAsStream(DEFAULT_PATH + "7wonders_small.png")));

		Main.primaryStage = primaryStage;
		swController = SevenWondersController.getInstance();
		swController.getIOController().loadRanking();

		try {
			MainMenuViewController mainMenuViewController = new MainMenuViewController();
			Scene scene = new Scene(mainMenuViewController, 1000, 800);
			primaryStage.setScene(scene);
			primaryStage.setFullScreenExitHint("");
			primaryStage.show();
			primaryStage.setFullScreen(true);
			primaryStage.setOnCloseRequest(event -> { swController.getIOController().saveRanking(); System.exit(0); });
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
