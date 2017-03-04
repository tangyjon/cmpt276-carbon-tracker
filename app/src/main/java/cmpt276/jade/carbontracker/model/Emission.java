package cmpt276.jade.carbontracker.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Singleton facade for use by UI
 */

public class Emission {
    private static final Emission instance = new Emission();
    private CarCollection carCollection = new CarCollection();
    private JourneyCollection journeyCollection = new JourneyCollection();

    /**
     * useful for keeping temporary journey to work with throughout journey creation process
     * without using SharedPreferences/Intents
     */
    private Journey buffer = new Journey();

    private final String SPREF_KEY = "cmpt276.jade.carbontracker";
    private final String KEY_CAR_COLLECTION = "CarCollection";

    public static Emission getInstance() {
        return instance;
    }

    private Emission() {
        if (instance != null)
            throw new IllegalStateException("Emission singleton already instantiated");

    }

    public CarCollection getCarCollection() {
        return carCollection;
    }

    public JourneyCollection getJourneyCollection() {
        return journeyCollection;
    }

    public void setCarCollection(CarCollection cc) {
        carCollection = cc;
    }

    public void setJourneyCollection(JourneyCollection jc) {
        journeyCollection = jc;
    }

    public Journey getJourneyBuffer() {
        return buffer;
    }

    public void setJourneyBuffer(Journey j) {
        buffer = j;
    }

    public void saveCarCollection(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SPREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        editor.putString(KEY_CAR_COLLECTION, gson.toJson(carCollection));

        editor.apply();
    }

    public void loadCarCollection(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SPREF_KEY, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_CAR_COLLECTION, null);
        carCollection = gson.fromJson(json, carCollection.getClass());

        if (carCollection == null) carCollection = new CarCollection();
    }
}
