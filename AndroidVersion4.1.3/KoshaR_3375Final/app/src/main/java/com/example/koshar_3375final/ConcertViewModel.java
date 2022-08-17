package com.example.koshar_3375final;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ConcertViewModel extends ViewModel {

    private MutableLiveData<List<Concert>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<Concert>> getConcertInfo(){
        if(mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }

        return mutableLiveData;
    }

    public void loadConcertInfo(List<Concert> anyList){

        //post the passed data to the mutable live data object
        mutableLiveData.postValue(anyList);

        //Another way to dod it is to set up a handler thread with a delay to post the data
        Handler handler = new Handler();
        handler.postDelayed(()->{
            mutableLiveData.setValue(anyList);
        },1000);

    }
}
