package com.example.pointme.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pointme.models.User;

public class WorkshopViewModelFactory implements ViewModelProvider.Factory {
    private String uId;


    public WorkshopViewModelFactory(String uId) {
        this.uId = uId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WorkshopViewModel(uId);
    }
}
