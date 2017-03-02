package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;

public class JourneySummaryActivity extends AppCompatActivity {


    private Journey journey;
    private Car car;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_summary);
        getData();
        setData();
        setupBackBtn();

    }



    private void getData() {
        Intent intent = getIntent();
        this.journey = (Journey)intent.getSerializableExtra("Journey");
        this.car = (Car)intent.getSerializableExtra("Car");
        this.route = (Route)intent.getSerializableExtra("Route");
    }

    private void setData() {
        TextView carName = (TextView) findViewById(R.id.textCarName);
        carName.setText(car.getNickname());
        TextView routeName = (TextView) findViewById(R.id.textRouteName);
        routeName.setText(route.getNickName());
        TextView cityDrive = (TextView) findViewById(R.id.textCitydrv);
        cityDrive.setText(""+ journey.getTotalCity());
        TextView hwayDrive = (TextView) findViewById(R.id.textHwaydrv);
        hwayDrive.setText(""+ journey.getTotalHighway());
        TextView totalDrive = (TextView) findViewById(R.id.textTotaldrv);
        totalDrive.setText("" + journey.getTotalHighway());

    }

    private void setupBackBtn() {
        Button btn = (Button) findViewById(R.id.btnJourneySummaryBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static Intent getJourneySummaryIntent(Context context) {
        Intent intent = new Intent(context, JourneySummaryActivity.class);
        return intent;
    }
}
