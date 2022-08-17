package com.example.roomdatabasetutorial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button buttonAdd,buttonView,buttonUpdate,buttonDelete;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonView =view.findViewById(R.id.buttonView);
        buttonUpdate =view.findViewById(R.id.buttonUpdate);
        buttonDelete =view.findViewById(R.id.buttonDelete);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAdd:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new AddStudentFragment()).addToBackStack(null).commit();
                break;
            case R.id.buttonView:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ViewStudentsFragment()).addToBackStack(null).commit();
                break;
            case R.id.buttonUpdate:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new UpdateStudentFragment()).addToBackStack(null).commit();
                break;
            case R.id.buttonDelete:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new DeleteStudentFragment()).addToBackStack(null).commit();
                break;
        }
    }
}