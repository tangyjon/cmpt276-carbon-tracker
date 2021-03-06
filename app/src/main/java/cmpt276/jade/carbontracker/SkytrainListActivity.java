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

import cmpt276.jade.carbontracker.adapter.SkytrainListAdaptor;
import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Skytrain;
import cmpt276.jade.carbontracker.model.SkytrainCollection;

/**
 * Displays all the skytrain objects and can add new ones
 */
public class SkytrainListActivity extends AppCompatActivity {

    public static SkytrainCollection recentSkyTrainList = new SkytrainCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_skytrain_list);
        getSupportActionBar().setTitle(R.string.SkytrainListActivityToolbarHint);

        setupAddBtn();
        setupListview();
        populateList();
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

    private Button setupAddBtn() {
        Button btn = (Button) findViewById(R.id.btnSkytrainListAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emission.getInstance().getJourneyBuffer().getTransType().setSkytrain(new Skytrain());
                Intent intent = SkytrainInfoActivity.getIntent(SkytrainListActivity.this);
                startActivity(intent);
            }
        });

        return btn;
    }


    private void setupListview() {

        ListView list = (ListView) findViewById(R.id.listViewSkytrainList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emission.getInstance().getJourneyBuffer().getTransType().setSkytrain(recentSkyTrainList.getTrain(position));
                Intent intent = Route_List_Activity.IntentForRouteList(SkytrainListActivity.this, 4);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Skytrain train = recentSkyTrainList.getTrain(position);
                EditDialog editDialog = EditDialog.newInstance(train.getNickName(), Transport.SKYTRAIN);
                editDialog.setPosition(position);
                editDialog.setEditDialogListener(new EditDialog.EditDialogListener() {
                    @Override
                    public void onDeleteClicked(int pos) {
                        setupDeleteAlert(pos);
                        populateList();
                    }

                    @Override
                    public void onEditClicked(int pos) {
                        Intent intent = SkytrainInfoActivity.getIntent(SkytrainListActivity.this);
                        Emission.getInstance().getJourneyBuffer().getTransType().setSkytrain(recentSkyTrainList.getTrain(pos));
                        Emission.getInstance().getJourneyBuffer().getTransType().getSkytrain().setPosition(pos);
                        Emission.getInstance().getJourneyBuffer().getTransType().getSkytrain().setMode(1);
                        startActivity(intent);
                    }
                });
                editDialog.show(getSupportFragmentManager(), "EditDialog");
                return true;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        populateList();
    }


    // Inspired by Raz
    private void setupDeleteAlert(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Skytrain thisTrain = recentSkyTrainList.getTrain(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisTrain.getNickName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recentSkyTrainList.deleteTrain(index);
                populateList();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void populateList() {
        dbRefreshSkytrainCarList();
        //TODO
        //Make Adaptor
        ListAdapter adapt = new SkytrainListAdaptor(this, recentSkyTrainList.getTrainList());
        ListView list = (ListView) findViewById(R.id.listViewSkytrainList);
        list.setAdapter(adapt);
    }

    private void dbRefreshSkytrainCarList() {
        DBAdapter myDB = new DBAdapter(this);
        myDB.open();

        // Delete Everything form DB with "RECENT"
        myDB.deleteAll(DBAdapter.DB_TABLE.SKYTRAIN, DBAdapter.TAG_ID.RECENT);

        // RE-ADD REMAINING RECENTS
        for (Skytrain s : recentSkyTrainList.getTrainList()) {
            myDB.insertRow(s, DBAdapter.TAG_ID.RECENT);
        }
        myDB.close();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SkytrainListActivity.class);
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
