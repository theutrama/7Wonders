package view.gameList;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;

public class GameListViewController extends VBox {

	@FXML
	private ImageView img_music;

	@FXML
	private Button btn_back;

	@FXML
	private VBox vbox_gameList;

	public GameListViewController(MainMenuViewController menu) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameList/GameList.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		btn_back.setOnAction(e -> Main.primaryStage.getScene().setRoot(menu));
		
		String[] games = Main.getSWController().getIOController().listGameFiles();
		for (String game: games) {
			Button button = new Button(game);
			button.setOnAction(e -> Main.primaryStage.getScene().setRoot(new GameBoardViewController())); // TODO game übergeben
			
			vbox_gameList.getChildren().add(button);
		}
	}

}
