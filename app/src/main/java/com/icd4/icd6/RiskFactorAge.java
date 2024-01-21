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


public class RiskFactorAge extends Activity {
    RadioGroup ageOldChoice = null;
    RadioGroup ageYoungChoice = null;
    TextView ageTextView = null;

    Button btnNext = null;
    Button btnBack = null;
    String ageGroup = "";
    String sex = "";
    float riskFac = 1.0F;

    private static final String TAG = "RiskFactorAge";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_factor_age);
        Log.d(TAG, "onCreate: Starting.");

        // Sex Choice Items
        RadioGroup sexChoice = (RadioGroup) findViewById(R.id.sex_choice);
        sexChoice.setOnCheckedChangeListener(sexChoiceListener);

        // Age Choice Items
        ageTextView = (TextView) findViewById(R.id.age);
        ageYoungChoice = (RadioGroup) findViewById(R.id.age_choice_young);
        ageOldChoice = (RadioGroup) findViewById(R.id.age_choice_old);

        ageTextView.setVisibility(View.INVISIBLE);
        ageYoungChoice.setVisibility(View.INVISIBLE);
        ageOldChoice.setVisibility(View.INVISIBLE);

        ageYoungChoice.setOnCheckedChangeListener(ageYoungListener);
        ageOldChoice.setOnCheckedChangeListener(ageOldListener);

        // Buttons
        btnNext = (Button) findViewById(R.id.button_age_next);
        btnBack = (Button) findViewById(R.id.button_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");

                Intent intent =  new Intent(RiskFactorAge.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnNext.setVisibility(View.INVISIBLE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");

                Intent intent =  new Intent(RiskFactorAge.this, RiskFactorSmoke.class);
                intent.putExtra("RiskFactorAge", riskFac);
                intent.putExtra("Sex", sex);
                startActivity(intent);
            }
        });
    }

    private RadioGroup.OnCheckedChangeListener sexChoiceListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            ageTextView.setVisibility(View.VISIBLE);
            ageYoungChoice.setVisibility(View.VISIBLE);
            ageOldChoice.setVisibility(View.VISIBLE);

            // determine risk factor
            RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
            if(((String)checkedRatioButton.getText()).equals("weiblich")){
                sex = "female";
            }
            else{
                sex = "male";
            }

            riskFac = determineAgeRiskFactor();
        }
    };

    private RadioGroup.OnCheckedChangeListener ageYoungListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            // clear other Radio Group
            if (i != -1){
                ageOldChoice.setOnCheckedChangeListener(null);
                ageOldChoice.clearCheck();
                ageOldChoice.setOnCheckedChangeListener(ageOldListener);
            }

            // determine risk factor
            RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
            ageGroup = (String)checkedRatioButton.getText();
            riskFac = determineAgeRiskFactor();

            //
            btnNext.setVisibility(View.VISIBLE);
        }
    };

    private RadioGroup.OnCheckedChangeListener ageOldListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            // clear other Radio Group
            if (i != -1){
                ageYoungChoice.setOnCheckedChangeListener(null);
                ageYoungChoice.clearCheck();
                ageYoungChoice.setOnCheckedChangeListener(ageYoungListener);
            }

            // determine risk factor
            RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
            ageGroup = (String)checkedRatioButton.getText();
            riskFac = determineAgeRiskFactor();

            //
            btnNext.setVisibility(View.VISIBLE);
        }
    };


    private float determineAgeRiskFactor(){
        float riskFac = 0;

        if(sex.equals("female")){
            switch (ageGroup){
                case "35-44":
                    riskFac = 4.0F;
                    break;
                case "45-54":
                    riskFac = 3.9F;
                    break;
                case "55-64":
                    riskFac = 3.7F;
                    break;
                case "65-74":
                    riskFac = 2.9F;
                    break;
                case "75-85":
                    riskFac = 1.9F;
                    break;
                default:
                    riskFac = 0;
                    break;
            }

        }
        else{ //male
            switch (ageGroup){
                case "35-44":
                    riskFac = 6.5F;
                    break;
                case "45-54":
                    riskFac = 6.6F;
                    break;
                case "55-64":
                    riskFac = 6.4F;
                    break;
                case "65-74":
                    riskFac = 5.5F;
                    break;
                case "75-85":
                    riskFac = 3.6F;
                    break;
                default:
                    riskFac = 0;
                    break;
            }
        }


        return riskFac;
    };
}
