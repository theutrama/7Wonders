package view.gameList;

import java.io.IOException;

import application.Main;
import controller.SoundController;
import controller.sound.Sound;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;

public class GameListViewController extends BorderPane {

	@FXML
	private ImageView img_music;

	@FXML
	private Button btn_back;

	@FXML
	private Button btn_mute;

	@FXML
	private VBox vbox_gameList;

	public GameListViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameList/GameList.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		btn_back.setOnAction(e -> { Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new MainMenuViewController()); });
		vbox_gameList.setAlignment(Pos.CENTER);

		String[] games = Main.getSWController().getIOController().listGameFiles();
		
		for (String game : games) {
			Button button = new Button(game);
			button.setOnAction(event -> { Main.getSWController().setGame(Main.getSWController().getIOController().load(game)); Main.primaryStage.getScene().setRoot(new GameBoardViewController()); });
			button.setText(game);
			button.setMinSize(534, 56);
			button.setPrefSize(534, 56);
			button.setMaxSize(534, 56);
			button.getStyleClass().add("buttonback");
			button.setAlignment(Pos.CENTER);
			vbox_gameList.getChildren().add(button);
		}

		SoundController.addMuteFunction(btn_mute, img_music);
	}

}
