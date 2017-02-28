package cmpt276.jade.carbontracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.adapter.CustomSpinnerAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.utils.CarManager;

public class CarInfoActivity extends AppCompatActivity {
    // Field to contain all car info from vehicle.csv
    private CarCollection carCollection;

    // String of Manufactures for spinner adapter
    private List<String> makeDisplayList = new ArrayList<>();

    // String of Models for Specific Car for spinner adapter
    private List<String> modelDisplayList = new ArrayList<>();

    // Helper Fields
    private String selectMake, selectModel, selectYear;

    // TAG
    private String TAG = "carinfoactivity";

    // Field to store the user selected car <----------- THIS IS OF INTEREST
    private Car userSelectedCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        // LOAD
        loadCarList();

        loadMakeDisplayList();

        setUpAllSpinners();


    }

    private Spinner setUpSpinner(int spnID, List<String> stringList) {
        Spinner spinner = (Spinner) findViewById(spnID);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return spinner;
    }

    private Spinner setUpCustomSpinner(int spnID, List<Car> carList) {
        final Spinner spinner = (Spinner) findViewById(R.id.spn_year);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(CarInfoActivity.this, carList);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        return spinner;
    }

    // Hierarchy Method of Spinners
    private void setUpAllSpinners() {

        // MAKE SPINNER ---------
        final Spinner spnMake = setUpSpinner(R.id.spn_make, makeDisplayList);
        spnMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectMake = (String) spnMake.getSelectedItem();
                loadModelDisplayList();
                Log.i(TAG, selectMake);

                // MODEL SPINNER -----------
                final Spinner spnModel = setUpSpinner(R.id.spn_model, modelDisplayList);
                spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectModel = (String) spnModel.getSelectedItem();
                        Log.i(TAG, selectModel);


                        // YEAR SPINNER -----------------
                        // Pull Car List with specified make and model
                        final List<Car> specList = carCollection.search(selectMake, selectModel).toList();
                        Spinner spnYear = setUpCustomSpinner(R.id.spn_year, specList);
                        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                userSelectedCar = specList.get(i);
                                selectYear = Integer.toString(userSelectedCar.getYear());
                                Log.i(TAG, selectYear);
                                /// LOAD SOMETHING
                                updateCarInfo();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void updateCarInfo() {
        setUpTextView(R.id.tv_ucity, Double.toString(userSelectedCar.getuCity()));
        setUpTextView(R.id.tv_uhighway, Double.toString(userSelectedCar.getuHighway()));
        setUpTextView(R.id.tv_carbon_tail_pipe, Double.toString(userSelectedCar.getCarbonTailPipe()));
    }

    private void setUpTextView(int tvID, String text) {
        TextView tv = (TextView) findViewById(tvID);
        tv.setText(text);
    }

    private void loadCarList() {
        carCollection = new CarCollection(CarManager.readCarData(this, R.raw.vehicle_trimmed));
    }

    private void loadModelDisplayList() {
        modelDisplayList.clear();
        for (String model :
                carCollection.getCarMaker(selectMake).modelToStringList()) {
            if (!modelDisplayList.contains(model)) {
                modelDisplayList.add(model);
            }
        }
    }

    // Filters Through CarCollection extract unique Makers to String list
    private void loadMakeDisplayList() {
        makeDisplayList.clear();
        for (String make :
                carCollection.makeToStringList()) {
            if (!makeDisplayList.contains(make)) {
                makeDisplayList.add(make);
            }
        }
    }


}
