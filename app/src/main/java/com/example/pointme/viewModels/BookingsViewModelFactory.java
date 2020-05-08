package com.example.pointme.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BookingsViewModelFactory implements ViewModelProvider.Factory{
    private String crId;


    public BookingsViewModelFactory(String crId) {
        this.crId = crId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new BookingsViewModel(crId);
    }
}
