package view.newgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
	private ArrayList<String> wonders;

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
		wonders = new ArrayList<String>();
		
		wonders.addAll(Arrays.asList("Zuf�llig","Alexandrien","Babylon","Ephesus","Gizeh","Halikarnassos","Olympia","Rhodos"));
		
		txt_maxplayers.setVisible(false);

		btn_back.setOnAction(event -> Main.primaryStage.getScene().setRoot(new MainMenuViewController()));
		btn_add.setOnAction(event -> addPlayer());

		txt_maxplayers.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addPlayer();
		});
	}

	private void addPlayer() {
		if (textfield_playername.getText().isEmpty())
			return;

		if (players.size() == 7) {
			txt_maxplayers.setText("Es k�nnen nicht mehr als 7 Spieler hinzugef�gt werden.");
			txt_maxplayers.setVisible(true);
			return;
		}

		for (HBox player : players) {
			Label label = (Label) player.getChildren().get(0);
			if (label.getText().equalsIgnoreCase(textfield_playername.getText())) {
				txt_maxplayers.setText("Es gibt bereits einen Spieler mit diesem Namen.");
				txt_maxplayers.setVisible(true);
				return;
			}
		}

		txt_maxplayers.setVisible(false);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(5.0);

		Label label_player = new Label();
		label_player.getStyleClass().add("playerstyle");
		label_player.setText(textfield_playername.getText());

		Button btn_minus = new Button();
		ImageView view = new ImageView(getClass().getResource("../images/minus.png").toExternalForm());
		view.setFitHeight(20.0);
		view.setFitWidth(20.0);

		btn_minus.setGraphic(view);
		
		ComboBox<String> cb = new ComboBox<String>();
		cb.getStyleClass().add("buttonback");
		cb.setItems(FXCollections.observableList(wonders));
		cb.setPrefWidth(160);
		
		cb.getSelectionModel().selectedItemProperty().addListener((option, oldval, newval) -> {
			
		});

		
		hbox.getChildren().add(label_player);
		hbox.getChildren().add(cb);
		hbox.getChildren().add(btn_minus);
		

		vbox_players.getChildren().add(hbox);

		players.add(hbox);

		btn_minus.setOnAction(event -> { players.remove(hbox); vbox_players.getChildren().remove(hbox); });
	}
}
