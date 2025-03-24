package com.g205emmanuelbryanhugo.powerhome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Patterns;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText etEmail;
    private Button btnReset;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.et_email);
        btnReset = findViewById(R.id.btn_reset);
        tvBackToLogin = findViewById(R.id.tv_back_to_login);

        btnReset.setOnClickListener(v -> {
            if (validateEmail()) {
                // TODO: Implémenter la réinitialisation du mot de passe
            }
        });

        tvBackToLogin.setOnClickListener(v -> finish());
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            etEmail.setError("L'email est requis");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email invalide");
            return false;
        }
        return true;
    }
}
