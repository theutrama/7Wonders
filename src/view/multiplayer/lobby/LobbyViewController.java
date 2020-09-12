package view.multiplayer.lobby;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import application.Main;
import application.Utils;
import controller.GameController;
import controller.MultiplayerController;
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
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.client.connector.Callback;
import main.lobby.packets.client.LobbyCreatePacket;
import main.lobby.packets.server.LobbyErrorPacket;
import main.lobby.packets.server.LobbyListPacket;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;

public class LobbyViewController extends BorderPane implements EventListener{

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

	public LobbyViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameList/Lobby.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		EventManager.unregister(arg0)
		EventManager.register(this);
		btn_back.setOnAction(e -> { Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new MainMenuViewController()); });


		SoundController.addMuteFunction(btn_mute, img_music);

	}
	
	public void createLobby(String lobbyname) {
		MultiplayerController con = Main.getSWController().getMultiplayerController();
		LobbyCreatePacket packet = new LobbyCreatePacket(lobbyname);
		PlayerClient client = con.getClient();
		client.write(packet);
	}
	
	@main.api.events.EventHandler
	public void rec(PacketReceiveEvent ev) {
		if(ev.getPacket() instanceof LobbyListPacket) {
			LobbyListPacket packet = (LobbyListPacket) ev.getPacket();
			
			//NAME, CLIENT AMOUNT
			HashMap<String, Integer> map = packet.getLobbys();
			
			for(String name: map.keySet()) {
				int playerCount = map.get(name);
				
				Button button = new Button(name);
				//button.setOnAction(event -> { Main.getSWController().setGame(Main.getSWController().getIOController().load(game)); Main.primaryStage.getScene().setRoot(new GameBoardViewController()); });
				button.setText(name + ": " + playerCount + " players");
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
				
			}
			
			
		}else if(ev.getPacket() instanceof LobbyErrorPacket) {
			
		}
	}
}
