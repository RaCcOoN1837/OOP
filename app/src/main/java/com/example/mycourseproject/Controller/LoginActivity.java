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
    private EditText passwordField;
    private Button loginButton, resetButton, okButton, cancelButton;
    private SharedPreferences sharedPreferences;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordField = findViewById(R.id.LOGetpassword);
        loginButton = findViewById(R.id.LOGbtnsugnin);
        resetButton = findViewById(R.id.LOGbtnresetpassword);
        helper = new DBHelper(this);

        // Получаем переданный пароль.
        Intent intent = getIntent();
        final String validPassword = intent.getStringExtra("password");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = passwordField.getText().toString();
                if (password.equals(validPassword)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Неверный пароль!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -670);
                    toast.show();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewContactDialog(
                        "Вы уверены что хотите сбросить пароль?\n" +
                                "Все сохраненные задания при этом удалятся!"
                );
            }
        });
    }

    /**
     * Метод для отображения окна с предупреждением.
     */
    public void createNewContactDialog(String warningText) {
        builder = new AlertDialog.Builder(this);
        final View warningWindow = getLayoutInflater().inflate(R.layout.reset_warning, null);

        tvWarning = warningWindow.findViewById(R.id.RESETtv);
        okButton = warningWindow.findViewById(R.id.RESETbtnOK);
        cancelButton = warningWindow.findViewById(R.id.RESETbtnCancel);

        // Устанавливаем текст предупреждения.
        tvWarning.setText(warningText);

        builder.setView(warningWindow);
        dialog = builder.create();
        dialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

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

        cancelButton.setOnClickListener(new View.OnClickListener() {
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