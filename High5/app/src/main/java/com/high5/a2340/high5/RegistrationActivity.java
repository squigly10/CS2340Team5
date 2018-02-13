package com.high5.a2340.high5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth firebaseAuth;

    private EditText userEmail;
    private EditText userPassword;
    private EditText userPasswordConfirmation;
    private Button signUpButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.emailTextBox);
        userPassword = findViewById(R.id.passwordTextBox);
        userPasswordConfirmation = findViewById(R.id.confirmPasswordTextBox);
        signUpButton = findViewById(R.id.signInButton);
        backButton = findViewById(R.id.backButton);

        signUpButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    public void createAccount() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (!validateForm()) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    //checks the validity of the fields in the form.
    private boolean validateForm() {
        boolean valid = true;

        String email = userEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Required.");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        String password = userPassword.getText().toString();
        String passwordConfirmation = userPasswordConfirmation.getText().toString();
        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Required.");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        if (TextUtils.isEmpty(passwordConfirmation)) {
            userPasswordConfirmation.setError("Required");
            valid = false;
        } else if (!password.equals(passwordConfirmation)) {
            userPasswordConfirmation.setError("Passwords must match!");
            valid = false;
        } else {
            userPasswordConfirmation.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View view) {
        if (view == signUpButton) {
            createAccount();
        }
        if (view == backButton) {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        }
    }
}
