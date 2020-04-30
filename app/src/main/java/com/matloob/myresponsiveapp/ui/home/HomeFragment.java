package com.matloob.myresponsiveapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    // Recycler Adapter
    private TracksRecyclerAdapter tracksRecyclerAdapter;
    // Main activity instance
    private MainActivity mainActivity;
    // Save current song tag
    private String currentTag;

    private RecyclerView recyclerView;

    private ProgressBar progressBar;

    private final static String SELECTED_ITEM_POS_KEY = "selectedItemPosition";
    private final static String CURRENT_TAG_KEY = "currentTag";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Access Shared view model
        homeViewModel = new ViewModelProvider(mainActivity).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
        progressBar = view.findViewById(R.id.progress_bar);

        int pos = 0;
        if (savedInstanceState != null){
            currentTag = savedInstanceState.getString(CURRENT_TAG_KEY);
            pos = savedInstanceState.getInt(SELECTED_ITEM_POS_KEY, -1);
            savedInstanceState.clear();
        }

        observeTagChange(currentTag, pos);
    }

    private void observeTagChange(final String currentTag, final int pos) {
        homeViewModel.getTagName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String tag) {
                Log.i("TAG", "Selected tag: " + tag);
                boolean shouldReload = true;
                setActionBarTitle(tag);
                if (currentTag != null) {
                    shouldReload = !currentTag.equals(tag);
                }
                if(shouldReload) {
                    recyclerView.scrollToPosition(0);
                }
                loadTopTagTracks(tag, shouldReload, pos);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_TAG_KEY,  currentTag);
        outState.putInt(SELECTED_ITEM_POS_KEY,  tracksRecyclerAdapter.getSelectedPosition());
        super.onSaveInstanceState(outState);
    }

    private void setActionBarTitle(String tag) {
        if (mainActivity.getSupportActionBar() != null) {
            mainActivity.getSupportActionBar().setTitle(tag);
        }
    }

    private void loadTopTagTracks(final String tag, final boolean shouldReload, final int pos) {
        homeViewModel.loadTopTrackList(tag);
        homeViewModel.getTopTracksList().removeObservers(getViewLifecycleOwner());
        homeViewModel.getTopTracksList().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                tracksRecyclerAdapter.setTracks(tracks, shouldReload, pos);
                currentTag = tag;
            }
        });

        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                recyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
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
