package com.example.mycourseproject.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycourseproject.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etPasswordFirst, etPasswordSecond;
    private Button btnSignUp;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etPasswordFirst = findViewById(R.id.REGetpasswordone);
        etPasswordSecond = findViewById(R.id.REGetpasswordtwo);
        btnSignUp = findViewById(R.id.REGbtnregister);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordFirst = etPasswordFirst.getText().toString();
                String passwordSecond = etPasswordSecond.getText().toString();

                if ( passwordFirst.equals("") || passwordSecond.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Заполните поля!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -670);
                    toast.show();

                } else {
                    if (passwordFirst.equals(passwordSecond)) {
                        sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", encrypt(passwordFirst));
                        editor.commit();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Пароли не идентичны!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, -670);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // "Парализуем" кнопку "Назад", так как все необходимые кнопки у нас имеются.
    }

    /**
     * Метод для шифрования пароля.
     */
    private String encrypt(String password) {
        return Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
    }
}