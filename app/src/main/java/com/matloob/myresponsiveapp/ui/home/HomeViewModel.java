package com.matloob.myresponsiveapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.matloob.myresponsiveapp.models.Tag;
import com.matloob.myresponsiveapp.models.Track;
import com.matloob.myresponsiveapp.repository.SongsRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> tagName = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private static SongsRepository songsRepository = null;

    public HomeViewModel() {
        if(songsRepository == null){
            songsRepository = new SongsRepository();
        }
    }

    public LiveData<List<Tag>> getTopTagsList() {
        return songsRepository.getTopTags();
    }

    public void loadTopTrackList(String tag) {
        songsRepository.loadTopTracks(tag);
    }

    public LiveData<List<Track>> getTopTracksList() {
        return songsRepository.getTopTracks();
    }

    public LiveData<String> getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName.setValue(tagName);
    }

    public LiveData<Boolean> getIsLoading() {
        return songsRepository.getIsLoading();
    }
}