package com.high5.a2340.high5.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.high5.a2340.high5.Model.Model;
import com.high5.a2340.high5.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth fireBaseAuth;

    private Button logoutButton;

    private ProgressDialog progressDialog;

    private Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = (Button) findViewById(R.id.logoutButton);

        progressDialog = new ProgressDialog(this);

        fireBaseAuth = FirebaseAuth.getInstance();

        logoutButton.setOnClickListener(this);

        this.model.populateShelters();
    }

    @Override
    public void onClick(View view) {
        if (view == logoutButton) {
            logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private void logout() {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();
        fireBaseAuth.signOut();
        progressDialog.dismiss();
    }
}
