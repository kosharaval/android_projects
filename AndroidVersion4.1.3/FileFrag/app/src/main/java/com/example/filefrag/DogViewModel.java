package com.example.filefrag;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DogViewModel extends ViewModel {

    private MutableLiveData<List<Dog>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<Dog>> getColors(){
        if(mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }

        return mutableLiveData;
    }

    public void loadDogInfo(List<Dog> anyList){

        //post the passed data to the mutable live data object
        mutableLiveData.postValue(anyList);

        //Another way to dod it is to set up a handler thread with a delay to post the data
        Handler handler = new Handler();
        handler.postDelayed(()->{
            mutableLiveData.setValue(anyList);
        },1000);

    }
}

