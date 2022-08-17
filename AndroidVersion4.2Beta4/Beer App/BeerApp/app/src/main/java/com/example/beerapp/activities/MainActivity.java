package com.example.beerapp.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerapp.R;
import com.example.beerapp.adapters.BeerAdapter;
import com.example.beerapp.interfaces.PunkResponse;
import com.example.beerapp.model.Beer;
import com.example.beerapp.services.PunkAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PunkResponse {

    public static final String BASE_URL = "https://api.punkapi.com/v2/beers";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private EditText searchField;
    private Button searchBtn;
    private Button prevBtn;
    private Button nextBtn;
    private List<Beer> beerList;

    private int currentPage = 0;
    private boolean maxPage = false;

    private TextView loginUserDisplay;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   final AppCompatActivity context = this;
    //    final PunkResponse pr = this;

        beerList = new ArrayList<>();
        searchField = (EditText)findViewById(R.id.searchField);

        //Declare buttons and set listener
        searchBtn = (Button)findViewById(R.id.searchBtn);
        prevBtn = (Button)findViewById(R.id.prevBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beerList.clear();
                String searchText = searchUrl("empty");
                new PunkAPI(com.example.beerapp.activities.MainActivity.this).call(searchText);

                //Display buttons on bottom
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.VISIBLE);

                //Hide keyboard when click Search
                View view = findViewById(android.R.id.content);
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beerList.clear();
                String searchText = searchUrl("next");

                if(currentPage > 0)
                {
                    if(!maxPage)
                    {
                        new PunkAPI(com.example.beerapp.activities.MainActivity.this).call(searchText);
                    }

                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beerList.clear();

                String searchText = searchUrl("prev");

                if(currentPage > 0)
                {
                    new PunkAPI(com.example.beerapp.activities.MainActivity.this).call(searchText);
                }

            }
        });

        //Declare recycler view and set adapter
        recyclerView = (RecyclerView)findViewById(R.id.beerRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BeerAdapter(this, beerList);
        recyclerView.setAdapter(mAdapter);

        nextBtn.setVisibility(View.INVISIBLE);
        prevBtn.setVisibility(View.INVISIBLE);

        loginUserDisplay = (TextView)findViewById(R.id.login_user_display);

        mAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                invalidateOptionsMenu();

                Log.w("firebaselistener","firebaselistener");

            }
        };


    }

    private String searchUrl(String type)
    {
        String searchResult = "";
        String textSearched = searchField.getText().toString();
        textSearched.replace(" ", "_");

        if(type == "empty")
        {
            beerList.clear();
            if(searchField.getText().toString().equals("")) // Empty
            {
                Log.w("call empty", "call empty");
                searchResult = BASE_URL;
            }else
            {
                searchResult = BASE_URL + "?beer_name=" + textSearched;
            }
            currentPage = 1;

        }else if(type == "next")
        {
            if(!maxPage)
            {
                currentPage++;
            }

            if(searchField.getText().toString().equals("")) // Empty
            {
                searchResult = BASE_URL + "?page=" + currentPage;
            }else
            {
                searchResult = BASE_URL + "?beer_name=" + textSearched + "&page=" + currentPage;
            }
        }else
        {
            currentPage--;

            if(currentPage <= 1)
            {
                currentPage = 1;
            }

            if(searchField.getText().toString().equals("")) // Empty
            {
                searchResult = BASE_URL + "?page=" + currentPage;
            }else
            {
                searchResult = BASE_URL + "?beer_name=" + textSearched + "&page=" + currentPage;
            }
        }

        return searchResult;
    }

    @Override
    public void onFinish(String result) {

        try
        {
            JSONArray jsonArray = new JSONArray(result);
            if(jsonArray.length() == 0)
            {
                maxPage = true;
            }else
            {
                maxPage = false;
            }

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                Beer beer = Beer.convertToBeerObject(obj);
                beerList.add(beer);
            }
            mAdapter.notifyDataSetChanged();

        }catch(JSONException jsex)
        {
            Log.e("Exception onFinish", jsex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.w("inflate menu","inflatemenu");
        getMenuInflater().inflate(R.menu.sign_in_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.w("optionselected","optionselected");
        switch(item.getItemId())
        {
            case R.id.signin_item:
                //open sign up page
                Intent intent = new Intent(com.example.beerapp.activities.MainActivity.this, SigninActivity.class);
                startActivity(intent);
                return true;
            case R.id.signout_item:
                mAuth.signOut();
                Toast.makeText(com.example.beerapp.activities.MainActivity.this,
                        getString(R.string.signout_success),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.favorite_item:
                Intent intentFav = new Intent(com.example.beerapp.activities.MainActivity.this, FavoriteListActivity.class);
                startActivity(intentFav);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(menu != null)
        {
            MenuItem signinItem = menu.getItem(0);
            MenuItem favoriteItem = menu.getItem(1);
            MenuItem signoutItem = menu.getItem(2);

            if (currentUser == null) { // No user signin
                signinItem.setVisible(true);
                favoriteItem.setVisible(false);
                signoutItem.setVisible(false);
                loginUserDisplay.setText("");
            }else{ // If signed in
                signinItem.setVisible(false);
                favoriteItem.setVisible(true);
                signoutItem.setVisible(true);
                loginUserDisplay.setText("Logged in as: " + currentUser.getEmail() + "");
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        Log.w("Resume Main Activity", "Resume");

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
           mAuth.removeAuthStateListener(authListener);
        }
    }
}
