package com.example.appetizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView tvAccName = findViewById(R.id.tvAccName);
        TextView tvAccEmail = findViewById(R.id.tvAccEmail);
        TextView tvAccPhone = findViewById(R.id.tvAccPhone);
        TextView tvAccAdd = findViewById(R.id.tvAccAdd);
        TextView tvAccZip = findViewById(R.id.tvAccZip);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sp.getInt("userId", 0);

        DatabaseHelper dhb = new DatabaseHelper(this);
        User user = dhb.GetUser(userId);

        tvAccName.setText(user.getFullName());
        tvAccEmail.setText(user.getEmail());
        tvAccAdd.setText(user.getAddress());
        tvAccPhone.setText(user.getPhone());
        tvAccZip.setText(user.getZIPCode());

    }

    public void EditAccount(View view){
        TextView tvAccName = findViewById(R.id.tvAccName);
        TextView tvAccEmail = findViewById(R.id.tvAccEmail);
        TextView tvAccPhone = findViewById(R.id.tvAccPhone);
        TextView tvAccAdd = findViewById(R.id.tvAccAdd);
        TextView tvAccZip = findViewById(R.id.tvAccZip);

        Intent signup = new Intent(Account.this, Signup.class);
        signup.putExtra("name", tvAccName.getText().toString());
        signup.putExtra("email",tvAccEmail.getText().toString() );
        signup.putExtra("address", tvAccAdd.getText().toString());
        signup.putExtra("phone", tvAccPhone.getText().toString());
        signup.putExtra("zip", tvAccZip.getText().toString());
        signup.putExtra("type", "edit");
        startActivity(signup);
    }

    public void Logout(View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("userId");
        editor.commit();
        startActivity(new Intent(Account.this, MainActivity.class));
    }
}
