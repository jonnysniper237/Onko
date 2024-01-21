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


public class SecondHandSmoke extends Activity {

    float riskFacAge = 1.0F;
    float riskFacSmoke = 1.0F;
    float riskFac = 1.0F;
    String sex = "";

    TextView secondHandSmokeTxt = null;
    RadioGroup secondHandSmokeChoice = null;
    RadioGroup secondHandSmokeSourceChoice = null;
    TextView secondHand20yearsTxt = null;
    RadioGroup secondHand20yearsChoice = null;
    Button btnNext = null;
    Button btnBack = null;

    private static final String TAG = "SecondHandSmoke";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_hand_smoke);
        Log.d(TAG, "onCreate: Starting.");


        // get extras from previous activity
        Intent myIntent = getIntent();
        riskFacAge = myIntent.getFloatExtra("RiskFactorAge",1.0F);
        riskFacSmoke = myIntent.getFloatExtra("RiskFactorSmoke",1.0F);
        sex = myIntent.getStringExtra("Sex");

        // get all elements
        secondHandSmokeTxt = findViewById(R.id.second_hand_smoke);
        secondHandSmokeTxt.setText("Passivrauchexposition? " + riskFacSmoke);

        secondHandSmokeChoice = findViewById(R.id.second_hand_smoke_choice);
        secondHandSmokeSourceChoice = findViewById(R.id.second_hand_smoke_source_choice);

        secondHand20yearsTxt = findViewById(R.id.second_hand_smoke_20_years);
        secondHand20yearsChoice = findViewById(R.id.second_hand_smoke_20_years_choice);

        btnNext = (Button) findViewById(R.id.button_second_hand_smoke_next);
        btnBack = (Button) findViewById(R.id.button_second_hand_smoke_back);

        // Visibility
        TextView riskFacTextView = findViewById(R.id.second_hand_smoke_risk_fac);
        riskFacTextView.setText(sex + ": " + riskFacAge + " x " + riskFacSmoke + " = " + riskFacAge*riskFacSmoke);
        resetPage();

        // Listeners
        secondHandSmokeChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
                int selectedRadioButtonId = -1;
                String selectedButtonText = "";
                switch ((String)checkedRatioButton.getText()) {
                    case "Ja":
                        secondHandSmokeSourceChoice.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.INVISIBLE);

                        selectedRadioButtonId = secondHandSmokeSourceChoice.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            selectedButtonText = ((RadioButton) secondHandSmokeSourceChoice.findViewById(selectedRadioButtonId)).getText().toString();
                            if (selectedButtonText.equals("Durch den Partner")) {
                                if (sex.equals("female")) {
                                    riskFac = 1.24F;
                                } else {
                                    riskFac = 1.37F;
                                }
                                btnNext.setVisibility(View.VISIBLE);
                            }
                            else{
                                riskFac = 1.0F;
                                secondHand20yearsTxt.setVisibility(View.VISIBLE);
                                secondHand20yearsChoice.setVisibility(View.VISIBLE);

                                selectedRadioButtonId = secondHand20yearsChoice.getCheckedRadioButtonId();
                                if (selectedRadioButtonId != -1) {
                                    selectedButtonText = ((RadioButton) secondHand20yearsChoice.findViewById(selectedRadioButtonId)).getText().toString();
                                    if (selectedButtonText.equals("Nein")) {
                                        riskFac = 1.0F;
                                    }
                                    else{
                                        if(sex.equals("female")){
                                            riskFac = 1.19F;
                                        }
                                        else{
                                            riskFac = 1.12F;
                                        }
                                    }

                                    btnNext.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                        break;
                    case "Nein":
                        resetPage();

                        riskFac = 1.0F;
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }
        });

        secondHandSmokeSourceChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);

                switch ((String)checkedRatioButton.getText()) {
                    case "Durch den Partner":
                        secondHand20yearsTxt.setVisibility(View.INVISIBLE);
                        secondHand20yearsChoice.setVisibility(View.INVISIBLE);
                        if(sex.equals("female")){
                            riskFac = 1.24F;
                        }
                        else{
                            riskFac = 1.37F;
                        }
                        btnNext.setVisibility(View.VISIBLE);

                        break;
                    case "Am Arbeitsplatz":
                        riskFac = 1.0F;
                        btnNext.setVisibility(View.INVISIBLE);

                        secondHand20yearsTxt.setVisibility(View.VISIBLE);
                        secondHand20yearsChoice.setVisibility(View.VISIBLE);
                        break;
                    default:
                }

            }
        });

        secondHand20yearsChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);

                switch ((String)checkedRatioButton.getText()) {
                    case "Ja":

                        if(sex.equals("female")){
                            riskFac = 1.19F;
                        }
                        else{
                            riskFac = 1.12F;
                        }
                        btnNext.setVisibility(View.VISIBLE);

                        break;
                    case "Nein":
                        riskFac = 1.0F;

                        break;
                    default:
                }

                btnNext.setVisibility(View.VISIBLE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");

                Intent intent =  new Intent(SecondHandSmoke.this, RiskFactorSmoke.class);
                intent.putExtra("Sex", sex);
                intent.putExtra("RiskFactorAge", riskFacAge);
                startActivity(intent);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");

                Intent intent =  new Intent(SecondHandSmoke.this, PollutantLoad.class);

                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                intent.putExtra("RiskFactorSecondHandSmoke", riskFac);
                intent.putExtra("Sex", sex);
                startActivity(intent);
            }
        });
    }

    private void resetPage(){
        riskFac = 1.0F;

        secondHandSmokeTxt.setVisibility(View.VISIBLE);
        secondHandSmokeChoice.setVisibility(View.VISIBLE);
        secondHandSmokeSourceChoice.setVisibility(View.INVISIBLE);
        secondHand20yearsTxt.setVisibility(View.INVISIBLE);
        secondHand20yearsChoice.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.VISIBLE);
    }

}
