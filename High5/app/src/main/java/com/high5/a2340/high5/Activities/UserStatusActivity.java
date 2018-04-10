package com.high5.a2340.high5.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.high5.a2340.high5.R;
import com.high5.a2340.high5.Registration.Administrator_Registration_Activity;
import com.high5.a2340.high5.Registration.Employee_Registration_Activity;
import com.high5.a2340.high5.Registration.User_Registration_Activity;

/**
 *  Activity for the type of user
 *  @author High5
 *  @version 1.4
 */
public class UserStatusActivity extends AppCompatActivity implements View.OnClickListener{

    private Button User;
    private Button Administrator;
    private Button Employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        User = findViewById(R.id.userButton);
        Administrator = findViewById(R.id.administratorButton);
        Employee = findViewById(R.id.employeeButton);

        User.setOnClickListener(this);
        Administrator.setOnClickListener(this);
        Employee.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == User) {
            startActivity(new Intent(UserStatusActivity.this,
                    User_Registration_Activity.class));
        }
        if (view == Administrator) {
            startActivity(new Intent(UserStatusActivity.this,
                    Administrator_Registration_Activity.class));
        }
        if (view == Employee) {
            startActivity(new Intent(UserStatusActivity.this,
                    Employee_Registration_Activity.class));
        }
    }
}
