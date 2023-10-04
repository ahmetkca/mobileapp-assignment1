package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.function.Function;

public class CalculateActivity extends AppCompatActivity {

    public static final String INTENT_EMI_RESULT_KEY = "EMI_RESULT";

    /**
     * This activity facilitates the calculation of EMI based on user input.
     * The user provides the principal amount, annual interest rate, and loan tenure.
     * Upon calculating the EMI, the result is then passed to the ResultActivity for display.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        Button calculateBtn = (Button) findViewById(R.id.calculateBtn);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout principalTextInputLayout = findViewById(R.id.principalLoanAmountTextInputLayout);
                TextInputLayout annualTextInputLayout = findViewById(R.id.annualInterestRateTextInputLayout);
                TextInputLayout loanTenureTextInputLayout = findViewById(R.id.loanTenureMonthsTextInputLayout);

                 // heck if any of the input fields (principal, annual rate, or loan tenure) are empty.
                 //If any field is empty, display a toast message prompting the user to fill all fields.
                if (TextUtils.isEmpty(principalTextInputLayout.getEditText().getText().toString())
                    || TextUtils.isEmpty(annualTextInputLayout.getEditText().getText().toString())
                    || TextUtils.isEmpty(loanTenureTextInputLayout.getEditText().getText().toString())) {
                    Toast.makeText(CalculateActivity.this, "All input fields must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }


                double principalLoanAmount = getInputValue(R.id.principalLoanAmountTextInputLayout, Double::parseDouble);

                Log.d("calculateBtn onClick: ", "Principal amount: " + principalLoanAmount);

                double annualInterestRate = getInputValue(R.id.annualInterestRateTextInputLayout, Double::parseDouble);
                Log.d("calculateBtn onClick: ", "Annual interest; " + annualInterestRate);
                double monthlyInterestRate = (annualInterestRate / 12) / 100;
                Log.d("calculateBtn onClick: ", "Monthly interest " + monthlyInterestRate);

                int loanTenureMonths = getInputValue(R.id.loanTenureMonthsTextInputLayout, Integer::parseInt);
                Log.d("calculateBtn onClick: ", "Loan tenure (Months): " + loanTenureMonths);

                double emiResult = calculateEMI(principalLoanAmount, monthlyInterestRate, loanTenureMonths);

                Log.d("calculateBtn onClick: ", "EMI Result: " + emiResult);

                // Create an intent to transition from CalculateActivity to ResultActivity.
                // Add the calculated EMI result as an extra to pass it to the ResultActivity.
                // Start the ResultActivity with the intent.
                Intent intent = new Intent(CalculateActivity.this, ResultActivity.class);
                intent.putExtra(INTENT_EMI_RESULT_KEY, emiResult);
                startActivity(intent);

            }
        });
    }

    /**
     * Retrieve the value from a TextInputLayout and convert it to the desired numeric type.
     *
     * @param <T>                The type of Number to which the input string needs to be converted.
     * @param textInputLayoutId  The ID of the TextInputLayout from which the input string should be retrieved.
     * @param converter          A function that takes a String as input and converts it to type T.
     * @return The converted numeric value of the input string.
     */
    private <T extends Number> T getInputValue(int textInputLayoutId, Function<String, T> converter) {
        TextInputLayout textInputLayout =  (TextInputLayout) findViewById(textInputLayoutId);
        String textValue = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();

        return converter.apply(textValue);
    }

    /**
     * Calculate the Equated Monthly Installment (EMI) for a loan.
     *
     * EMI is calculated using the formula:
     * EMI = [P * r * (1+r)^n] / [(1+r)^n – 1]
     * where P is the principal amount, r is the monthly interest rate, and n is the tenure in months.
     *
     * @param principal           The principal loan amount.
     * @param monthlyInterestRate The monthly interest rate (e.g., if annual rate is 12%, then monthly rate is 0.01).
     * @param tenure              The tenure of the loan in months.
     * @return The EMI amount.
     */
    private double calculateEMI(double principal, double monthlyInterestRate, int tenure) {
        return (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenure)) /
                (Math.pow(1 + monthlyInterestRate, tenure) - 1);


    }

}
