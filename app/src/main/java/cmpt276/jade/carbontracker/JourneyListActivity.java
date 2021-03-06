package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.JourneyListAdapter;
import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.Transportation;

/**
 * Journey List is your list of journeys and can either add a new journey going to car list or to the emissions overview
 * Can also edit journey entries or delete journey entries
 */
public class JourneyListActivity extends AppCompatActivity {
    public static JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
    //  private Journey intentJourney;
    private int Mode = 0;

    public static Intent getJourneyListIntent(Context context) {
        return new Intent(context, JourneyListActivity.class);

    }

    //maybe have a get data from intent that can handle a journey being passed into it?
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.JourneyListActivityHint));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_journey_list);


        setupAddBtn();
        setupFootprintBtn();
        //getIntentData();
        setupClickJourneyList();
        populateList();
        checkFootprintBtn();
        hideSystemUI();
    }

    private void hideSystemUI() {
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideSystemUI();
//            }
//        });

        final View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;

        mDecorView.setSystemUiVisibility(uiOptions);

        mDecorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_FULLSCREEN;

                mDecorView.setSystemUiVisibility(uiOptions);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }
    private void checkFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        if (listOfJourneys.countJourneys() == 0) button.setEnabled(false);
        else button.setEnabled(true);
    }

    private void setupFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarbonFootprintActivity.getIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });
    }

    private Button setupAddBtn() {
        Button button = (Button) findViewById(R.id.btnAddJourney);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transportation trans = new Transportation();
                Route route = new Route("TEMP ROUTE NAME AND DATA", -1, -1);
                Journey journey = new Journey("T3EMP NAME", trans, route);
                Emission.getInstance().setJourneyBuffer(journey);
                Intent intent = TransportSelectActivity.getTransportIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });

        return button;
    }

    private void setupClickJourneyList() {
        //goto Journey Summary - short click
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(position));
                Intent intent = JourneySummaryActivity.getJourneySummaryIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });


        //edit - long click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final Journey journey = listOfJourneys.getJourney(position);

                // Set Up Edit Dialog Includes Edit and Delete Features
                EditDialog editDialog = EditDialog.newInstance(journey);
                editDialog.setPosition(position);
                editDialog.setEditDialogListener(new EditDialog.EditDialogListener() {
                    @Override
                    public void onDeleteClicked(int pos) {
                        setupDeleteAlert(pos);
                        populateList();
                    }

                    @Override
                    public void onEditClicked(int pos) {
                        if (listOfJourneys.getJourney(pos).getTransType().getTransMode().equals(Transport.CAR)) {
                            Intent intent = CarListActivity
                                    .getIntentFromActivity(JourneyListActivity.this);
                            Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(pos));
                            Emission.getInstance().getJourneyBuffer().setPosition(pos);
                            Emission.getInstance().getJourneyBuffer().setMode(1);
                            startActivity(intent);
                        } else if (listOfJourneys.getJourney(pos).getTransType().getTransMode().equals(Transport.BUS)) {
                            Intent intent = BusListActivity
                                    .getIntent(JourneyListActivity.this);

                            Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(pos));
                            Emission.getInstance().getJourneyBuffer().setPosition(pos);
                            Emission.getInstance().getJourneyBuffer().setMode(1);
                            startActivity(intent);
                        } else if (listOfJourneys.getJourney(pos).getTransType().getTransMode().equals(Transport.SKYTRAIN)) {
                            Intent intent = SkytrainListActivity
                                    .getIntent(JourneyListActivity.this);

                            Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(pos));
                            Emission.getInstance().getJourneyBuffer().setPosition(pos);
                            Emission.getInstance().getJourneyBuffer().setMode(1);
                            startActivity(intent);
                        } else if (listOfJourneys.getJourney(pos).getTransType().getTransMode().equals(Transport.WALK)) {
                            Intent intent = Route_List_Activity
                                    .IntentForRouteList(JourneyListActivity.this, 2);

                            Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(pos));
                            Emission.getInstance().getJourneyBuffer().setPosition(pos);
                            Emission.getInstance().getJourneyBuffer().setMode(1);
                            startActivity(intent);
                        } else if (listOfJourneys.getJourney(pos).getTransType().getTransMode().equals(Transport.BIKE)) {
                            Intent intent = Route_List_Activity
                                    .IntentForRouteList(JourneyListActivity.this, 2);

                            Emission.getInstance().setJourneyBuffer(listOfJourneys.getJourney(pos));
                            Emission.getInstance().getJourneyBuffer().setPosition(pos);
                            Emission.getInstance().getJourneyBuffer().setMode(1);
                            startActivity(intent);
                        }
                    }
                });
                editDialog.show(getSupportFragmentManager(), "EditDialog");


                return true;
            }
        });
    }

    private void populateList() {
        // Save Journey to Data Base by complete refresh
        dbRefreshJourneyTable();

        //ListAdapter bucky=new RouteListAdapter(this,listOfJourneys.getJourneyDetails(),getMode());
        ListAdapter bucky = new JourneyListAdapter(this, listOfJourneys.getJourneyDetails(), listOfJourneys);
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setAdapter(bucky);
    }

    private void dbRefreshJourneyTable() {
        DBAdapter db = new DBAdapter(this);
        db.open();
        // Complete Refresh RecentCarList DB
        JourneyCollection jC = db.getAllJourney();
        // Delete Everything form DB with "RECENT"
        for (Journey j : jC.getJourneyList()) {
            db.deleteJourney(j);
        }
        // RE-ADD REMAINING RECENTS
        for (Journey j : Emission.getInstance().getJourneyCollection().getJourneyList()) {
            db.insertRow(j);
        }
        db.close();
    }

    // Inspired by Raz
    private void setupDeleteAlert(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Journey thisJourney = listOfJourneys.getJourney(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisJourney.getName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                DBAdapter.delete(JourneyListActivity.this,thisJourney);
                listOfJourneys.deleteJourney(index);
                Emission.getInstance().setJourneyCollection(listOfJourneys);
                populateList();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish(); // close this activity and return to preview activity (if there is any)
                break;
            case R.id.action_add:
                // add method
                Button btn = setupAddBtn();
                btn.performClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }
}
