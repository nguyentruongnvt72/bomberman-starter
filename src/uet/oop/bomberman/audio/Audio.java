package uet.oop.bomberman.audio;

public class Audio {
    private static String PATH_EXPLOSION = "/sound/Explosion.mp3";
    private static Thread threadExplosion;

    public static void playExplosion() {
        threadExplosion = new Thread(new ThreadAudio(PATH_EXPLOSION));
        threadExplosion.start();
    }
    private static boolean hasThread(Thread thread, Thread[] threads) {
        for (Thread temp : threads) {
            if (thread == temp) return true;
        }

        return false;
    }



}
