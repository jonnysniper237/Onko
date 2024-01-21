package com.icd4.icd6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class PollutantLoad extends Activity {

    float riskFacAge = 1.0F;
    float riskFacSmoke = 1.0F;
    float riskFacSecondHandSmoke = 1.0F;
    float riskFac = 1.0F;
    String sex = "";

    TextView pollutantLoadTxt = null;
    RadioGroup pollutantLoadChoice = null;
    TextView kindOfPollutionAtWorkTxt = null;
    TextView pollutionSiliconEtc = null;
    TextView pollutionRadonEtc = null;
    RadioGroup kindOfPollutionAtWorkChoice = null;

    Button btnNext = null;
    Button btnBack = null;

    private static final String TAG = "PollutantLoad";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pollutant_load);
        Log.d(TAG, "onCreate: Starting.");


        // get extras from previous activity
        Intent myIntent = getIntent();
        riskFacAge = myIntent.getFloatExtra("RiskFactorAge",1.0F);
        riskFacSmoke = myIntent.getFloatExtra("RiskFactorSmoke",1.0F);
        riskFacSecondHandSmoke = myIntent.getFloatExtra("RiskFactorSecondHandSmoke",1.0F);
        sex = myIntent.getStringExtra("Sex");

        // get all elements
        pollutantLoadTxt = findViewById(R.id.pollutant_load);
        pollutantLoadChoice = findViewById(R.id.pollutant_load_choice);

        kindOfPollutionAtWorkTxt = findViewById(R.id.kind_of_pollution_at_work);
        pollutionSiliconEtc = findViewById(R.id.pollution_silicon_etc);
        pollutionRadonEtc = findViewById(R.id.pollution_radon_etc);
        kindOfPollutionAtWorkChoice = findViewById(R.id.kind_of_pollution_at_work_choice);

        btnNext = (Button) findViewById(R.id.button_pollutant_load_next);
        btnBack = (Button) findViewById(R.id.button_pollutant_load_back);

        // Visibility
        TextView riskFacTextView = findViewById(R.id.pollutant_load_risk_fac);
        riskFacTextView.setText(sex + ": " + riskFacAge + " x " + riskFacSmoke + " x " + riskFacSecondHandSmoke + " = " + riskFacAge*riskFacSmoke*riskFacSecondHandSmoke);
        resetPage();

        // Listeners
        pollutantLoadChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
                int selectedRadioButtonId = -1;
                String selectedButtonText = "";
                switch ((String)checkedRatioButton.getText()) {
                    case "1.":
                        // pollutant_load_general
                        resetPage();
                        riskFac = 1.14F;
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                    case "2.":
                        // pollutant_load_at_work
                        kindOfPollutionAtWorkTxt.setVisibility(View.VISIBLE);
                        pollutionSiliconEtc.setVisibility(View.VISIBLE);
                        pollutionRadonEtc.setVisibility(View.VISIBLE);
                        kindOfPollutionAtWorkChoice.setVisibility(View.VISIBLE);

                        selectedRadioButtonId = kindOfPollutionAtWorkChoice.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            selectedButtonText = ((RadioButton) kindOfPollutionAtWorkChoice.findViewById(selectedRadioButtonId)).getText().toString();
                            if (selectedButtonText.equals("1.")) {
                                // pollution_silicon_etc_choice
                                riskFac = 2.0F;
                            } else {
                                // pollution_radon_etc_choice
                                riskFac = 1.5F;
                            }
                            btnNext.setVisibility(View.VISIBLE);
                        }
                        else{
                            riskFac = 1.0F;
                            btnNext.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case "Keine":
                        resetPage();
                        riskFac = 1.0F;
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        kindOfPollutionAtWorkChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);

                switch ((String)checkedRatioButton.getText()) {
                    case "1.":
                        //pollution_silicon_etc_choice
                        riskFac = 2.0F;
                        break;
                    case "2.":
                        //pollution_radon_etc_choice
                        riskFac = 1.5F;
                        break;
                }

                btnNext.setVisibility(View.VISIBLE);

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");

                Intent intent =  new Intent(PollutantLoad.this, SecondHandSmoke.class);
                intent.putExtra("Sex", sex);
                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);

                startActivity(intent);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");

                Intent intent =  new Intent(PollutantLoad.this, GeneticFactor.class);
                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                intent.putExtra("RiskFactorPollutantLoad", riskFac);
                intent.putExtra("Sex", sex);
                startActivity(intent);
            }
        });
    }

    private void resetPage(){
        riskFac = 1.0F;
        pollutantLoadTxt.setVisibility(View.VISIBLE);
        pollutantLoadChoice.setVisibility(View.VISIBLE);
        kindOfPollutionAtWorkTxt.setVisibility(View.INVISIBLE);
        pollutionSiliconEtc.setVisibility(View.INVISIBLE);
        pollutionRadonEtc.setVisibility(View.INVISIBLE);
        kindOfPollutionAtWorkChoice.setVisibility(View.INVISIBLE);

        btnNext.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.VISIBLE);
    }

}
