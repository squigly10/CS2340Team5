package com.high5.a2340.high5.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.high5.a2340.high5.Activities.MainActivity;
import com.high5.a2340.high5.Model.User;
import com.high5.a2340.high5.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee_Registration_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference Database;

    private EditText userEmail;
    private EditText userPassword;
    private EditText userPasswordConfirmation;
    private Button signUpButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration_);

        firebaseAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();

        userEmail = findViewById(R.id.emailTextBox);
        userPassword = findViewById(R.id.passwordTextBox);
        userPasswordConfirmation = findViewById(R.id.confirmPasswordTextBox);
        signUpButton = findViewById(R.id.signInButton);

        //get spinner object and populate with UserTypes Enum

        ArrayAdapter<String> userTypeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, MainActivity.legalUserTypes);
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        signUpButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    public void createAccount() {
        final String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();
        final String userType = "Employee";

        if (!validateForm()) {
            return;
        }
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            // Add new user information into the database
                            String userID = firebaseAuth.getUid();
                            User newUser = new User(email, password, userType);
                            Database.child("users").child(userID).setValue(newUser);

                            startActivity(new Intent(Employee_Registration_Activity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if (task.getException().getMessage().contains("The email address is already in use by another account.")) {
                                Toast.makeText(Employee_Registration_Activity.this,
                                        "The email address is already in use by another account.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Employee_Registration_Activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
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
        }
        else if (!isEmailValid(email)) {
            Toast.makeText(Employee_Registration_Activity.this, "Please enter valid email address.",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else {
            userEmail.setError(null);
        }

        String password = userPassword.getText().toString();
        String passwordConfirmation = userPasswordConfirmation.getText().toString();
        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Required.");
            valid = false;
        } else if (password.length() < 6) {
            userPassword.setError("Must be longer than 6 characters.");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        if (TextUtils.isEmpty(passwordConfirmation)) {
            userPasswordConfirmation.setError("Required");
            valid = false;
        } else {
            userPasswordConfirmation.setError(null);
        }
        if (!password.equals(passwordConfirmation)) {
            userPasswordConfirmation.setError("Passwords must match!");
            valid = false;
        } else {
            userPasswordConfirmation.setError(null);
        }

        return valid;
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public void onClick(View view) {
        if (view == signUpButton) {
            createAccount();
        }
    }
}
