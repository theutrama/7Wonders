package view.menu;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.gameList.GameListViewController;
import view.gameboard.GameBoardViewController;
import view.ranking.RankingViewController;

public class MainMenuViewController extends BorderPane {

    @FXML
    private ImageView img_music;

    @FXML
    private Button btn_newgame;

    @FXML
    private Button btn_loadgame;

    @FXML
    private Button btn_ranking;

	public MainMenuViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/MainMenu.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		btn_newgame.setOnAction(event -> {
			Main.primaryStage.getScene().setRoot(new NewGameViewController());
		});
		
		btn_ranking.setOnAction(e -> Main.primaryStage.getScene().setRoot(new RankingViewController(this)));
		
		btn_loadgame.setOnAction(e -> Main.primaryStage.getScene().setRoot(new GameListViewController(this)));
	}
}
