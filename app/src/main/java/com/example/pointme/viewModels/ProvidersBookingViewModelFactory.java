package com.example.pointme.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProvidersBookingViewModelFactory implements ViewModelProvider.Factory{
    private String spId;


    public ProvidersBookingViewModelFactory(String spId) {
        this.spId = spId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ProvidersBookingViewModel(spId);
    }
}
