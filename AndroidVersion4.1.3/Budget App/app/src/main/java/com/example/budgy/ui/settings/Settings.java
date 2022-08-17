package com.example.budgy.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgy.DatabaseHelper;
import com.example.budgy.Models.CategoryItem;
import com.example.budgy.Models.LoggedInUser;
import com.example.budgy.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Settings extends Fragment {

    private SettingsViewModel mViewModel;

    TextView txtName, txtEmail, txtPassword, txtPhone, addBudget, txtCurrentMonth, txtBudget, addCategory;

    DatabaseHelper db;
    ArrayList<CategoryItem> categoryItemArrayList = new ArrayList<>();

    RecyclerView categoryItemRecyclerView;
    CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        db = new DatabaseHelper(getContext());

        // set user data to textviews
        txtName = root.findViewById(R.id.txtName);
        txtName.setText(LoggedInUser.getLoggedInUser().getFname() + " " + LoggedInUser.getLoggedInUser().getLname());

        txtEmail = root.findViewById(R.id.txtEmail);
        txtEmail.setText(LoggedInUser.getLoggedInUser().getEmail());

        txtPassword = root.findViewById(R.id.txtPassword);
        txtPassword.setText("********");

        txtPhone = root.findViewById(R.id.txtPhone);
        txtPhone.setText(LoggedInUser.getLoggedInUser().getPhone());

        // recyclerview
        categoryItemRecyclerView = root.findViewById(R.id.categoryList);
        categoryItemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter = new CategoryAdapter(categoryItemArrayList);
        categoryItemRecyclerView.setAdapter((categoryAdapter));

        getCategoryItems();

        // display budget data
        txtCurrentMonth = root.findViewById(R.id.txtCurrentMonth);
        txtCurrentMonth.setText(getMonth(Calendar.getInstance().get(Calendar.MONTH)) + ", " + Calendar.getInstance().get(Calendar.YEAR));
        txtBudget = root.findViewById(R.id.txtBudget);
        txtBudget.setText("$" + LoggedInUser.getCurrentBudget());

        // adding budget
        addBudget = root.findViewById(R.id.catBudget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBudget(txtBudget);
            }
        });

        // adding category
        addCategory = root.findViewById(R.id.catExpense);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryItem();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void addCategoryItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View categoryLayout = getLayoutInflater().inflate(R.layout.settings_add_category, null);
        builder.setView(categoryLayout);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText newCategoryInput = categoryLayout.findViewById(R.id.addCategoryInput);

                if (!newCategoryInput.getText().toString().equals("")) {
                    db.insertCategory(newCategoryInput.getText().toString(), "Active", LoggedInUser.getLoggedInUser().getID());
                    Toast.makeText(getContext(), newCategoryInput.getText().toString() + " category added", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Category discarded, cannot be empty", Toast.LENGTH_SHORT).show();

                getCategoryItems();
            }
        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addBudget(TextView txtBudget) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        final View budgetLayout = getLayoutInflater().inflate(R.layout.settings_add_budget, null);
        builder.setView(budgetLayout);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText newBudgetInput = budgetLayout.findViewById(R.id.addCategoryInput);

                if (!newBudgetInput.getText().toString().equals("")) {
                    if(db.updateBudget(getMonth(Calendar.getInstance().get(Calendar.MONTH)), Calendar.getInstance().get(Calendar.YEAR) + "", newBudgetInput.getText().toString(), LoggedInUser.getLoggedInUser().getID())>1)
                        Log.d("ok", "updated");
                    LoggedInUser.setCurrentBudget(newBudgetInput.getText().toString());
                    txtBudget.setText("$" + newBudgetInput.getText().toString());
                    Toast.makeText(getContext(), "Budget of $" + newBudgetInput.getText().toString() + " added", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Budget discarded, cannot be empty", Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String getMonth(int month){
        String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthArray[month];
    }

    public void getCategoryItems()
    {
        List<CategoryItem> categoryItemList = null;
        categoryItemList = db.getCategoryNameByUserId(LoggedInUser.getLoggedInUser().getID());
        categoryItemArrayList.clear();
        categoryItemArrayList.addAll(categoryItemList);

        if(!categoryItemArrayList.isEmpty()) {
            categoryAdapter.notifyDataSetChanged();
        }
    }
}