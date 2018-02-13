package com.high5.a2340.high5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fireBaseAuth;

    private Button loginButton;
    private EditText emailText;
    private EditText passwordText;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fireBaseAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.button);
        emailText = (EditText) findViewById((R.id.emailTextBox));
        passwordText = (EditText) findViewById((R.id.passwordTextBox));
        signUpButton = (TextView) findViewById((R.id.signInButton));

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

}
