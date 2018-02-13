package com.high5.a2340.high5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth fireBaseAuth;

    private Button loginButton;
    private EditText emailText;
    private EditText passwordText;
    private TextView signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fireBaseAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.button);
        emailText = (EditText) findViewById((R.id.emailTextBox));
        passwordText = (EditText) findViewById((R.id.passwordTextBox));
        signInButton = (TextView) findViewById((R.id.registrationButton));

        loginButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
        // DO THIS
        // got to main screen
    }

    public void signIn() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        fireBaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = fireBaseAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == loginButton) {
            signIn();
        }
        if (view ==  signInButton) {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        }
    }
}
