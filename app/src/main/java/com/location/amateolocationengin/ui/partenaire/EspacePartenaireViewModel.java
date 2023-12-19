package com.location.amateolocationengin.ui.partenaire;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EspacePartenaireViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public EspacePartenaireViewModel() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}
