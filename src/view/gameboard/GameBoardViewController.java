package view.gameboard;

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
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
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
import model.Game;
import model.GameState;
import model.card.Card;
import model.player.Player;
import view.menu.MainMenuViewController;

public class GameBoardViewController extends VBox {

	@FXML
	private Button btn_mute;

	@FXML
	private ImageView img_music;

	@FXML
	private ImageView btn_undo;

	@FXML
	private ImageView btn_redo;

	@FXML
	private Label label_gametime;

	@FXML
	private ImageView img_age;

	@FXML
	private ImageView img_direction;

	@FXML
	private Button btn_back;
	
	@FXML
	private HBox hbox_cards;

	@FXML
	private BorderPane borderpane;

	@FXML
	private ScrollPane scrollpane;

	private ArrayList<StackPane> boardPanes;
	private boolean action;

	private Timer timer;

	public GameBoardViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameboard/GameBoard.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		Main.primaryStage.setOnCloseRequest(event -> {
			Main.getSWController().getIOController().save(Main.getSWController().getGame());
		});

		scrollpane.setMinSize(1200, 800);
		hbox_cards.setAlignment(Pos.CENTER);
		
		btn_back.setOnAction(event -> {
			exit();
			Main.getSWController().getIOController().save(Main.getSWController().getGame());
			Main.getSWController().setGame(null);
			Main.primaryStage.getScene().setRoot(new MainMenuViewController());
			Main.primaryStage.setOnCloseRequest(null);
		});

		btn_undo.setOnMouseClicked(event -> Main.getSWController().getGameController().undo(Main.getSWController().getGame()));
		btn_redo.setOnMouseClicked(event -> Main.getSWController().getGameController().redo(Main.getSWController().getGame()));

		SoundController.addMuteFunction(btn_mute, img_music);

		Main.getSWController().getSoundController().stop(Sound.BACKGROUND_MENU);
		Main.getSWController().getSoundController().play(Sound.BACKGROUND_GAME, true);

		boardPanes = new ArrayList<>();
		generatePanes();
		refreshBoards();

		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Game game = Main.getSWController().getGame();
				game.nextSecond();
				Platform.runLater(() -> label_gametime
						.setText(String.format("%02d", game.getSeconds() / 3600) + ":" + String.format("%02d", (game.getSeconds() / 60) % 60) + ":" + String.format("%02d", game.getSeconds() % 60)));
			}
		}, 1000, 1000);
	}

	public void exit() {
		timer.cancel();
	}

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
			boardPanes.add(pane);
			right.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			right.getChildren().add(pane);

			borderpane.setRight(right);

			bottom = new HBox();
			pane = new StackPane();
			boardPanes.add(pane);
			bottom.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			bottom.getChildren().add(0, pane);

			bottom.setSpacing(75);
			bottom.setAlignment(Pos.CENTER);
			borderpane.setBottom(bottom);

			pane = new StackPane();
			boardPanes.add(pane);
			left.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			left.getChildren().add(0, pane);

			borderpane.setLeft(left);
			break;
		case 6:
			left = new VBox();
			right = new VBox();
			pane = new StackPane();

			boardPanes.add(pane);
			right.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			right.getChildren().add(pane);

			borderpane.setRight(right);

			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setBottom(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			left.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			left.getChildren().add(0, pane);

			borderpane.setLeft(left);
			break;
		case 5:
			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setRight(pane);

			bottom = new HBox();
			pane = new StackPane();
			boardPanes.add(pane);
			bottom.getChildren().add(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			bottom.getChildren().add(0, pane);

			bottom.setSpacing(75);
			bottom.setAlignment(Pos.CENTER);
			borderpane.setBottom(bottom);

			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setLeft(pane);
			break;
		case 4:
			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setRight(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setBottom(pane);

			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setLeft(pane);
			break;
		case 3:
			pane = new StackPane();
			boardPanes.add(pane);
			borderpane.setRight(pane);

			pane = new StackPane();
			boardPanes.add(pane);
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
			img_age.setImage(Utils.toImage(Main.TOKENS_PATH + "age" + game().getAge() + ".png"));
			img_direction.setImage(Utils.toImage(Main.DEFAULT_PATH + (game().getAge() == 2 ? "a" : "") + "clockwise.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setHandCards();
	}

	public void turn() {
		if (game().getCurrentPlayer() == game().getPlayers().size() - 1) {
			game().setCurrentPlayer(0);
		} else {
			game().setCurrentPlayer(game().getCurrentPlayer() + 1);
		}

		if (game().getCurrentPlayer() == game().getFirstPlayer()) {
			action = !action;

			if (!action) { // neue Spielrunde hat begonnen
				Main.getSWController().getGameController().createNextRound(Main.getSWController().getGame(), game());
				updateAllBoards();
			}
		} else
			game().setBeginOfRound(false);

		refreshBoards();

		if (action)
			setActionCard();
		else
			setHandCards();
	}

	private static GameState game() {
		return Main.getSWController().getGame().getCurrentGameState();
	}

	private static Player getCurrentPlayer() {
		return game().getPlayers().get(game().getCurrentPlayer());
	}

	private void updateAllBoards() {
		for (StackPane pane : boardPanes) {
			((Board) pane.getChildren().get(0)).updatePlayer();
		}
	}

	private static class Board extends VBox {
		private Player player;

		HBox hboxmilitary, hboxtrade, hboxscience, hboxguild, hboxcivil, hboxresources, hboxSlots;

		Label labelcoins, labelplayer;

		public Board(Player player) {
			this.player = player;

			BorderPane playerstats = new BorderPane();
			labelplayer = new Label();
			labelplayer.getStyleClass().addAll("fontstyle", "dropshadow");

			HBox hboxcoins = new HBox();
			labelcoins = new Label();
			labelcoins.getStyleClass().addAll("fontstyle", "dropshadow");
			ImageView imgcoin = new ImageView();
			try {
				imgcoin.setImage(Utils.toImage(Main.TOKENS_PATH + "coin.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			hboxcoins.getChildren().addAll(labelcoins, imgcoin);
			playerstats.setLeft(labelplayer);
			playerstats.setRight(hboxcoins);
			this.getChildren().add(playerstats);

			hboxresources = new HBox();
			hboxresources.setMaxHeight(35.0);
			this.getChildren().add(hboxresources);

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
			this.getChildren().add(grid);

			getChildren().add(hboxSlots);
		}

		private boolean isCurrentBoard() {
			return player.getName().equals(getCurrentPlayer().getName());
		}

		private void adaptSize(ImageView view) {
			view.setFitWidth(view.getImage().getWidth() * (isCurrentBoard() ? 40 : 30) / view.getImage().getHeight());
			view.setFitHeight(isCurrentBoard() ? 40 : 30);
			view.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 4, 0.5, 0, 0));
		}

		private void updatePlayer() {
			player = Main.getSWController().getPlayerController().getPlayer(player.getName());
		}

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
				Tooltip.install(resicon, new Tooltip(card.getDescription()));
				resicon.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px;");
				adaptSize(resicon);
				hboxresources.getChildren().add(resicon);
			}
			for (Card card : player.getBoard().getMilitary()) {
				ImageView milicon = new ImageView();
				milicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(milicon, new Tooltip(card.getDescription()));
				adaptSize(milicon);
				hboxmilitary.getChildren().add(milicon);
			}
			for (Card card : player.getBoard().getCivil()) {
				ImageView civilicon = new ImageView();
				civilicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(civilicon, new Tooltip(card.getDescription()));
				adaptSize(civilicon);
				hboxcivil.getChildren().add(0, civilicon);
			}
			for (Card card : player.getBoard().getResearch()) {
				ImageView scicon = new ImageView();
				scicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(scicon, new Tooltip(card.getDescription()));
				adaptSize(scicon);
				hboxscience.getChildren().add(0, scicon);
			}
			for (Card card : player.getBoard().getTrade()) {
				ImageView tricon = new ImageView();
				tricon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(tricon, new Tooltip(card.getDescription()));
				adaptSize(tricon);
				hboxtrade.getChildren().add(tricon);
			}
			for (Card card : player.getBoard().getGuilds()) {
				ImageView guildicon = new ImageView();
				guildicon.setImage(Main.getSWController().getCardController().getPreviewImage(card));
				Tooltip.install(guildicon, new Tooltip(card.getDescription()));
				adaptSize(guildicon);
				hboxguild.getChildren().add(0, guildicon);
			}

			for (int i = 0; i < 3 && player.getBoard().isFilled(i); i++) {
				try {
					ImageView img = new ImageView(Utils.toImage(Main.CARDS_PATH + "age" + (i + 1) + ".png"));
					img.setFitWidth(isCurrentBoard() ? 158 : 118.5);
					img.setFitHeight(isCurrentBoard() ? 80 : 60);
					hboxSlots.getChildren().add(img);
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public void setActionCard() {
		if (!action)
			return;
		Card card = getCurrentPlayer().getChosenCard();
		hbox_cards.getChildren().clear();

		boolean hasCard = Main.getSWController().getCardController().hasCard(getCurrentPlayer(), getCurrentPlayer().getChosenCard().getInternalName());

		int arrowWidth = 45, arrowHeight = 25;
		double scaleFactor = 1.2;

		try {
			StackPane outter = new StackPane();
			VBox vbox = new VBox();
			// Button Place Card
			if (!getCurrentPlayer().isOlympiaUsed()) {
				Button btnOlympia = new Button();
				HBox hboxOlympia = new HBox();
				ImageView img1 = new ImageView();
				img1.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
				img1.setFitWidth(arrowWidth);
				img1.setFitHeight(arrowHeight);
				ImageView img2 = new ImageView();
				img2.setImage(Utils.toImage(Main.TOKENS_PATH + "olympia.png"));
				img2.setFitWidth(40 * scaleFactor);
				img2.setFitHeight(40 * scaleFactor);
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
				btnOlympia.setOnAction(event -> { Main.getSWController().getCardController().placeCard(card, getCurrentPlayer(), null); getCurrentPlayer().setOlympiaUsed(true); turn(); });
				btnOlympia.setTooltip(new Tooltip("Olympia-Fähigkeit:\nBaue diese Karte kostenlos"));
				vbox.getChildren().add(btnOlympia);
				btnOlympia.setDisable(hasCard);
			}
			Button btn_place = new Button();
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
			btn_place.setGraphic(hbox_place);
			btn_place.hoverProperty().addListener((obs, oldVal, newValue) -> {
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
			btn_place.setOnAction(event -> {
				switch (Main.getSWController().getPlayerController().canBuild(getCurrentPlayer(), card)) {
				case FREE:
				case OWN_RESOURCE:
					Main.getSWController().getCardController().placeCard(card, getCurrentPlayer(), null);
					turn();
					break;
				case TRADE:
					ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(getCurrentPlayer(), card.getRequired());
					VBox tradeNodes = new VBox();
					for (TradeOption option : trades) {
						tradeNodes.getChildren().add(option.getNode(getCurrentPlayer(), event2 -> { Main.getSWController().getCardController().placeCard(card, getCurrentPlayer(), option); turn(); }));
					}
					hbox_cards.getChildren().add(tradeNodes);
					outter.getChildren().remove(vbox);
					break;
				default:
					break;
				}
			});
			btn_place.setDisable(hasCard || Main.getSWController().getPlayerController().canBuild(getCurrentPlayer(), card) == BuildCapability.NONE);

			// Button place card on WonderBoard
			Button btn_wonder = new Button();
			HBox hbox_wonder = new HBox();
			ImageView img3 = new ImageView();
			img3.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
			img3.setFitWidth(arrowWidth);
			img3.setFitHeight(arrowHeight);
			ImageView img4 = new ImageView();
			img4.setImage(Utils.toImage(Main.TOKENS_PATH + "pyramid-stage" + (getCurrentPlayer().getBoard().nextSlot() + 1) + ".png"));
			img4.setFitWidth(45 * scaleFactor);
			img4.setFitHeight(35 * scaleFactor);
			hbox_wonder.getChildren().add(img3);
			hbox_wonder.getChildren().add(img4);
			hbox_wonder.setAlignment(Pos.CENTER_LEFT);
			hbox_wonder.setSpacing(5);
			btn_wonder.setGraphic(hbox_wonder);
			btn_wonder.hoverProperty().addListener((obs, oldVal, newValue) -> {
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
			btn_wonder.setOnAction(event -> {
				switch (Main.getSWController().getPlayerController().hasResources(getCurrentPlayer(),
						new ArrayList<>(Arrays.asList(getCurrentPlayer().getBoard().getSlotResquirement(getCurrentPlayer().getBoard().nextSlot()))))) {
				case FREE:
				case OWN_RESOURCE:
					Main.getSWController().getCardController().setSlotCard(card, getCurrentPlayer(), null);
					turn();
					break;
				case TRADE:
					ArrayList<TradeOption> trades = Main.getSWController().getPlayerController().getTradeOptions(getCurrentPlayer(),
							new ArrayList<>(Arrays.asList(getCurrentPlayer().getBoard().getSlotResquirement(getCurrentPlayer().getBoard().nextSlot()))));
					VBox tradeNodes = new VBox();
					for (TradeOption option : trades) {
						tradeNodes.getChildren()
								.add(option.getNode(getCurrentPlayer(), event2 -> { Main.getSWController().getCardController().setSlotCard(card, getCurrentPlayer(), option); turn(); }));
					}
					hbox_cards.getChildren().add(tradeNodes);
					outter.getChildren().remove(vbox);
					break;
				default:
					break;
				}
			});
			btn_wonder.setDisable(getCurrentPlayer().getBoard().isFilled(2) || Main.getSWController().getPlayerController().hasResources(getCurrentPlayer(),
					new ArrayList<>(Arrays.asList(getCurrentPlayer().getBoard().getSlotResquirement(getCurrentPlayer().getBoard().nextSlot())))) == BuildCapability.NONE);

			// Button card sell
			Button btn_sell = new Button();
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
			btn_sell.setGraphic(hbox_sell);
			btn_sell.hoverProperty().addListener((obs, oldVal, newValue) -> {
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
			btn_sell.setOnAction(e -> { Main.getSWController().getCardController().sellCard(card, getCurrentPlayer()); turn(); });

			vbox.getChildren().add(btn_place);
			vbox.getChildren().add(btn_wonder);
			vbox.getChildren().add(btn_sell);
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(5);
			vbox.setStyle("-fx-background-color: #d9d9d999");
			outter.getChildren().add(vbox);
			outter.setBackground(new Background(new BackgroundImage(Utils.toImage(card.getImage()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					new BackgroundSize(1, 1, false, false, true, false))));
			outter.setMinSize(141, 215);
			outter.setPrefSize(141, 215);
			hbox_cards.getChildren().add(outter);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setHandCards() {
		if (action)
			return;
		Player player = getCurrentPlayer();
		ArrayList<Card> hand = player.getHand();

		hbox_cards.getChildren().clear();
		hbox_cards.setSpacing(10);

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
					tooltip = "Du kannst die erforderlichen Resourcen von Nachbarstädten kaufen";
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
			Tooltip.install(img, new Tooltip(tooltip));
			vbox.getChildren().addAll(img, btn);
			vbox.setAlignment(Pos.CENTER_RIGHT);
			hbox_cards.getChildren().add(vbox);

			int cardIndex = i;

			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (!action) {
						Main.getSWController().getSoundController().play(Sound.CHOOSE_CARD);
						getCurrentPlayer().setChooseCard(player.getHand().get(cardIndex));
					} else {
						getCurrentPlayer().setChooseCard(null);
					}
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
			btn.setTooltip(new Tooltip(card.getDescription()));
			ImageView img2 = (ImageView) btn.getGraphic();
			try {
				img.setImage(Utils.toImage(path + ".png"));
				img2.setImage(Utils.toImage(card.getImage()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void selectCardFromTrash(Player player) {

	}
}