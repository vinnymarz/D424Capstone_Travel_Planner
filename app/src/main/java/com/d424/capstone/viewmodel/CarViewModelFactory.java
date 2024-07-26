package com.d424.capstone.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.d424.capstone.database.Repository;

public class CarViewModelFactory implements ViewModelProvider.Factory {
    private final Repository repository;

    public CarViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CarViewModel.class)) {
            return (T) new CarViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}