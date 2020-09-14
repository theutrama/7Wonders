package view.gameboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import application.Utils;
import controller.SoundController;
import controller.sound.Sound;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.api.events.EventManager;
import model.Game;
import model.GameState;
import model.card.Card;
import model.card.Effect;
import model.card.EffectType;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.HardAI;
import model.player.ai.Move.Action;
import model.player.multiplayer.Multiplayer;
import model.player.multiplayer.events.PlayerActionEvent;
import model.player.multiplayer.events.PlayerSelectedCardEvent;
import model.player.multiplayer.events.PlayerTradeOptionEvent;
import view.menu.MainMenuViewController;

/**
 * this controller controls the game flow
 */
public class GameBoardViewController extends VBox {

	@FXML
	private Button btnMute;

	@FXML
	private ImageView imgMusic;

	@FXML
	private Button btnUndo;

	@FXML
	private Button btnRedo;

	@FXML
	private Label labelGametime;

	@FXML
	private ImageView imgAge;

	@FXML
	private ImageView imgDirection;

	@FXML
	private Button btnBack;

	@FXML
	private HBox hboxCards;

	@FXML
	private BorderPane borderpane;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private Button btnHint;

	private ArrayList<StackPane> boardPanes;
	private boolean action;

	private Timer timer;

	private EventHandler<MouseEvent> inputBlocker = MouseEvent::consume;

	/**
	 * create new controller
	 */
	public GameBoardViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameboard/GameBoard.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}

		Main.primaryStage.setOnCloseRequest(event -> { Main.getSWController().getIOController().save(Main.getSWController().getGame()); Main.getSWController().getIOController().saveRanking(); });

		scrollpane.setMinSize(1000, 500);
		hboxCards.setAlignment(Pos.CENTER);

		btnBack.setOnAction(event -> {
			exit();
			Main.getSWController().getIOController().save(Main.getSWController().getGame());
			Main.getSWController().getSoundController().stopAll();
			Main.getSWController().setGame(null);
			Main.primaryStage.getScene().setRoot(new MainMenuViewController());
			Main.primaryStage.setOnCloseRequest(event2 -> Main.getSWController().getIOController().saveRanking());
			Main.getSWController().getGameController().setGbvController(null);
		});

		btnUndo.setOnAction(event -> {
			if (Main.getSWController().getGameController().undo(Main.getSWController().getGame())) {
				action = false;
				refreshBoards();
				setHandCards();
				updateMouseBlocking();
			}
		});
		btnUndo.setPickOnBounds(true);
		btnRedo.setOnAction(event -> {
			if (Main.getSWController().getGameController().redo(Main.getSWController().getGame())) {
				action = false;
				refreshBoards();
				setHandCards();
				updateMouseBlocking();
			}
		});
		btnRedo.setPickOnBounds(true);
		
		btnHint.setTooltip(noDelay(new Tooltip("Tipp anzeigen")));

		SoundController.addMuteFunction(btnMute, imgMusic);

		Main.getSWController().getSoundController().stopAll();
		Main.getSWController().getSoundController().play(Sound.BACKGROUND_GAME, true);

		boardPanes = new ArrayList<>();
		generatePanes();
		refreshBoards();

		if (game().getChoosingPlayer() == null) {
			if (game().isAtBeginOfRound() || getCurrentPlayer().getChosenCard() == null) {
				action = false;
				setHandCards();
			} else {
				action = true;
				setActionCard();
			}
		} else {
			selectCardFromTrash(game().getChoosingPlayer());
		}

		Main.getSWController().getGameController().setGbvController(this);

		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Game game = Main.getSWController().getGame();
				game.nextSecond();
				Platform.runLater(() -> labelGametime
						.setText(String.format("%02d", game.getSeconds() / 3600) + ":" + String.format("%02d", (game.getSeconds() / 60) % 60) + ":" + String.format("%02d", game.getSeconds() % 60)));
			}
		}, 1000, 1000);
	}

	/**
	 * cancel the timer
	 */
	public void exit() {
		timer.cancel();
	}

	/**
	 * fills the borderpane and {@link #boardPanes} list with components to hold wonder boards, depending on the player count
	 */
	private void generatePanes() {
		StackPane pane = new StackPane();
		boardPanes.add(pane);
		borderpane.setTop(pane);

		VBox left, right;
		HBox bottom;

		switch (game().getPlayers().size()) {
		case 7:
			left = new VBox();
			right = new VBox();

			pane = new StackPane();
			boardPanes.add(1, pane);
			right.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			right.getChildren().add(pane);

			borderpane.setRight(right);

			bottom = new HBox();
			pane = new StackPane();
			boardPanes.add(1, pane);
			bottom.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			bottom.getChildren().add(0, pane);

			bottom.setSpacing(75);
			bottom.setAlignment(Pos.CENTER);
			borderpane.setBottom(bottom);

			pane = new StackPane();
			boardPanes.add(1, pane);
			left.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			left.getChildren().add(0, pane);

			borderpane.setLeft(left);
			break;
		case 6:
			left = new VBox();
			right = new VBox();
			pane = new StackPane();

			boardPanes.add(1, pane);
			right.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			right.getChildren().add(pane);

			borderpane.setRight(right);

			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setBottom(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			left.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			left.getChildren().add(0, pane);

			borderpane.setLeft(left);
			break;
		case 5:
			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setRight(pane);

			bottom = new HBox();
			pane = new StackPane();
			boardPanes.add(1, pane);
			bottom.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			bottom.getChildren().add(0, pane);

			bottom.setSpacing(75);
			bottom.setAlignment(Pos.CENTER);
			borderpane.setBottom(bottom);

			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setLeft(pane);
			break;
		case 4:
			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setRight(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setBottom(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setLeft(pane);
			break;
		case 3:
			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setRight(pane);

			pane = new StackPane();
			boardPanes.add(1, pane);
			borderpane.setLeft(pane);
			break;
		case 2:
			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setRight(pane);
		}

		for (StackPane stpane : boardPanes)
			stpane.setPadding(new Insets(0, 0, 20, 0));
	}

	/**
	 * create new Boards and display them
	 */
	private void refreshBoards() {
		int index2 = game().getCurrentPlayer();
		for (int i = 0; i < game().getPlayers().size(); i++) {
			Board board = new Board(game().getPlayers().get(index2));
			board.refresh();
			boardPanes.get(i).getChildren().clear();
			boardPanes.get(i).getChildren().add(board);
			index2++;
			if (index2 == game().getPlayers().size())
				index2 = 0;
		}

		try {
			imgAge.setImage(Utils.toImage(Main.TOKENS_PATH + "age" + game().getAge() + ".png"));
			imgDirection.setImage(Utils.toImage(Main.DEFAULT_PATH + (game().getAge() == 2 ? "" : "a") + "clockwise.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * next player
	 */
	public void turn() {
		if (game().isAtBeginOfRound()) {
			GameState state = game().deepClone();
			Main.getSWController().getGame().deleteRedoStates();
			Main.getSWController().getGame().getStates().add(state);
			Main.getSWController().getGame().setCurrentState(Main.getSWController().getGame().getStates().size() - 1);
			updateAllBoards();
			game().setBeginOfRound(false);
		}

		if (game().getCurrentPlayer() == game().getPlayers().size() - 1) {
			game().setCurrentPlayer(0);
		} else {
			game().setCurrentPlayer(game().getCurrentPlayer() + 1);
		}

		if (game().getCurrentPlayer() == game().getFirstPlayer()) {
			action = !action;

			if (!action) { // neue Spielrunde hat begonnen
				boolean halikarnassus = false;
				for (Player player : game().getPlayers()) {
					if (player.isMausoleum()) {
						halikarnassus = true;
						player.setMausoleum(false);
						selectCardFromTrash(player);
						break;
					}
				}
				if (halikarnassus) {
					return;
				}
				refreshBoards();
				new Thread(() -> {
					if (Main.getSWController().getGameController().createNextRound(Main.getSWController().getGame(), game()))
						return;
					Platform.runLater(() -> { refreshBoards(); setHandCards(); });
				}).start();
				return;
			}
		}

		refreshBoards();

		if (action)
			setActionCard();
		else
			setHandCards();
	}

	/**
	 * show conflict points on all boards
	 */
	public void showConflicts() {
		setMouseBlocked(true);
		Platform.runLater(() -> {
			hboxCards.getChildren().clear();
			for (StackPane pane : boardPanes) {
				((Board) pane.getChildren().get(0)).showConflict();
			}
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Platform.runLater(() -> {
			for (StackPane pane : boardPanes) {
				((Board) pane.getChildren().get(0)).hideConflict();
			}
		});
		setMouseBlocked(false);
	}

	/**
	 * shortcut method to get the current game state
	 * 
	 * @return current game state
	 */
	private static GameState game() {
		return Main.getSWController().getGame().getCurrentGameState();
	}

	/**
	 * shortcut method to get the current player
	 * 
	 * @return current player
	 */
	private static Player getCurrentPlayer() {
		return game().getPlayers().get(game().getCurrentPlayer());
	}

	/**
	 * calls {@link Board#updatePlayer()} on all boards
	 */
	private void updateAllBoards() {
		for (StackPane pane : boardPanes) {
			((Board) pane.getChildren().get(0)).updatePlayer();
		}
	}

	/**
	 * inner class that represents a game board
	 */
	private static class Board extends VBox {
		private Player player;

		private HBox hboxmilitary, hboxtrade, hboxscience, hboxguild, hboxcivil, hboxresources, hboxSlots;
		private StackPane stackpane;

		Label labelcoins, labelplayer;

		/**
		 * create game board
		 * 
		 * @param player assigned player
		 */
		public Board(Player player) {
			this.player = player;

			VBox mainBox = new VBox();

			BorderPane playerstats = new BorderPane();
			labelplayer = new Label();
			labelplayer.getStyleClass().addAll("lightwoodstyle", "dropshadow");

			HBox hboxcoins = new HBox();
			labelcoins = new Label();
			labelcoins.getStyleClass().addAll("lightwoodstyle", "dropshadow");
			ImageView imgcoin = new ImageView();
			try {
				imgcoin.setImage(Utils.toImage(Main.TOKENS_PATH + "coin.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			hboxcoins.getChildren().addAll(labelcoins, imgcoin);
			playerstats.setLeft(labelplayer);
			playerstats.setRight(hboxcoins);
			mainBox.getChildren().add(playerstats);

			hboxresources = new HBox();
			hboxresources.setMaxHeight(35.0);
			mainBox.getChildren().add(hboxresources);

			GridPane grid = new GridPane();
			RowConstraints rconstraint1 = new RowConstraints();
			rconstraint1.setPercentHeight(22);
			rconstraint1.setVgrow(Priority.SOMETIMES);

			RowConstraints rconstraint2 = new RowConstraints();
			rconstraint2.setPercentHeight(27);
			rconstraint2.setVgrow(Priority.SOMETIMES);

			RowConstraints rconstraint3 = new RowConstraints();
			rconstraint3.setPercentHeight(24);
			rconstraint3.setVgrow(Priority.SOMETIMES);

			RowConstraints rconstraint4 = new RowConstraints();
			rconstraint4.setPercentHeight(27);
			rconstraint4.setVgrow(Priority.SOMETIMES);

			grid.getRowConstraints().addAll(rconstraint1, rconstraint2, rconstraint3, rconstraint4);

			ColumnConstraints cconstraint1 = new ColumnConstraints();
			cconstraint1.setPercentWidth(22);
			cconstraint1.setHgrow(Priority.SOMETIMES);

			ColumnConstraints cconstraint2 = new ColumnConstraints();
			cconstraint2.setPercentWidth(27);
			cconstraint2.setHgrow(Priority.SOMETIMES);

			ColumnConstraints cconstraint3 = new ColumnConstraints();
			cconstraint3.setPercentWidth(51);
			cconstraint3.setHgrow(Priority.SOMETIMES);

			grid.getColumnConstraints().addAll(cconstraint1, cconstraint2, cconstraint3);

			grid.addColumn(0);

			hboxmilitary = new HBox();
			grid.add(hboxmilitary, 0, 1);

			hboxtrade = new HBox();
			grid.add(hboxtrade, 0, 2);

			hboxscience = new HBox();
			hboxscience.setAlignment(Pos.CENTER_RIGHT);
			grid.add(hboxscience, 2, 2);

			hboxguild = new HBox();
			hboxguild.setAlignment(Pos.CENTER_RIGHT);
			grid.add(hboxguild, 2, 0);

			hboxcivil = new HBox();
			hboxcivil.setAlignment(Pos.CENTER_RIGHT);
			grid.add(hboxcivil, 2, 1);

			// grid.setGridLinesVisible(true);

			try {
				grid.setBackground(new Background(new BackgroundImage(Utils.toImage(player.getBoard().getImage()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
						new BackgroundSize(1, 1, false, false, true, false))));
			} catch (IOException e) {
				e.printStackTrace();
			}

			hboxSlots = new HBox(isCurrentBoard() ? 23 : 17.25);
			hboxSlots.setAlignment(Pos.CENTER_LEFT);
			hboxSlots.setPadding(isCurrentBoard() ? new Insets(0, 46, 0, 37) : new Insets(0, 34.5, 0, 27.75));

			if (isCurrentBoard()) {
				grid.setMinSize(600, 250);
				grid.setPrefSize(600, 250);
				grid.setMaxSize(600, 250);
				hboxSlots.setMinSize(600, 80);
				hboxSlots.setPrefSize(600, 80);
				hboxSlots.setMaxSize(600, 80);

				this.setMaxWidth(600);
			} else {
				grid.setMinSize(450, 188);
				grid.setPrefSize(450, 188);
				grid.setMaxSize(450, 188);
				hboxSlots.setMinSize(450, 60);
				hboxSlots.setPrefSize(450, 60);
				hboxSlots.setMaxSize(450, 60);
				this.setMaxWidth(450);
			}
			mainBox.getChildren().add(grid);
			stackpane = new StackPane(mainBox);

			this.getChildren().add(stackpane);

			this.getChildren().add(hboxSlots);
		}

		/**
		 * find out whether this board is currently on the top place
		 * 
		 * @return true if this board is displayed on top
		 */
		private boolean isCurrentBoard() {
			return player.getName().equals(getCurrentPlayer().getName());
		}

		/**
		 * set style of an image view
		 * 
		 * @param view image view
		 */
		private void adaptSize(ImageView view) {
			view.setFitWidth(view.getImage().getWidth() * (isCurrentBoard() ? 40 : 30) / view.getImage().getHeight());
			view.setFitHeight(isCurrentBoard() ? 40 : 30);
			view.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 4, 0.5, 0, 0));
		}

		/**
		 * update player attribute to the object from the current game state
		 */
		private void updatePlayer() {
			player = Main.getSWController().getPlayerController().getPlayer(player.getName());
		}

		/**
		 * update the card views
		 */
		private void refresh() {
			labelplayer.setText(player.getName());
			labelcoins.setText(String.valueOf(player.getCoins()));

			hboxcivil.getChildren().clear();
			hboxguild.getChildren().clear();
			hboxmilitary.getChildren().clear();
			hboxresources.getChildren().clear();
			hboxscience.getChildren().clear();
			hboxtrade.getChildren().clear();

			for (Card card : player.getBoard().getResources()) {
				ImageView resicon = new ImageView();
				resicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(resicon, noDelay(new Tooltip(card.getDescription())));
				resicon.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px;");
				adaptSize(resicon);
				hboxresources.getChildren().add(resicon);
			}
			for (Card card : player.getBoard().getMilitary()) {
				ImageView milicon = new ImageView();
				milicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(milicon, noDelay(new Tooltip(card.getDescription())));
				adaptSize(milicon);
				hboxmilitary.getChildren().add(milicon);
			}
			for (Card card : player.getBoard().getCivil()) {
				ImageView civilicon = new ImageView();
				civilicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(civilicon, noDelay(new Tooltip(card.getDescription())));
				adaptSize(civilicon);
				hboxcivil.getChildren().add(0, civilicon);
			}
			for (Card card : player.getBoard().getResearch()) {
				ImageView scicon = new ImageView();
				scicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(scicon, noDelay(new Tooltip(card.getDescription())));
				adaptSize(scicon);
				hboxscience.getChildren().add(0, scicon);
			}
			for (Card card : player.getBoard().getTrade()) {
				ImageView tricon = new ImageView();
				tricon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(tricon, noDelay(new Tooltip(card.getDescription())));
				adaptSize(tricon);
				hboxtrade.getChildren().add(tricon);
			}
			for (Card card : player.getBoard().getGuilds()) {
				ImageView guildicon = new ImageView();
				guildicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(guildicon, noDelay(new Tooltip(card.getDescription())));
				adaptSize(guildicon);
				hboxguild.getChildren().add(0, guildicon);
			}

			for (int i = 0; i < 3 && player.getBoard().isFilled(i); i++) {
				try {
					ImageView img = new ImageView(Utils.toImage(Main.CARDS_PATH + "age" + player.getBoard().getAgeOfSlotCards(i) + ".png"));
					img.setFitWidth(isCurrentBoard() ? 158 : 118.5);
					img.setFitHeight(isCurrentBoard() ? 80 : 60);
					hboxSlots.getChildren().add(img);
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}

		/**
		 * shows the conflict points
		 */
		private void showConflict() {
			HBox hbox = new HBox(20);
			hbox.setAlignment(Pos.CENTER);
			Label points = new Label(String.valueOf(player.getConflictPoints() - player.getLosePoints()));
			points.getStyleClass().addAll("fontstyle", "dropshadow");
			points.setStyle("-fx-text-fill: black");
			hbox.getChildren().add(points);
			try {
				ImageView img = new ImageView(Utils.toImage(Main.TOKENS_PATH + "conflictPoints.png"));
				img.setFitWidth(60);
				img.setFitHeight(60);
				hbox.getChildren().add(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BorderPane borderpane = new BorderPane(hbox);
			borderpane.setStyle("-fx-background-color: #FFFFFF80; -fx-background-radius: 10px");
			stackpane.getChildren().add(borderpane);
		}

		/**
		 * hide conflict points
		 */
		private void hideConflict() {
			stackpane.getChildren().remove(1);
		}
	}

	/**
	 * show chosen card view
	 */
	public void setActionCard() {
		if (!action)
			return;

		btnHint.setVisible(false);
		btnHint.setOnAction(null);

		updateMouseBlocking();

		Card card = getCurrentPlayer().getChosenCard();
		hboxCards.getChildren().clear();

		boolean hasCard = Main.getSWController().getCardController().hasCard(getCurrentPlayer(), getCurrentPlayer().getChosenCard().getInternalName());

		int arrowWidth = 45, arrowHeight = 25;
		double scaleFactor = 1.2;

		Player player = getCurrentPlayer();

		try {
			StackPane outter = new StackPane();
			VBox vbox = new VBox();

			Button btnOlympia = new Button();
			Button btnSell = new Button();
			if (!player.isOlympiaUsed()) {
				HBox hboxOlympia = new HBox();
				ImageView img1 = new ImageView();
				img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
				img1.setFitWidth(arrowWidth);
				img1.setFitHeight(arrowHeight);
				ImageView img2 = new ImageView();
				img2.setImage(Utils.toImage(Main.TOKENS_PATH + "olympia.png"));
				img2.setFitWidth(40 * scaleFactor);
				img2.setFitHeight(50 * scaleFactor);
				hboxOlympia.getChildren().add(img1);
				hboxOlympia.getChildren().add(img2);
				hboxOlympia.setAlignment(Pos.CENTER_LEFT);
				hboxOlympia.setSpacing(5);
				btnOlympia.setGraphic(hboxOlympia);
				btnOlympia.hoverProperty().addListener((obs, oldVal, newValue) -> {
					try {
						if (newValue) {
							img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowhover.png"));
						} else {
							img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				btnOlympia.setOnAction(event -> { Main.getSWController().getCardController().placeCard(card, player, null, true); player.setOlympiaUsed(true); turn(); });
				btnOlympia.setTooltip(noDelay(new Tooltip("Olympia-Faehigkeit:\nBaue diese Karte kostenlos")));
				vbox.getChildren().add(btnOlympia);
				btnOlympia.setDisable(hasCard);
			}
			Button btnPlace = new Button();
			HBox hbox_place = new HBox();
			ImageView img1 = new ImageView();
			img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
			img1.setFitWidth(arrowWidth);
			img1.setFitHeight(arrowHeight);
			ImageView img2 = new ImageView();
			img2.setImage(Utils.toImage(Main.TOKENS_PATH + "place.png"));
			img2.setFitWidth(45 * scaleFactor);
			img2.setFitHeight(30 * scaleFactor);
			hbox_place.getChildren().add(img1);
			hbox_place.getChildren().add(img2);
			hbox_place.setAlignment(Pos.CENTER_LEFT);
			hbox_place.setSpacing(5);
			btnPlace.setGraphic(hbox_place);
			btnPlace.hoverProperty().addListener((obs, oldVal, newValue) -> {
				try {
					if (newValue) {
						img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowhover.png"));
					} else {
						img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			BuildCapability placeCapability = Main.getSWController().getPlayerController().canBuild(player, card);
			btnPlace.setOnAction(event -> {
				if(!(player instanceof ArtInt)) 
					EventManager.callEvent(new PlayerActionEvent(player, Action.BUILD));
				
				
				switch (placeCapability) {
				case FREE:
				case OWN_RESOURCE:
					Main.getSWController().getCardController().placeCard(card, player, null, false);
					turn();
					break;
				case TRADE:
					ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(player, card.getRequired());
					VBox tradeNodes = new VBox();
					for (int index = 0; index < trades.size(); index++) {
						TradeOption option = trades.get(index);
						final int optionIndex = index;
						tradeNodes.getChildren().add(option.getNode(player, event2 -> { 
							if(!(player instanceof ArtInt)) 
								EventManager.callEvent(new PlayerTradeOptionEvent(player, optionIndex));
							Main.getSWController().getCardController().placeCard(card, player, option, false); turn(); 
						}));
					}
					hboxCards.getChildren().add(tradeNodes);
					outter.getChildren().remove(vbox);

					if (player instanceof ArtInt) {
						TradeOption option = ((ArtInt) player).getTradeOption();
						
						int index = trades.indexOf(option);
						if (index == -1) {
							System.err.println(player.getName() + " has chosen an invalid trade option:");
							System.err.println("Trades: "+trades.size());
							System.err.println("TradeOption: "+option);
							System.err.println("action: " + ((ArtInt) player).getAction());
							Platform.runLater(() -> btnSell.fire());
						} else
							Platform.runLater(() -> { ((Button) tradeNodes.getChildren().get(index)).fire(); });
					}
					break;
				default:
					break;
				}
			});
			btnPlace.setDisable(hasCard || placeCapability == BuildCapability.NONE);

			// Button place card on WonderBoard
			Button btnWonder = new Button();
			HBox hbox_wonder = new HBox();
			ImageView img3 = new ImageView();
			img3.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
			img3.setFitWidth(arrowWidth);
			img3.setFitHeight(arrowHeight);
			ImageView img4 = new ImageView();
			img4.setImage(Utils.toImage(Main.TOKENS_PATH + "pyramid-stage" + (player.getBoard().nextSlot() + 1) + ".png"));
			img4.setFitWidth(45 * scaleFactor);
			img4.setFitHeight(35 * scaleFactor);
			hbox_wonder.getChildren().add(img3);
			hbox_wonder.getChildren().add(img4);
			hbox_wonder.setAlignment(Pos.CENTER_LEFT);
			hbox_wonder.setSpacing(5);
			btnWonder.setGraphic(hbox_wonder);
			btnWonder.hoverProperty().addListener((obs, oldVal, newValue) -> {
				try {
					if (newValue) {
						img3.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowhover.png"));
					} else {
						img3.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			if (!player.getBoard().isFilled(2)) {
				BuildCapability wonderCapability = Main.getSWController().getPlayerController().hasResources(player, new ArrayList<>(Arrays.asList(player.getBoard().getNextSlotRequirement())));
				btnWonder.setOnAction(event -> {
					if(!(player instanceof ArtInt))
						EventManager.callEvent(new PlayerActionEvent(player, Action.PLACE_SLOT));
					
					switch (wonderCapability) {
					case FREE:
					case OWN_RESOURCE:
						Main.getSWController().getCardController().setSlotCard(card, player, null);
						turn();
						break;
					case TRADE:
						ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(player,
								new ArrayList<>(Arrays.asList(player.getBoard().getSlotResquirement(player.getBoard().nextSlot()))));
						VBox tradeNodes = new VBox();
						for (int index = 0; index<trades.size(); index++) {
							TradeOption option = trades.get(index);
							final int optionIndex = index;
							tradeNodes.getChildren().add(option.getNode(player, event2 -> { 
								if(!(player instanceof ArtInt))
									EventManager.callEvent(new PlayerTradeOptionEvent(player, optionIndex));
								
								Main.getSWController().getCardController().setSlotCard(card, player, option); turn(); 
							}));
						}
						hboxCards.getChildren().add(tradeNodes);
						outter.getChildren().remove(vbox);
						if (player instanceof ArtInt) {
							TradeOption option = ((ArtInt) player).getTradeOption();
							int index = trades.indexOf(option);
							if (index == -1) {
								System.err.println(player.getName() + " has chosen an invalid trade option:");
								System.err.println("Trades: "+trades.size());
								System.err.println("TradeOption: "+option);
								System.err.println("action: " + ((ArtInt) player).getAction());
								Platform.runLater(() -> btnSell.fire());
							} else
								Platform.runLater(() -> { ((Button) tradeNodes.getChildren().get(index)).fire(); });
						}
						break;
					default:
						break;
					}
				});
				btnWonder.setDisable(wonderCapability == BuildCapability.NONE);
			} else
				btnWonder.setDisable(true);

			// Button card sell
			HBox hbox_sell = new HBox();
			ImageView img5 = new ImageView();
			img5.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
			img5.setFitWidth(arrowWidth);
			img5.setFitHeight(arrowHeight);
			ImageView img6 = new ImageView();
			img6.setImage(Utils.toImage(Main.TOKENS_PATH + "coin3.png"));
			HBox.setMargin(img6, new Insets(0, 5, 0, 0));
			img6.setFitWidth(35 * scaleFactor);
			img6.setFitHeight(33 * scaleFactor);
			hbox_sell.getChildren().add(img5);
			hbox_sell.getChildren().add(img6);
			hbox_sell.setAlignment(Pos.CENTER_LEFT);
			hbox_sell.setSpacing(14);
			btnSell.setGraphic(hbox_sell);
			btnSell.hoverProperty().addListener((obs, oldVal, newValue) -> {
				try {
					if (newValue) {
						img5.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowhover.png"));
					} else {
						img5.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			btnSell.setOnAction(e -> { 

				if(!(player instanceof ArtInt))
					EventManager.callEvent(new PlayerActionEvent(player, Action.SELL));
				
				Main.getSWController().getCardController().sellCard(card, player); turn(); 
			});

			vbox.getChildren().add(btnPlace);
			vbox.getChildren().add(btnWonder);
			vbox.getChildren().add(btnSell);
			vbox.setAlignment(Pos.CENTER);
			if (player.isOlympiaUsed())
				vbox.setSpacing(5);
			vbox.setStyle("-fx-background-color: #d9d9d999");
			outter.getChildren().add(vbox);
			outter.setBackground(new Background(new BackgroundImage(Utils.toImage(card.getImage()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					new BackgroundSize(1, 1, false, false, true, false))));
			outter.setMinSize(141, 215);
			outter.setPrefSize(141, 215);
			hboxCards.getChildren().add(outter);

			if (player instanceof ArtInt) {
				Action action = ((ArtInt) player).getAction();
				if (action == Action.OLYMPIA && btnOlympia != null)
					btnOlympia.fire();
				else if (action == Action.BUILD && !btnPlace.isDisabled())
					btnPlace.fire();
				else if (action == Action.PLACE_SLOT && !btnWonder.isDisabled())
					btnWonder.fire();
				else
					btnSell.fire();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * show select-card view
	 */
	public void setHandCards() {
		if (action)
			return;

		updateMouseBlocking();

		Player player = getCurrentPlayer();
		ArrayList<Card> hand = player.getHand();

		hboxCards.getChildren().clear();
		hboxCards.setSpacing(10);

		Card card;
		for (int i = 0; i < hand.size(); i++) {
			card = hand.get(i);

			String path = Main.TOKENS_PATH, tooltip = null;
			if (Main.getSWController().getCardController().hasCard(player, card.getInternalName())) {
				path += "noplace";
				tooltip = "Du besitzt diese Karte bereits";
			} else {
				BuildCapability capability = Main.getSWController().getPlayerController().canBuild(player, card);
				switch (capability) {
				case FREE:
					path += "free";
					tooltip = "Diese Karte kann kostenlos gebaut werden,\nda alle erforderlichen Karten bereits gebaut sind";
					break;
				case OWN_RESOURCE:
					path += "check";
					tooltip = "Du kannst diese Karte mit eigenen Ressourcen bezahlen";
					break;
				case TRADE:
					path += "checkyellow";
					tooltip = "Du kannst die erforderlichen Resourcen von Nachbarstaedten kaufen";
					break;
				case NONE:
					path += "cross";
					tooltip = "Diese Karte kann nicht gebaut werden";
					break;
				}
			}

			Button btn = new Button();
			VBox vbox = new VBox();
			ImageView img = new ImageView();
			img.setFitHeight(25);
			img.setFitWidth(25);
			img.getStyleClass().add("dropshadow");
			img.setPickOnBounds(true);
			Tooltip.install(img, noDelay(new Tooltip(tooltip)));
			vbox.getChildren().addAll(img, btn);
			vbox.setAlignment(Pos.CENTER_RIGHT);
			hboxCards.getChildren().add(vbox);

			int cardIndex = i;

			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					if(!(player instanceof ArtInt))
						EventManager.callEvent(new PlayerSelectedCardEvent(player, player.getHand().get(cardIndex)));
					
					Main.getSWController().getSoundController().play(Sound.CHOOSE_CARD);
					getCurrentPlayer().setChooseCard(player.getHand().get(cardIndex));
					turn();
				}
			});

			if (!(btn.getGraphic() instanceof ImageView)) {
				ImageView view = new ImageView();
				view.setFitHeight(215);
				view.setFitWidth(141);
				view.setPickOnBounds(true);
				view.setPreserveRatio(true);
				btn.setGraphic(view);
			}
			btn.setTooltip(noDelay(new Tooltip(card.getDescription())));
			ImageView img2 = (ImageView) btn.getGraphic();
			try {
				img.setImage(Utils.toImage(path + ".png"));
				img2.setImage(Utils.toImage(card.getImage()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		btnHint.setVisible(!(player instanceof ArtInt));

		if (player instanceof ArtInt) {
			new Thread(() -> {
				long t1 = System.currentTimeMillis();
				((ArtInt) player).calculateNextMove();
				System.out.println("time: " + (System.currentTimeMillis() - t1));
				Card selected = ((ArtInt) player).getSelectedCard();
				int index = indexOf(player.getHand(), selected);
				
				VBox vbox = (VBox) hboxCards.getChildren().get(index);
				Platform.runLater(() -> { ((Button) vbox.getChildren().get(1)).fire(); });
			}).start();
		} else {
			btnHint.setOnAction(event -> {
				btnHint.setVisible(false);
				Main.getSWController().getGame().disableHighscore();
				Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK);
				HardAI artint = new HardAI(player.getName(), player.getBoard());
				artint.calculateNextMove();
				int index = indexOf(player.getHand(), artint.getSelectedCard());
				if (index == -1)
					return;
				Button btn = (Button) ((VBox) hboxCards.getChildren().get(index)).getChildren().get(1);
				ImageView img = (ImageView) btn.getGraphic();
				ImageView icon = null;
				try {
					switch (artint.getAction()) {
					case BUILD:
						icon = new ImageView(Utils.toImage(Main.TOKENS_PATH + "place.png"));
						icon.setFitWidth(90);
						icon.setFitHeight(60);
						break;
					case OLYMPIA:
						icon = new ImageView(Utils.toImage(Main.TOKENS_PATH + "olympia.png"));
						icon.setFitWidth(52.5);
						icon.setFitHeight(60);
						break;
					case PLACE_SLOT:
						icon = new ImageView(Utils.toImage(Main.TOKENS_PATH + "pyramid-stage" + (player.getBoard().nextSlot() + 1) + ".png"));
						icon.setFitWidth(77);
						icon.setFitHeight(60);
						break;
					case SELL:
						icon = new ImageView(Utils.toImage(Main.TOKENS_PATH + "coin3.png"));
						icon.setFitWidth(65);
						icon.setFitHeight(61.5);
						break;
					default:
						break;
					}
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				BorderPane bpane = new BorderPane(icon);
				bpane.setPadding(new Insets(10, 10, 10, 10));
				bpane.setStyle("-fx-background-color: #D0D0D090");
				StackPane stackpane = new StackPane(img, bpane);
				stackpane.setAlignment(Pos.CENTER);
				stackpane.setStyle("-fx-border-color: white; -fx-border-width: 4px; -fx-border-radius: 3px;");
				btn.setGraphic(stackpane);
			});
		}
	}

	/**
	 * called to let the mausoleum player choose his free-to-build card
	 * 
	 * @param player player
	 */
	public void selectCardFromTrash(Player player) {

		updateMouseBlocking();

		if (game().getTrash().isEmpty()) {
			new Thread(() -> {
				Platform.runLater(() -> {
					hboxCards.getChildren().clear();
					Label label = new Label(player.getName() + " kann keine Karte waehlen, da der Ablagestapel leer ist!");
					label.getStyleClass().addAll("fontstyle", "dropshadow");
					hboxCards.getChildren().add(label);
				});
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> { hboxCards.getChildren().clear(); exitHalikarnassus(); });
			}).start();
			return;
		}

		game().setChoosingPlayer(player);

		hboxCards.getChildren().clear();
		HBox hboxTitle = new HBox(50);
		Label label = new Label(player.getName() + " darf eine Karte von Ablagestapel waehlen");
		label.getStyleClass().addAll("fontstyle", "dropshadow");
		label.setStyle("-fx-text-fill: #F5F5F5; -fx-font-size: 20;");
		Label labelBtn = new Label("Keine Karte auswaehlen");
		labelBtn.getStyleClass().addAll("fontstyle", "dropshadow");
		labelBtn.setStyle("-fx-text-fill: #F5F5F5; -fx-font-size: 20;");
		Button exit = new Button();
		exit.setStyle("-fx-border-width: 3px; -fx-border-color: #11111188;");
		exit.getStyleClass().add("buttonback");
		exit.setGraphic(labelBtn);
		exit.setOnAction(event -> exitHalikarnassus());
		hboxTitle.getChildren().addAll(label, exit);
		hboxTitle.setAlignment(Pos.CENTER);

		HBox hboxChooseCard = new HBox(10);

		for (Card card : game().getTrash()) {
			Button button = new Button();
			try {
				ImageView img = new ImageView(Utils.toImage(card.getImage()));
				img.setFitWidth(141);
				img.setFitHeight(215);
				img.setPickOnBounds(true);
				img.setPreserveRatio(true);
				button.setGraphic(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			button.setTooltip(noDelay(new Tooltip(card.getDescription())));
			button.setOnAction(event -> {
				game().getTrash().remove(card);
				player.getBoard().addCard(card);
				Main.getSWController().getSoundController().play(Sound.BUILD);
				if (card.getEffects() != null) {
					for (Effect effect : card.getEffects()) {
						if (effect.getType() == EffectType.WHEN_PLAYED)
							effect.run(player, Main.getSWController().getGame());
					}
				}
				exitHalikarnassus();
			});

			hboxChooseCard.getChildren().add(button);
		}

		ScrollPane scrollpane = new ScrollPane(hboxChooseCard);
		scrollpane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setMinViewportHeight(240);

		VBox vboxChoose = new VBox(10);
		vboxChoose.getChildren().addAll(hboxTitle, scrollpane);
		vboxChoose.setPadding(new Insets(0, 10, 0, 20));

		hboxCards.getChildren().add(vboxChoose);

		if (player instanceof ArtInt) {
			btnHint.setVisible(false);
			new Thread(() -> {
				Card selected = ((ArtInt) player).getHalikarnassusCard(player, game().getTrash(), game());
				int index = indexOf(game().getTrash(), selected);
				if (selected == null || index == -1)
					Platform.runLater(() -> exit.fire());
				else
					Platform.runLater(() -> { ((Button) hboxChooseCard.getChildren().get(index)).fire(); });
			}).start();
		} else {
			btnHint.setVisible(true);
			btnHint.setOnAction(event -> {
				btnHint.setVisible(false);
				Main.getSWController().getGame().disableHighscore();
				Main.getSWController().getSoundController().play(Sound.BUTTON_CLICK);
				Card card = new HardAI(player.getName(), player.getBoard()).getHalikarnassusCard(player, game().getTrash(), game());
				int index = indexOf(game().getTrash(), card);
				if (index == -1) {
					System.err.println("mausoleum card invalid: " + card.getName());
					return;
				}
				ImageView img = (ImageView) ((Button) hboxChooseCard.getChildren().get(index)).getGraphic();
				img.setStyle("-fx-border-color: white; -fx-border-width: 4px; -fx-border-radius: 3px;");
				StackPane spane = new StackPane(img);
				((Button) hboxChooseCard.getChildren().get(index)).setGraphic(spane);
			});
		}
	}

	/**
	 * continue the game and do all procedures
	 * 
	 * @param choosing the player that used the choosing ability
	 */
	private void exitHalikarnassus() {
		game().setChoosingPlayer(null);
		new Thread(() -> {
			if (Main.getSWController().getGameController().createNextRound(Main.getSWController().getGame(), game()))
				return;
			Platform.runLater(() -> { refreshBoards(); setHandCards(); });
		}).start();
	}

	/**
	 * sets the mouse blocking property depending on the current player
	 */
	private void updateMouseBlocking() {
		setMouseBlocked((game().getChoosingPlayer() == null && getCurrentPlayer() instanceof ArtInt) || (game().getChoosingPlayer() != null && game().getChoosingPlayer() instanceof ArtInt));
	}

	/**
	 * sets the mouse blocking property
	 * 
	 * @param blocked blocked
	 */
	public void setMouseBlocked(boolean blocked) {
		if (blocked) {
			hboxCards.addEventFilter(MouseEvent.ANY, inputBlocker);
			scrollpane.addEventFilter(MouseEvent.ANY, inputBlocker);
		} else {
			hboxCards.removeEventFilter(MouseEvent.ANY, inputBlocker);
			scrollpane.removeEventFilter(MouseEvent.ANY, inputBlocker);
		}
		btnUndo.setDisable(blocked);
		btnRedo.setDisable(blocked);
	}

	/**
	 * index of given card in a list
	 * 
	 * @param list list of cards
	 * @param card card
	 * @return index of first occurance in list or -1
	 */
	private int indexOf(ArrayList<Card> list, Card card) {
		if (card == null) throw new NullPointerException("card is null?");
			
		int index = -1;
		for (int i = 0; i < list.size(); i++)
			if (card.getInternalName().equals(list.get(i).getInternalName())) {
				index = i;
				break;
			}
			
		return index;
	}

	/**
	 * sets show and hide delays of a tooltip to zero
	 * 
	 * @param tip tooltip
	 * @return the same tooltip
	 */
	private static Tooltip noDelay(Tooltip tip) {
		tip.setShowDelay(new Duration(0));
		tip.setHideDelay(new Duration(0));
		return tip;
	}
}