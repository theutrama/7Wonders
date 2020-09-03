package view.result;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import controller.SoundController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.player.Player;
import view.menu.MainMenuViewController;

public class ResultViewController extends BorderPane {

	@FXML
	private TableView<Stats> table_result;

	@FXML
	private TableColumn<Stats, Integer> col_rank;

	@FXML
	private TableColumn<Stats, String> col_name;

	@FXML
	private TableColumn<Stats, Integer> col_victoryPoints;

	@FXML
	private TableColumn<Stats, Integer> col_conflictPoints;

	@FXML
	private TableColumn<Stats, Integer> col_losePoints;

	@FXML
	private TableColumn<Stats, Integer> col_coins;

	@FXML
	private Button btn_mute;

	@FXML
	private ImageView img_music;

	@FXML
	private Button btn_ok;

	public ResultViewController(ArrayList<Player> players) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/result/Result.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		btn_ok.setOnAction(e -> { Main.primaryStage.getScene().setRoot(new MainMenuViewController()); });

		col_rank.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRank()));
		col_name.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayer().getName()));
		col_victoryPoints.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayer().getVictoryPoints()));
		col_conflictPoints.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayer().getConflictPoints()));
		col_losePoints.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayer().getLosePoints()));
		col_coins.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPlayer().getCoins()));

		col_rank.setReorderable(false);
		col_name.setReorderable(false);
		col_victoryPoints.setReorderable(false);
		col_conflictPoints.setReorderable(false);
		col_losePoints.setReorderable(false);
		col_coins.setReorderable(false);

		table_result.setEditable(false);

		col_rank.setSortType(SortType.ASCENDING);

		SoundController.addMuteFunction(btn_mute, img_music);

		players.sort((p1, p2) -> p2.getVictoryPoints() - p1.getVictoryPoints());

		ArrayList<Stats> stats = new ArrayList<>();
		int rank = 1;
		for (Player player : players) {
			stats.add(new Stats(player, rank));
			rank++;
		}
	}

	private static class Stats {
		private Player player;
		private int rank;

		public Stats(Player player, int rank) {
			this.player = player;
			this.rank = rank;
		}

		public int getRank() {
			return rank;
		}

		public Player getPlayer() {
			return player;
		}
	}

}
