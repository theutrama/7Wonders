package application;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.console.ConsoleViewController;

public class Console {

	private static Stage stage;
	private static ConsoleViewController console;
	private static final Rectangle2D SCREEN_BOUNDS= Screen.getPrimary()
            .getVisualBounds();
    private static double posX;
	private static double posY;

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
				Scene scene = new Scene(console, 400, 300);
				scene.setFill(Color.TRANSPARENT);
				stage.setScene(scene);
				stage.setTitle("Console");
				stage.initStyle(StageStyle.TRANSPARENT);
				console.addEventFilter(MouseEvent.MOUSE_PRESSED,
			            event -> {
		            posX = event.getSceneX();
		            posY = event.getSceneY();
		            System.out.println("SceneX: "+posX+", SceneY:"+posY);
		        });

		        console.setOnMouseDragged((MouseEvent d) -> {
		            //Ensures the stage is not dragged past the taskbar
		            if (d.getScreenY()<(SCREEN_BOUNDS.getMaxY()-20))
		                stage.setY(d.getScreenY() - posY);
		            stage.setX(d.getScreenX() - posX);
		        });

		        console.setOnMouseReleased((MouseEvent r)-> {
		            //Ensures the stage is not dragged past top of screen
		            if (stage.getY()<0.0) stage.setY(0.0);
		        });
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
