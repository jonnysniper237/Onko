package com.icd4.icd6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Symptoms extends Activity {

    float riskFacAge = 1.0F;
    float riskFacSmoke = 1.0F;
    float riskFacSecondHandSmoke = 1.0F;
    float riskFacPollutantLoad = 1.0F;
    String sex = "";

    TextView symptomsHeading = null;
    CheckBox persistentCough = null;
    CheckBox unintendetWeightLoss = null;
    CheckBox dyspnoea = null;
    CheckBox chestPain = null;
    CheckBox sputum = null;
    CheckBox feverNightSweat = null;
    CheckBox fatigue = null;
    CheckBox hoarseness = null;
    CheckBox chronicCough = null;

    Button btnNext = null;
    Button btnBack = null;

    private static final String TAG = "Sympoms";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symptoms);
        Log.d(TAG, "onCreate: Starting.");


        // get extras from previous activity
        Intent myIntent = getIntent();
        riskFacAge = myIntent.getFloatExtra("RiskFactorAge",1.0F);
        riskFacSmoke = myIntent.getFloatExtra("RiskFactorSmoke",1.0F);
        riskFacSecondHandSmoke = myIntent.getFloatExtra("RiskFactorSecondHandSmoke",1.0F);
        riskFacPollutantLoad = myIntent.getFloatExtra("RiskFactorPollutantLoad",1.0F);
        sex = myIntent.getStringExtra("Sex");


        // get all elements
        symptomsHeading = findViewById(R.id.symptoms_heading);
        persistentCough = findViewById(R.id.persistent_cough);
        unintendetWeightLoss = findViewById(R.id.unintendet_weight_loss);
        dyspnoea = findViewById(R.id.dyspnoea);
        chestPain = findViewById(R.id.chest_pain);
        sputum = findViewById(R.id.sputum);
        feverNightSweat = findViewById(R.id.fever_night_sweat);
        fatigue = findViewById(R.id.fatigue);
        hoarseness = findViewById(R.id.hoarseness);
        chronicCough = findViewById(R.id.chronic_cough);

        btnNext = (Button) findViewById(R.id.button_symptoms_next);
        btnBack = (Button) findViewById(R.id.button_symptoms_back);

        symptomsHeading.setVisibility(View.VISIBLE);
        persistentCough.setVisibility(View.VISIBLE);
        unintendetWeightLoss.setVisibility(View.VISIBLE);
        dyspnoea.setVisibility(View.VISIBLE);
        chestPain.setVisibility(View.VISIBLE);
        sputum.setVisibility(View.VISIBLE);
        feverNightSweat.setVisibility(View.VISIBLE);
        fatigue.setVisibility(View.VISIBLE);
        hoarseness.setVisibility(View.VISIBLE);
        chronicCough.setVisibility(View.VISIBLE);


        btnNext.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");

                Intent intent =  new Intent(Symptoms.this, GeneticFactor.class);

                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                intent.putExtra("RiskFactorPollutantLoad", riskFacPollutantLoad);
                intent.putExtra("Sex", sex);

                startActivity(intent);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");

                int severity = 0;

                boolean hasPersistentCough = persistentCough.isChecked();           // 1
                boolean hasUnintendetWeightLoss = unintendetWeightLoss.isChecked(); // 2
                boolean hasDyspnoea = dyspnoea.isChecked();                         // 3
                boolean hasChestPain = chestPain.isChecked();                       // 4
                boolean hasSputum = sputum.isChecked();                             // 5
                boolean hasFeverNightSweat = feverNightSweat.isChecked();           // 6
                boolean hasFatigue = fatigue.isChecked();                           // 7
                boolean hasHoarseness = hoarseness.isChecked();                     // 8
                boolean hasChronicCough = chronicCough.isChecked();                 // 9

                int sumOfOneToThree = (hasPersistentCough?1:0) + (hasUnintendetWeightLoss?1:0) + (hasDyspnoea?1:0);
                int sumOfFourToSix = (hasChestPain?1:0) + (hasSputum?1:0) + (hasFeverNightSweat?1:0);
                int sumOfSevenToNine = (hasFatigue?1:0) + (hasHoarseness?1:0) + (hasChronicCough?1:0);

                if(sumOfOneToThree>1){
                    severity = 4;
                }
                else if (sumOfOneToThree == 1 || sumOfFourToSix > 0){
                    severity = 3;
                }
                else{
                    // sumOfOneToThree == 0 && sumOfFourToSix == 0
                    if (sumOfSevenToNine > 1){
                        severity = 2;
                    }
                    else if (sumOfSevenToNine == 1){
                        severity = 1;
                    }
                }

                Intent intent =  new Intent(Symptoms.this, OncologyConsultation.class);
                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                intent.putExtra("RiskFactorPollutantLoad", riskFacPollutantLoad);

                intent.putExtra("Sex", sex);
                intent.putExtra("SourceActivity","Symptoms");
                intent.putExtra("Severity",severity);
                startActivity(intent);
            }
        });
    }



}
