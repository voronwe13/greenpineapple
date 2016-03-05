package com.greenpineapple.sound;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GPASound {
	private Map<String, Sound> soundeffects;
	private Map<String, Music> songs;
	private float soundeffectvolume = 1.0f, musicvolume = 1.0f;
	
	public void playSound(String soundname){
		soundeffects.get(soundname).play(soundeffectvolume);
	}
	
	public void playSong(String songname){
		songs.get(songname).play();
	}
	
	
	
}
