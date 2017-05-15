package com.calculator.lab1.calculatorsimpleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;

public class SimpleCalcActivity extends AppCompatActivity {

    StringBuilder editText = new StringBuilder("");
    TextView textView;
    private boolean pointAvailable = true;
    private StringBuilder stringBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calc);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(editText);
    }

    public void onNumClicked(View view) {
        Button button = (Button) view;
        concatenate(button.getText());
    }

    public void show() {
        textView.setText(editText);
    }

    public boolean concatenate(CharSequence charSequence) {
        if (editText.length() < 30) {
            editText.append(charSequence);
            show();
            return true;
        } else {
            Toast.makeText(this, "Input is too long", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onOperationClicked(View view) {
        if (editText.toString().length() > 0) {
            if(!isSign(String.valueOf(editText.charAt(editText.length()-1)))) {
                Button button = (Button) view;
                char c = editText.charAt(editText.length() - 1);
                if (isSign(String.valueOf(c))) {
                    return;
                }
                if (concatenate(button.getText())) {
                    setPointAvailable(true);
                }
            }
        }
    }

    public void onMinusClicked(View view) {
        if(editText.toString().length() == 0) {
            editText.append("-");
            show();
        } else if (editText.toString().length() > 0) {
            Button button = (Button) view;
            if (editText.charAt(editText.length() - 1) == '-') {
                return;
            }
            if (concatenate(button.getText())) {
                setPointAvailable(true);
            }
        }
    }

    public void onEqualClicked(View view) {
        try {
            if (editText.toString().length() > 0) {
                BigDecimal result = new Expression(editText.toString()).eval();
                editText = new StringBuilder(result.toString());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
        }

        checkIfPointExists(editText);
        show();
    }

    public void onClearClicked(View view) {
        editText.delete(0, editText.length());
        show();
        setPointAvailable(true);
    }

    public void onDeleteClicked(View view) {
        if (editText.toString().length() > 0) {
            int length = editText.length();
            char lastChar = editText.charAt(length - 1);
            if (length > 0) {
                if (lastChar == '.') {
                    setPointAvailable(true);
                } else if (editText.toString().contains(".") && (lastChar == '+' || lastChar == '-' || lastChar == '/' || lastChar == '*' || lastChar == '^')) {
                    setPointAvailable(false);
                }
                editText = new StringBuilder(editText.substring(0, editText.length() - 1));
                show();
            }
        }
    }

    public void onChangeSignClicked(View view) {
        if (editText.length() > 0) {
            String s = editText.substring(1, editText.length());
            if(!isSign(String.valueOf(s))) {
                if (editText.charAt(0) == '-') {
                    editText = new StringBuilder(editText.substring(1, editText.length()));

                } else if (editText.charAt(0) != '-') {
                    stringBuilder = editText = new StringBuilder("-".concat(editText.toString()));
                }
            }
        }
            show();
    }

    public void setPointAvailable(boolean pointAvailable) {
        this.pointAvailable = pointAvailable;
    }

    public void checkIfPointExists(StringBuilder string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '.') {
                setPointAvailable(false);
            }
        }
    }

    public void onPointClicked(View view) {
        if (editText.length() > 0) {
            if (pointAvailable) {
                Button button = (Button) view;
                if (concatenate(button.getText())) {
                    setPointAvailable(false);
                }
            }
        }
    }

    public boolean isSign(String s) {
        return (s.matches(".*[+].*") || s.matches(".*[-].*") || s.matches(".*[*].*") || s.matches(".*[/].*"));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("editText", editText.toString());
        savedInstanceState.putBoolean("point", pointAvailable);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText = new StringBuilder(savedInstanceState.getString("editText"));
        savedInstanceState.getBoolean("point");
        checkIfPointExists(editText);
        show();
    }
}
