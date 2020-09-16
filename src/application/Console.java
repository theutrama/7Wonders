package application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.console.ConsoleViewController;

public class Console {

	private static Stage stage;
	private static ConsoleViewController console;
	private static double posX, posY;

	/**
	 * display a new line of text in a console window
	 * 
	 * @param text text
	 */
	public synchronized static void log(String text) {
		Platform.runLater(() -> {
			if (console == null) {
				console = new ConsoleViewController();
				stage = new Stage();
				stage.getIcons().add(new Image(Main.class.getClassLoader().getResourceAsStream(Main.DEFAULT_PATH + "7wonders_small.png")));
				stage.setAlwaysOnTop(true);
				console.setOnMousePressed(event -> { posX = event.getSceneX(); posY = event.getSceneY(); });
				console.setOnMouseDragged(event -> { stage.setX(event.getSceneX() - posX); stage.setY(event.getSceneY() - posY); });
				Scene scene = new Scene(console, 400, 300);
				scene.setFill(Color.TRANSPARENT);
				stage.setScene(scene);
				stage.setTitle("Console");
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.show();
			}
			console.addConsoleText(text);
		});
	}

	/** closes the console */
	public static void exit() {
		if (stage != null) {
			stage.close();
			stage = null;
			console = null;
		}
	}
}
