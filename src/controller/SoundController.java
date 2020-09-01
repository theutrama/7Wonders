package controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {

	private boolean mute = false;

	private ArrayList<MediaPlayer> players;

	public void play(String sound) {
		try {
			MediaPlayer player = new MediaPlayer(
					new Media(getClass().getResource("sounds/" + sound).toURI().toString()));
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

}
