package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * Initializes the MainActivity upon creation.
     * Sets the layout for the activity and creates an onClick listener for the 'goToCalculate' button.
     * Once the button is clicked, the user is navigated to the CalculateActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToCalculate = (Button) findViewById(R.id.goToCalculateBtn);

        goToCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
                startActivity(intent);
            }
        });
    }
}