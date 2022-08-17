package com.example.budgy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
        import android.os.Bundle;
import android.util.Log;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
import android.widget.Toast;

import com.example.budgy.Models.Budget;
import com.example.budgy.Models.LoggedInUser;
import com.example.budgy.Models.User;

import java.util.Calendar;

import static com.example.budgy.ui.settings.Settings.getMonth;

public class LoginActivity extends AppCompatActivity
{
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button btnReg = (Button)findViewById(R.id.btnReg);
        EditText edtxtUser = (EditText)findViewById(R.id.edtxtUser);
        EditText edtxtPass = (EditText)findViewById(R.id.edtxtPass);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        TextView txtNewUser = (TextView)findViewById(R.id.txtNewUser);

        db = new DatabaseHelper(this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, Registration.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String userInput = edtxtUser.getText().toString().trim();
                String passwordInput = edtxtPass.getText().toString().trim();
                User user = db.getUserByEmailAndPassword(userInput, passwordInput);

                if (user == null)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                }
                else
                {
                    LoggedInUser.setLoggedInUser(user);
                    String month, year;
                    month = getMonth(Calendar.getInstance().get(Calendar.MONTH));
                    year = Calendar.getInstance().get(Calendar.YEAR) + "";
                    Budget budget = new Budget(db.getCurrentBudget(LoggedInUser.getLoggedInUser().getID(), year, month));
                    LoggedInUser.setCurrentBudget(budget.getAmount());
                    if (LoggedInUser.getCurrentBudget().equals("")) {
                        db.insertBudget(month, year, "0", LoggedInUser.getLoggedInUser().getID());
                        LoggedInUser.setCurrentBudget("0");
                    }
                    else {
                        LoggedInUser.setCurrentBudget(db.getCurrentBudget(LoggedInUser.getLoggedInUser().getID(), year, month).getAmount());
                    }
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

    }
}