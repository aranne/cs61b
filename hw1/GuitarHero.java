/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
import synthesizer.GuitarString;
import java.io.IOException;
public class GuitarHero {
    /** Returns a GuitarString corresponding to the index in the piano keyboard. */
    private static GuitarString getGuitarString(char key) throws IOException {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        int index = keyboard.indexOf(key);
        if (index == -1) {
            throw new IOException("You are out of the keyboard!");
        }
        double frequency = 440.0 * Math.pow(2, (index - 24.0) / 12.0);

        return new GuitarString(frequency);
    }

    public static void main(String[] args) {
        GuitarString string = new GuitarString(440.0);
        while (true) {
            try {
                /* check if the user has typed a key; if so, process it */
                if (StdDraw.hasNextKeyTyped()) {
                    char key = StdDraw.nextKeyTyped();
                    string = getGuitarString(key);
                    string.pluck();
                }
                double sample = string.sample();
                StdAudio.play(sample);
                string.tic();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
