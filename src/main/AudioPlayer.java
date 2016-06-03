package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioPlayer {
	public static void play(String sound) {
		Clip c;
		AudioInputStream ais;
		
		try {
			c = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(new File("assets/audio/" + sound));
			
			c.open(ais);
			c.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
