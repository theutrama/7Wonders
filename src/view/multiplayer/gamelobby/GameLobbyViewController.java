package view.multiplayer.gamelobby;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import application.Main;
import controller.sound.Sound;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import main.api.events.EventHandler;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.lobby.packets.client.LobbyUpdatePacket;
import main.lobby.packets.server.LobbyPlayersPacket;
import view.multiplayer.list.MultiplayerListViewController;

public class GameLobbyViewController extends StackPane implements EventListener{

	private boolean owner = false;
    @FXML
    private Button btn_add;

    @FXML
    private Button btn_done;

    @FXML
    private Button btn_mute;

    @FXML
    private Label label_lobbyname;

    @FXML
    private Label txt_owner;

    @FXML
    private VBox vbox_players;

    @FXML
    private Label label_drag;

    @FXML
    private VBox vbox_wonders;

    @FXML
    private TextField textfield_playername;

    @FXML
    private Button btn_back;

    @FXML
    private Label txt_error;

    @FXML
    private ImageView img_music;
    private Settings settings;
    private HashMap<String,HBox> players = new HashMap<>();
    
	private static final ObservableList<String> types = FXCollections.observableList(Arrays.asList("Benutzer", "KI Einfach", "KI Mittel", "KI Schwer"));
	protected static final int NO_WONDER_ASSIGNED = 3, WONDER_ASSIGNED = 4, WONDER_INDEX = 2;
    
    public GameLobbyViewController(LobbyPlayersPacket packet) {
    	EventManager.unregister(GameLobbyViewController.class);
    	EventManager.register(this);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/multiplayer/gamelobby/GameLobby.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		label_lobbyname.setText(packet.getName());
		txt_owner.setText("");
		
		String ownname = Main.getSWController().getMultiplayerController().getClient().getName();
		if(packet.getOwner().equalsIgnoreCase(ownname)) {
			this.owner=true;
			this.settings = new Settings(ownname);
			Main.getSWController().getMultiplayerController().getClient().write(new LobbyUpdatePacket(settings.toBytes()));
			btn_done.setOnAction(event -> done());
		}else btn_done.setVisible(false);

		btn_back.setOnAction(e -> { EventManager.unregister(this); Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new MultiplayerListViewController()); });
    }
    
    public void done(){
    	
    }
    
    @EventHandler
    public void rec(PacketReceiveEvent ev) {
    	if(ev.getPacket() instanceof LobbyUpdatePacket) {
    		LobbyUpdatePacket packet = ev.getPacket(LobbyUpdatePacket.class);
    		this.settings = Settings.fromBytes(packet.getArr());
    		
    		Settings tthis = this.settings;
    		Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					txt_owner.setText("Owner: "+settings.getOwner());
					
					ArrayList<String> remove = new ArrayList();
					for(String playername : players.keySet()) {
						if(!tthis.getPlayers().containsKey(playername.toLowerCase())) 
							remove.add(playername);
						
					remove.forEach( player -> {
						HBox box = players.get(player);
						vbox_players.getChildren().remove(box);
						players.remove(player);
					});
					}
					
		    		for(String playername : tthis.getPlayers().keySet()) {
	    				Settings.PlayerSettings psettings = tthis.getPlayers().get(playername);
		    			if(!players.containsKey(playername)) {
		    				addPlayer(playername);
		    			}
	    				HBox box = players.get(playername);
	    				
	    				ComboBox<String> type = (ComboBox<String>) box.getChildren().get(1);
	    				type.getSelectionModel().select(psettings.type);
		    		}
		    		
		    		btn_done.setVisible(vbox_players.getChildren().size() > 1);
				}
			});
    	}
    }
    
    private HBox createBox(String playername) {
    	HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(10);

		Label label_player = new Label();
		label_player.getStyleClass().add("playerstyle");
		label_player.setText(playername);

		ComboBox<String> type = new ComboBox<>(types);
		type.getSelectionModel().select(0);

		hbox.getChildren().add(label_player);
		hbox.getChildren().add(type);
		return hbox;
    }
    
    protected HBox addPlayer(String playername) {
		HBox hbox = createBox(playername);
		vbox_players.getChildren().add(hbox);
		
		players.put(playername.toLowerCase(),hbox);
		
		settings.addPlayer(playername);
		btn_done.setVisible(vbox_players.getChildren().size() > 1);
		return hbox;
	}
    
    public static class Settings implements Serializable{
		private static final long serialVersionUID = 4530125647693261827L;

		public static Settings fromBytes(byte[] arr) {
    		try {
    			ByteArrayInputStream byteIn = new ByteArrayInputStream(arr);
    			ObjectInputStream objIn = new ObjectInputStream(byteIn);
    			Settings copy = (Settings) objIn.readObject();
    			byteIn.close();
    			objIn.close();
    			return copy;
    		} catch (IOException | ClassNotFoundException e) {
    			e.printStackTrace();
    			return null;
    		}
    	}
		
		private class PlayerSettings implements Serializable{
			private static final long serialVersionUID = 8749489882298040084L;
			
			protected PlayerSettings(String playername){
				this.playername=playername;
			}
			
			public String playername = "";
			public int type = 0;
		}
		
    	private String owner;
    	private HashMap<String, PlayerSettings> players = new HashMap<String, PlayerSettings>();
    	
    	public Settings(String owner) {
    		this.owner = owner;
    		addPlayer(owner);
    	}
    	
    	public PlayerSettings getSettings(String playername) {
    		return players.get(playername.toLowerCase());
    	}
    	
    	public void removePlayer(String playername) {
    		this.players.remove(playername.toLowerCase());
    	}
    	
    	public PlayerSettings addPlayer(String playername) {
    		PlayerSettings settings = new PlayerSettings(playername);
    		this.players.put(settings.playername.toLowerCase(), settings);
    		return settings;
    	}
    	
    	public HashMap<String, PlayerSettings> getPlayers(){
    		return this.players;
    	}    
    	
    	public String getOwner() {
    		return owner;
    	}
    	
    	public byte[] toBytes() {
    		try {
    			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
    			objOut.writeObject(this);
    			objOut.flush();
    			objOut.close();
    			byteOut.close();

    			return byteOut.toByteArray();
    		} catch (IOException e) {
    			e.printStackTrace();
    			return null;
    		}
    	}
    }
}
