package com.g205emmanuelbryanhugo.powerhome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Patterns;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLoginLink = findViewById(R.id.tv_login_link);

        btnRegister.setOnClickListener(view -> {
            if (validateFields()) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                String url = "http://10.0.2.2/powerhome_php/register.php?email=" + email + "&password=" + password;

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        response -> {
                            Toast.makeText(RegisterActivity.this, "Inscription réussie. Vous pouvez maintenant vous connecter.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        },
                        error -> {
                            Toast.makeText(RegisterActivity.this, "Erreur réseau ou serveur", Toast.LENGTH_LONG).show();
                        });

                queue.add(request);
            }
        });

        tvLoginLink.setOnClickListener(view -> {
            finish(); // Retourne à l'écran de connexion
        });
    }

    private boolean validateFields() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

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

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Les mots de passe ne correspondent pas");
            return false;
        }

        return true;
    }
}
