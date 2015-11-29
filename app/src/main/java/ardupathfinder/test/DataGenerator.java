package ardupathfinder.test;


import android.util.Log;

/**
 * Created by jun on 15. 11. 25..
 */
public class DataGenerator {
    private static final String TAG = "DataGenerator";



    public String get() {
        double ran = Math.random();
        String record = "%d,%d,%d,%d,%d,%d";
        if (ran < 0.2)
            record = String.format(record, 0, 300, random(50, 100), random(50, 100), random(50, 100), 90);
        else if (ran > 0.8)
            record = String.format(record, 300, 0, random(50, 100), random(50, 100), random(50, 100), 90);
        else
            record = String.format(record, random(1000), random(1, 5), 5000, random(50, 100), random(50, 100), 90);

        Log.v(TAG, record);
        return record;
    }

    private int random(int max) {
        return (int) (Math.random() * max + 1.0);
    }

    private int random(int min, int max) {
        return (int) (min + Math.random() * (max - min) + 1.0);
    }
}
