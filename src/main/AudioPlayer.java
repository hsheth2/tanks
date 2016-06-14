package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

public class AudioPlayer {
	public static void play(String sound) {
		Clip c;
		AudioInputStream ais;

		try {
			c = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(new File("assets/audio/" + sound));

			c.open(ais);
			c.start();
			c.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == Type.STOP) {
						try {
							c.close();
							ais.close();
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("non-fatal error in sound system");
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
