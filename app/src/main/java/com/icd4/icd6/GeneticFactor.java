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


public class GeneticFactor extends Activity {

    float riskFacAge = 1.0F;
    float riskFacSmoke = 1.0F;
    float riskFacSecondHandSmoke = 1.0F;
    float riskFacPollutantLoad = 1.0F;
    float riskFac = 1.0F;
    String sex = "";

    TextView geneticFactorHeading = null;
    TextView geneticFactorQuestion = null;
    RadioGroup geneticFactorChoice = null;

    Button btnNext = null;
    Button btnBack = null;


    private static final String TAG = "GeneticFactor";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genetic_factor);
        Log.d(TAG, "onCreate: Starting.");


        // get extras from previous activity
        Intent myIntent = getIntent();
        riskFacAge = myIntent.getFloatExtra("RiskFactorAge",1.0F);
        riskFacSmoke = myIntent.getFloatExtra("RiskFactorSmoke",1.0F);
        riskFacSecondHandSmoke = myIntent.getFloatExtra("RiskFactorSecondHandSmoke",1.0F);
        riskFacPollutantLoad = myIntent.getFloatExtra("RiskFactorPollutantLoad",1.0F);
        sex = myIntent.getStringExtra("Sex");

        // get all elements
        geneticFactorHeading = findViewById(R.id.genetic_factor_heading);
        geneticFactorQuestion = findViewById(R.id.genetic_factor_question);
        geneticFactorChoice = findViewById(R.id.genetic_factor_choice);


        btnNext = (Button) findViewById(R.id.button_genetic_factor_next);
        btnBack = (Button) findViewById(R.id.button_genetic_factor_back);

        // Visibility
        TextView riskFacTextView = findViewById(R.id.genetic_factor_risk_fac);
        riskFacTextView.setText(sex + ": " + riskFacAge + " x " + riskFacSmoke + " x " + riskFacSecondHandSmoke + " x " + riskFacPollutantLoad + " = " + riskFacAge*riskFacSmoke*riskFacSecondHandSmoke*riskFacPollutantLoad);
        resetPage();

        // Listeners
        geneticFactorChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);

                switch ((String)checkedRatioButton.getText()) {
                    case "Ja":
                        riskFac = 2.0F;
                        break;
                    case "Nein":
                        riskFac = 1.0F;
                        break;
                }
                btnNext.setVisibility(View.VISIBLE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");

                Intent intent =  new Intent(GeneticFactor.this, PollutantLoad.class);
                intent.putExtra("Sex", sex);
                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                startActivity(intent);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");



                Intent intent =  new Intent(GeneticFactor.this, OncologyConsultation.class);

                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                intent.putExtra("RiskFactorPollutantLoad", riskFacPollutantLoad);
                intent.putExtra("RiskFactorGeneticFactor", riskFac);

                intent.putExtra("Sex", sex);
                intent.putExtra("SourceActivity","GeneticFactor");

                startActivity(intent);
            }
        });
    }

    private void resetPage(){
        btnNext.setText("Weiter");
        riskFac = riskFacPollutantLoad;

        geneticFactorHeading.setVisibility(View.VISIBLE);
        geneticFactorQuestion.setVisibility(View.VISIBLE);
        geneticFactorChoice.setVisibility(View.VISIBLE);

        btnNext.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.VISIBLE);
    }

}
