package com.example.budgy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgy.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity
{
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        TextView txtPD = (TextView)findViewById(R.id.txtPersD);
        EditText edtxtFName = (EditText)findViewById(R.id.edtxtName);
        EditText edtxtLstName = (EditText)findViewById(R.id.edtxtLstName);
        EditText edtxtEmail = (EditText)findViewById(R.id.edtxtEmail);
        EditText edtxtPhno = (EditText)findViewById(R.id.edtxtPhno);
        EditText edtxtRegPass = (EditText)findViewById(R.id.edtxtRegPass);
        EditText edtxtConPass = (EditText)findViewById(R.id.edtxtConPass);
        RadioGroup rdGen = (RadioGroup)findViewById(R.id.rdGrp);
        RadioButton Female = (RadioButton)findViewById(R.id.rdFem);
        RadioButton Male = (RadioButton)findViewById(R.id.rdMale);
        int radioId = rdGen.getCheckedRadioButtonId();

        db = new DatabaseHelper(this);

        Button btnRegSub = (Button)findViewById(R.id.btnRegSub);
        btnRegSub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean isValid = true;
                List<EditText> listControls = new ArrayList<>();
                listControls.add(edtxtFName);
                listControls.add(edtxtLstName);
                listControls.add(edtxtEmail);
                listControls.add(edtxtPhno);
                listControls.add(edtxtRegPass);
                listControls.add(edtxtConPass);
                for(EditText e : listControls)
                {
                    if(e.getText().toString().trim().length()==0)
                    {
                        e.setError(e.getHint().toString().replace("Enter","  ") + " is invalid");
                        isValid = false;
                    }
                }
                if(!isValidEmail(edtxtEmail.getText().toString()))
                {
                   edtxtEmail.setError("Invalid email");
                   isValid = false;
                }
                if(!edtxtRegPass.getText().toString().trim().equals(edtxtConPass.getText().toString().trim()))
                {
                    edtxtConPass.setError("Password does not match");
                    isValid = false;
                }
//                if(radioId == -1)
//                {
//                    Toast.makeText(Registration.this,"Please select gender",Toast.LENGTH_LONG).show();
//                    isValid = false;
//                }
                //else{ isValid = true; }
                if(isValid)
                {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(radioId);
                    User user = new User(
                            edtxtFName.getText().toString(),
                            edtxtLstName.getText().toString(),
                            edtxtEmail.getText().toString(),
                            edtxtPhno.getText().toString(),
                            edtxtRegPass.getText().toString(),
                            "female");
                    long x = db.insertUser(user);
                    if (x > 0)
                    {
                        finish();
                        startActivity(new Intent(Registration.this, LoginActivity.class));
                    }
                }
            }
        });
    }
    public boolean isValidEmail(String email)
    {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}