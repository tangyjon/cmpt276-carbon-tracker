package cmpt276.jade.carbontracker.sample;

import android.util.Log;

import java.util.Random;

import cmpt276.jade.carbontracker.CarListActivity;
import cmpt276.jade.carbontracker.Route_List_Activity;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.Transportation;

/**
 * Load Dummy Data to Emissions Class with Journeys, Car and Routes to recent lists
 */

public class LoadDummyData {
    private static String TAG = "LOADDUMMYCLASS";
    private static JourneyCollection jDummy;
    private static CarCollection cData;
    private static String instance = null;
    private LoadDummyData() {

    }

    // Generates a Route and Car into the recent lists
    // Does not generate a journey..
    public static void load(){

        if(instance == null){

            Log.i(TAG, "load: ");
            CarListActivity.recentCarList.add(generateCar());
            CarListActivity.recentCarList.add(generateCar());
            //Route_List_Activity.routes.addRoute(generateRoute());
            //Route_List_Activity.routes.addRoute(generateRoute());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());


            instance = "LOADED!";
        }
    }


    public static Car generateCar(){
        Random rn = new Random();
        int index = rn.nextInt(cData.toList().size()) - 1;
        Car car = cData.toList().get(index);
        car.setNickname("Car" + index %20);
        return car;
    }

    public static Route generateRoute(){
        Random rn = new Random();
        int index = rn.nextInt(100);
        return new Route("Route" + index, rn.nextInt(100),rn.nextInt(100));
    }

    public static Journey generateJourney(){
        Random rn = new Random();
        int index = rn.nextInt(500);
        Car car = generateCar();
        Transportation trans = new Transportation();
        trans.setCar(car);
        trans.setTransMode(Transport.CAR);
        Route route = generateRoute();
        Journey journey = new Journey("Journey " + index, trans,route);
        journey.setDate("" + (rn.nextInt(26)+1) + "/" + (rn.nextInt(12) + 1) + "/2016");
        return journey;
    }

    static {
        Log.i(TAG, "static initializer: ");
        cData = Emission.getInstance().getCarCollection();
    }


}
