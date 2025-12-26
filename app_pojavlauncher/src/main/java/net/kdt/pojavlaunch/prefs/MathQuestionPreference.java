package net.kdt.pojavlaunch.prefs;

import static net.kdt.pojavlaunch.Tools.dialog;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.SwitchPreferenceCompat;

import net.kdt.pojavlaunch.R;

import java.util.Random;

public class MathQuestionPreference extends SwitchPreferenceCompat {
    public MathQuestionPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {
        if (isChecked()) { // Don't ask for braincells if turning off
            super.onClick();
            return;
        }
        Random random = new Random();
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(10) + 1;
        int c = random.nextInt(10) + 1;
        int d = random.nextInt(10) + 1;
        int f = random.nextInt(10) + 1;
        final int answer = (a * b) + c - d;

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.sodium_math_warning_title)
                .setMessage(this.getContext().getString(R.string.sodium_math_warning_message, a, b, c, d))
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    try {
                        int userAnswer = Integer.parseInt(input.getText().toString());
                        if (userAnswer == answer) {
                            super.onClick();
                        } else {
                            dialog(getContext(), "Wrong!", "You failed the math test!");
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Please enter a number.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
