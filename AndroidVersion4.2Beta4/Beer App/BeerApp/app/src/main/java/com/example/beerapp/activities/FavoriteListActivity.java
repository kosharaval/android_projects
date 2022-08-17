package com.example.beerapp.activities;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerapp.R;
import com.example.beerapp.adapters.BeerAdapter;
import com.example.beerapp.interfaces.PunkResponse;
import com.example.beerapp.model.Beer;
import com.example.beerapp.services.PunkAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteListActivity extends AppCompatActivity implements PunkResponse {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Beer> beerList;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> userFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        // Display back button in action bar
       ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userFavorite = new HashMap<>();

        beerList = new ArrayList<>();

        //Declare recycler view and set adapter
        recyclerView = (RecyclerView)findViewById(R.id.beerRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BeerAdapter(this, beerList);
        recyclerView.setAdapter(mAdapter);

        getUserFavoriteData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getUserFavoriteData()
    {
        if(currentUser != null)
        {
            DocumentReference docRef = db.collection("favorite").document(currentUser.getEmail());

            Log.w("user here", currentUser.getEmail()+ "");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.w("DocumentSnapshot", "DocumentSnapshot data: " + document.getData());
                            userFavorite = document.getData();
                            handleResult();
                        } else {
                            Log.w("No document", "No such document");
                        }
                    } else {
                        Log.d("fail document", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    private void handleResult()
    {
        if(userFavorite != null)
        {
            List<String> beerIds = (List<String>)userFavorite.get("beer_list");
            String base_url = com.example.beerapp.activities.MainActivity.BASE_URL + "?ids=";

            //Concat url to retrieve beers
            if(beerIds != null)
            {
                for(int i = 0; i < beerIds.size(); i++)
                {
                    String eachId = beerIds.get(i);
                    if(i == 0)
                    {
                        base_url += eachId;
                    }else
                    {
                        base_url += "|" + eachId;
                    }
                }
                if(beerIds.size() > 0) // If there is something stored then call API
                {
                    new PunkAPI(com.example.beerapp.activities.FavoriteListActivity.this).call(base_url);
                }
            }

        }
    }

    @Override
    public void onFinish(String result) {

        try
        {
            JSONArray jsonArray = new JSONArray(result);

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
}
