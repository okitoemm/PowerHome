package com.g205emmanuelbryanhugo.powerhome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegisterLink;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegisterLink = findViewById(R.id.tv_register_link);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);

        btnLogin.setOnClickListener(view -> {
            if (validateFields()) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                String url = "http://10.0.2.2/powerhome_php/login.php?email=" + email + "&password=" + password;

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            if (response.contains("incorrect")) {
                                etPassword.setError("Email ou mot de passe incorrect");
                                return;
                            }

                            JSONObject json = new JSONObject(response);
                            String token = json.getString("token");
                            String expiredAt = json.getString("expired_at");

                            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                            prefs.edit()
                                .putString("token", token)
                                .putString("expired_at", expiredAt)
                                .apply();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> {
                        Toast.makeText(LoginActivity.this, "Erreur réseau", Toast.LENGTH_LONG).show();
                    });

                queue.add(request);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        tvRegisterLink.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        tvForgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });
    }

    private boolean validateFields() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("L'email est requis");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email invalide");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Le mot de passe est requis");
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
            return false;
        }

        return true;
    }
}