package com.example.karyc.calculator;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.karyc.calculator.databinding.ActivityMainBinding;

import java.util.EmptyStackException;

public class MainActivity extends AppCompatActivity {
    String expression = "";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        KeyboardInteractor keyboardInteractor = new KeyboardInteractor();
        binding.setKeyboardInteractor(keyboardInteractor);

    }

    public class KeyboardInteractor {
        public void onClickCancel(View view) {
            Log.d("v", "Cancel");
            expression = "";
            binding.textResult.setText("");
            binding.textExpression.setText(expression);
        }

        public void onClickEqual(View view) {
            Log.d("v", "Eq" + expression);
            double result = 0;
            try {
                result = ExpressionSolver.calculate(expression);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, R.string.error_message, Toast.LENGTH_LONG).show();
            }
            Log.d("dsd", "=" + result);
            binding.textResult.setText(String.valueOf(result));
            binding.textExpression.setText(expression);
            expression = "";

        }

        public void onClickSymbol(View view, String string) {
            Log.d("v", "symb-" + string);
            expression += string;
            binding.textResult.setText(expression);

        }
    }
}
