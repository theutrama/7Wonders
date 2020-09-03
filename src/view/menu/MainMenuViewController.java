package view.menu;

import java.io.IOException;

import application.Main;
import controller.SevenWondersController;
import controller.SoundController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import view.gameList.GameListViewController;
import view.newgame.NewGameViewController;
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
    
    @FXML
    private Button btn_mute;

	public MainMenuViewController(SevenWondersController sw) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/MainMenu.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		SoundController.addMuteFunction(btn_mute, img_music);

		btn_newgame.setOnAction(event -> {
			Main.primaryStage.getScene().setRoot(new NewGameViewController(sw));
		});
		
		btn_ranking.setOnAction(e -> Main.primaryStage.getScene().setRoot(new RankingViewController(sw)));
		
		btn_loadgame.setOnAction(e -> Main.primaryStage.getScene().setRoot(new GameListViewController(sw)));
	}
}
