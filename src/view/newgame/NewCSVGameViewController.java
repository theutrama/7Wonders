package view.newgame;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import application.Utils;
import controller.GameController;
import controller.IOController;
import controller.PlayerController;
import controller.SevenWondersController;
import controller.sound.Sound;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Game;
import model.GameState;
import model.card.Card;
import model.player.Player;
import model.player.ai.Difficulty;
import view.gameboard.GameBoardViewController;

public class NewCSVGameViewController extends NewGameViewController {

	private ArrayList<Card> cardStack;
	
	public void setCardStack(ArrayList<Card> cardStack) {
		this.cardStack=cardStack;
	}
	
	public void addPlayer() {
		error("Du kannst keine neuen Spieler hinzufügen!");
	}
	
	@SuppressWarnings("unchecked")
	public void addPlayer(String playername, String wondername, boolean KI) throws IOException {
		HBox box = this.addPlayer(playername);
		addWonderToPlayer(box, wondername);
		((ComboBox<String>)box.getChildren().get(1)).getSelectionModel().select( (KI ? 1 : 0) );
	}
	
	private void addWonderToPlayer(HBox player, String wondername) {
		Label wonder = null;
		for(Node node : this.vbox_wonders.getChildren()) {
			if(node instanceof Label) {
				wonder = (Label)node;
				
				if(wonder.getText().equalsIgnoreCase(wondername)) 
					break;
				else 
					wonder=null;
			}
		}
		
		if(wonder != null) {
			vbox_wonders.getChildren().remove(wonder);
			this.addWonderToPlayer(player, wonder);
		}else System.out.println("Das Wunder "+wondername+" konnte nicht gefunden werden!");
	}
	
	protected void done() {
		SevenWondersController con = Main.getSWController();
		GameController game_con = con.getGameController();
		con.getSoundController().play(Sound.BUTTON_CLICK);

		if (players.size() <= 1) {
			error("Es muessen mindestens 2 Spieler mitspielen!");
			return;
		}

		// Wenn ein Spieler kein Wonder ausgewaehlt hat wird ihn nun eins zugewiesen
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
		
		String name = textfield_gamename.getText();
		Game game = new Game( name!=null && name.isBlank() ? "KI-Turnier" : name );
		con.setGame(game);

		GameState state;
		game_con.nextAge(game, state = new GameState(0, 1, game_players, cardStack));
		game.getStates().add(state);
		game.getCurrentGameState().setFirstPlayer(0);
		game.getCurrentGameState().setCurrentPlayer(0);
		
		Main.primaryStage.getScene().setRoot(new GameBoardViewController());
	}
}
