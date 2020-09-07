package controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import application.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {

	private boolean mute = false;

	private ArrayList<MediaPlayer> players;
	
	public SoundController() {
		players = new ArrayList<MediaPlayer>();
	}

	public void play(String sound) {
		try {
			MediaPlayer player = new MediaPlayer(
					new Media(getClass().getResource("../sounds/" + sound).toURI().toString()));
			player.setOnEndOfMedia(() -> players.remove(player));
			if (!mute)
				player.play();
			players.add(player);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public boolean mute() {
		if (mute)
			for (MediaPlayer player : players)
				player.play();
		else
			for (MediaPlayer player: players)
				player.stop();
		return (mute ^= true);
	}

	public boolean isMuted() {
		return mute;
	}
	
	
	public static void addMuteFunction(Button btn, ImageView imgv) {
		btn.setOnAction(event -> {
			Main.getSWController().getSoundController().mute();
			updateMuteIcon(imgv);
		});
		updateMuteIcon(imgv);
	}
	
	public static void updateMuteIcon(ImageView imgv) {
		if (Main.getSWController().getSoundController().isMuted())
			imgv.setImage(new Image(SoundController.class.getResourceAsStream("../view/images/musicoff.png")));
		else
			imgv.setImage(new Image(SoundController.class.getResourceAsStream("../view/images/music.png")));
	}

}
