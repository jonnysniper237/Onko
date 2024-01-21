package com.icd4.icd6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;




public class RiskFactorSmoke extends Activity {
    Button btnNext = null;
    Button btnBack = null;
    float riskFacAge = 1.0F;
    float riskFac = 1.0F;
    String sex = "";

    RadioGroup smokerChoice = null;
    RadioGroup nicotineAbstinence = null;
    TextView nicotineAbstinenceTxt = null;
    RadioGroup cigaretteType = null;
    TextView packsPerDayTxt = null;
    TextView yearsOfSmokingTxt = null;
    EditText packsPerDay = null;
    EditText yearsOfSmoking = null;
    TextView packYearsError = null;

    private static final String TAG = "RiskFactorSmoke";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_factor_smoke);
        Log.d(TAG, "onCreate: Starting.");

        TextView smokerHeading = (TextView) findViewById(R.id.smoker);

        Intent myIntent = getIntent();
        riskFacAge = myIntent.getFloatExtra("RiskFactorAge",1.0F);
        sex = myIntent.getStringExtra("Sex");

        TextView riskFacTextView = findViewById(R.id.smoke_risk_fac);
        riskFacTextView.setText(sex + ": " + riskFacAge);

        smokerChoice = findViewById(R.id.smoker_choice);
        smokerChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                resetPage();
                // determine risk factor
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
                int selectedRadioButtonId = -1;
                switch ((String)checkedRatioButton.getText()){
                    case "Raucher":
                        cigaretteType.setVisibility(View.VISIBLE);

                        selectedRadioButtonId = cigaretteType.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1){
                            String selectedButtonText = ((RadioButton) cigaretteType.findViewById(selectedRadioButtonId)).getText().toString();
                            if (selectedButtonText.equals("Zigarette?")){
                                packsPerDayTxt.setVisibility(View.VISIBLE);
                                yearsOfSmokingTxt.setVisibility(View.VISIBLE);
                                packsPerDay.setVisibility(View.VISIBLE);
                                yearsOfSmoking.setVisibility(View.VISIBLE);
                                if(calcPackYears() > 0){
                                    if (calcPackYears() > 60){
                                        if(sex.equals("female")){
                                            riskFac = 8.7F;
                                        }
                                        else{
                                            riskFac = 24F;
                                        }

                                    }
                                    btnNext.setVisibility(View.VISIBLE);
                                }
                            }
                            else{
                                btnNext.setVisibility(View.VISIBLE);
                            }
                            //packYearsError.setText(selectedButtonText);
                        }

                        break;
                    case "Ex-Raucher":
                        nicotineAbstinenceTxt.setVisibility(View.VISIBLE);
                        nicotineAbstinence.setVisibility(View.VISIBLE);
                        selectedRadioButtonId = nicotineAbstinence.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            String selectedButtonText = ((RadioButton) nicotineAbstinence.findViewById(selectedRadioButtonId)).getText().toString();
                            switch (selectedButtonText) {
                                case "Ja":
                                    // Abstinence > 10 years
                                    riskFac = 1.0F;
                                    btnNext.setVisibility(View.VISIBLE);
                                    break;
                                case "Nein":
                                    // Abstinence < 10 years
                                    if(sex.equals("female")){
                                        riskFac = 2.0F;
                                    }
                                    else{
                                        riskFac = 7.5F;
                                    }
                                    btnNext.setVisibility(View.VISIBLE);
                                    break;
                            }

                        }
                        break;
                    case "Nichtraucher":
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        nicotineAbstinenceTxt = (TextView) findViewById(R.id.nicotine_abstinence);
        nicotineAbstinence = (RadioGroup) findViewById(R.id.nicotine_abstinence_choice);
        nicotineAbstinenceTxt.setVisibility(View.INVISIBLE);
        nicotineAbstinence.setVisibility(View.INVISIBLE);
        nicotineAbstinence.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
                switch ((String)checkedRatioButton.getText()) {
                    case "Ja":
                        // Abstinence > 10 years
                        riskFac = 1.0F;
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                    case "Nein":
                        // Abstinence < 10 years
                        if(sex.equals("female")){
                            riskFac = 2.0F;
                        }
                        else{
                            riskFac = 7.5F;
                        }
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        cigaretteType = (RadioGroup) findViewById(R.id.cigarette_type);
        cigaretteType.setVisibility(View.INVISIBLE);
        cigaretteType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRatioButton = (RadioButton) radioGroup.findViewById(i);
                switch ((String)checkedRatioButton.getText()) {
                    case "Zigarette?":
                        // Cigarette Type: Cigarette
                        riskFac = 1.0F;
                        packsPerDayTxt.setVisibility(View.VISIBLE);
                        yearsOfSmokingTxt.setVisibility(View.VISIBLE);
                        packsPerDay.setVisibility(View.VISIBLE);
                        yearsOfSmoking.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.INVISIBLE);

                        if(calcPackYears() > 0){
                            if (calcPackYears() > 60){
                                if(sex.equals("female")){
                                    riskFac = 8.7F;
                                }
                                else{
                                    riskFac = 24F;
                                }

                            }
                            btnNext.setVisibility(View.VISIBLE);
                        }

                        break;
                    case "Zigarre, Zigarillo, Pfeife?":
                        // Cigarette Type: Cigarello
                        packsPerDayTxt.setVisibility(View.INVISIBLE);
                        yearsOfSmokingTxt.setVisibility(View.INVISIBLE);
                        packsPerDay.setVisibility(View.INVISIBLE);
                        yearsOfSmoking.setVisibility(View.INVISIBLE);
                        riskFac = 8F;
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        packsPerDayTxt = (TextView) findViewById(R.id.packs_per_day_txt);
        yearsOfSmokingTxt = (TextView) findViewById(R.id.years_of_smoking_txt);
        packsPerDay = (EditText) findViewById(R.id.packs_per_day);
        yearsOfSmoking = (EditText) findViewById(R.id.years_of_smoking);
        packYearsError = (TextView) findViewById(R.id.packs_years_error);

        packsPerDayTxt.setVisibility(View.INVISIBLE);
        yearsOfSmokingTxt.setVisibility(View.INVISIBLE);
        packsPerDay.setVisibility(View.INVISIBLE);
        yearsOfSmoking.setVisibility(View.INVISIBLE);
        packYearsError.setText(" ");

        packsPerDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(calcPackYears() > 0){
                    if (calcPackYears() > 60){
                        if(sex.equals("female")){
                            riskFac = 8.7F;
                        }
                        else{
                            riskFac = 24F;
                        }

                    }
                    else{
                        riskFac = 1.0F;
                    }

                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });

        yearsOfSmoking.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(calcPackYears() > 0){
                    if (calcPackYears() > 60){
                        if(sex.equals("female")){
                            riskFac = 8.7F;
                        }
                        else{
                            riskFac = 24F;
                        }

                    }
                    else{
                        riskFac = 1.0F;
                    }

                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });

        btnBack = findViewById(R.id.button_smoke_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");

                Intent intent =  new Intent(RiskFactorSmoke.this, RiskFactorAge.class);
                startActivity(intent);
            }
        });

        btnNext = findViewById(R.id.button_smoke_next);
        btnNext.setVisibility(View.INVISIBLE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");

                Intent intent =  new Intent(RiskFactorSmoke.this, SecondHandSmoke.class);
                intent.putExtra("RiskFactorAge", riskFacAge);
                intent.putExtra("RiskFactorSmoke", riskFac);
                intent.putExtra("Sex", sex);
                startActivity(intent);
            }
        });
    }

    private void resetPage(){

        riskFac = 1.0F;

        cigaretteType.setVisibility(View.INVISIBLE);
        nicotineAbstinenceTxt.setVisibility(View.INVISIBLE);
        nicotineAbstinence.setVisibility(View.INVISIBLE);
        packsPerDayTxt.setVisibility(View.INVISIBLE);
        yearsOfSmokingTxt.setVisibility(View.INVISIBLE);
        packsPerDay.setVisibility(View.INVISIBLE);
        yearsOfSmoking.setVisibility(View.INVISIBLE);
        packYearsError.setText(" ");
        btnNext.setVisibility(View.INVISIBLE);
    }

    private int calcPackYears(){
        int packYears = 0;
        if (!(packsPerDay.getText().toString().isEmpty() || yearsOfSmoking.getText().toString().isEmpty())){
            Double ppd = Double.parseDouble(packsPerDay.getText().toString());
            Double yos = Double.parseDouble(yearsOfSmoking.getText().toString());
            packYears = (int) (ppd * yos);
        }

        return  packYears;
    }
}
