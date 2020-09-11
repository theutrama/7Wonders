package view.gameList;

import java.io.File;
import java.io.IOException;

import application.Main;
import application.Utils;
import controller.GameController;
import controller.SoundController;
import controller.sound.Sound;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;

public class GameListViewController extends BorderPane {

	@FXML
	private ImageView img_music;

	@FXML
	private Button btn_back;

	@FXML
	private Button btn_load;

	@FXML
	private Button btn_mute;

	@FXML
	private VBox vbox_gameList;

	@FXML
	private ScrollPane scrollpane;

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

		String[] games = Main.getSWController().getIOController().listGameFiles();

		for (String game : games) {
			Button button = new Button(game);
			button.setOnAction(event -> { Main.getSWController().setGame(Main.getSWController().getIOController().load(game)); Main.primaryStage.getScene().setRoot(new GameBoardViewController()); });
			button.setText(game);
			button.setMinSize(534, 56);
			button.setPrefSize(534, 56);
			button.setMaxSize(534, 56);
			button.getStyleClass().addAll("menubutton", "dropshadow", "fontstyle");
			button.setStyle("-fx-text-fill: #F5F5F5");
			button.setAlignment(Pos.CENTER);
			HBox hbox = new HBox(5);
			hbox.setPadding(new Insets(0, 0, 0, 33));
			hbox.setAlignment(Pos.CENTER);
			hbox.getChildren().add(button);
			Button delete = new Button();
			ImageView img;
			try {
				img = new ImageView(Utils.toImage(Main.TOKENS_PATH + "trash.png"));
				img.setFitWidth(28);
				img.setFitHeight(35);
				delete.setGraphic(img);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			delete.setOnAction(event -> {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Möchtest du das Spiel \"" + game + "\" wirklich löschen?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Main.getSWController().getIOController().deleteFile(game);
					vbox_gameList.getChildren().remove(hbox);
				}
			});
			hbox.getChildren().add(delete);
			vbox_gameList.getChildren().add(hbox);
		}

		SoundController.addMuteFunction(btn_mute, img_music);

		FileChooser file_chooser = new FileChooser();
		btn_load.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = file_chooser.showOpenDialog(Main.primaryStage);

				if (file != null) {
					GameController con = Main.getSWController().getGameController();
					try {
						Main.getSWController().getWonderBoardController().loadBoardClasses();
						boolean loaded = con.loadCSV(file);

						if (!loaded) {
							System.out.println("Die Datei konnte nicht geladen werden!");
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
