package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {


    /**
     * Initializes the ResultActivity upon creation.
     * This activity displays the calculated EMI result that was passed from the CalculateActivity.
     * Users can see the result formatted as currency and also have the option to navigate back to the CalculateActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get the Intent that started this activity
        Intent intent = getIntent();

        double emiResult = intent.getDoubleExtra(CalculateActivity.INTENT_EMI_RESULT_KEY, 0.0);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String formattedEMI = currencyFormat.format(emiResult);

        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        resultTextView.setText(getString(R.string.emi_result, formattedEMI));

        Button goBackBtn = findViewById(R.id.goBackBtn);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, CalculateActivity.class);
                startActivity(intent);
            }
        });
    }

}
