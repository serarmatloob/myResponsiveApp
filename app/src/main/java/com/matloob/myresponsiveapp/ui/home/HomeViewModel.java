package com.matloob.myresponsiveapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.matloob.myresponsiveapp.models.Tag;
import com.matloob.myresponsiveapp.repository.SongsRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private SongsRepository songsRepository;

    public HomeViewModel() {
        songsRepository = new SongsRepository();
    }

    public LiveData<List<Tag>> getTopTagsList() {
        return songsRepository.getTopTags();
    }

    public LiveData<String> getText() {
        return mText;
    }
}