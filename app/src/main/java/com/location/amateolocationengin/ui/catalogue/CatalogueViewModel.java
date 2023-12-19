package com.location.amateolocationengin.ui.catalogue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatalogueViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CatalogueViewModel() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}
