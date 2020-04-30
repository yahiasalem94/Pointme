package com.example.pointme.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pointme.models.User;

public class ProvidersViewModelFactory implements ViewModelProvider.Factory {
    private String service;


    public ProvidersViewModelFactory(String service) {
        this.service = service;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ProvidersViewModel(service);
    }
}
