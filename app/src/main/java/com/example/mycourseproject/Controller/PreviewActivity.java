package com.example.mycourseproject.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;

import com.example.mycourseproject.R;

public class PreviewActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                preferences = getSharedPreferences("password", MODE_PRIVATE);

                /*
                    Считываем пароль из Shared Preferences.
                    Если его нет, то открываем окно регистрации.
                    Если он есть, то открываем окно авторизации.
                 */
                String password = decrypt(preferences.getString("password", ""));

                if (password.equals("")) {
                    PreviewActivity.this.startActivity(new Intent(PreviewActivity.this, RegistrationActivity.class));
                } else {
                    Intent intent = new Intent(PreviewActivity.this, LoginActivity.class);

                    // Передаем считанный пароль, чтобы сравнивать с ним введенный.
                    intent.putExtra("password", password);
                    PreviewActivity.this.startActivity(intent);
                }
                PreviewActivity.this.finish();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        // "Парализуем" кнопку "Назад", так как все необходимые кнопки у нас имеются.
    }

    private String decrypt(String password) {
        return new String(Base64.decode(password, Base64.DEFAULT));
    }
}