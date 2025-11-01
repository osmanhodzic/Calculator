package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button korijenButton;
    Button kvadratButton;
    Button kubButton;
    Button sinusStepenButton, sinusRadijanButton, kosinusStepenButton, kosinusRadijanButton;
    private TextView resultText;
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.resultText);

        setNumberButtonListeners();
        setOperatorButtonListeners();

        kvadratButton = findViewById(R.id.kvadratButton);
        korijenButton = findViewById(R.id.korijenButton);
        kubButton = findViewById(R.id.kubButton);
        sinusStepenButton = findViewById(R.id.sinusStepenButton);
        sinusRadijanButton = findViewById(R.id.sinusRadijanButton);
        kosinusStepenButton = findViewById(R.id.kosinusStepenButton);
        kosinusRadijanButton = findViewById(R.id.kosinusRadijanButton);

        // Kub button listener
        kubButton.setOnClickListener(v -> {
            String value = resultText.getText().toString();
            if (!value.isEmpty()) {
                try {
                    double broj = Double.parseDouble(value);
                    double kub = Math.pow(broj, 3);
                    resultText.setText(String.valueOf(kub));
                } catch (NumberFormatException e) {
                    resultText.setText("Error");
                }
            }
        });

        // Kosinus i sinus Button Listeners
        sinusStepenButton.setOnClickListener(v -> calculateSinusInDegrees());
        sinusRadijanButton.setOnClickListener(v -> calculateSinusInRadians());
        kosinusStepenButton.setOnClickListener(v -> calculateCosinusInDegrees());
        kosinusRadijanButton.setOnClickListener(v -> calculateCosinusInRadians());

        // Korijen Button Listener
        korijenButton.setOnClickListener(v -> {
            String value = resultText.getText().toString();
            if (!value.isEmpty()) {
                try {
                    double broj = Double.parseDouble(value);
                    if (broj >= 0) {
                        double korijen = Math.sqrt(broj);
                        resultText.setText(String.valueOf(korijen));
                    } else {
                        resultText.setText("Error");
                    }
                } catch (NumberFormatException e) {
                    resultText.setText("Error");
                }
            }
        });

        // Kvadrat Button Listener
        kvadratButton.setOnClickListener(v -> {
            String value = resultText.getText().toString();
            if (!value.isEmpty()) {
                try {
                    double broj = Double.parseDouble(value);
                    double kvadrat = broj * broj;
                    resultText.setText(String.valueOf(kvadrat));
                } catch (NumberFormatException e) {
                    resultText.setText("Error");
                }
            }
        });
    }

    private void calculateSinusInDegrees() {
        try {
            double number = Double.parseDouble(resultText.getText().toString());
            double result = Math.sin(Math.toRadians(number));  // Pretvara stepen u radijane
            resultText.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            resultText.setText("Error");
        }
    }

    private void calculateSinusInRadians() {
        try {
            double number = Double.parseDouble(resultText.getText().toString());
            double result = Math.sin(number);  // Sinus u radijanima
            resultText.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            resultText.setText("Error");
        }
    }

    private void calculateCosinusInDegrees() {
        try {
            double number = Double.parseDouble(resultText.getText().toString());
            double result = Math.cos(Math.toRadians(number));  // Pretvara stepen u radijane
            resultText.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            resultText.setText("Error");
        }
    }

    private void calculateCosinusInRadians() {
        try {
            double number = Double.parseDouble(resultText.getText().toString());
            double result = Math.cos(number);  // Kosinus u radijanima
            resultText.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            resultText.setText("Error");
        }
    }

    private void setNumberButtonListeners() {
        int[] numberIds = {R.id.jedanButton, R.id.dvaButton, R.id.triButton, R.id.cetiriButton, R.id.petButton,
                R.id.sestButton, R.id.sedamButton, R.id.osamButton, R.id.devetButton, R.id.nulaButton};

        View.OnClickListener listener = v -> {
            Button b = (Button) v;

            if (isNewOperation) {
                currentInput = b.getText().toString();
                isNewOperation = false;
            } else {
                currentInput += b.getText().toString();
            }
            resultText.setText(currentInput);
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        findViewById(R.id.saberiButton).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.oduzmiButton).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.mnoziButton).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.dijeliButton).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.jednakoButton).setOnClickListener(v -> calculateResult());
        findViewById(R.id.clearButton).setOnClickListener(v -> clear());
    }

    private void setOperator(String op) {
        if (currentInput.isEmpty()) {
            return;
        }

        currentInput += " " + op + " ";
        resultText.setText(currentInput);
        firstNumber = Double.parseDouble(currentInput.split(" ")[0]);
        operator = op;
        isNewOperation = false;
    }

    private void calculateResult() {
        try {
            String[] parts = currentInput.split(" ");
            if (parts.length < 3) {
                resultText.setText("Error");
                return;
            }

            double secondNumber = Double.parseDouble(parts[2]);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        resultText.setText("Error");
                        return;
                    }
                    break;
                default:
                    resultText.setText("Error");
                    return;
            }

            currentInput += " = " + result;
            resultText.setText(currentInput);
            isNewOperation = true;
        } catch (Exception e) {
            resultText.setText("Error");
        }
    }

    //clear textView
    private void clear() {
        currentInput = "";
        firstNumber = 0;
        operator = "";
        resultText.setText("0");
        isNewOperation = true;
    }
}
