package com.matloob.myresponsiveapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matloob.myresponsiveapp.R;
import com.matloob.myresponsiveapp.models.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serar Matloob on 4/30/2020.
 */
public class TracksRecyclerAdapter extends RecyclerView.Adapter<TracksRecyclerAdapter.ItemViewHolder>{
    // Data list of tracks
    private List<Track> tracks = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.track_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textView.setText(tracks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout linearLayout;
        ItemViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_item);
            linearLayout = view.findViewById(R.id.item_linear_layout);
        }
    }
}