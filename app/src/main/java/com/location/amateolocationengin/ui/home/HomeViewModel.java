package com.location.amateolocationengin.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public HomeViewModel() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("Louez votre matériel au meilleur prix\n Simple, rapide et sans stress!");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}