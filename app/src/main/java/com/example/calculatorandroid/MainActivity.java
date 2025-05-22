package com.example.calculatorandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView screen;
    StringBuilder expression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);

        Button[] numberButtons = {
                findViewById(R.id.button_0), findViewById(R.id.button_1), findViewById(R.id.button_2),
                findViewById(R.id.button_3), findViewById(R.id.button_4), findViewById(R.id.button_5),
                findViewById(R.id.button_6), findViewById(R.id.button_7), findViewById(R.id.button_8),
                findViewById(R.id.button_9)
        };

        for (Button b : numberButtons) {
            b.setOnClickListener(v -> {
                expression.append(b.getText());
                screen.setText(expression.toString());
            });
        }

        Button add = findViewById(R.id.button_add);
        Button sub = findViewById(R.id.button_sub);
        Button mul = findViewById(R.id.button_mul);
        Button div = findViewById(R.id.button_div);
        Button dot = findViewById(R.id.button_dot);
        Button del = findViewById(R.id.button_del);
        Button ac = findViewById(R.id.button_ac);
        Button on = findViewById(R.id.button_on);
        Button off = findViewById(R.id.button_off);
        Button eq = findViewById(R.id.button_eq);

        View.OnClickListener opListener = v -> {
            if (expression.length() > 0 && !isOperator(expression.charAt(expression.length() - 1))) {
                expression.append(((Button)v).getText());
                screen.setText(expression.toString());
            }
        };

        add.setOnClickListener(opListener);
        sub.setOnClickListener(opListener);
        mul.setOnClickListener(opListener);
        div.setOnClickListener(opListener);

        dot.setOnClickListener(v -> {
            if (expression.length() == 0 || isOperator(expression.charAt(expression.length() - 1))) {
                expression.append("0.");
            } else {
                expression.append(".");
            }
            screen.setText(expression.toString());
        });

        del.setOnClickListener(v -> {
            if (expression.length() > 0) {
                expression.deleteCharAt(expression.length() - 1);
                screen.setText(expression.length() > 0 ? expression.toString() : "0");
            }
        });

        ac.setOnClickListener(v -> {
            expression.setLength(0);
            screen.setText("0");
        });

        on.setOnClickListener(v -> {
            screen.setVisibility(View.VISIBLE);
            screen.setText(expression.length() == 0 ? "0" : expression.toString());
        });

        off.setOnClickListener(v -> screen.setVisibility(View.GONE));

        eq.setOnClickListener(v -> {
            try {
                double result = eval(expression.toString());
                if (result == (long) result) {
                    screen.setText(String.valueOf((long) result));
                    expression = new StringBuilder(String.valueOf((long) result));
                } else {
                    screen.setText(String.valueOf(result));
                    expression = new StringBuilder(String.valueOf(result));
                }
            } catch (Exception e) {
                screen.setText("Error");
                expression.setLength(0);
            }
        });
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }
}
