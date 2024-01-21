package com.icd4.icd6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OncologyConsultation extends Activity {
    float riskFacAge = 1.0F;
    float riskFacSmoke = 1.0F;
    float riskFacSecondHandSmoke = 1.0F;
    float riskFacPollutantLoad = 1.0F;
    float riskFacGeneticFactor = 1.0F;
    String sex = "";

    int severity = 0;

    String sourceActitvity = "";

    private static final String TAG = "OncologyConsultation";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onc_consultn);
        Log.d(TAG, "onCreate: Starting.");
        Intent myIntent = getIntent();

        sex = myIntent.getStringExtra("Sex");
        riskFacAge = myIntent.getFloatExtra("RiskFactorAge",1.0F);
        riskFacSmoke = myIntent.getFloatExtra("RiskFactorSmoke",1.0F);
        riskFacSecondHandSmoke = myIntent.getFloatExtra("RiskFactorSecondHandSmoke",1.0F);
        riskFacPollutantLoad = myIntent.getFloatExtra("RiskFactorPollutantLoad",1.0F);
        riskFacGeneticFactor = myIntent.getFloatExtra("RiskFactorGeneticFactor",1.0F);
        severity = myIntent.getIntExtra("Severity", 0);

        sourceActitvity = myIntent.getStringExtra("SourceActivity");

        TextView consultationTextView = (TextView)findViewById(R.id.kind_of_consultation);
        String consultationText = "keine onkologische Beratung";

        if (sourceActitvity.equals("GeneticFactor")){

            float riskFac = riskFacAge * riskFacSmoke * riskFacSecondHandSmoke * riskFacPollutantLoad * riskFacGeneticFactor;

            if (riskFac > 90){
                consultationText = "onkologische Beratung bei Aufnahme, wiederkehrend alle 7 Tage, vor Entlassung und bei Bedarf poststationär (telefonisch)";
            }
            else if (riskFac > 75){
                consultationText = "onkologische Beratung bei Aufnahme und vor Entlassung, spätestens aber 7 Tage nach Erstberatung";
            }
            else if (riskFac > 50){
                consultationText = "onkologische Beratung bei Aufnahme";
            }
        }
        else if(sourceActitvity.equals("Symptoms") || sourceActitvity.equals("MainActivity")){

            if (severity == 4){
                consultationText = "onkologische Beratung bei Aufnahme, wiederkehrend alle 7 Tage, vor Entlassung und bei Bedarf poststationär (telefonisch)";
            }
            else if (severity == 3){
                consultationText = "onkologische Beratung bei Aufnahme und vor Entlassung, spätestens aber 7 Tage nach Erstberatung";
            }
            else if (severity == 2){
                consultationText = "onkologische Beratung bei Aufnahme";
            }
            else if (severity == 1){
                consultationText = "onkologische Beratung wenn gewünscht";
            }
        }

        consultationTextView.setText(consultationText);



        Button btnBack = (Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnBack clicked.");
                Intent intent =  null;
                if (sourceActitvity.equals("MainActivity")){
                    intent =  new Intent(OncologyConsultation.this, MainActivity.class);
                }
                else if (sourceActitvity.equals("GeneticFactor")){
                    intent =  new Intent(OncologyConsultation.this, GeneticFactor.class);
                    intent.putExtra("RiskFactorAge", riskFacAge);
                    intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                    intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                    intent.putExtra("RiskFactorPollutantLoad", riskFacPollutantLoad);

                    intent.putExtra("Sex", sex);
                }
                else if (sourceActitvity.equals("Symptoms")){
                    intent =  new Intent(OncologyConsultation.this, Symptoms.class);
                    intent.putExtra("RiskFactorAge", riskFacAge);
                    intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                    intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                    intent.putExtra("RiskFactorPollutantLoad", riskFacPollutantLoad);

                    intent.putExtra("Sex", sex);
                }

                startActivity(intent);
            }
        });

        Button btnNext = (Button) findViewById(R.id.button_next);
        Button btnMain = (Button) findViewById(R.id.button_main);

        if (sourceActitvity.equals("MainActivity")){
            btnMain.setVisibility(View.INVISIBLE);
            btnNext.setText("Risikofaktoren");
        }
        else if (sourceActitvity.equals("GeneticFactor")){
            btnNext.setText("Symptome");
        }
        else if (sourceActitvity.equals("Symptoms")){
            btnNext.setVisibility(View.INVISIBLE);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNext clicked.");
                Intent intent =  null;

                if (sourceActitvity.equals("MainActivity")){
                    intent =  new Intent(OncologyConsultation.this, RiskFactorAge.class);
                }
                else if (sourceActitvity.equals("GeneticFactor")){
                    intent =  new Intent(OncologyConsultation.this, Symptoms.class);
                    intent.putExtra("RiskFactorAge", riskFacAge);
                    intent.putExtra("RiskFactorSmoke", riskFacSmoke);
                    intent.putExtra("RiskFactorSecondHandSmoke", riskFacSecondHandSmoke);
                    intent.putExtra("RiskFactorPollutantLoad", riskFacPollutantLoad);

                    intent.putExtra("Sex", sex);
                }
                startActivity(intent);
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnMain clicked.");
                Intent intent =  null;
                intent =  new Intent(OncologyConsultation.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
