package com.example.calculatorandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    double firstNum;
    String operation;


    TextView screen;
    Button off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        screen = findViewById(R.id.screen);
        //off = findViewById(R.id.button_off);


        Button num0 = findViewById(R.id.button_0);
        Button num1 = findViewById(R.id.button_1);
        Button num2 = findViewById(R.id.button_2);
        Button num3 = findViewById(R.id.button_3);
        Button num4 = findViewById(R.id.button_4);
        Button num5 = findViewById(R.id.button_5);
        Button num6 = findViewById(R.id.button_6);
        Button num7 = findViewById(R.id.button_7);
        Button num8 = findViewById(R.id.button_8);
        Button num9 = findViewById(R.id.button_9);

        Button on = findViewById(R.id.button_on);
        Button off = findViewById(R.id.button_off);
        Button ac = findViewById(R.id.button_ac);
        Button del = findViewById(R.id.button_del);
        Button div = findViewById(R.id.button_div);
        Button add = findViewById(R.id.button_add);
        Button mul = findViewById(R.id.button_mul);
        Button dot = findViewById(R.id.button_dot);
        Button sub = findViewById(R.id.button_sub);
        Button eq = findViewById(R.id.button_eq);


        ac.setOnClickListener(view -> {
            firstNum = 0;
            screen.setText("0");
        });


        off.setOnClickListener(view -> screen.setVisibility(View.GONE));


        on.setOnClickListener(view -> {
            screen.setVisibility(View.VISIBLE);
            screen.setText("0");
        });


        ArrayList<Button> nums = new ArrayList<>();
        nums.add(num0);
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);
        nums.add(num4);
        nums.add(num5);
        nums.add(num6);
        nums.add(num7);
        nums.add(num8);
        nums.add(num9);


        for (Button b : nums) {
            b.setOnClickListener(view -> {
                if (!screen.getText().toString().equals("0")) {
                    screen.setText(screen.getText().toString() + b.getText().toString());
                } else {
                    screen.setText(b.getText().toString());
                }
            });
        }


        ArrayList<Button> opers = new ArrayList<>();
        opers.add(div);
        opers.add(mul);
        opers.add(add);
        opers.add(sub);


        for (Button b : opers) {
            b.setOnClickListener(view -> {
                firstNum = Double.parseDouble(screen.getText().toString());
                operation = b.getText().toString(); // Store the operation as a String
                screen.setText("0");
            });
        }


        del.setOnClickListener(view -> {
            String num = screen.getText().toString();
            if (num.length() > 1) {
                screen.setText(num.substring(0, num.length() - 1));
            } else if (num.length() == 1 && !num.equals("0")) {
                screen.setText("0");
            }
        });

        // Set click listener for dot button
        dot.setOnClickListener(view -> {
            if (!screen.getText().toString().contains(".")) {
                screen.setText(screen.getText().toString() + ".");
            }
        });


        eq.setOnClickListener(view -> {
            double secondNum = Double.parseDouble(screen.getText().toString());
            double result = 0;

            if (operation.equals("/")) {
                result = firstNum / secondNum;
            } else if (operation.equals("X")) {
                result = firstNum * secondNum;
            } else if (operation.equals("+")) {
                result = firstNum + secondNum;
            } else if (operation.equals("-")) {
                result = firstNum - secondNum;
            }

            screen.setText(String.valueOf(result));
            firstNum = result; // Update firstNum for subsequent operations
        });

    }
}
