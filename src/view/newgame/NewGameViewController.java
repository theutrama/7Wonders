package view.newgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Main;
import application.Utils;
import controller.GameController;
import controller.IOController;
import controller.PlayerController;
import controller.SevenWondersController;
import controller.SoundController;
import controller.sound.Sound;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Game;
import model.player.Difficulty;
import model.player.Player;
import view.gameboard.GameBoardViewController;
import view.menu.MainMenuViewController;

public class NewGameViewController extends StackPane {

	@FXML
	private TextField textfield_playername;

	@FXML
	private Button btn_add;

	@FXML
	private VBox vbox_players;

	@FXML
	private VBox vbox_wonders;

	@FXML
	private ImageView img_music;

	@FXML
	private Button btn_back;

	@FXML
	private Label txt_maxplayers;

	@FXML
	private TextField textfield_gamename;

	@FXML
	private Button btn_done;

	@FXML
	private Button btn_mute;

	@FXML
	private Label label_drag;

	private ArrayList<HBox> players;
	
	private static SevenWondersController swController;

	private static final ObservableList<String> types = FXCollections.observableList(Arrays.asList("Benutzer", "KI Einfach", "KI Mittel", "KI Schwer"));

	private static final int NO_WONDER_ASSIGNED = 3, WONDER_ASSIGNED = 4, WONDER_INDEX = 2;

	public NewGameViewController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/newgame/NewGame.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		swController = new SevenWondersController();

		label_drag.setWrapText(true);
		label_drag.setText("Bitte ziehen Sie das Wunder auf den entsprechenden Spieler");
		label_drag.getStyleClass().add("dropshadow");
		label_drag.setTextAlignment(TextAlignment.LEFT);

		players = new ArrayList<HBox>();
		for (String wonder : Main.getSWController().getWonderBoardController().getWonderBoardNames()) {
			Label label = new Label(wonder);
			label.setPadding(new Insets(2, 5, 2, 5));
			label.getStyleClass().add("wonder-label");
			label.setOnDragDetected(e -> {
				Dragboard db = label.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putString(wonder);
				db.setContent(content);

				e.consume();
			});
			vbox_wonders.getChildren().add(label);
		}

		vbox_wonders.setOnDragOver(e -> {
			if (((Label) e.getGestureSource()).getParent() != vbox_wonders && e.getDragboard().hasString()) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
			e.consume();
		});

		vbox_wonders.setOnDragDropped(e -> {
			if (e.getDragboard().hasString() && e.getGestureSource() instanceof Label) {
				Label label = (Label) e.getGestureSource();
				((HBox) label.getParent()).getChildren().remove(label);
				addWonderToSidebox(label);

				e.setDropCompleted(true);
			} else
				e.setDropCompleted(false);

			e.consume();
		});

		txt_maxplayers.setVisible(false);

		btn_back.setOnAction(event -> {swController.getSoundController().play(Sound.BUTTON_CLICK); Main.primaryStage.getScene().setRoot(new MainMenuViewController());});
		btn_add.setOnAction(event -> addPlayer());

		btn_done.setVisible(false);
		btn_done.setOnAction(event -> done());

		textfield_playername.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addPlayer();
		});
		textfield_playername.textProperty().addListener((ov, oldVal, newVal) -> {
			if (textfield_playername.getText().length() > 15) {
				textfield_playername.setText(textfield_playername.getText().substring(0, 15));
			}
		});

		SoundController.addMuteFunction(btn_mute, img_music);
	}

	@SuppressWarnings("unchecked")
	private void done() {
		swController.getSoundController().play(Sound.BUTTON_CLICK);
		if (textfield_gamename.getText().isBlank()) {
			error("Bitte gib ein Spielnamen an!");
			return;
		}

		IOController ioCon = Main.getSWController().getIOController();
		String[] files = ioCon.listGameFiles();

		for (String filename : files) {
			if (filename.equalsIgnoreCase(textfield_gamename.getText())) {
				error("Dieser Spielname ist schon vergeben!");
				return;
			}
		}

		if (players.size() <= 1) {
			error("Es müssen mindestens 2 Spieler mitspielen!");
			return;
		}

		// Wenn ein Spieler kein Wonder ausgewählt hat wird ihn nun eins zugewiesen
		ObservableList<Node> last_wonders = vbox_wonders.getChildren();
		for (HBox player : players) {
			if (!hasWonder(player) && !last_wonders.isEmpty()) {
				Label wonder = (Label) last_wonders.get(Utils.randInt(0, last_wonders.size() - 1));
				last_wonders.remove(wonder);
				addWonderToPlayer(player, wonder);
			}
		}

		PlayerController pcon = Main.getSWController().getPlayerController();
		GameController gcon = Main.getSWController().getGameController();
		ArrayList<Player> game_players = new ArrayList<Player>();
		for (HBox player : players) {
			Label nameLabel = (Label) player.getChildren().get(0);
			Label wonderLabel = (Label) player.getChildren().get(WONDER_INDEX);
			String type = ((ComboBox<String>) player.getChildren().get(1)).getSelectionModel().getSelectedItem();

			if (type.contains("KI")) {
				Difficulty diff = Difficulty.fromString(type.split(" ")[1]);
				game_players.add(pcon.createAI(nameLabel.getText(), wonderLabel.getText(), diff));
			} else {
				game_players.add(pcon.createPlayer(nameLabel.getText(), wonderLabel.getText()));
			}
		}
		Game game = gcon.createGame(textfield_gamename.getText(), game_players);
		Main.getSWController().setGame(game);

		Main.primaryStage.getScene().setRoot(new GameBoardViewController());
	}

	private void error(String txt) {
		txt_maxplayers.setText(txt);
		txt_maxplayers.setVisible(true);
	}

	private void addPlayer() {
		if (textfield_playername.getText().isEmpty() || textfield_playername.getText().isBlank())
			return;

		if (players.size() == 7) {
			error("Es können nicht mehr als 7 Spieler hinzugefügt werden.");
			return;
		}

		for (HBox player : players) {
			Label label = (Label) player.getChildren().get(0);
			if (label.getText().equalsIgnoreCase(textfield_playername.getText())) {
				error("Es gibt bereits einen Spieler mit diesem Namen.");
				return;
			}
		}

		txt_maxplayers.setVisible(false);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(10);

		Label label_player = new Label();
		label_player.getStyleClass().add("playerstyle");
		label_player.setText(textfield_playername.getText());

		ComboBox<String> type = new ComboBox<>(types);
		type.getSelectionModel().select(0);

		Button btn_minus = new Button();
		ImageView view = new ImageView(getClass().getResource("../images/minus.png").toExternalForm());
		view.setFitHeight(20.0);
		view.setFitWidth(20.0);

		btn_minus.setGraphic(view);

		hbox.getChildren().add(label_player);
		hbox.getChildren().add(type);
		hbox.getChildren().add(btn_minus);

		hbox.setOnDragOver(e -> {
			if (((Label) e.getGestureSource()).getParent() != hbox && hbox.getChildren().size() == NO_WONDER_ASSIGNED) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
			e.consume();
		});

		hbox.setOnDragDropped(e -> {
			if (e.getDragboard().hasString()) {
				if (((Node) e.getGestureSource()).getParent() instanceof VBox) {
					Label label = (Label) e.getGestureSource();
					vbox_wonders.getChildren().remove(label);
					addWonderToPlayer(hbox, label);
				} else if (((Node) e.getGestureSource()).getParent() instanceof HBox) {
					Label label = (Label) e.getGestureSource();
					((HBox) label.getParent()).getChildren().remove(label);
					addWonderToPlayer(hbox, label);
				}

				e.setDropCompleted(true);
			} else
				e.setDropCompleted(false);

			e.consume();
		});

		vbox_players.getChildren().add(hbox);

		players.add(hbox);

		textfield_playername.clear();

		btn_minus.setOnAction(event -> {
			players.remove(hbox);
			vbox_players.getChildren().remove(hbox);
			if (hbox.getChildren().size() == WONDER_ASSIGNED)
				addWonderToSidebox((Label) hbox.getChildren().get(WONDER_INDEX));
			btn_done.setVisible(vbox_players.getChildren().size() > 1);
		});

		btn_done.setVisible(vbox_players.getChildren().size() > 1);
	}

	private boolean hasWonder(HBox playerBox) {
		return playerBox.getChildren().size() == WONDER_ASSIGNED;
	}

	private void addWonderToPlayer(HBox playerBox, Label wonder) {
		playerBox.getChildren().add(WONDER_INDEX, wonder);
		wonder.getStyleClass().clear();
		wonder.getStyleClass().add("wonder-label-selected");
	}

	private void addWonderToSidebox(Label label) {
		int i = 0;
		while (i < vbox_wonders.getChildren().size() && ((Label) vbox_wonders.getChildren().get(i)).getText().compareToIgnoreCase(label.getText()) < 0)
			i++;
		vbox_wonders.getChildren().add(i, label);
		label.getStyleClass().clear();
		label.getStyleClass().add("wonder-label");
	}
}
