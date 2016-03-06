package com.greenpineapple.sound;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GPASound {
	private Map<String, Sound> soundeffects;
	private Map<String, Music> songs;
	private float soundeffectvolume = 1.0f, musicvolume = 1.0f;
	
	private static GPASound soundinstance = null;

	public void playSound(String soundname){
		soundeffects.get(soundname).play(soundeffectvolume);
	}
	
	public void stopSound(String soundname){
		soundeffects.get(soundname).stop();
	}
	
	public void playSong(String songname){
		songs.get(songname).play();
	}
	
	public void stopSong(String songname){
		songs.get(songname).stop();
	}
	
	public static GPASound getInstance(){
		if(soundinstance == null)
			soundinstance = new GPASound();
		return soundinstance;
	}
	
	public void addSoundEffect(String soundpath, String soundname){
		
	}
	
	public void addSong(String songpath, String songname){
		
	}

	public void setSoundEffectVolume(float volume){
		
	}
	
	public void setMusicVolume(float volume){
		
	}
	
}
