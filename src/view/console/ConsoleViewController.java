package view.console;

import java.io.IOException;

import application.Console;
import application.Main;
import controller.SevenWondersController;
import controller.SoundController;
import controller.sound.Sound;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.gameList.GameListViewController;
import view.multiplayer.list.MultiplayerListViewController;
import view.newgame.NewGameViewController;
import view.ranking.RankingViewController;

public class ConsoleViewController extends VBox {

    @FXML
    private VBox vbox_console;

    @FXML
    private ScrollPane scrollpane;
    
    @FXML
    private Button btnclose;
    
	public ConsoleViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/console/Console.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		btnclose.setOnAction(event -> {
			Console.exit();
		});
		
	}
	
	public void addConsoleText(String text) {
		Label label = new Label(text);
		label.getStyleClass().add("labelstyle");
		vbox_console.getChildren().add(label);
		scrollpane.setVvalue(2);
	}
}
