package com.Battleship.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.*;

/**
 * The type Sound factory.
 */
public class SoundFactory {
    /**
     * The constant sound.
     */
    public static InputStream sound;
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
        if (s == Sound.MAINSOUND) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sounds/Karibik.wav");
            sound = inputStream;
        }
    }

    /**
     * Play.
     *
     * @param Sound the sound
     */
    public void play(InputStream Sound){
        try {
            clip = AudioSystem.getClip();
            // Buffer fuer mark / reset Unterstützung
            InputStream bufferedIn = new BufferedInputStream(Sound);
            System.out.println(sound);
            clip.open(AudioSystem.getAudioInputStream(bufferedIn));
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