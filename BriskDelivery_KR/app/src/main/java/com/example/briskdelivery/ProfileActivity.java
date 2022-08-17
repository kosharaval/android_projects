package com.example.briskdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button logoutBtn;
    TextView userName,userEmail;
    ImageView profileImage;
    EditText phoneNum,address;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String idPass;
    String emailll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button save = findViewById(R.id.save);
        phoneNum = findViewById(R.id.phoneNum);
        address = findViewById(R.id.address);
        logoutBtn=findViewById(R.id.logoutBtn);
        userName=findViewById(R.id.name);
        userEmail=findViewById(R.id.email);
        profileImage=findViewById(R.id.profileImage);
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        phoneNum.setText(sp.getString("phone",""));
        address.setText(sp.getString("address",""));


        save.setOnClickListener((View v) -> {
            User newUser = new User(userName.getText().toString(),
                    userEmail.getText().toString(),
                    phoneNum.getText().toString(),
                    address.getText().toString(),
                    idPass,sp.getInt("userId",0));
            DatabaseHelper dbh = new DatabaseHelper(this);

            boolean emailExists = dbh.checkEmailExists(userEmail.getText().toString());
            SharedPreferences.Editor editor = sp.edit();

            if(emailExists){
                dbh.updateUser(newUser);
                Intent options = new Intent(ProfileActivity.this, Buttons.class);
                editor.putString("phone", phoneNum.getText().toString());
                editor.putString("address", address.getText().toString());
                editor.commit();
                this.finish();
                startActivity(options);
            }
            else{
                int id = (int) dbh.addUser(newUser);
                if (id == -1) {
                    Toast.makeText(this, "Please check information", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Information saved.", Toast.LENGTH_LONG).show();
                    Intent options = new Intent(ProfileActivity.this, Buttons.class);
                    editor.putInt("userId", id);
                    editor.putString("phone", phoneNum.getText().toString());
                    editor.putString("address", address.getText().toString());
                    editor.commit();
                    this.finish();
                    startActivity(options);
                }


            }

        });

        logoutBtn.setOnClickListener(view -> Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                status -> {
                    if (status.isSuccess()){
                        gotoMainActivity();
                    }else{
                        Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                    }
                }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(googleSignInResult -> handleSignInResult(googleSignInResult));
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            String name =account.getDisplayName();
            String email =account.getEmail();

            System.out.println(name+"  "+email);

            userName.setText(name);
            userEmail.setText(email);
            idPass = account.getId().toString();
            try{
                Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found", Toast.LENGTH_LONG).show();
            }

        }else{
            gotoMainActivity();
        }
    }

    private void gotoMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}