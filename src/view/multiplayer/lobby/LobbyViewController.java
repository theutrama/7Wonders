package view.multiplayer.lobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import application.Main;
import controller.MultiplayerController;
import controller.SoundController;
import controller.sound.Sound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.client.connector.PacketListener;
import main.lobby.packets.client.LobbyCreatePacket;
import main.lobby.packets.client.LobbyEnterPacket;
import main.lobby.packets.client.LobbyLeavePacket;
import main.lobby.packets.client.LobbyUpdatePacket;
import main.lobby.packets.server.LobbyErrorPacket;
import main.lobby.packets.server.LobbyListPacket;
import main.lobby.packets.server.LobbyListPacket.LobbySettings;
import main.lobby.packets.server.LobbyPlayersPacket;
import view.menu.MainMenuViewController;
import view.multiplayer.gamelobby.GameLobbyViewController;
import view.multiplayer.list.MultiplayerListViewController;

@SuppressWarnings("all")
public class LobbyViewController extends StackPane implements PacketListener{

    @FXML
    private Label txt_error;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_mute;

    @FXML
    private VBox vbox_lobbys;

    @FXML
    private Button btn_back;

    @FXML
    private TextField textfield_lobbyname;

    @FXML
    private ImageView img_music;

	public LobbyViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/multiplayer/lobby/ListLobby.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btn_add.setOnAction(event -> createLobby());
		btn_back.setOnAction(e -> { 
			Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); 
			Main.primaryStage.getScene().setRoot(new MultiplayerListViewController()); 
		});
		SoundController.addMuteFunction(btn_mute, img_music);
	}
    
    protected void error(String txt) {
    	txt_error.setText(txt);
		txt_error.setVisible(true);
	}
    
    protected void createLobby() {
    	if(textfield_lobbyname.getText().isEmpty() || textfield_lobbyname.getText().isBlank()) {
			error("Du musst einen Namen angeben!");
			return;
		}
    	
    	createLobby(textfield_lobbyname.getText());
    }
	
	public void createLobby(String lobbyname) {
		MultiplayerController con = Main.getSWController().getMultiplayerController();
		LobbyCreatePacket packet = new LobbyCreatePacket(lobbyname);
		PlayerClient client = con.getClient();
		client.write(packet);
	}

	@Override
	public boolean handle(Packet packet0) {
    	System.out.println("LOBBY HANDLE "+packet0.getPacketName());
		PlayerClient client = Main.getSWController().getMultiplayerController().getClient();
		
		if(packet0 instanceof LobbyListPacket) {
			LobbyListPacket packet = (LobbyListPacket) packet0;
			
			//NAME, CLIENT AMOUNT
			final ArrayList<LobbySettings> list = packet.getLobbys();
			System.out.println("UPDATE "+list.size());
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					vbox_lobbys.getChildren().clear();
					for(LobbySettings settings : list) {
						System.out.println("SETTINGS "+settings.lobbyname+" "+settings.size+" "+settings.open);
						HBox hbox = new HBox();
						hbox.setAlignment(Pos.CENTER);
						hbox.setSpacing(10);

						Label label = new Label();
						label.getStyleClass().add("playerstyle");
						label.setText(settings.lobbyname+" ["+settings.size+"/7]"+(settings.open ? "" : " INGAME"));
						
						Button btn_connect = new Button();
						ImageView view1 = new ImageView(getClass().getResource("../../images/enter.png").toExternalForm());
						view1.setFitHeight(20.0);
						view1.setFitWidth(20.0);
						btn_connect.setGraphic(view1);


						hbox.getChildren().add(label);
						hbox.getChildren().add(btn_connect);

						vbox_lobbys.getChildren().add(hbox);
						
						btn_connect.setOnAction(event -> {
							LobbyEnterPacket packet = new LobbyEnterPacket(settings.lobbyname);
							client.write(packet);
						});
						btn_connect.setVisible( settings.open );
					}
				}
			});
			return true;
		}else if(packet0 instanceof LobbyPlayersPacket) {
			LobbyPlayersPacket packet = (LobbyPlayersPacket) packet0;
			Main.primaryStage.getScene().setRoot(new GameLobbyViewController(packet));
			return true;
		}else if(packet0 instanceof LobbyErrorPacket) {
			LobbyErrorPacket packet = (LobbyErrorPacket) packet0;
			error("Error: "+packet.getStatus());
			return true;
		}
		return false;
	}
	
	
}
