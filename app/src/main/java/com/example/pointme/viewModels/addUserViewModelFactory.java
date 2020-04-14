package com.example.pointme.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pointme.models.User;

public class addUserViewModelFactory implements ViewModelProvider.Factory {
    private User user;


    public addUserViewModelFactory(User user) {
        this.user = user;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new addUserViewModel(user);
    }
}
