package view.gameboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import application.Utils;
import controller.utils.BuildCapability;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Game;
import model.card.Card;
import model.player.Player;

public class GameBoardViewController extends VBox {
    @FXML
    public ImageView btn_undo1;

    @FXML
    public ImageView btn_undo;

    @FXML
    public ImageView btn_redo;

    @FXML
    public Label label_gametime;

    @FXML
    public Label label_age;

    @FXML
    public HBox hbox_cards;

    @FXML
    public VBox vbox_board1;

    @FXML
    public HBox hbox_board1_ressources;

    @FXML
    public HBox hbox_board1_military;

    @FXML
    public HBox hbox_board1_civil;

    @FXML
    public HBox hbox_board1_trade;

    @FXML
    public HBox hbox_board1_uni;

    @FXML
    public HBox hbox_board1_guild;

    @FXML
    public ImageView img_boardcard1_1;

    @FXML
    public ImageView img_boardcard1_2;

    @FXML
    public ImageView img_boardcard1_3;

    @FXML
    public VBox vbox_board6;

    @FXML
    public HBox hbox_board6_ressources;

    @FXML
    public HBox hbox_board6_military;

    @FXML
    public HBox hbox_board6_civil;

    @FXML
    public HBox hbox_board6_trade;

    @FXML
    public HBox hbox_board6_uni;

    @FXML
    public HBox hbox_board6_guild;

    @FXML
    public ImageView img_boardcard6_1;

    @FXML
    public ImageView img_boardcard6_2;

    @FXML
    public ImageView img_boardcard6_3;

    @FXML
    public VBox vbox_board7;

    @FXML
    public HBox hbox_board7_ressources;

    @FXML
    public HBox hbox_board7_military;

    @FXML
    public HBox hbox_board7_civil;

    @FXML
    public HBox hbox_board7_trade;

    @FXML
    public HBox hbox_board7_uni;

    @FXML
    public HBox hbox_board7_guild;

    @FXML
    public ImageView img_boardcard7_1;

    @FXML
    public ImageView img_boardcard7_2;

    @FXML
    public ImageView img_boardcard7_3;

    @FXML
    public VBox vbox_board2;

    @FXML
    public HBox hbox_board2_ressources;

    @FXML
    public HBox hbox_board2_military;

    @FXML
    public HBox hbox_board2_civil;

    @FXML
    public HBox hbox_board2_trade;

    @FXML
    public HBox hbox_board2_uni;

    @FXML
    public HBox hbox_board2_guild;

    @FXML
    public ImageView img_boardcard2_1;

    @FXML
    public ImageView img_boardcard2_2;

    @FXML
    public ImageView img_boardcard2_3;

    @FXML
    public VBox vbox_board4;

    @FXML
    public HBox hbox_board4_ressources;

    @FXML
    public HBox hbox_board4_military;

    @FXML
    public HBox hbox_board4_civil;

    @FXML
    public HBox hbox_board4_trade;

    @FXML
    public HBox hbox_board4_uni;

    @FXML
    public HBox hbox_board4_guild;

    @FXML
    public ImageView img_boardcard4_1;

    @FXML
    public ImageView img_boardcard4_2;

    @FXML
    public ImageView img_boardcard4_3;

    @FXML
    public VBox vbox_board3;

    @FXML
    public HBox hbox_board3_ressources;

    @FXML
    public HBox hbox_board3_military;

    @FXML
    public HBox hbox_board3_civil;

    @FXML
    public HBox hbox_board3_trade;

    @FXML
    public HBox hbox_board3_uni;

    @FXML
    public HBox hbox_board3_guild;

    @FXML
    public ImageView img_boardcard3_1;

    @FXML
    public ImageView img_boardcard3_2;

    @FXML
    public ImageView img_boardcard3_3;

    @FXML
    public VBox vbox_board5;

    @FXML
    public HBox hbox_board5_ressources;

    @FXML
    public HBox hbox_board5_military;

    @FXML
    public HBox hbox_board5_civil;

    @FXML
    public HBox hbox_board5_trade;

    @FXML
    public HBox hbox_board5_uni;

    @FXML
    public HBox hbox_board5_guild;

    @FXML
    public ImageView img_boardcard5_1;

    @FXML
    public ImageView img_boardcard5_2;

    @FXML
    public ImageView img_boardcard5_3;
    private ArrayList<Board> boards = new ArrayList<Board>();
    private Card[] currentCards = new Card[7];
    private boolean choose = true;
    private Player firstPlayer;
    
    private final String imagePath = "src"+File.separator+"view"+File.separator+"images"+File.separator+"tokens"+File.separator;
    private final int ACTION_CARD_SLOT = 3;
    
    public Board createBoard(Player player, int i) {
    	VBox vbox_board = Utils.getValue(this, "vbox_board"+i);
    	HBox hbox_board_ressources = Utils.getValue(this, "hbox_board"+i+"_ressources");
    	HBox hbox_board_military = Utils.getValue(this, "hbox_board"+i+"_military");
    	HBox hbox_board_civil = Utils.getValue(this, "hbox_board"+i+"_civil");
    	HBox hbox_board_trade = Utils.getValue(this, "hbox_board"+i+"_trade");
    	HBox hbox_board_uni = Utils.getValue(this, "hbox_board"+i+"_uni");
    	HBox hbox_board_guild = Utils.getValue(this, "hbox_board"+i+"_guild");
    	ImageView img_boardcard1 = Utils.getValue(this, "img_boardcard"+i+"_1");
    	ImageView img_boardcard2 = Utils.getValue(this, "img_boardcard"+i+"_2");
    	ImageView img_boardcard3 = Utils.getValue(this, "img_boardcard"+i+"_3");
    	
    	return Board.create(player, 
				vbox_board, 
				hbox_board_ressources, 
				hbox_board_military,
				hbox_board_civil, 
				hbox_board_trade, 
				hbox_board_uni, 
				hbox_board_guild, 
				img_boardcard1, 
				img_boardcard2, 
				img_boardcard3);
    }
    
	public GameBoardViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameboard/GameBoard.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		setup();
	}
	
	public void setActionCard() {
		if(choose)return;
		Player player = this.boards.get(0).getPlayer();
		Card card = player.getChosenCard();
		
		try {
			Button btn = (Button) hbox_cards.getChildren().get(ACTION_CARD_SLOT);
			VBox vbox = new VBox();
			//Button Place Card
			Button btn_place = new Button();
			HBox hbox_place = new HBox();
			ImageView img1 = new ImageView();
			img1.setImage(Utils.toImage(imagePath+"arrowgrey.png"));
			img1.setFitWidth(38);
			img1.setFitHeight(20);
			ImageView img2 = new ImageView();
			img2.setImage(Utils.toImage(imagePath+"place.png"));
			img2.setFitWidth(45);
			img2.setFitHeight(30);
			hbox_place.getChildren().add(img1);
			hbox_place.getChildren().add(img2);
			hbox_place.setAlignment(Pos.CENTER_LEFT);
			hbox_place.setSpacing(5);
			btn_place.setGraphic(hbox_place);
			btn_place.hoverProperty().addListener((obs, oldVal, newValue) -> {
				try {
		            if (newValue) {
						img1.setImage(Utils.toImage(imagePath+"arrowhover.png"));
		            } else {
		            	img1.setImage(Utils.toImage(imagePath+"arrowgrey.png"));
		            }
				} catch (IOException e) {
					e.printStackTrace();
				}
	        });
			
			
			//Button place card on WonderBoard
			Button btn_wonder = new Button();
			HBox hbox_wonder = new HBox();
			ImageView img3 = new ImageView();
			img3.setImage(Utils.toImage(imagePath+"arrowgrey.png"));
			img3.setFitWidth(38);
			img3.setFitHeight(20);
			ImageView img4 = new ImageView();
			img4.setImage(Utils.toImage(imagePath+"pyramid-stage3.png"));
			img4.setFitWidth(45);
			img4.setFitHeight(35);
			hbox_wonder.getChildren().add(img3);
			hbox_wonder.getChildren().add(img4);
			hbox_wonder.setAlignment(Pos.CENTER_LEFT);
			hbox_wonder.setSpacing(5);
			btn_wonder.setGraphic(hbox_wonder);
			btn_wonder.hoverProperty().addListener((obs, oldVal, newValue) -> {
				try {
		            if (newValue) {
						img3.setImage(Utils.toImage(imagePath+"arrowhover.png"));
		            } else {
		            	img3.setImage(Utils.toImage(imagePath+"arrowgrey.png"));
		            }
				} catch (IOException e) {
					e.printStackTrace();
				}
	        });
			
			//Button card sell
			Button btn_sell = new Button();
			HBox hbox_sell = new HBox();
			ImageView img5 = new ImageView();
			img5.setImage(Utils.toImage(imagePath+"arrowgrey.png"));
			img5.setFitWidth(38);
			img5.setFitHeight(20);
			ImageView img6 = new ImageView();
			img6.setImage(Utils.toImage(imagePath+"coin3.png"));
			HBox.setMargin(img6, new Insets(0, 5, 0, 0));
			img6.setFitWidth(35);
			img6.setFitHeight(33);
			hbox_sell.getChildren().add(img5);
			hbox_sell.getChildren().add(img6);
			hbox_sell.setAlignment(Pos.CENTER_LEFT);
			hbox_sell.setSpacing(14);
			btn_sell.setGraphic(hbox_sell);
			btn_sell.hoverProperty().addListener((obs, oldVal, newValue) -> {
				try {
		            if (newValue) {
						img5.setImage(Utils.toImage(imagePath+"arrowhover.png"));
		            } else {
		            	img5.setImage(Utils.toImage(imagePath+"arrowgrey.png"));
		            }
				} catch (IOException e) {
					e.printStackTrace();
				}
	        });
			
			vbox.getChildren().add(btn_place);
			vbox.getChildren().add(btn_wonder);
			vbox.getChildren().add(btn_sell);
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(5);
			btn.setGraphic(vbox);
			vbox.setBackground(new Background(new BackgroundImage(Utils.toImage(card.getImage()), 
					BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(1,1,false,false, true, false))));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setHandCards() {
		if(!choose)return;
		Player player = Main.getSWController().getGame().getCurrentPlayer();
		ArrayList<Card> hand = player.getHand();

		Card card;
		for(int i = 0; i < hand.size(); i++) {
			card = hand.get(i);
			
			BuildCapability capability = Main.getSWController().getPlayerController().canBuild(player, card);
			String path = imagePath;
			switch(capability) {
			case FREE:
				path += "free";
				break;
			case OWN_RESOURCE:
				path += "check";
				break;
			case TRADE:
				path += "checkyellow";
				break;
			case NONE:
				path += "cross";
				break;
			}
			
			System.out.println("SET CURRENT CARD "+i+" "+card.getName());
			currentCards[i] = card;
			Button btn = (Button) hbox_cards.getChildren().get(i);
			
			System.out.println("BTN ");
			
			if(!(btn.getGraphic() instanceof ImageView)) {
				ImageView view = new ImageView();
				view.setFitHeight(150);
				view.setFitWidth(200);
				view.setPickOnBounds(true);
				view.setPreserveRatio(true);
				btn.setGraphic(view);
			}
			btn.setTooltip(new Tooltip(card.getDescription()));
			ImageView img = (ImageView) btn.getGraphic();
			try {
				img.setImage(Utils.toImage(card.getImage()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setup() {
		Game game = Main.getSWController().getGame();
		int[] ids = loadBoards(game.getCurrentGameState().getPlayers().size());
		int index = 0;
		firstPlayer = game.getCurrentGameState().getPlayers().get(0);
		for(Player player : game.getCurrentGameState().getPlayers()) {
			this.boards.add(createBoard(player, ids[index]));
			index++;
		}
		
		for(int i = 0; i < 7; i++) {
			Button btn = (Button) hbox_cards.getChildren().get(i);
			
			final int a = i;
			btn.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if( choose ) {
						getCurrentPlayer().setChooseCard(currentCards[a]);
					} else {
						getCurrentPlayer().setChooseCard(null);
					}
					turnToNext();
				}
			} );
			
		}
		
		for(Board board : this.boards) {
			board.refresh();
		}
		Main.getSWController().getGame().setCurrentPlayer(this.boards.get(0).getPlayer());
		System.out.println("SetHandCards start");
		setHandCards();
	}
	
	public Player getCurrentPlayer() {
		return Main.getSWController().getGame().getCurrentPlayer();
	}
	
	public void selectCardFromTrash(Player player) {
		
	}
	
	private static class Board {
		public static Board create(Player player,
				VBox vbox_board, 
				HBox vbox_res, 
				HBox vbox_mili, 
				HBox vbox_civil, 
				HBox vbox_trade, 
				HBox vbox_science, 
				HBox vbox_guild,
				ImageView img_card1,
				ImageView img_card2,
				ImageView img_card3) {
			return new Board(player, vbox_board, new HBox[] {vbox_res,vbox_mili,vbox_civil,vbox_trade,vbox_science,vbox_guild}, new ImageView[] {img_card1,img_card2,img_card3});
		}
		
		private static final int RESOURCE = 0;
		private static final int MILITARY = 1;
		private static final int CIVIL = 2;
		private static final int TRADE = 3;
		private static final int SCIENCE = 4;
		private static final int GUILD = 5;
		
		public Player player;
	    private VBox box;
	    private HBox[] board_boxes;
	    private ImageView[] slots;
		
		private Board(Player player,VBox box, HBox[] boxes, ImageView[] imgs) {
			this.player = player;
			this.box = box;
			this.board_boxes = boxes;
			this.slots = imgs;
		}
		
		public void refresh() {
			setBackground();
		}
		
		private void setBackground(){
			try {
				GridPane pane = (GridPane) this.box.getChildren().get(1);
				Image img = Utils.toImage(this.player.getBoard().getImage());
				pane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(img.getWidth(), img.getHeight(), false,  false, true,  false))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public VBox getBox() {
			return box;
		}
		
		public void setPlayer(Player player) {
			this.player=player;
		}
		
		public Player getPlayer() {
			return player;
		}
		
		public ImageView getSlot(int index) {
			return this.slots[index];
		}
		
		public HBox getBoard(int index) {
			return this.board_boxes[index];
		}
	}
	
	public void turnToNext() {
		Player last = this.boards.get(0).getPlayer();
		Player next = null;
		for(int i = 1; i < this.boards.size(); i++) {
			next = this.boards.get(i).getPlayer();
			this.boards.get(i).setPlayer(last);
			this.boards.get(i).refresh();
			last = next;
			
			if(i == this.boards.size()-1) {
				this.boards.get(0).setPlayer(last);
				this.boards.get(0).refresh();
			}
		}
		
		if(this.boards.get(0).getPlayer().getName().equalsIgnoreCase(firstPlayer.getName())) {
			choose = !choose;
			
			for(int i = 0; i < 7; i++) {
				Node n = hbox_cards.getChildren().get(i);
				if(i!=ACTION_CARD_SLOT) {
					n.setVisible(choose);
				}else n.setVisible(true);
			}
			
			
			if(choose)
				System.out.println("Switch to choose");
			else
				System.out.println("Switch to action");
		}
		
		System.out.println("CURRENT: "+this.boards.get(0).getPlayer().getName()+" "+this.boards.get(0).getPlayer().getBoard().getBoardName());
		Main.getSWController().getGame().setCurrentPlayer(this.boards.get(0).getPlayer());
		
		if(choose) {
			setHandCards();
		}else {
			setActionCard();
		}
	}

	private int[] loadBoards(int count) {
		switch(count) {
		case 2:
			vbox_board3.getChildren().clear();
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			vbox_board6.getChildren().clear();
			vbox_board7.getChildren().clear();
			return new int[] {2,1};
		case 3:
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			vbox_board6.getChildren().clear();
			vbox_board7.getChildren().clear();
			return new int[] {3,2,1};
		case 4:
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			vbox_board7.getChildren().clear();
			HBox parent1 = (HBox) vbox_board7.getParent();
			parent1.getChildren().remove(vbox_board7);
			return new int[] {6,3,2,1};
		case 5:
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			return new int[] {7,6,3,2,1};
		case 6:
			vbox_board7.getChildren().clear();
			HBox parent2 = (HBox) vbox_board7.getParent();
			parent2.getChildren().remove(vbox_board7);
			return new int[] {6,5,4,3,2,1};
		}
		return new int[] {7,6,5,4,3,2,1};
	}
}
