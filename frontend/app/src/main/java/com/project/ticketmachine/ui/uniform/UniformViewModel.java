package com.project.ticketmachine.ui.uniform;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UniformViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UniformViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}