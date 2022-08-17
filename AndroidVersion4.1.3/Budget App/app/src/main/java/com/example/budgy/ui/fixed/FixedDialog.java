package com.example.budgy.ui.fixed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.budgy.R;
import com.google.android.material.textfield.TextInputLayout;

public class FixedDialog extends DialogFragment {

    private TextInputLayout editTextName;
    private TextInputLayout editTextAmount;
    private FixedDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String amountType = getArguments().getString("type");
        String title;

        if(amountType.equals("E"))
        {
            title="Add an Expense";
        }else{
            title="Add an Income";
        }

        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fixed,null);

        builder.setView(view).setTitle(title).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editTextName.getEditText().getText().toString().trim();
                String amount = editTextAmount.getEditText().getText().toString().trim();

                listener.apply(name,Double.parseDouble(amount),amountType.charAt(0));
            }
        });

        editTextName = view.findViewById(R.id.textinput_fixedtitle);
        editTextAmount = view.findViewById(R.id.textinput_fixedamount);

        return builder.create();


    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        try {
            Fragment fragment = getTargetFragment();
            listener = (FixedDialogListener) fragment;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement FixedDialogListener");
        }
    }


    public interface  FixedDialogListener{
        void apply(String name, Double amount, Character expenseType);

    }
}
