package com.example.budgy.ui.invite;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgy.LoginActivity;
import com.example.budgy.R;
import com.example.budgy.ui.fixed.FixedIncomeExpenseViewModel;

public class Invite extends Fragment {

    private InviteViewModel inviteViewModel;

    public static Invite newInstance() {
        return new Invite();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        inviteViewModel = new ViewModelProvider(this).get(InviteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_invite, container, false);
        TextView txtlink = root.findViewById(R.id.txtlink);
        Button btnInvite = root.findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String text = txtlink.getText().toString();
                Intent  mSharingIntent = new Intent(Intent.ACTION_SEND);
                mSharingIntent.setType("text/plain");
                mSharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Add Subject here");
                mSharingIntent.putExtra(Intent.EXTRA_TEXT, "https://budgylink");
                startActivity(Intent.createChooser(mSharingIntent,"Share via"));
            }
        });
        return root;
    }
}
