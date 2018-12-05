package uet.oop.bomberman.audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;

public class GameSound {
    public static GameSound instance;

    public static final String MENU = "/sound/menu.wav";
    public static final String PLAYGAME = "/sound/playgame.mid";
    public static final String BOMB = "/sound/newbomb.wav";
    public static final String BOMBER_DIE = "/sound/bomber_die.wav";
    public static final String MONSTER_DIE = "/sound/monster_die.wav";
    public static final String BONG_BANG = "/sound/bomb_bang.wav";
    public static final String ITEM = "/sound/item.wav";
    public static final String WIN = "/sound/win.wav";
    public static final String LOSE = "/sound/lose.mid";
    private HashMap<String, AudioClip> audioMap;

    public GameSound() {
        audioMap = new HashMap<>();
        loadAllAudio();
    }


    public static GameSound getIstance() {
        if (instance == null) {
            instance = new GameSound();
        }
        return instance;
    }

    public void loadAllAudio() {
        putAudio(MENU);
        putAudio(PLAYGAME);
        putAudio(BOMB);
        putAudio(MONSTER_DIE);
        putAudio(BOMBER_DIE);
        putAudio(BONG_BANG);
        putAudio(ITEM);
        putAudio(WIN);
        putAudio(LOSE);
    }

    public void stop() {
        getAudio(MENU).stop();
        getAudio(PLAYGAME).stop();
        getAudio(BOMB).stop();
        getAudio(BONG_BANG).stop();
        getAudio(WIN).stop();
        getAudio(LOSE).stop();
    }

    public void putAudio(String name) {
        AudioClip auClip = Applet.newAudioClip(GameSound.class.getResource(name));
        audioMap.put(name, auClip);
    }

    public AudioClip getAudio(String name) {
        return audioMap.get(name);
    }
}
