package view.gameboard;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.player.Player;

public class GameBoardViewController extends VBox {
    @FXML
    private ImageView btn_undo1;

    @FXML
    private ImageView btn_undo;

    @FXML
    private ImageView btn_redo;

    @FXML
    private Label label_gametime;

    @FXML
    private Label label_age;

    @FXML
    private HBox hbox_cards;

    @FXML
    private VBox vbox_board1;

    @FXML
    private HBox hbox_board1_ressources;

    @FXML
    private HBox hbox_board1_military;

    @FXML
    private HBox hbox_board1_civil;

    @FXML
    private HBox hbox_board1_trade;

    @FXML
    private HBox hbox_board1_uni;

    @FXML
    private HBox hbox_board1_guild;

    @FXML
    private ImageView img_boardcard1_12;

    @FXML
    private ImageView img_boardcard1_121;

    @FXML
    private ImageView img_boardcard1_122;

    @FXML
    private VBox vbox_board6;

    @FXML
    private HBox hbox_board6_ressources;

    @FXML
    private HBox hbox_board6_military;

    @FXML
    private HBox hbox_board6_civil;

    @FXML
    private HBox hbox_board6_trade;

    @FXML
    private HBox hbox_board6_uni;

    @FXML
    private HBox hbox_board6_guild;

    @FXML
    private ImageView img_boardcard6_1;

    @FXML
    private ImageView img_boardcard6_2;

    @FXML
    private ImageView img_boardcard6_3;

    @FXML
    private VBox vbox_board7;

    @FXML
    private HBox hbox_board7_ressources;

    @FXML
    private HBox hbox_board7_military;

    @FXML
    private HBox hbox_board7_civil;

    @FXML
    private HBox hbox_board7_trade;

    @FXML
    private HBox hbox_board7_uni;

    @FXML
    private HBox hbox_board7_guild;

    @FXML
    private ImageView img_boardcard7_1;

    @FXML
    private ImageView img_boardcard7_2;

    @FXML
    private ImageView img_boardcard7_3;

    @FXML
    private VBox vbox_board2;

    @FXML
    private HBox hbox_board2_ressources;

    @FXML
    private HBox hbox_board2_military;

    @FXML
    private HBox hbox_board2_civil;

    @FXML
    private HBox hbox_board2_trade;

    @FXML
    private HBox hbox_board2_uni;

    @FXML
    private HBox hbox_board2_guild;

    @FXML
    private ImageView img_boardcard2_1;

    @FXML
    private ImageView img_boardcard2_2;

    @FXML
    private ImageView img_boardcard2_3;

    @FXML
    private VBox vbox_board4;

    @FXML
    private HBox hbox_board4_ressources;

    @FXML
    private HBox hbox_board4_military;

    @FXML
    private HBox hbox_board4_civil;

    @FXML
    private HBox hbox_board4_trade;

    @FXML
    private HBox hbox_board4_uni;

    @FXML
    private HBox hbox_board4_guild;

    @FXML
    private ImageView img_boardcard4_1;

    @FXML
    private ImageView img_boardcard4_2;

    @FXML
    private ImageView img_boardcard4_3;

    @FXML
    private VBox vbox_board3;

    @FXML
    private HBox hbox_board3_ressources;

    @FXML
    private HBox hbox_board3_military;

    @FXML
    private HBox hbox_board3_civil;

    @FXML
    private HBox hbox_board3_trade;

    @FXML
    private HBox hbox_board3_uni;

    @FXML
    private HBox hbox_board3_guild;

    @FXML
    private ImageView img_boardcard3_1;

    @FXML
    private ImageView img_boardcard3_2;

    @FXML
    private ImageView img_boardcard3_3;

    @FXML
    private VBox vbox_board5;

    @FXML
    private HBox hbox_board5_ressources;

    @FXML
    private HBox hbox_board5_military;

    @FXML
    private HBox hbox_board5_civil;

    @FXML
    private HBox hbox_board5_trade;

    @FXML
    private HBox hbox_board5_uni;

    @FXML
    private HBox hbox_board5_guild;

    @FXML
    private ImageView img_boardcard5_1;

    @FXML
    private ImageView img_boardcard5_2;

    @FXML
    private ImageView img_boardcard5_3;
    
    private final int PLAYER = 6; //@TODO: DELETE AFTER IMPLEMENTING CONTROLLER

    
	public GameBoardViewController() {
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameboard/GameBoard.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		loadBoards(PLAYER);
	}
	
	
	public void selectCardFromTrash(Player player) {
		
	}


	private void loadBoards(int count) {
		switch(count) {
		case 2:
			vbox_board3.getChildren().clear();
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			vbox_board6.getChildren().clear();
			vbox_board7.getChildren().clear();
			break;
		case 3:
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			vbox_board6.getChildren().clear();
			vbox_board7.getChildren().clear();
			break;
		case 4:
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			vbox_board7.getChildren().clear();
			HBox parent1 = (HBox) vbox_board7.getParent();
			parent1.getChildren().remove(vbox_board7);
			break;
		case 5:
			vbox_board4.getChildren().clear();
			vbox_board5.getChildren().clear();
			break;
		case 6:
			vbox_board7.getChildren().clear();
			HBox parent2 = (HBox) vbox_board7.getParent();
			parent2.getChildren().remove(vbox_board7);
			break;
		}
	}
}
