package view.multiplayer.list;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import controller.SoundController;
import controller.sound.Sound;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.api.events.EventManager;
import view.menu.MainMenuViewController;
import view.multiplayer.lobby.LobbyViewController;
import view.ranking.RankingViewController;

@SuppressWarnings({"javadoc", "all", "PMD"})
public class MultiplayerListViewController extends StackPane {

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_mute;

    @FXML
    private VBox vbox_server;

    @FXML
    private Button btn_back;

    @FXML
    private Label txt_error;

    @FXML
    private TextField textfield_host;

    @FXML
    private TextField textfield_playername;

    @FXML
    private ImageView img_music;
	protected ArrayList<HBox> hosts = new ArrayList<HBox>();
    
    public MultiplayerListViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/multiplayer/list/MultiplayerList.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		addHost("localhost:6001");
		btn_add.setOnAction(event -> addHost());
		btn_back.setOnAction(e -> { Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new MainMenuViewController()); });
		SoundController.addMuteFunction(btn_mute, img_music);
	}
    
    protected void error(String txt) {
    	txt_error.setText(txt);
		txt_error.setVisible(true);
	}

	protected void addHost() {
		if(textfield_host.getText().isEmpty() || textfield_host.getText().isBlank()) {
			error("Das Feld ist leer!");
			return;
		}
		
		if(!textfield_host.getText().contains(":")) {
			error("Es muss ein Port mit angegeben werden!");
			return;
		}
		
		for (HBox host : hosts) {
			Label label = (Label) host.getChildren().get(0);
			if (label.getText().equalsIgnoreCase(textfield_host.getText())) {
				error("Diese Server wurde bereits angegeben!");
				return;
			}
		}

		txt_error.setVisible(false);

		addHost(textfield_host.getText());
		textfield_host.clear();
	}
    
    protected HBox addHost(String adress) {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(10);

		Label label_host = new Label();
		label_host.getStyleClass().add("playerstyle");
		label_host.setText(adress);

		Button btn_minus = new Button();
		ImageView view = new ImageView(getClass().getResource("../../images/minus.png").toExternalForm());
		view.setFitHeight(20.0);
		view.setFitWidth(20.0);
		btn_minus.setGraphic(view);
		
		Button btn_connect = new Button();
		ImageView view1 = new ImageView(getClass().getResource("../../images/enter.png").toExternalForm());
		view1.setFitHeight(20.0);
		view1.setFitWidth(20.0);
		btn_connect.setGraphic(view1);


		hbox.getChildren().add(label_host);
		hbox.getChildren().add(btn_minus);
		hbox.getChildren().add(btn_connect);

		vbox_server.getChildren().add(hbox);

		hosts.add(hbox);

		btn_minus.setOnAction(event -> {
			hosts.remove(hbox);
			vbox_server.getChildren().remove(hbox);
		});
		
		btn_connect.setOnAction(event -> {
			if(textfield_playername.getText().isEmpty() || textfield_playername.getText().isBlank()) {
				error("Du musst einen Spielernamen angeben!");
				return;
			}
			
			String playername = textfield_playername.getText().replaceAll(" ", "");
			LobbyViewController lobbies = new LobbyViewController();
			Main.getSWController().getMultiplayerController().connect(playername,adress);
			Main.primaryStage.getScene().setRoot(lobbies);
		});

		return hbox;
	}
}
