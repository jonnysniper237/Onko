package com.icd4.icd6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        TextView icdlist = (TextView) findViewById(R.id.icdlist);
        String icdlistStr = "<b>C34 Hauptbronchus</b><br><br>" +
                "<b>C34.1 Oberlappen</b><br>(-Bronchus)<br><br>" +
                "<b>C34.2 Mittellappen</b><br>(-Bronchus)<br><br>" +
                "<b>C34.3 Unterlappen</b><br>(-Bronchus)<br><br>" +
                "<b>C34.8 Bronchus und Lunge</b><br>mehrere Teilbereiche überlappend<br><br>" +
                "<b>C34.9 Bronchus und Lunge</b><br>nicht näher bezeichnet<br><br>";
        icdlist.setText(Html.fromHtml(icdlistStr));

        Button btnIcdNo = (Button) findViewById(R.id.button_icdNo);
        btnIcdNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked btnIcdYes");
                Intent intent =  new Intent(MainActivity.this, RiskFactorAge.class);

                startActivity(intent);
            }
        });

        Button btnIcdYes = (Button) findViewById(R.id.button_icdYes);
        btnIcdYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked btnIcdYes");

                Intent intent =  new Intent(MainActivity.this, OncologyConsultation.class);
                intent.putExtra("Severity",4);
                intent.putExtra("SourceActivity","MainActivity");

                startActivity(intent);
            }
        });
    }


}
