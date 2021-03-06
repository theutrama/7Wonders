package view.multiplayer.gamelobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import application.Main;
import controller.CardController;
import controller.GameController;
import controller.PlayerController;
import controller.SoundController;
import controller.sound.Sound;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.client.connector.PacketListener;
import main.lobby.packets.client.LobbyLeavePacket;
import main.lobby.packets.server.LobbyClosePacket;
import main.lobby.packets.server.LobbyPlayersPacket;
import model.Game;
import model.card.Card;
import model.player.Player;
import model.player.ai.Difficulty;
import model.player.multiplayer.packets.StartGamePacket;
import view.gameboard.GameBoardViewController;
import view.multiplayer.lobby.LobbyViewController;

@SuppressWarnings("all")
public class GameLobbyViewController extends StackPane implements PacketListener{

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
    private HashMap<String,HBox> players = new HashMap<>();
    private ArrayList<Card> cardStack;
    private HashMap<String,String> wonderList;
	private static final ObservableList<String> types = FXCollections.observableList(Arrays.asList("Benutzer", "KI Einfach", "KI Mittel", "KI Schwer"));
	private static final ObservableList<String> KItypes = FXCollections.observableList(Arrays.asList("KI Einfach", "KI Mittel", "KI Schwer"));
	protected static final int NO_WONDER_ASSIGNED = 3, WONDER_ASSIGNED = 4, WONDER_INDEX = 2;
    
    public GameLobbyViewController(LobbyPlayersPacket packet) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/multiplayer/gamelobby/GameLobby.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		label_lobbyname.setText(packet.getName());
		txt_owner.setText("Owner: "+packet.getOwner());

		btn_add.setVisible(false);
		textfield_playername.setVisible(false);
		vbox_wonders.setVisible(false);
		label_drag.setVisible(false);
		
		String ownname = Main.getSWController().getMultiplayerController().getClient().getName();
		System.out.println("IS OWNER "+(packet.getOwner().equalsIgnoreCase(ownname))+" "+packet.getOwner()+" "+ownname);
		
		if(packet.getOwner().equalsIgnoreCase(ownname)) {
			System.out.println("OWNER!");
			this.owner=true;
			btn_done.setOnAction(event -> done());

			btn_done.setVisible(true);
//			btn_add.setVisible(true);
//			textfield_playername.setVisible(true);
			
			btn_add.setOnAction(event -> addPlayer());
		} else {
			System.out.println("NOT OWNER!");
			btn_done.setVisible(false);
			textfield_playername.setVisible(false);
		}
		
		handle(packet);

		btn_back.setOnAction(e -> { 
			Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK);
			PlayerClient client = (PlayerClient) Main.getSWController().getMultiplayerController().getClient();
			client.write(new LobbyLeavePacket());
			Main.primaryStage.getScene().setRoot(new LobbyViewController()); 
		});
		SoundController.addMuteFunction(btn_mute, img_music);
    }
    
    public boolean isOwner(){
    	return this.owner;
    }
    
    public boolean hasGameStarted() {
    	return this.cardStack != null;
    }
    
    public void done(){
    	if(!isOwner() && !hasGameStarted()) {
    		error("Du musst der Owner der Lobby sein um dies tun zukönnen!");
			return;
    	}
    	if (players.size() <= 1) {
			error("Es muessen mindestens 2 Spieler mitspielen!");
			return;
		}
    	
    	Main.getSWController().getMultiplayerController().setInGame(true);
    	String wondername = "";
		PlayerController pcon = Main.getSWController().getPlayerController();
		GameController gcon = Main.getSWController().getGameController();
		ArrayList<Player> game_players = new ArrayList<Player>();
		
		Stack<String> stack = null;
		if(isOwner()) {
			wonderList = new HashMap();
			List<String> wonders = Arrays.asList(Main.getSWController().getWonderBoardController().getWonderBoardNames());
			stack = new Stack<String>();
			stack.addAll(wonders);
			Collections.shuffle(stack);
		}
		
		for (String playername : this.players.keySet()) {
			HBox player = this.players.get(playername);
			Label nameLabel = (Label) player.getChildren().get(0);
			if(isOwner()) {
				wondername = stack.pop();
				this.wonderList.put(playername, wondername);
			}else {
				wondername = this.wonderList.get(nameLabel.getText());
			}
			String type = ((ComboBox<String>) player.getChildren().get(1)).getSelectionModel().getSelectedItem();

			if (isOwner() && type.contains("KI")) {
				Difficulty diff = Difficulty.fromString(type.split(" ")[1]);
				game_players.add(pcon.createAI(nameLabel.getText(), wondername, diff));
			}else {
				
				if(!getOwnName().equalsIgnoreCase(nameLabel.getText())) {
					game_players.add(Main.getSWController().getMultiplayerController().createPlayer(nameLabel.getText(), wondername));
				}	else {
					game_players.add(pcon.createPlayer(nameLabel.getText(), wondername));
				}
			}
			
			
		}
		ArrayList<Card> cardStack = null;
		if(isOwner()) {
			cardStack = Main.getSWController().getCardController().generateCardStack(game_players.size());
			ArrayList<String> cardStackString = new ArrayList<String>();
			for(Card c : cardStack)cardStackString.add(c.getInternalName());
			Main.getSWController().getMultiplayerController().getClient().write(new StartGamePacket(cardStackString,wonderList));
			for(Card c : cardStack) {
				System.out.println("CREATE "+c.getAge()+" "+c.getInternalName());
			}
		}else {
			for(Card c : this.cardStack) {
				System.out.println("GOT "+c.getAge()+" "+c.getInternalName());
			}
			cardStack = this.cardStack;
		}
		Game game = gcon.createGame(label_lobbyname.getText(),cardStack, game_players);
		Main.getSWController().setGame(game);
		Main.getSWController().getMultiplayerController().setInGame(true);
		Main.primaryStage.getScene().setRoot(new GameBoardViewController());
    }

	protected void error(String txt) {
		txt_error.setText(txt);
		txt_error.setVisible(true);
	}
    
    @Override
	public boolean handle(Packet packet0) {
    	System.out.println("GAMELOBBY HANDLE "+packet0.getPacketName());

		if(packet0 instanceof LobbyPlayersPacket) {
    		Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					LobbyPlayersPacket packet = (LobbyPlayersPacket) packet0;
					System.out.println("GOT LobbyPlayersPacket "+packet.getPlayers().size());  
		    		ArrayList<String> lobbyplayers = packet.getPlayers();
		    		ArrayList<String> remove = new ArrayList<>();
		    		
		    		for(String playername : lobbyplayers) {
		    			if(!players.containsKey(playername)) {
		    				addPlayer(playername,true);
		    				System.out.println("ADD PLAYER "+playername);  
		    				
		    				
		    			}
		    		}
		    		
		    		for(String playername : players.keySet()) {
		    			HBox box = players.get(playername);
		    			if(!lobbyplayers.contains(playername) && ((ComboBox<String>)box.getChildren().get(1)).getSelectionModel().getSelectedIndex() == 0) {
		    				remove.add(playername);
		    				System.out.println("REMOVE PLAYER "+playername);  	
		    			}
		    		}
		    		
		    		remove.forEach( player -> {
						HBox box = players.get(player);
						vbox_players.getChildren().remove(box);
						players.remove(player);
					});
				}
			});
    		return true;
    	}else if(packet0 instanceof LobbyClosePacket) {
    		Main.primaryStage.getScene().setRoot(new LobbyViewController()); 
    	}else if(packet0 instanceof StartGamePacket) {
    		StartGamePacket packet = (StartGamePacket)packet0;
    		System.out.println("GOT StartGamePacket");
    		CardController cardcon = Main.getSWController().getCardController();
    		this.cardStack = new ArrayList<Card>();
    		for(String cardname : packet.getCardStack()) {
    			this.cardStack.add(cardcon.getCard(cardname));
				System.out.println("GOT1 "+cardname);
    		}
    		
    		this.wonderList = packet.getWonders();
    		this.done();
    	}
    	return false;
    }
    
    public String getOwnName() {
    	return Main.getSWController().getMultiplayerController().getClient().getName();
    }
    
    private HBox createBox(String playername,boolean human) {
    	HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(10);

		Label label_player = new Label();
		label_player.getStyleClass().add("playerstyle");
		label_player.setText(playername);

		ComboBox<String> type = new ComboBox<>( types );
		type.getSelectionModel().select( !human ? 1 : 0 );
		type.setVisible(false);

		hbox.getChildren().add(label_player);
		hbox.getChildren().add(type);
		return hbox;
    }
    
    protected HBox addPlayer() {
    	if(textfield_playername.getText().isBlank() || textfield_playername.getText().isEmpty()) {
    		error("Bitte gib einen Namen an");
    		return null;
    	}
    	
    	for(String playername : this.players.keySet())
    		if(playername.equalsIgnoreCase(textfield_playername.getText())) {
    			error("Der Name ist bereits besetzt!");
    			return null;
    		}
    	
    	HBox box = addPlayer(textfield_playername.getText(),false);
    	return box;
    }
    
    protected HBox addPlayer(String playername,boolean human) {
		HBox hbox = createBox(playername,human);
		this.vbox_players.getChildren().add(hbox);
		
		this.players.put(playername,hbox);
		if(isOwner())this.btn_done.setVisible(this.vbox_players.getChildren().size() > 1);
		return hbox;
	}
}
