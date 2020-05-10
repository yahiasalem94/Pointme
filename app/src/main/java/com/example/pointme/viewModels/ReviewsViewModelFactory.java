package com.example.pointme.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ReviewsViewModelFactory implements ViewModelProvider.Factory{
    private String uId;


    public ReviewsViewModelFactory(String uId) {
        this.uId = uId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ReviewsViewModel(uId);
    }
}