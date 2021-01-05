package com.example.mycourseproject.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycourseproject.Model.Storage.DBHelper;
import com.example.mycourseproject.R;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView tvWarning;
    private EditText tvPassword;
    private Button btnLogIn, btnResetPassword, btnOK, btnCancel;
    private SharedPreferences preferences;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvPassword = findViewById(R.id.LOGetpassword);
        btnLogIn = findViewById(R.id.LOGbtnsugnin);
        btnResetPassword = findViewById(R.id.LOGbtnresetpassword);
        helper = new DBHelper(this);

        // Получаем переданный пароль.
        final String validPassword = getIntent().getStringExtra("password");

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = tvPassword.getText().toString();
                if (password.equals(validPassword)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Неверный пароль!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -670);
                    toast.show();
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewContactDialog(
                        "Вы уверены что хотите сбросить пароль?\n" +
                                "Все сохраненные задания при этом удалятся!"
                );
            }
        });
    }

    public void createNewContactDialog(String warningText) {
        builder = new AlertDialog.Builder(this);
        final View warningWindow = getLayoutInflater().inflate(R.layout.reset_warning, null);

        tvWarning = warningWindow.findViewById(R.id.RESETtv);
        btnOK = warningWindow.findViewById(R.id.RESETbtnOK);
        btnCancel = warningWindow.findViewById(R.id.RESETbtnCancel);

        // Устанавливаем текст предупреждения.
        tvWarning.setText(warningText);

        builder.setView(warningWindow);
        dialog = builder.create();
        dialog.show();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("password", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                // Удаляем существующий пароль.
                editor.clear();
                editor.commit();

                // Очищаем БД.
                helper.clearDataBase();

                // Переходим на окно регистрации.
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // "Парализуем" кнопку "Назад", так как все необходимые кнопки у нас имеются.
    }
}