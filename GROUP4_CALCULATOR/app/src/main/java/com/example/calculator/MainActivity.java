package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText num1 = (EditText) findViewById(R.id.no1);
        final EditText num2 = (EditText) findViewById(R.id.no2);
        final EditText total =(EditText) findViewById(R.id.result);
        Button addBut = (Button) findViewById(R.id.addButton);
        Button subBut = (Button) findViewById(R.id.subButton);
        Button mulBut = (Button) findViewById(R.id.multiplyButton);
        Button divBut = (Button) findViewById(R.id.divideButton);

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double sum1 = Double.parseDouble(num1.getText().toString());
                double sum2 = Double.parseDouble(num2.getText().toString());
                double result1 = sum1 + sum2;
                total.setText(Double.toString(result1));
            }
        });

        subBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double sub1 = Double.parseDouble(num1.getText().toString());
                double sub2 = Double.parseDouble(num2.getText().toString());
                double result2 = sub1 - sub2;
                total.setText(Double.toString(result2));
            }
        });
        mulBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mul1 = Double.parseDouble(num1.getText().toString());
                double mul2 = Double.parseDouble(num2.getText().toString());
                double result3 = mul1 * mul2;
                total.setText(Double.toString(result3));
            }
        });

        divBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double div1 = Double.parseDouble(num1.getText().toString());
                double div2 = Double.parseDouble(num2.getText().toString());
                double result4 = div1/div2;
                total.setText(Double.toString(result4));
                if (div2 == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "ERROR! Invalid Input! try again";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }
}

