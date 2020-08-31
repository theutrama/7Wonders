package view.gameboard;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class GameBoardViewController extends VBox {
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
    private ImageView img_board1;

    @FXML
    private ImageView img_boardcard1_1;

    @FXML
    private ImageView img_boardcard1_2;

    @FXML
    private ImageView img_boardcard1_3;

    @FXML
    private VBox vbox_board2;

    @FXML
    private ImageView img_board2;

    @FXML
    private ImageView img_boardcard2_1;

    @FXML
    private ImageView img_boardcard2_2;

    @FXML
    private ImageView img_boardcard2_3;

    @FXML
    private VBox vbox_board4;

    @FXML
    private ImageView img_board4;

    @FXML
    private ImageView img_boardcard4_1;

    @FXML
    private ImageView img_boardcard4_2;

    @FXML
    private ImageView img_boardcard4_3;

    @FXML
    private VBox vbox_board6;

    @FXML
    private ImageView img_board6;

    @FXML
    private ImageView img_boardcard6_1;

    @FXML
    private ImageView img_boardcard6_2;

    @FXML
    private ImageView img_boardcard6_3;

    @FXML
    private VBox vbox_board7;

    @FXML
    private ImageView img_board7;

    @FXML
    private ImageView img_boardcard7_1;

    @FXML
    private ImageView img_boardcard7_2;

    @FXML
    private ImageView img_boardcard7_3;

    @FXML
    private VBox vbox_board3;

    @FXML
    private ImageView img_board3;

    @FXML
    private ImageView img_boardcard3_1;

    @FXML
    private ImageView img_boardcard3_2;

    @FXML
    private ImageView img_boardcard3_3;

    @FXML
    private VBox vbox_board5;

    @FXML
    private ImageView img_board5;

    @FXML
    private ImageView img_boardcard5_1;

    @FXML
    private ImageView img_boardcard5_2;

    @FXML
    private ImageView img_boardcard5_3;
    
	public GameBoardViewController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameboard/GameBoard.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
