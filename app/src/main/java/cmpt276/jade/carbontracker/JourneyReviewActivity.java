package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.TipDialog;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Tip;

/**
 * Journey review lets you review the data you entered for car and route and
 * will allow you to name the journey and the date
 */
public class JourneyReviewActivity extends AppCompatActivity {
    private Journey journey;
    private Journey storedJourney;

    public static Intent getJourneyReviewIntent(Context context) {
        return new Intent(context, JourneyReviewActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.JourneyReviewActivityHint));
        setContentView(R.layout.activity_journey_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getJourneyData();
        setupPage();
        setupDoneBtn();

//        setUpTips();
        hideSystemUI();

        final EditText inputDate = (EditText) findViewById(R.id.editDate);
        inputDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!dateCheck(inputDate)){
                    inputDate.setError("dd/mm/yyyy");
                }
            }
        });
    }

    private boolean dateCheck(EditText inputDate){
        String[] dateCheck = inputDate.getText().toString().split("/", 3);

        int month = 0;
        int day = 0;
        int year = 0;
        if (dateCheck.length == 3) {
            if (dateCheck[1].equals("") || dateCheck[0].equals("") || dateCheck[2].equals("")
                || dateCheck[1].contains(".") || dateCheck[0].contains(".") || dateCheck[2].contains(".")
                || dateCheck[1].contains("-") || dateCheck[0].contains("-") || dateCheck[2].contains("-")) {
                return false;
            } else {
                month = Integer.parseInt(dateCheck[1]);
                day = Integer.parseInt(dateCheck[0]);
                year = Integer.parseInt(dateCheck[2]);
            }
        } else {
            return false;
        }

        if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 9999) {
            return false;
        } else if ((month == 2 && day > 28 && year % 4 != 0) || (month == 2 && day > 29 && year % 4 == 0)) {
            return false;
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            return false;
        } else {
            return true;
        }
    }

    private void hideSystemUI() {
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideSystemUI();
//            }
//        });

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;

        mDecorView.setSystemUiVisibility(uiOptions);
    }

    private void setUpTips() {
//        final TextView tv_tips = (TextView) findViewById(R.id.tv_tips);
//        tv_tips.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tip tip = new Tip();
//                Journey j = Emission.getInstance().getJourneyBuffer();
//                switch (j.getTransType().getTransMode()) {
//                    case CAR:
//                        tip.setTotalCarEmissions(j.getTotalTravelledEmissions());
//                        tv_tips.setText(tip.getJourneyTip());
//                        break;
//                    case BIKE:
//                        tip.setTotalBike(j.getTotalTravelled());
//                        tv_tips.setText(tip.getJourneyTip());
//                        break;
//                    case WALK:
//                        tip.setTotalWalk(j.getTotalTravelled());
//                        tv_tips.setText(tip.getJourneyTip());
//                        break;
//                    case BUS:
//                        tip.setTotalBusEmission(j.getTotalTravelledEmissions());
//                        tv_tips.setText(tip.getJourneyTip());
//                        break;
//                    case SKYTRAIN:
//                        tip.setTotalSkyTrainEmission(j.getTotalTravelledEmissions());
//                        tv_tips.setText(tip.getJourneyTip());
//                        break;
//                }
//
//            }
//        });

        TipDialog tips = new TipDialog();
        tips.setTipDialogListener(new TipDialog.TipDialogListener() {
            @Override
            public void onNextClicked(TextView tv) {
                Tip tip = new Tip();
                Journey j = Emission.getInstance().getJourneyBuffer();
                switch (j.getTransType().getTransMode()) {
                    case CAR:
                        tip.setTotalCarEmissions(j.getTotalTravelledEmissions());
                        tv.setText(tip.getJourneyTip(JourneyReviewActivity.this));
                        break;
                    case BIKE:
                        tip.setTotalBike(j.getTotalTravelled());
                        tv.setText(tip.getJourneyTip(JourneyReviewActivity.this));
                        break;
                    case WALK:
                        tip.setTotalWalk(j.getTotalTravelled());
                        tv.setText(tip.getJourneyTip(JourneyReviewActivity.this));
                        break;
                    case BUS:
                        tip.setTotalBusEmission(j.getTotalTravelledEmissions());
                        tv.setText(tip.getJourneyTip(JourneyReviewActivity.this));
                        break;
                    case SKYTRAIN:
                        tip.setTotalSkyTrainEmission(j.getTotalTravelledEmissions());
                        tv.setText(tip.getJourneyTip(JourneyReviewActivity.this));
                        break;
                }
            }

            @Override
            public void onCloseClicked(TextView tv) {

            }
        });
        tips.show(getSupportFragmentManager(),"Tip Dialog");


    }

    private void setupPage() {
        TextView transInfo = (TextView) findViewById(R.id.txtTransInfo);
        TextView transTag = (TextView) findViewById(R.id.textViewJourneyReviewTransportTag);
        TextView routeInfo = (TextView) findViewById(R.id.txtRouteInfo);
        if (Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.CAR)) {
            transTag.setText(R.string.car);
            transInfo.setText(storedJourney.getTransType().getCar().getNickName() + "\n" + getString(R.string.make) +
                    storedJourney.getTransType().getCar().getMake() + "\n" + getString(R.string.model) +
                    storedJourney.getTransType().getCar().getModel() + "\n" + getString(R.string.year) +
                    storedJourney.getTransType().getCar().getYear());


            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.city_distance) +
                    storedJourney.getRoute().getCityDistance() + "\n" + getString(R.string.highway_distance)
                    + storedJourney.getRoute().getHighWayDistance());
        } else if (Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.BUS)) {
            transTag.setText(R.string.bus);
            transInfo.setText(storedJourney.getTransType().getBus().getNickName() + "\n" + getString(R.string.route_number) +
                    storedJourney.getTransType().getBus().getRouteNumber());

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());

        } else if (Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.SKYTRAIN)) {
            transTag.setText(R.string.Skytrain);
            transInfo.setText(storedJourney.getTransType().getSkytrain().getNickName() + "\n" + getString(R.string.line_name) +
                    storedJourney.getTransType().getSkytrain().getSkytrainLine() + "\n" + getString(R.string.boarding_station) +
                    storedJourney.getTransType().getSkytrain().getBoardingStation());

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());
        } else if (Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.WALK)) {
            transTag.setText(R.string.walked);
            transInfo.setText(" ");

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());
        } else if (Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.BIKE)) {
            transTag.setText(R.string.rode_bike);
            transInfo.setText(" ");

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());
        }
        if (journey.getMode() == 1) {
            EditText inputName = (EditText) findViewById(R.id.editJourneyName);
            inputName.setText(journey.getName());
            EditText inputDate = (EditText) findViewById(R.id.editDate);
            inputDate.setText(journey.getDate());
        }
    }

    //Should go back to finish after checking name and date
    private void setupDoneBtn() {
        Button button = (Button) findViewById(R.id.btnFinish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputName = (EditText) findViewById(R.id.editJourneyName);
                EditText inputDate = (EditText) findViewById(R.id.editDate);
                // String date = inputDate.getText().toString();


                String[] dateCheck = inputDate.getText().toString().split("/", 3);

                int month = 0;
                int day = 0;
                int year = 0;
                if (dateCheck.length == 3) {
                    if (dateCheck[1].equals("") || dateCheck[0].equals("") || dateCheck[2].equals("")
                            || dateCheck[1].contains(".") || dateCheck[0].contains(".") || dateCheck[2].contains(".")
                            || dateCheck[1].contains("-") || dateCheck[0].contains("-") || dateCheck[2].contains("-")) {
                        inputDate.setError(getString(R.string.valid_date));
                    } else {
                        month = Integer.parseInt(dateCheck[1]);
                        day = Integer.parseInt(dateCheck[0]);
                        year = Integer.parseInt(dateCheck[2]);
                    }
                } else {
                    inputDate.setError(getString(R.string.valid_date));
                }
                if (inputName.getText().toString().trim().length() == 0) {
                    inputName.setError(getString(R.string.valid_nickname));
                } else if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 9999) {
                    inputDate.setError(getString(R.string.valid_date));
                } else if ((month == 2 && day > 28 && year % 4 != 0) || (month == 2 && day > 29 && year % 4 == 0)) {
                    inputDate.setError(getString(R.string.valid_date));
                } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                    inputDate.setError(getString(R.string.valid_date));
                } else {


                    storedJourney.setPosition(journey.getPosition());
                    storedJourney.setMode(journey.getMode());
                    storedJourney.setDate(inputDate.getText().toString().trim());
                    //storedJourney.setDateObj(new Date(month+"/"+day+"/"+year));
                    storedJourney.setName(inputName.getText().toString().trim());
                    Emission.getInstance().setJourneyBuffer(storedJourney);


                    if (journey.getMode() == 0) {
                        Emission.getInstance().getJourneyCollection().addJourney(storedJourney);
//                        DBAdapter.save(JourneyReviewActivity.this, storedJourney); // Moved "saving to journey list" <--- BAD!
                    } else if (journey.getMode() == 1) {
//                        Toast.makeText(JourneyReviewActivity.this, "" + storedJourney.getPosition(), Toast.LENGTH_SHORT).show();
                        JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
                        listOfJourneys.editJourney(storedJourney, journey.getPosition());
                        Emission.getInstance().setJourneyCollection(listOfJourneys);
                    }


                    Intent intent = JourneyListActivity.getJourneyListIntent(JourneyReviewActivity.this);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    /*

                   This is not used anywhere is it needed?

                     */
                    // Journey journey = (Journey)intent.getSerializableExtra("Journey");

                }
            }
        });
    }


    public void getJourneyData() {
        journey = Emission.getInstance().getJourneyBuffer();
        storedJourney = new Journey(journey.getName(), journey.getTransType(), journey.getRoute());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


}
