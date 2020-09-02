package view.newgame;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.player.Player;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class NewGameViewController extends BorderPane {

    @FXML
    private TextField textfield_playername;

    @FXML
    private Button btn_add;

    @FXML
    private VBox vbox_players;

    @FXML
    private ImageView img_music;

    @FXML
    private Button btn_back;
    
    @FXML
    private Label txt_maxplayers;
    
    @FXML
    private Button btn_done;
    
    
    private ArrayList<HBox> players;

    
	public NewGameViewController() {
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newgame/NewGame.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		players = new ArrayList<HBox>();
		txt_maxplayers.setVisible(false);
		
		btn_back.setOnAction(event -> {
			Main.primaryStage.getScene().setRoot(new MainMenuViewController());
		});
		btn_add.setOnAction(event -> {
			addPlayer();
		});
	}


	private void addPlayer() {
		if(textfield_playername.getText().isEmpty()) return;
		
		if(players.size() == 7) {
			txt_maxplayers.setText("Es können nicht mehr als 7 Spieler hinzugefügt werden.");
			txt_maxplayers.setVisible(true);
			return;
		}
		
		for(HBox player : players) {
			Label label = (Label) player.getChildren().get(0);
			if(label.getText().equalsIgnoreCase(textfield_playername.getText())) {
				txt_maxplayers.setText("Es gibt bereits einen Spieler mit diesem Namen.");
				txt_maxplayers.setVisible(true);
				return;
			}
		}
		
		txt_maxplayers.setVisible(false);
		
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		
		Label label_player = new Label();
		label_player.getStyleClass().add("playerstyle");
		label_player.setText(textfield_playername.getText());
		
		Button btn_minus = new Button();
		ImageView view = new ImageView(getClass().getResource("../images/minus.png").toExternalForm());
		view.setFitHeight(20.0);
		view.setFitWidth(20.0);
		
		btn_minus.setGraphic(view);
		
		hbox.getChildren().add(label_player);
		hbox.getChildren().add(btn_minus);
		
		vbox_players.getChildren().add(hbox);
		
		players.add(hbox);
		
		btn_minus.setOnAction(event -> {
			players.remove(hbox);
			vbox_players.getChildren().remove(hbox);
		});
	}
}
