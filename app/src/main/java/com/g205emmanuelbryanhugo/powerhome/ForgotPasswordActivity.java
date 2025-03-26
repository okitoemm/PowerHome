package com.g205emmanuelbryanhugo.powerhome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
                String email = etEmail.getText().toString().trim();
                String url = "http://10.0.2.2/powerhome_php/forgotPassword.php?email=" + email;

                RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        response -> {
                            Toast.makeText(this, "Si l'email existe, un lien a été envoyé.", Toast.LENGTH_LONG).show();
                        },
                        error -> {
                            Toast.makeText(this, "Erreur réseau", Toast.LENGTH_LONG).show();
                        });

                queue.add(request);
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
