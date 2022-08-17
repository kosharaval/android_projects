package com.example.budgy.ui.fixed;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FixedIncomeExpenseViewModel extends  ViewModel {
    private MutableLiveData<String> mText;



    public FixedIncomeExpenseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fixed income fragment");




    }

    public LiveData<String> getText() {
        return mText;
    }
}
