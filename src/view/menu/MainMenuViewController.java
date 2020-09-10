package view.menu;

import java.io.IOException;

import application.Main;
import controller.SevenWondersController;
import controller.SoundController;
import controller.sound.Sound;
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

	public MainMenuViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/MainMenu.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		SoundController.addMuteFunction(btn_mute, img_music);

		btn_newgame.setOnAction(event -> {Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK);
			Main.primaryStage.getScene().setRoot(new NewGameViewController());
		});
		
		btn_ranking.setOnAction(e -> {Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new RankingViewController());});
		
		btn_loadgame.setOnAction(e -> {Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new GameListViewController());});
		Main.getSWController().getSoundController().play(Sound.BACKGROUND_MENU, true);
	}
}
