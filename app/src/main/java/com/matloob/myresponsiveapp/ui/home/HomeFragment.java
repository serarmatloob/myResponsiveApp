package com.matloob.myresponsiveapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matloob.myresponsiveapp.R;
import com.matloob.myresponsiveapp.adapter.TracksRecyclerAdapter;
import com.matloob.myresponsiveapp.models.Track;
import com.matloob.myresponsiveapp.ui.MainActivity;

import java.util.List;


public class HomeFragment extends Fragment {
    // Shared view model
    private HomeViewModel homeViewModel;

    private TracksRecyclerAdapter tracksRecyclerAdapter;

    // Main activity instance
    private MainActivity mainActivity;

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        // Access Shared view model
        homeViewModel = new ViewModelProvider(mainActivity).get(HomeViewModel.class);

        setupRecyclerView();

        homeViewModel.getTagName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String tag) {
                Log.i("TAG", "onChanged: " + tag);
                setActionBarTitle(tag);
                loadTopTagTracks(tag);
            }
        });

    }

    private void setActionBarTitle(String tag) {
        if(mainActivity.getSupportActionBar() != null) {
            mainActivity.getSupportActionBar().setTitle(tag);
        }
    }

    private void loadTopTagTracks(String tag) {
        homeViewModel.getTopTracksList(tag).observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                tracksRecyclerAdapter.setTracks(tracks);
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_item);
        tracksRecyclerAdapter = new TracksRecyclerAdapter();
        recyclerView.setAdapter(tracksRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
