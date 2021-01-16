package com.Battleship.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * The type Sound factory.
 */
public class SoundFactory {
    /**
     * The constant sound.
     */
    public static File sound;
    /**
     * The constant value.
     */
//lautstärke
    public static float value;

    /**
     * The Clip.
     */
    Clip clip;

    /**
     * Instantiates a new Sound factory.
     */
    public SoundFactory(){
        value = -15f;
    }

    /**
     * Load.
     *
     * @param s the s
     */
    public void load(Sound s){
        switch (s){
            case MAINSOUND:
                sound = new File("assets/sounds/Karibik.wav");
                break;
            default:
                break;
        }
    }

    /**
     * Play.
     *
     * @param Sound the sound
     */
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

    /**
     * Stop.
     */
    public void stop(){
        clip.stop();
    }

}