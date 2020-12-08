package com.Battleship.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundFactory {
    public static File sound;
    //lautstärke
    public static float value;

    Clip clip;

    public SoundFactory(){
        value = -15f;
    }

    public void load(Sound s){
        switch (s){
            case MAINSOUND:
                sound = new File("assets/sounds/Karibik.wav");
                break;
            default:
                break;
        }
    }

    public void play(File Sound){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(value);
            clip.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        clip.stop();
    }

}