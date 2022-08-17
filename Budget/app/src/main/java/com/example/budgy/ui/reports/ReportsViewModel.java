package com.example.budgy.ui.reports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportsViewModel extends ViewModel
{
    private MutableLiveData<String> mText;

    public ReportsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is invite fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}