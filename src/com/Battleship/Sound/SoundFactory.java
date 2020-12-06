package com.Battleship.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundFactory {
    public static File sound;
    //lautstärke
    public static float value;

    Clip clip;

    public SoundFactory(){
        value = 0;
    }

    public void load(){
        sound = new File("../assets/sounds/Karibik.wav");

    }

    public void play(File Sound){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}