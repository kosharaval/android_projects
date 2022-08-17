package com.example.budgy.ui.dashboard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.budgy.DatabaseHelper;
import com.example.budgy.Models.CategoryItem;
import com.example.budgy.Models.LoggedInUser;
import com.example.budgy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AddTransactionDialog extends DialogFragment implements View.OnClickListener
{
    private Spinner spinnerCategoryType;
    private Spinner spinnerCategoryName;
    private EditText txtCategoryAmount;
    private TextView txtUploadURL;
    private TextView txtDate;
    private Button btnUploadReceipt;
    private AddCategoryDialogListener categoryListener;
    private String selectedImage;
    private EditText txtTitle;

    private static final int RESULT_LOAD_IMAGE = 1;
    final Calendar myCalendar = Calendar.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        String loggedInUserId = LoggedInUser.getLoggedInUser().getID();
        String title = "Add new entry";
        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_transaction,null);

        spinnerCategoryType = view.findViewById(R.id.spCategoryType);
        spinnerCategoryName = view.findViewById(R.id.spCategoryName);
        txtCategoryAmount = view.findViewById(R.id.txtCategoryAmount);
        txtUploadURL = view.findViewById(R.id.txtUploadURL);
        txtDate = view.findViewById(R.id.txtDate);
        btnUploadReceipt = view.findViewById(R.id.btnUploadReceipt);
        txtTitle = view.findViewById(R.id.txtTitle);

        builder.setView(view).setTitle(title)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                View focusView = null;
                String type = spinnerCategoryType.getSelectedItem().toString().trim();
                String name = spinnerCategoryName.getSelectedItem().toString().trim();
                String amount = txtCategoryAmount.getText().toString().trim();
                String imageURL = txtUploadURL.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String title = txtTitle.getText().toString().trim();
                categoryListener.apply(name,amount,type,imageURL,date,title);


            }
        });

        List<String> spType = new ArrayList<>();
        spType.add("Income");
        spType.add("Expense");
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,spType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterType.notifyDataSetChanged();
        spinnerCategoryType.setAdapter(adapterType);

        DatabaseHelper db = new DatabaseHelper(getActivity());
        List<CategoryItem> list = db.getCategoryNameByUserId(loggedInUserId);
        List<String> list1 = new ArrayList<>();
        for(CategoryItem item : list)
        {
            list1.add(item.getCategoryName());
        }
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list1);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory.notifyDataSetChanged();
        spinnerCategoryName.setAdapter(adapterCategory);

        btnUploadReceipt.setOnClickListener(this);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach( Context context)
    {
        super.onAttach(context);
        try {
            Fragment fragment = getTargetFragment();
            categoryListener = (AddCategoryDialogListener) fragment;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddCategoryDialogListener");
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    public interface AddCategoryDialogListener
    {
        void apply(String name, String amount, String type, String imageURL,String date,String title);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null)
        {
            selectedImage = data.getData().toString();
            if(selectedImage.isEmpty())
            {
                txtUploadURL.setText(" ");
            }
            else {
                txtUploadURL.setText(selectedImage);
            }
        }


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
}
