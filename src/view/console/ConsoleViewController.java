package view.console;

import java.io.IOException;

import application.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ConsoleViewController extends VBox {

	@FXML
	private VBox vbox_console;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private Button btnclose;

	/**
	 * create new console controller
	 */
	public ConsoleViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/console/Console.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}

		btnclose.setOnAction(event -> { Console.exit(); });

	}

	/**
	 * write text to console
	 * 
	 * @param text new text
	 */
	public void addConsoleText(String text) {
		Label label = new Label(text);
		label.getStyleClass().add("labelstyle");
		vbox_console.getChildren().add(label);
		scrollpane.getParent().layout();
		new Thread(() -> {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			Platform.runLater(() -> scrollpane.setVvalue(1));
		}).start();
	}
}
