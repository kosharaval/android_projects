package com.example.budgy.ui.invite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InviteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InviteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is invite fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}