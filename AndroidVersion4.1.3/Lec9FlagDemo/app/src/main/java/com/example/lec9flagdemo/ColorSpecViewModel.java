package com.example.lec9flagdemo;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ColorSpecViewModel extends ViewModel {

    private MutableLiveData<List<ColorSpec>> ColorList = new MutableLiveData<>();
    public LiveData<List<ColorSpec>> getColors(){
        if(ColorList == null){
            ColorList = new MutableLiveData<>();
        }
        return ColorList;
    }
    public void loadColors(List<ColorSpec>anyList){
        //ColorList.postValue(anyList);//post the passed data to the mutable live data object

        //another way to do it is to set up a handler thread with a delay to post the data
        Handler handler = new Handler();
        handler.postDelayed(()->{
            ColorList.setValue(anyList);
        },1000);
    }
}
