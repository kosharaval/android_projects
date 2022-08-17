package com.example.beerapp.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beerapp.R;
import com.example.beerapp.model.Beer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private ImageView detailsImg;
    private TextView detailsId;
    private TextView detailsName;
    private TextView detailsTag;
    private TextView detailsDescription;
    private TextView detailsFirstBrew;
    private TextView detailsFood;
    private Beer currentBeer;

    private boolean canClickFavorite = true;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> userFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Retrieve beer intent and store to Beer object
        Intent retrievedIntent = getIntent();
        String beerGson = retrievedIntent.getStringExtra("beer");
        Gson gson = new Gson();
        currentBeer = gson.fromJson(beerGson, Beer.class);

        displayInfo();

        //Check favorite
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userFavorite = new HashMap<>();

        getUserFavoriteData();
    }

    private void displayInfo()
    {
        //Declare
        detailsImg = (ImageView)findViewById(R.id.detailsBeerImageView);
        detailsId = (TextView)findViewById(R.id.detailsBeerIdTextView);
        detailsName = (TextView)findViewById(R.id.detailsBeerNameTextView);
        detailsTag = (TextView)findViewById(R.id.detailsBeerTagTextView);
        detailsDescription = (TextView)findViewById(R.id.detailsBeerDescriptionTextView);
        detailsFirstBrew = (TextView)findViewById(R.id.detailsFirstBrewTextView);
        detailsFood = (TextView)findViewById(R.id.detailsBeerFoodTextView);

        //Set value
        Picasso.get().load(currentBeer.getImageLink()).into(detailsImg);
        detailsId.setText("ID: " + currentBeer.getId());
        detailsName.setText(currentBeer.getName());
        detailsTag.setText("\u005c\u0022 " + currentBeer.getTagline() + " \u005c\u0022");
        detailsDescription.setText(currentBeer.getDescription());
        detailsFirstBrew.setText("Brewed: " + currentBeer.getFirstbrew());

        String foodList = "";
        for(int i = 0; i < currentBeer.getFoodPair().size();i++)
        {
            foodList += "\u2022 " + currentBeer.getFoodPair().get(i) + "\n";
        }
        detailsFood.setText(foodList);
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
                            invalidateOptionsMenu();
                        } else {
                            Log.w("No document", "No such document");
                            //Create new
                            addNewFavoriteUser();
                        }
                    } else {
                        Log.d("fail document", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.w("prepare","prepare");
        if(menu != null && userFavorite != null)
        {
            List<String> beerIds = (List<String>)userFavorite.get("beer_list");
            if(beerIds != null)
            {
                MenuItem star = menu.getItem(0);

                if(beerIds.contains(String.valueOf(currentBeer.getId()))) // Match
                {
                    star.setIcon(android.R.drawable.star_on);
                }else
                {
                    star.setIcon(android.R.drawable.star_off);
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.star_favorite:

                if(canClickFavorite)
                {
                    handleFavoriteAction();
                }

                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewFavoriteUser()
    {
        Map<String, Object> newDocument = new HashMap<>();
        newDocument.put("beer_list", Arrays.asList()); // Set empty list

        db.collection("favorite").document(currentUser.getEmail())
                .set(newDocument)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getUserFavoriteData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Fail when create", "fail");
                        Toast.makeText(com.example.beerapp.activities.DetailsActivity.this,
                                getString(R.string.favorite_fail),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void handleFavoriteAction()
    {
        if(currentUser != null)
        {
            final DocumentReference docRef = db.collection("favorite").document(currentUser.getEmail());

            canClickFavorite = false;

            db.runTransaction(new Transaction.Function<Integer>() {
                @Override
                public Integer apply(Transaction transaction) throws FirebaseFirestoreException {

                    List<String> beerIds = (List<String>)userFavorite.get("beer_list");
                    Log.w("What is beerlist", beerIds.size()+"");
                    int addOrRemove = 0;
                    if(beerIds != null)
                    {
                        Log.w("Inside?", "Inside");
                        if(beerIds.contains(String.valueOf(currentBeer.getId())))
                        {
                            Log.w("Yes?", "yes");
                            //Remove favorite
                            beerIds.remove(String.valueOf(currentBeer.getId()));
                            addOrRemove = 1; // REMOVE
                        }else
                        {
                            Log.w("No?", String.valueOf(currentBeer.getId()));
                            //Add favorite
                            beerIds.add(String.valueOf(currentBeer.getId()));
                            addOrRemove = 2; // ADD
                        }
                        Log.w("beer before up", beerIds.toString());
                        transaction.update(docRef, "beer_list", beerIds);
                    }else
                    {
                        Log.w("Null?","null");
                    }
                    return addOrRemove;
                }
            }).addOnSuccessListener(new OnSuccessListener<Integer>() {
                @Override
                public void onSuccess(Integer addOrRemove) {
                    //Success add / remove
                    canClickFavorite = true;

                    String msgAlert = "";
                    if(addOrRemove == 1) // REMOVED
                    {
                        msgAlert = getString(R.string.favorite_success_remove);
                    }else if(addOrRemove == 2) // ADDED
                    {
                        msgAlert = getString(R.string.favorite_success_add);
                    }else
                    {
                        msgAlert = "Something's wrong";
                    }
                    Toast.makeText(com.example.beerapp.activities.DetailsActivity.this,
                            msgAlert,
                            Toast.LENGTH_LONG).show();

                    invalidateOptionsMenu();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Failure
                    canClickFavorite = true;
                    Log.e("Fail when modify", e.getMessage()+"");
                    Toast.makeText(com.example.beerapp.activities.DetailsActivity.this,
                            getString(R.string.favorite_fail) + " " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
