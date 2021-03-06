package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.adapter.ImageRowAdapter;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Skytrain;

/**
 * Add info to the skytrain object
 */
public class SkytrainInfoActivity extends AppCompatActivity {

    private Skytrain incomingTrain;
    private Skytrain outgoingTrain;

    ImageRowAdapter imageRowAdapter = new ImageRowAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_skytrain_info);
        setUpImageRowSelect();
        setupNextBtn();
        setupPage();
        getTrainData();

        // Quick fix
        if (incomingTrain != null && incomingTrain.getMode() == 1) {
            imageRowAdapter.setImage(incomingTrain.getImageId());
            imageRowAdapter.updateSelectedScreen();
        }
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

    private void setUpImageRowSelect() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.layout_table);
        imageRowAdapter = new ImageRowAdapter(this);
        tableLayout.addView(imageRowAdapter.getRow());
    }
//
    public static Intent getIntent(Context context) {
        return new Intent(context, SkytrainInfoActivity.class);
    }

    private void setupPage() {
        if (incomingTrain != null && incomingTrain.getMode() == 1) {
            EditText inputName = (EditText) findViewById(R.id.editTextSkytrainName);
            inputName.setText(incomingTrain.getNickName());
            EditText inputLine = (EditText) findViewById(R.id.editTextSkytrainLine);
            inputLine.setText(incomingTrain.getSkytrainLine());
            EditText inputStation = (EditText) findViewById(R.id.editTextSkytrainBoardingStation);
            inputLine.setText(incomingTrain.getBoardingStation());
        }
    }

    private void setupNextBtn() {
        Button btn = (Button) findViewById(R.id.btnSkytrainInfoNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText inputName = (EditText) findViewById(R.id.editTextSkytrainName);
                EditText inputLine = (EditText) findViewById(R.id.editTextSkytrainLine);
                EditText inputStation = (EditText) findViewById(R.id.editTextSkytrainBoardingStation);

                if(!imageRowAdapter.isImageSelected()){
                    TextView tv = (TextView) findViewById(R.id.tv_pick_icon_label);
                    tv.setError("");
                }
                else if (inputName.getText().toString().trim().length() == 0) {
                    inputName.setError(getString(R.string.skytrain_info_enter_nick));
                } else if (inputLine.getText().toString().trim().length() == 0) {
                    inputLine.setError(getString(R.string.skytrain_info_enter_line));
                } else if (inputStation.getText().toString().trim().length() == 0) {
                    inputStation.setError(getString(R.string.skytrain_info_enter_station));
                } else {
                    outgoingTrain.setNickName(inputName.getText().toString().trim());
                    outgoingTrain.setSkytrainLine(inputLine.getText().toString().trim());
                    outgoingTrain.setBoardingStation(inputStation.getText().toString().trim());
                    outgoingTrain.setImageId(imageRowAdapter.getSelectedImage());

                    if (incomingTrain == null || incomingTrain.getMode() == 0) {
                        SkytrainListActivity.recentSkyTrainList.addTrain(outgoingTrain);
                    } else if (incomingTrain != null && incomingTrain.getMode() == 1) {
                        SkytrainListActivity.recentSkyTrainList.editTrain(outgoingTrain, incomingTrain.getPosition());
                    }
                    Emission.getInstance().getJourneyBuffer().getTransType().setSkytrain(outgoingTrain);
                    finish();
                }

            }
        });
    }

    private void getTrainData() {

        this.incomingTrain = Emission.getInstance().getJourneyBuffer().getTransType().getSkytrain();
        this.outgoingTrain = new Skytrain();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
